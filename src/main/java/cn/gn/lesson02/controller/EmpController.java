package cn.gn.lesson02.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gn.lesson02.entity.Emp;
import cn.gn.lesson02.entity.Result;
import cn.gn.lesson02.service.EmpService;
import cn.gn.lesson02.util.PageTools;


/**
 * 后台验证步骤 1.javabean 添加验证注解 2.action中使用 @Valid 表示javabean
 * 使用Errirs或者BindingResult判断是否验证失败 3.出现jar包冲突 4.3.1
 * 
 * 
 * @author GuNiao
 * 
 */
@Controller
public class EmpController {
	@Autowired
	EmpService service;
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
	@RequestMapping(value = "/queryEmp", method ={RequestMethod.GET,RequestMethod.POST})
	public  PageTools queryFoodList(Integer id,Integer page,Integer rows) throws Exception {
		return service.queryEmp(id,page,rows);
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryEmpList", method ={RequestMethod.GET,RequestMethod.POST})
	public PageTools queryEmpList(String ename,Integer page,Integer rows) throws Exception {
		PageTools list = service.queryEmpByEname(ename, page, rows);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/Emp/{id}",method=RequestMethod.DELETE)
	public Result deleteEmp(@PathVariable(value="id") String id) throws Exception{
		Result result = new Result();
		try {
			service.deleteEmp(id);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/Emp/{id}",method=RequestMethod.PUT)
	public Result updateEmp(@PathVariable(value="id") Integer id,Emp emp) throws Exception{
		emp.setId(id);
		Result result = new Result();
		try {
			service.updateEmp(emp);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/Emp",method=RequestMethod.POST)
	public Result saveEmp(Emp emp) throws Exception{
		Result result = new Result();
		try {
			service.saveEmp(emp);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}

}
