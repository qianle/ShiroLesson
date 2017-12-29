package cn.gn.shiro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Servlet implementation class LoginControoler
 */
public class LoginControoler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginControoler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		// 获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			currentUser.login(token);
			request.getRequestDispatcher("/suc.jsp").forward(request, response);
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
