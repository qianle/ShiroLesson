package cn.gn.lesson02.service;

import cn.gn.lesson02.entity.Emp;
import cn.gn.lesson02.util.PageTools;

public interface EmpService {

	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.DeptService#queryTreeNode(java.lang.Integer)
	 */
	public abstract PageTools queryEmp(Integer pid,Integer page,Integer rows);
	
	public abstract PageTools queryEmpByEname(String ename,Integer page,Integer rows);
	

	public abstract void deleteEmp(String pid);

	public abstract void saveEmp(Emp emp);

	public abstract void updateEmp(Emp emp);

}