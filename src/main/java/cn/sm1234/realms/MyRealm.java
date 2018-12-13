package cn.sm1234.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权...");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//基于资源的授权
		info.addStringPermission("product:add");
		//基于角色的授权
		info.addRole("admin");
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证...");
		//模拟数据库数据
		String name = "jack";
		String password = "1234";
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		
		if(!name.equals(token.getUsername())) {
			return null;//login会抛出UnknownAccountException异常。
		}
		return new SimpleAuthenticationInfo(name, password, "");//第一、二个参数可以自己指定返回值。
	}

}
