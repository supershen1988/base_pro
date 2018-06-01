package com.supershen.example.config.shiro;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;
import com.supershen.example.entity.User;
import com.supershen.example.service.UserService;
import com.supershen.example.utils.Encodes;
/**
 * shiro授权回调类
 * @author gshen
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected UserService userService;
	

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		logger.info(String.format("用户[%s]尝试登录,...", token.getUsername()));

		User user = userService.findByUsernameAndState(token.getUsername());
		if (user == null) {
			return null;
		}
		//获取盐值
		byte[] salt = Encodes.decodeHex(user.getSalt());
		ShiroUser shiroUser = new ShiroUser();
		shiroUser.setId(user.getId());
		shiroUser.setUsername(user.getUsername());
		shiroUser.setNickname(user.getNickname());
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),ByteSource.Util.bytes(salt), getName());

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Set<String> resourceNames = userService.findResourceNamesByUserId(shiroUser.getId());

		info.addStringPermissions(resourceNames);

		logger.info(String.format("用户[%s]登录成功,获取权限集合:%s", shiroUser.getUsername(), resourceNames.toString()));

		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		
		//SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
		
		 matcher.setHashIterations(UserService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Integer id;
		public String username;
		
		public String nickname;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return username;
		}

		/**
		 * 重载hashCode,只计算username;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(username);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (username == null) {
				if (other.username != null) {
					return false;
				}
			} else if (!username.equals(other.username)) {
				return false;
			}

			return true;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
}
