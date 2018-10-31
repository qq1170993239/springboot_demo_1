package com.lix.study.config.shiro.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lix.study.login.dao.LoginUserDao;
import com.lix.study.login.dto.LoginUserEntity;
import com.lix.study.login.dto.PermissionEntity;
import com.lix.study.login.dto.RoleEntity;

@Component
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private LoginUserDao loginUserDao;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		// 获取session中的用户
		LoginUserEntity user = (LoginUserEntity) principal.fromRealm(this.getClass().getName()).iterator().next();
		Set<String> allPermission = new HashSet<>();
		for (RoleEntity role : user.getRole()) {
			Set<PermissionEntity> permissions = role.getPermissions();
			for (PermissionEntity permission : permissions) {
				allPermission.add(permission.getPermissionValue());
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 将权限放入shiro中.
		info.addStringPermissions(allPermission);
		return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户输入的token
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		
		LoginUserEntity user = loginUserDao.findUserByName(utoken.getUsername());
		// 放入shiro.调用CredentialsMatcher检验密码
		return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
	}

}
