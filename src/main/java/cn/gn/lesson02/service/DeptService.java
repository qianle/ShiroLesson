package cn.gn.lesson02.service;

import java.util.List;

import cn.gn.lesson02.entity.TreeNode;


public interface DeptService {

	public abstract List<TreeNode> queryTreeNode(Integer pid);

}