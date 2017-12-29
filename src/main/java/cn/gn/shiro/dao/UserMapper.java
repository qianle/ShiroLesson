package cn.gn.shiro.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;

import cn.gn.shiro.entity.Menu;
import cn.gn.shiro.entity.UserInfo;

public interface UserMapper {
	
	@Select("select user_name as userName,pass_word as password from user_info where user_name=#{0}")
	public UserInfo queryUser(String userName);
	
	@Select("SELECT r.role_name FROM user_info AS u "
			+ "INNER JOIN user_role_relation urr ON u.user_id = urr.user_id "
			+ "INNER JOIN role r ON r.role_id = urr.role_id  "
			+ "WHERE u.user_name= #{0} ")
	public Set<String> queryRoleByName(String userName);
	
	@Select("SELECT p.pem_tag FROM user_info AS u "
			+ "INNER JOIN user_role_relation urr ON u.user_id = urr.user_id "
			+ "INNER JOIN role r ON r.role_id = urr.role_id   "
			+ "INNER JOIN role_perms_relation AS rps ON r.role_id = rps.role_id "
			+ "INNER JOIN perms AS p ON rps.pem_id = p.pem_id "
			+ "WHERE u.user_name=#{0}")
	public Set<String> queryPermsByName(String userName);
	
	@Select("select  menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter,is_menu as ismenu from menu")
	public List<Menu> queryMenu();
	
	@Select("select  menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter,is_menu as ismenu from menu where menu_url = #{0}")
	public List<Menu> queryMenuByUrl(String url);
	
	@Select("SELECT m.menu_id as menuid,m.menu_name as menuname,m.menu_url as menuurl,m.menu_filter as menufilter,m.is_menu as ismenu  FROM menu m INNER JOIN user_menu_relation umr ON m.menu_id = umr.menu_id "
			+ "INNER JOIN user_info u ON u.user_id = umr.user_id WHERE u.user_name = #{0} AND is_menu = 1")
	public List<Menu> queryMenuByName(String userName);
	
}
