package cn.sm1234.realms;

import java.util.List;

import cn.sm1234.domain.User;
import cn.sm1234.service.UserService;

import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.*;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.druid.util.StringUtils;

public class MyRealm extends AuthorizingRealm{
	@Resource
	private UserService userService;
//	授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权...");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
/*		//基于资源的授权
		info.addStringPermission("product:add");
		//基于角色的授权
		info.addRole("admin");*/
//		获取当前用户的pricipal
		User dbUser = (User)SecurityUtils.getSubject().getPrincipal();
//		查询当前用户拥有的资源授权码。
		List<String> perms = userService.findPermissionByUserId(dbUser.getId());
		if(perms!=null) {
//			遍历授权
			for(String perm:perms) {
				if(!StringUtils.isEmpty(perm)) {
					info.addStringPermission(perm);
				}
			}
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证...");
		//模拟数据库数据
//		String name = "jack";
//		String password = "1234";
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		
//		if(!name.equals(token.getUsername())) {
//			return null;//login会抛出UnknownAccountException异常。
//		}
		User dbUser = userService.findByName(token.getUsername());//查询的数据库对象。
		if(dbUser==null) {
			return null;//用户名不存在
		}
//		return new SimpleAuthenticationInfo(name, password, "");//第一、二个参数可以自己指定返回值。
		return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), "");

	}
}
