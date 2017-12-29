package cn.gn.lesson02.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cn.gn.lesson02.entity.Emp;

@RestController
public class SbController {
	@Autowired
	DataSource jdbc;
	
/*	@Autowired
	EmpMapper mapper;
	
	@RequestMapping("/sysoSource")
	public String hello(){
		return jdbc.toString();
	}
	
	@RequestMapping("/emp")
	public List<Emp> queryAll(){
		return mapper.queryEmp();
	}
	
	@RequestMapping("/emp1")
	public Emp queryAlls(){
		return mapper.queryEmp().get(0);
	}*/
	
}
