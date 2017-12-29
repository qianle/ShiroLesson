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
		//从ini中读取权限信息 构建SecurityManager对象
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		//当前用户的会话
		Session session = currentUser.getSession();
		//判断是否登入 未登入 才需要登入
		  /**  
         * 用户包括两部分   
//       *    principals and credentials  
         *     principals（本人）表示用户的标识信息 比如用户名 用户地址等  
         *     credentials（凭证）表示用户用于登录的凭证 比如密码 证书等  
         */  
		if( !currentUser.isAuthenticated()){
			//令牌 用户名和密码 其实就是credentials  
			//用户输入的用户名和密码
            UsernamePasswordToken token = new UsernamePasswordToken("jiaozi", "123456");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                System.out.println("登入成功");
                System.out.println(currentUser.isAuthenticated());
                System.out.println(currentUser.isRemembered());
                //检查登入后的用户是否拥有某个角色
                if(currentUser.hasRole("role1")){
                	System.out.println("拥有role1角色");
                }
                
                if(currentUser.isPermitted("user:query:1")){
                	System.out.println("拥有查询1号的权限");
                }
                //if no exception, that's it, we're done!
            } catch ( UnknownAccountException uae ) {
                //username wasn't in the system, show them an error message?
            	System.out.println("帐号错误");
            } catch ( IncorrectCredentialsException ice ) {
                //password didn't match, try again?
            	System.out.println("密码不匹配");
            } catch ( LockedAccountException lae ) {
                //account for that username is locked - can't login.  Show them a message?
            	System.out.println("帐号被锁定");
            } catch ( AuthenticationException ae ) {
                //unexpected condition - error?
            	System.out.println("位置异常");
            }
		}
	}
}
