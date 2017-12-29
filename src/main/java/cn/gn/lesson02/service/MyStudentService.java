package cn.gn.lesson02.service;

import cn.gn.lesson02.entity.Student;
import cn.gn.lesson02.entity.StudentExample;
import cn.gn.lesson02.util.PageTools;

public interface MyStudentService {

	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#queryFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#queryFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryStudentCount(cn.gn.student.entity.StudentExample)
	 */
	public abstract int queryStudentCount(StudentExample fe);

	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#queryFood(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public abstract PageTools queryStudent(String sname, Integer page,
			Integer rows);

	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#deleteFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#deleteFood(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#deleteFood(java.lang.String)
	 */
	public abstract void deleteStudent(String sid);

	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#saveFood(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#saveFood(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#saveFood(cn.gn.student.entity.Student)
	 */
	public abstract void saveStudent(Student student);

	/* (non-Javadoc)
	 * @see cn.gn.springmvc.lesson05.dao.MyFoodDao#updateFood(java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.food.service.impl.MyFoodService#updateFood(java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.MyStudentService#updateFood(cn.gn.student.entity.Student)
	 */
	public abstract void updateFood(Student student);

}