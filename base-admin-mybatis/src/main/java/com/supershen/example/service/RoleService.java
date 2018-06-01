package com.supershen.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.supershen.example.core.mybatisplus.BaseService;
import com.supershen.example.dao.PermMapper;
import com.supershen.example.dao.RoleMapper;
import com.supershen.example.dao.UserMapper;
import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.entity.User;
/**
 * 角色业务逻辑类
 * @author gshen
 *
 */
@Component
@Transactional
public class RoleService extends BaseService<RoleMapper, Role>{

	@Resource
	private UserMapper userMapper;
	@Resource
	private PermMapper permMapper;
	/**
	 * 获取所有角色集合
	 */
	@Transactional(readOnly = true)
	public List<Role> findRoleAll() {
		return (List<Role>) baseMapper.selectList(null);
	}
	
	/**
	 * 获取用户的角色集合
	 * @param id 用户id
	 */
	@Transactional(readOnly = true)
	public List<Role> selectRoleByUserId(Integer id) {
		return ((RoleMapper) baseMapper).selectListByUserId(id);
	}

	public Role findRoleByName(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return baseMapper.selectOne(role);
	}

	/**
	 * 查询所有权限集合
	 */
	public List<Perm> getPerms() {
		return permMapper.selectList(null);
	}
	

	public void savePerm(Role entity, Integer[] permIds) {
		
		// 清空该角色下已有的资源
		permMapper.deletePermByRoleId(entity.getId());
		
		List<Perm> parentList = new ArrayList<Perm>();
		// 保存新的资源
		for (Integer id : permIds) {
			Perm perm = permMapper.selectById(id);
			permMapper.insertRoleAndPerm(id,entity.getId());
			if (null != perm.getParentId()) {
				
				if(permListContains(parentList,perm)){
					permMapper.insertRoleAndPerm(perm.getParentId(),entity.getId());
					parentList.add(perm);
				}
				
//				Perm parent = permMapper.selectById(perm.getParentId());
//				if (null != parent.getParentId()) {
//					permMapper.insertRoleAndPerm(parent.getParentId(),entity.getId());
//				}
			}
		}

	}
	
	private Boolean permListContains(List<Perm> parentList,Perm perm){
		if(parentList == null || parentList.size() == 0){
			return false;
		}
		
		for (Perm permItem : parentList) {
			if(permItem.getId() == perm.getId()){
				return true;
			}
		}
		return false;
		
	}

	public void delete(Integer roleId) {
		//判断是否有用户使用该角色
		List<User> userList = userMapper.selectUserRoleByRoleId(roleId);
		if(userList != null && userList.size() > 0){
			throw new RuntimeException("该角色已分配权限，请取消后再试！");
		}
		//删除角色、权限关系
		((RoleMapper) baseMapper).deleteRoleAndPerm(roleId);
		//删除角色
		baseMapper.deleteById(roleId);
	}

	public List<Perm> getCengjiPerm() {
		List<Perm> permsAll = this.getPerms();
		
		List<Perm> permsTop = new ArrayList<Perm>();
		for (Perm perm : permsAll) {
			if(perm.getParentId() == null){
				//获取子项
				List<Perm> childrenList = permMapper.selectList(new EntityWrapper<Perm>().eq("parent_id", perm.getId()));
				perm.setChildren(childrenList);
				//添加顶级权限
				permsTop.add(perm);
			}
		}
		return permsTop;
	}

	public List<Perm> selectPermByRoleId(Integer id) {
		return permMapper.selectListByRoleId(id);
	}

	
}
