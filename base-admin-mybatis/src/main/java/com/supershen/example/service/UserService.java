package com.supershen.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.search.Direction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.supershen.example.dao.UserMapper;
import com.supershen.example.entity.User;

@Component
@Transactional
public class UserService {

	@Resource
	private UserMapper userMapper;

	public void insert(User entity) {

		userMapper.insert(entity);
	}

	public void update(User entity) {

		userMapper.updateByPrimaryKeySelective(entity);
	}

	@Transactional(readOnly = true)
	public User findOne(Integer id) {

		return userMapper.selectByPrimaryKey(id);
	}

	public void delete(Integer id) {
		userMapper.deleteByPrimaryKey(id);
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}

}
