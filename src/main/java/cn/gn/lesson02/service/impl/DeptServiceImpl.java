package cn.gn.lesson02.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gn.lesson02.dao.DeptMapper;
import cn.gn.lesson02.entity.Dept;
import cn.gn.lesson02.entity.DeptExample;
import cn.gn.lesson02.entity.TreeNode;
import cn.gn.lesson02.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	DeptMapper dao;
	/* (non-Javadoc)
	 * @see cn.gn.student.service.impl.DeptService#queryTreeNode(java.lang.Integer)
	 */
	public List<TreeNode> queryTreeNode(Integer pid){
		DeptExample se  = new DeptExample();
		se.createCriteria().andPidEqualTo(pid);
		List<Dept> dept = dao.selectByExample(se);
		List<TreeNode> deptList = new ArrayList<TreeNode>();
		for(Dept d:dept){
			TreeNode tNode = new TreeNode();
			tNode.setId(d.getId());
			tNode.setText(d.getDname());
			//判断当前节点下是否还存在子节点
			if(queryTreeNode(d.getId()).size()==0){
				tNode.setState("open");
			};
			deptList.add(tNode);
			
		}
		return deptList;
	}
}
