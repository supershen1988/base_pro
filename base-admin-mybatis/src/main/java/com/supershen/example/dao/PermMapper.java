package com.supershen.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.supershen.example.entity.Perm;

public interface PermMapper extends BaseMapper<Perm> {

	@Select("select p.* from base_perm p left join base_role_perm rp on p.id = rp.perm_id where rp.role_id = ${role_id}")
	List<Perm> selectListByRoleId(@Param("role_id") Integer id);
	
	@Delete("delete from base_role_perm where role_id = #{id}")
	void deletePermByRoleId(Integer id);
	
	@Insert("insert into base_role_perm(perm_id,role_id) values(#{perm_id},#{role_id})")
	void insertRoleAndPerm(@Param("perm_id")Integer permId, @Param("role_id")Integer roleId);

}
