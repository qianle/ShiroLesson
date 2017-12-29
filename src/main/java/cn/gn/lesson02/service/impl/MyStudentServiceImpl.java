package cn.gn.lesson02.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gn.lesson02.dao.StudentMapper;
import cn.gn.lesson02.entity.Student;
import cn.gn.lesson02.entity.StudentExample;
import cn.gn.lesson02.service.MyStudentService;
import cn.gn.lesson02.util.PageTools;

@Service
public class MyStudentServiceImpl implements MyStudentService {
	@Autowired
	StudentMapper dao;
	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#queryFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#queryFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryStudentCount(cn.gn.student.entity.StudentExample)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryStudentCount(cn.gn.student.entity.StudentExample)
	 */
	public int queryStudentCount(StudentExample fe){
		return (int)dao.countByExample(fe);
	}
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryFood(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryStudent(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public PageTools queryStudent(String sname,Integer page,Integer rows){
		if(sname==null || sname.trim()=="" ){
			sname="";
		}
		//发起sql语句查询总记录数
		StudentExample fe = new StudentExample();
		fe.createCriteria().andSnameLike("%"+sname+"%");
		int total = queryStudentCount(fe);
		System.out.println(total);
		//limit 开始位置，总记录数
		PageTools pts = new PageTools(page,rows,total);
		System.out.println(pts.getCurPage());
		System.out.println(pts.getPrePage());
		System.out.println(pts.getNextPage());
		System.out.println(pts.getPageCount());
		System.out.println(pts.getTotalPage());
		System.out.println(pts.getStaIndex());
		System.out.println(pts.getEndIndex());
		System.out.println(pts.getTotal());
		RowBounds rb = new RowBounds(pts.getStaIndex()-1,rows);
		List<Student> studentList = dao.selectByExampleWithRowbounds(fe,rb);
		pts.setRows(studentList);
		return pts;
	}
	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#deleteFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#deleteFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#deleteFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#deleteStudent(java.lang.String)
	 */
	public void deleteStudent(String sid) {
		String [] str = sid.split(",");
		List<Integer> list = new ArrayList<Integer>(); 
		for(String fid:str){
			list.add(Integer.parseInt(fid));
		}
		/*for(String fid:str){
			dao.deleteByPrimaryKey(Integer.parseInt(fid));
		}*/
		StudentExample fe = new StudentExample();
		fe.createCriteria().andSidIn(list);
		dao.deleteByExample(fe);
	}
	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#saveFood(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#saveFood(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#saveFood(cn.gn.student.entity.Student)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#saveStudent(cn.gn.student.entity.Student)
	 */
	public void saveStudent(Student student) {
		dao.insert(student);
	}
	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#updateFood(java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#updateFood(java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#updateFood(cn.gn.student.entity.Student)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#updateFood(cn.gn.student.entity.Student)
	 */
	public void updateFood(Student student) {
		dao.updateByPrimaryKey(student);
	}
}
