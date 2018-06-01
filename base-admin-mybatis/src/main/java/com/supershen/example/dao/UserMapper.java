package com.supershen.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.supershen.example.entity.User;

public interface UserMapper extends BaseMapper<User> {
	
	@Select("select * from base_user where state='1' and username=#{username}")
	User selectOneByUsername(String username);
	
	@Select("select * from base_user where state='1' and username=#{username} and id != #{userId}")
	User selectByUsernameAndStateAndIdNot(@Param("username")String username,@Param("userId")Integer userId);

	@Select("select r.* from base_user r left join base_user_role ur on r.id = ur.user_id where ur.role_id = #{role_id} and r.state = '1'")
	List<User> selectUserRoleByRoleId(@Param("role_id") Integer roleId);
}
