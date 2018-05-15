package com.supershen.example.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.config.ObjectFactory;
import org.sitemesh.config.properties.PropertiesFilterConfigurator;
import org.sitemesh.config.xml.XmlFilterConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * SiteMesh filter configration.
 */
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {
    private final static Logger logger = Logger.getLogger(ConfigurableSiteMeshFilter.class.getName());

    // Final(ish) fields. These are intialized during init() and will never change. Can't be marked final
    // as they can't be initia	lized in constructor.
    private boolean initialized;
    private File xmlConfigFile;
    private FilterConfig filterConfig;
    private Map<String,String> configProperties;
    private boolean autoReload;

    // Fields that may change during the lifecycle of the Filter (if enableReload==true).
    // Modifications of these should be synchronized on configLock (below).
    private volatile Filter filter;
    private volatile long timestampOfXmlFileAtLastLoad;

    // See above.
    private final Object configLock = new Object();

    public static final String CONFIG_FILE_PARAM = "configFile";
    public static final String CONFIG_FILE_DEFAULT = "/WEB-INF/sitemesh3.xml";

    public static final String AUTO_RELOAD_PARAM = "autoReload";
    public static final boolean AUTO_RELOAD_DEFAULT = true;
    
    /**
     * 过滤不需要meshFilter的资源
     */
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/WEB-INF/layouts/default.jsp");
        builder.addExcludedPath("/static/*");
        builder.addExcludedPath("/login*");
    }
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        configProperties = getConfigProperties(filterConfig);
        autoReload = getAutoReload();
        logger.config("Auto reloading " + (autoReload ? "enabled" : "disabled"));

        synchronized (configLock) {
            deployNewFilter(setup());
        }
        initialized = true;
    }
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	if (!initialized) {
            throw new ServletException(getClass().getName() + ".init(FilterConfig) was not called");
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
            // 得到当前访问路径
     		String path = request.getServletPath();
        	reloadIfNecessary();
        	
       
 		//预览word文档
        if(path.lastIndexOf(".doc")!=-1 || path.lastIndexOf(".xlsx")!=-1 || path.lastIndexOf(".ppt")!=-1){
        	response.sendRedirect(request.getContextPath()+ "/auxiliary/zsk/toPreview?url="+path);
        	return;
        }
        filter.doFilter(servletRequest, servletResponse, filterChain);
        
    }
    
    public void destroy() {
        synchronized (configLock) {
            if (filter != null) {
                filter.destroy();
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
	protected Map<String, String> getConfigProperties(FilterConfig filterConfig) {
        Map<String, String> initParams = new HashMap<String, String>();
        for (Enumeration initParameterNames = filterConfig.getInitParameterNames(); initParameterNames.hasMoreElements();) {
            String key = (String) initParameterNames.nextElement();
            String value = filterConfig.getInitParameter(key).trim();
            initParams.put(key, value);
        }
        return initParams;
    }

    protected Filter setup() throws ServletException {
        ObjectFactory objectFactory = getObjectFactory();
        SiteMeshFilterBuilder builder = new SiteMeshFilterBuilder();

        new PropertiesFilterConfigurator(objectFactory, configProperties)
                .configureFilter(builder);

        new XmlFilterConfigurator(getObjectFactory(),
                loadConfigXml(filterConfig, getConfigFileName()))
                .configureFilter(builder);

        applyCustomConfiguration(builder);

        return builder.create();
    }

    /**
     * Determine if a reload is required. Override this to change the behavior.
     */
    protected boolean reloadRequired() {
        return timestampOfXmlFileAtLastLoad != xmlConfigFile.lastModified();
    }

    /**
     * Whether the config file should be monitored for changes and automatically reloaded.
     */
    protected boolean getAutoReload() {
        String autoReload = configProperties.get(AUTO_RELOAD_PARAM);
        if (autoReload == null) {
            return AUTO_RELOAD_DEFAULT;
        } else {
            String lower = autoReload.toLowerCase();
            return lower.equals("1") || lower.equals("true") || lower.equals("yes");
        }
    }

    /**
     * Gets the SiteMesh XML config file name.
     * Looks for a 'config' property in the Filter init-params. 
     * If not found, defaults to '/WEB-INF/sitemesh3.xml'.
     */
    protected String getConfigFileName() {
        String config = configProperties.get(CONFIG_FILE_PARAM);
        if (config == null || config.length() == 0) {
            config = CONFIG_FILE_DEFAULT;
        }
        return config;
    }

    protected ObjectFactory getObjectFactory() {
        return new ObjectFactory.Default();
    }

    /**
     * Load the XML config file. Will try a number of locations until it finds the file.
     * <pre>
     * - Will first search for a file on disk relative to the root of the web-app.
     * - Then a file with the absolute path.
     * - Then a file as a resource in the ServletContext (allowing for files embedded in a .war file).
     * - If none of those find the file, null will be returned.
     * </pre>
     */
    protected Element loadConfigXml(FilterConfig filterConfig, String configFilePath) throws ServletException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            xmlConfigFile = new File(configFilePath);

            ServletContext servletContext = filterConfig.getServletContext();

            if (servletContext.getRealPath(configFilePath) != null) {
                xmlConfigFile = new File(servletContext.getRealPath(configFilePath));
            }

            if (xmlConfigFile.canRead()) {
                try {
                    timestampOfXmlFileAtLastLoad = xmlConfigFile.lastModified();
                    logger.config("Loading SiteMesh 3 config file: " + xmlConfigFile.getAbsolutePath());
                    Document document = documentBuilder.parse(xmlConfigFile);
                    return document.getDocumentElement();
                } catch (SAXException e) {
                    throw new ServletException("Could not parse " + xmlConfigFile.getAbsolutePath(), e);
                }
            } else {
                InputStream stream = servletContext.getResourceAsStream(configFilePath);
                if (stream == null) {
                    logger.config("No config file present - using defaults and init-params. Tried: "
                            + xmlConfigFile.getAbsolutePath() + " and ServletContext:" + configFilePath);
                    return null;
                }
                try {
                    logger.config("Loading SiteMesh 3 config file from ServletContext " + configFilePath);
                    Document document = documentBuilder.parse(stream);
                    return document.getDocumentElement();
                } catch (SAXException e) {
                    throw new ServletException("Could not parse " + configFilePath + " (loaded by ServletContext)", e);
                } finally {
                    stream.close();
                }
            }

        } catch (IOException e) {
            throw new ServletException(e);
        } catch (ParserConfigurationException e) {
            throw new ServletException("Could not initialize DOM parser", e);
        }
    }

    protected void reloadIfNecessary() throws ServletException {
        // TODO: Allow finer grained control of reload strategies:
        // - don't check file timestamp on every single request (once per N seconds).
        // - periodically check in background, instead of blocking request threads.

        if (autoReload && reloadRequired()) {
            synchronized (configLock) { // Double check lock for performance (works in JDK5+, with volatile items).
                if (reloadRequired()) {
                    deployNewFilter(setup());
                }
            }
        }
    }

    protected void deployNewFilter(Filter newFilter) throws ServletException {
        Filter oldFilter = filter;
        if (newFilter == null) {
            throw new ServletException("Cannot deploy null filter");
        }
        newFilter.init(filterConfig);
        filter = newFilter;
        if (oldFilter != null) {
            oldFilter.destroy();
        }
    }


}
