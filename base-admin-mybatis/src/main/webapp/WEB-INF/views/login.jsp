<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>登录验证</title>
	  <meta name="renderer" content="webkit">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
	  <link rel="stylesheet" href="${ctx}/static/layuiadmin/layui/css/layui.css" media="all">
	  <link rel="stylesheet" href="${ctx}/static/layuiadmin/style/admin.css" media="all">
	  <link rel="stylesheet" href="${ctx}/static/layuiadmin/style/login.css" media="all">
  	<script type="text/javascript">
		function FillContent(UserName, PassWord, url){
			$("#username").val(UserName);
			$("#password").val(PassWord);
			$.ajax({
			    type: "post",
			    url: "${ctx}/login",
			    data: {username:UserName, password:PassWord},
			    success: function(data){
			  	  window.location.href=url;
			    }
			});
		}
	</script>
</head>
<body class="hold-transition login-page" onload="inputForm.username.focus();">
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>管理平台</h2>
        <%
				String error = (String) 
					request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
				if (error != null) {
			%> 
        <p style="color: red;">登录失败，帐号或密码错误！</p>
       <%
				}
			%>
      </div>
      <form id="inputForm" action="${ctx}/login" method="post">
	      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
	          <input type="text" name="username" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
	          <input type="password" name="password" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
	        </div>
	        <div class="layui-form-item">
	          <div class="layui-row">
	            <!--  <div class="layui-col-xs7">
	              <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
	              <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
	            </div>
	            <div class="layui-col-xs5">
	              <div style="margin-left: 10px;">
	                <img src="https://www.oschina.net/action/user/captcha" class="layadmin-user-login-codeimg" id="LAY-user-get-vercode">
	              </div>
	            </div>
	            -->
	          </div>
	        </div>
	        <div class="layui-form-item" style="margin-bottom: 20px;">
	          <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
	          <a href="forget.html" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>
	        </div>
	        <div class="layui-form-item">
	          <button type="submit" class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-login-submit">登 入</button>
	        </div>
	        <div class="layui-trans layui-form-item layadmin-user-login-other">
	          <label>社交账号登入</label>
	          <a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
	          <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
	          <a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>
	          
	          <a href="reg.html" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
	        </div>
	      </div>
	    </form>
	    </div>
    
    <div class="layui-trans layadmin-user-login-footer">
      
      <p>© 2018 <a href="http://www.layui.com/" target="_blank">layui.com</a></p>
      <p>
        <span><a href="http://www.layui.com/admin/#get" target="_blank">获取授权</a></span>
        <span><a href="http://www.layui.com/admin/pro/" target="_blank">在线演示</a></span>
        <span><a href="http://www.layui.com/admin/" target="_blank">前往官网</a></span>
      </p>
    </div>
  </div>
	<!-- /.login-box -->
	<!-- jQuery 2.2.3 -->
	<script src="${ctx}/static/jquery/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/static/layuiadmin/layui/layui.js"></script> 
</body>
</html>
