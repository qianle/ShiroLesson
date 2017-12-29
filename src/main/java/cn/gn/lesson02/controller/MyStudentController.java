package cn.gn.lesson02.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gn.lesson02.entity.Result;
import cn.gn.lesson02.entity.Student;
import cn.gn.lesson02.service.MyStudentService;
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
public class MyStudentController {
	@Autowired
	MyStudentService mdi;
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
	@RequestMapping(value = "/queryStudentList", method ={RequestMethod.GET,RequestMethod.POST})
	public PageTools queryFoodList(String sname,Integer page,Integer rows) throws Exception {
		PageTools list = mdi.queryStudent(sname, page, rows);
		return list;
	}
	/**
	 * 删除food
	 * @param foodId 菜品id
	 * @param os 输出流 输出到游览器
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/Student/{id}",method=RequestMethod.DELETE)
	public Result deleteFood(@PathVariable(value="id") String sid) throws Exception{
		Result result = new Result();
		try {
			mdi.deleteStudent(sid);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}
	/**
	 * 修改food
	 * @param foodId 菜品id
	 * @param foodName 菜品名字
	 * @param price 菜品价格
	 * @param os 输出流 输出到游览器
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/Student/{id}",method=RequestMethod.PUT)
	public Result updateFood(@PathVariable(value="id") Integer sid,Student student) throws Exception{
		student.setSid(sid);
		Result result = new Result();
		try {
			mdi.updateFood(student);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}
	/**
	 * 新增food
	 * @param foodName 菜品名字
	 * @param price 菜品价格
	 * @param os 输出流 输出到游览器
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/Student",method=RequestMethod.POST)
	public Result saveFood(Student student,MultipartFile myImage) throws Exception{
		Result result = new Result();
		try {

				String fileName = myImage.getOriginalFilename();
				if(fileName!=null && !"".equals(fileName)){
					File destFile = new File("E:\\myimgUrl\\"+fileName);
					myImage.transferTo(destFile);
				}
			mdi.saveStudent(student);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage(e);
		}
		return result;
	}

}
