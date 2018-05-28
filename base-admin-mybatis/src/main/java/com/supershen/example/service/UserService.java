package com.supershen.example.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.supershen.example.dao.UserPlusMapper;
import com.supershen.example.entity.User;

@Component
@Transactional
public class UserService {

	@Resource
	private UserPlusMapper userMapper;

	public void insert(User entity) {

		userMapper.insert(entity);
	}

	public void update(User entity) {

		userMapper.updateById(entity);
	}

	@Transactional(readOnly = true)
	public User findOne(Integer id) {

		return userMapper.selectById(id);
	}

	public void delete(Integer id) {
		userMapper.deleteById(id);
	}

	public List<User> findAll() {
		return userMapper.selectList(
		        new EntityWrapper<User>()
				);
	}

	public void save(User entity) {
		userMapper.insert(entity);
	}

}
