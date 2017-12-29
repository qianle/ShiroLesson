package cn.gn.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gn.shiro.dao.UserMapper;

@Controller
public class SpringLoginController {
	@Autowired
	UserMapper UserMapper;
	
	@RequestMapping("/loginShiro")
	public String login(String userName,String password,Model model){
		// 获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			currentUser.login(token);
			//查询菜单
			model.addAttribute("menuList", UserMapper.queryMenuByName(userName));
			System.out.println("登入成功");
			System.out.println(currentUser.isAuthenticated());
			System.out.println(currentUser.isRemembered());
			// 检查登入后的用户是否拥有某个角色
			if (currentUser.hasRole("role1")) {
				System.out.println("拥有role1角色");
			}

			if (currentUser.isPermitted("user:query:1")) {
				System.out.println("拥有查询1号的权限");
			}
			return "layout1.jsp";
			// if no exception, that's it, we're done!
		} catch (UnknownAccountException uae) {
			// username wasn't in the system, show them an error message?
			System.out.println("帐号错误");
		} catch (IncorrectCredentialsException ice) {
			// password didn't match, try again?
			System.out.println("密码不匹配");
		} catch (LockedAccountException lae) {
			// account for that username is locked - can't login. Show them a
			// message?
			System.out.println("帐号被锁定");
		} catch (AuthenticationException ae) {
			// unexpected condition - error?
			System.out.println("位置异常");
		}
		return "/a.html";
	}
	
}
