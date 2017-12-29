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
		// ��ȡ��ǰ���û�
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			currentUser.login(token);
			//��ѯ�˵�
			model.addAttribute("menuList", UserMapper.queryMenuByName(userName));
			System.out.println("����ɹ�");
			System.out.println(currentUser.isAuthenticated());
			System.out.println(currentUser.isRemembered());
			// ���������û��Ƿ�ӵ��ĳ����ɫ
			if (currentUser.hasRole("role1")) {
				System.out.println("ӵ��role1��ɫ");
			}

			if (currentUser.isPermitted("user:query:1")) {
				System.out.println("ӵ�в�ѯ1�ŵ�Ȩ��");
			}
			return "layout1.jsp";
			// if no exception, that's it, we're done!
		} catch (UnknownAccountException uae) {
			// username wasn't in the system, show them an error message?
			System.out.println("�ʺŴ���");
		} catch (IncorrectCredentialsException ice) {
			// password didn't match, try again?
			System.out.println("���벻ƥ��");
		} catch (LockedAccountException lae) {
			// account for that username is locked - can't login. Show them a
			// message?
			System.out.println("�ʺű�����");
		} catch (AuthenticationException ae) {
			// unexpected condition - error?
			System.out.println("λ���쳣");
		}
		return "/a.html";
	}
	
}
