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
		// ��ȡ��ǰ���û�
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			currentUser.login(token);
			request.getRequestDispatcher("/suc.jsp").forward(request, response);
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
