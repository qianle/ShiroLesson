package cn.gn.shiro.conf;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gn.shiro.dao.UserMapper;
import cn.gn.shiro.entity.UserInfo;

@Component
public class MyDbRealm extends AuthorizingRealm{
	@Autowired
	UserMapper userMapper;
	/**
	 * en认证
	 * 将登录输入的用户名和密码和数据库中的用户名和密码比较 是否相等
	 * 返回值 null表示认证失败  非null认证成功
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//页面传入的token
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(upt.getUsername());
		if(queryUser!=null && queryUser.getPassword().equals(new String(upt.getPassword()))){
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			
			return sa;
		}
		return null;
	}
	
	/**
	 * 获取当前用户的授权数据
	 * 将当前用户在数据库的角色和权限加载到 AuthorizationInfo
	 * 默认 在进行授权认证时调用
	 * 检查权限调用 checkRole checkPerm
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = principals.getPrimaryPrincipal().toString();
		Set<String> roleList = userMapper.queryRoleByName(userName);
		Set<String> permsList = userMapper.queryPermsByName(userName);
		//权限对象
		SimpleAuthorizationInfo   sa = new SimpleAuthorizationInfo();
		sa.setRoles(roleList);
		sa.setStringPermissions(permsList);
		return sa;
	}

}
