package cn.gn.lesson02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gn.lesson02.entity.TreeNode;
import cn.gn.lesson02.service.DeptService;


/**
 * 后台验证步骤 1.javabean 添加验证注解 2.action中使用 @Valid 表示javabean
 * 使用Errirs或者BindingResult判断是否验证失败 3.出现jar包冲突 4.3.1
 * 
 * 
 * @author GuNiao
 * 
 */
@Controller
public class DeptController {
	@Autowired
	DeptService service;
	/**
	 * 直接返回对象 springmvc自动转换成json
	 * 
	 * 需要配置消息转换器
	 * @param foodname
	 * @param os
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDept", method ={RequestMethod.GET,RequestMethod.POST})
	public  List<TreeNode> queryFoodList(Integer id) throws Exception {
		if(id == null){
			id=0;
		}
		return service.queryTreeNode(id);
	}

}
