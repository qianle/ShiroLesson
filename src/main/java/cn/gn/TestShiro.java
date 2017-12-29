package cn.gn;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {
	public static void main(String[] args) {
		//��ini�ж�ȡȨ����Ϣ ����SecurityManager����
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//��ȡ��ǰ���û�
		Subject currentUser = SecurityUtils.getSubject();
		//��ǰ�û��ĻỰ
		Session session = currentUser.getSession();
		//�ж��Ƿ���� δ���� ����Ҫ����
		  /**  
         * �û�����������   
//       *    principals and credentials  
         *     principals�����ˣ���ʾ�û��ı�ʶ��Ϣ �����û��� �û���ַ��  
         *     credentials��ƾ֤����ʾ�û����ڵ�¼��ƾ֤ �������� ֤���  
         */  
		if( !currentUser.isAuthenticated()){
			//���� �û��������� ��ʵ����credentials  
			//�û�������û���������
            UsernamePasswordToken token = new UsernamePasswordToken("jiaozi", "123456");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                System.out.println("����ɹ�");
                System.out.println(currentUser.isAuthenticated());
                System.out.println(currentUser.isRemembered());
                //���������û��Ƿ�ӵ��ĳ����ɫ
                if(currentUser.hasRole("role1")){
                	System.out.println("ӵ��role1��ɫ");
                }
                
                if(currentUser.isPermitted("user:query:1")){
                	System.out.println("ӵ�в�ѯ1�ŵ�Ȩ��");
                }
                //if no exception, that's it, we're done!
            } catch ( UnknownAccountException uae ) {
                //username wasn't in the system, show them an error message?
            	System.out.println("�ʺŴ���");
            } catch ( IncorrectCredentialsException ice ) {
                //password didn't match, try again?
            	System.out.println("���벻ƥ��");
            } catch ( LockedAccountException lae ) {
                //account for that username is locked - can't login.  Show them a message?
            	System.out.println("�ʺű�����");
            } catch ( AuthenticationException ae ) {
                //unexpected condition - error?
            	System.out.println("λ���쳣");
            }
		}
	}
}
