package cn.sm1234.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sm1234.domain.User;
@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request,Model model) {
		System.out.println("执行/user/login");
		//使用shiro认证操作
//		获取Subject对象，如果没有配置shiro.ini或filterChainDefinitions，则无法获取subject。
		Subject subject = SecurityUtils.getSubject();
//		封装登陆用户信息
		AuthenticationToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
//		执行认证
		try {
			subject.login(token);
//			String userName = (String)subject.getPrincipal();//不是token.getPrincipal()
			User dbUser = (User)subject.getPrincipal();
//			认证成功
//			request.getSession().setAttribute("userName", userName);
			request.getSession().setAttribute("userName", dbUser.getName());
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			System.out.println("用户不存在");
			model.addAttribute("msg", "用户不存在");//由于是重定向，不能使用request.setAttribute();设置返回信息。
		} catch (IncorrectCredentialsException e) {
			System.out.println("密码错误");
			model.addAttribute("msg", "密码错误");
		} catch (Exception e) {
			System.out.println("登陆失败");
			e.printStackTrace(); 
		}
		return "login";
	}

}
