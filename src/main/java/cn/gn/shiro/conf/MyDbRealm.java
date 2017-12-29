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
	 * en��֤
	 * ����¼������û�������������ݿ��е��û���������Ƚ� �Ƿ����
	 * ����ֵ null��ʾ��֤ʧ��  ��null��֤�ɹ�
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//ҳ�洫���token
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(upt.getUsername());
		if(queryUser!=null && queryUser.getPassword().equals(new String(upt.getPassword()))){
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			
			return sa;
		}
		return null;
	}
	
	/**
	 * ��ȡ��ǰ�û�����Ȩ����
	 * ����ǰ�û������ݿ�Ľ�ɫ��Ȩ�޼��ص� AuthorizationInfo
	 * Ĭ�� �ڽ�����Ȩ��֤ʱ����
	 * ���Ȩ�޵��� checkRole checkPerm
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = principals.getPrimaryPrincipal().toString();
		Set<String> roleList = userMapper.queryRoleByName(userName);
		Set<String> permsList = userMapper.queryPermsByName(userName);
		//Ȩ�޶���
		SimpleAuthorizationInfo   sa = new SimpleAuthorizationInfo();
		sa.setRoles(roleList);
		sa.setStringPermissions(permsList);
		return sa;
	}

}
