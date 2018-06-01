package com.supershen.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.supershen.example.entity.Role;
/**
 * 角色持久化接口
 * @author gshen
 *
 */
public interface RoleMapper extends BaseMapper<Role> {
	@Select("select r.* from base_role r left join base_user_role ur on r.id = ur.role_id where ur.user_id = #{user_id}")
	List<Role> selectListByUserId(@Param("user_id") Integer userId);

	@Insert("insert into base_user_role(user_id,role_id) values(#{user_id},#{role_id})")
	void insertUserAndRole(@Param("user_id")Integer user_id, @Param("role_id")Integer role_id);

	/**
	 * 删除用户角色关系
	 * @param id
	 */
	@Delete("delete from base_user_role where user_id = #{id}")
	void deleteRoleByUserId(Integer id);
	
	/**
	 * 删除角色权限关系
	 */
	@Delete("delete from base_role_perm where role_id = #{roleId}")
	void deleteRoleAndPerm(Integer roleId);

}
