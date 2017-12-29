package cn.gn.lesson02.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gn.lesson02.dao.EmpMapper;
import cn.gn.lesson02.entity.Emp;
import cn.gn.lesson02.entity.EmpExample;
import cn.gn.lesson02.service.EmpService;
import cn.gn.lesson02.util.PageTools;

@Service
public class EmpServiceImpl implements EmpService {
	@Autowired
	EmpMapper dao;
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.DeptService#queryTreeNode(java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.emp.service.impl.EmpService#queryTreeNode(java.lang.Integer)
	 */
	public int queryStudentCount(EmpExample fe){
		return (int)dao.countByExample(fe);
	}
	public PageTools queryEmp(Integer pid,Integer page,Integer rows){
		EmpExample se  = new EmpExample();
		if(pid!=null){
			se.createCriteria().andDeptidEqualTo(pid);
		}
		int total = queryStudentCount(se);
		//limit 开始位置，总记录数
		PageTools pts = new PageTools(page,rows,total);
		RowBounds rb = new RowBounds(pts.getStaIndex()-1,rows);
		List<Emp> empList = dao.selectByExampleWithRowbounds(se,rb);
		pts.setRows(empList);
		return pts;
	}
	public PageTools queryEmpByEname(String ename,Integer page,Integer rows){
		if(ename==null || ename.trim()=="" ){
			ename="";
		}
		//发起sql语句查询总记录数
		EmpExample fe = new EmpExample();
		fe.createCriteria().andEnameLike("%"+ename+"%");
		int total = queryStudentCount(fe);
		//limit 开始位置，总记录数
		PageTools pts = new PageTools(page,rows,total);
		RowBounds rb = new RowBounds(pts.getStaIndex()-1,rows);
		List<Emp> empList = dao.selectByExampleWithRowbounds(fe,rb);
		pts.setRows(empList);
		return pts;
	}
	public void deleteEmp(String pid) {
		String [] str = pid.split(",");
		List<Integer> list = new ArrayList<Integer>(); 
		for(String fid:str){
			list.add(Integer.parseInt(fid));
		}
		/*for(String fid:str){
			dao.deleteByPrimaryKey(Integer.parseInt(fid));
		}*/
		EmpExample fe = new EmpExample();
		fe.createCriteria().andIdIn(list);
		dao.deleteByExample(fe);
	}
	
	/* (non-Javadoc)
	 * @see cn.gn.emp.service.impl.EmpService#saveEmp(cn.gn.emp.entity.Emp)
	 */
	public void saveEmp(Emp emp) {
		try {
			emp.setEname(new String(emp.getEname().getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.insert(emp);
	}
	
	/* (non-Javadoc)
	 * @see cn.gn.emp.service.impl.EmpService#updateEmp(cn.gn.emp.entity.Emp)
	 */
	public void updateEmp(Emp emp) {
		try {
			emp.setEname(new String(emp.getEname().getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.updateByPrimaryKey(emp);
	}
}
