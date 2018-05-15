package com.supershen.example.service;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.entity.User;
import com.supershen.example.repository.UserDao;

@Component
@Transactional
public class AccountService {

	@Resource
	private UserDao userDao;

	@Resource
	private UserService userService;

	@Transactional(readOnly = true)
	public User findUserById(Long id) {
		return userDao.findOne(id);
	}

	@Transactional(readOnly = true)
	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Set<String> findResourceNamesByUserId(Long id) {
		Set<String> result = new LinkedHashSet<String>();

		User user = userDao.findUserById(id);
		for (Role r : user.getRoles()) {
			for (Perm rr : r.getPerms()) {
				result.add(rr.getName());
			}
		}
		return result;
	}

	public void updateUser(User address) {
		User entity = userDao.findOne(address.getId());
		userDao.save(entity);
	}

	public int changePassword(Long userId, String old_password,
			String new_password) {
		User entity = userDao.findOne(userId);
		entity.setPlainPassword(old_password);
		// 旧密码加密
		String oldpassword = userService.entryptOldPassword(entity);

		if (!oldpassword.equals(entity.getPassword())) {
			return 0;
		}

		entity.setPlainPassword(new_password);
		userService.entryptPassword(entity);
		
		userDao.save(entity);

		return 1;
	}

	@Transactional(readOnly = true)
	public User findByUsernameAndState(String username) {
		return userDao.findByUsernameAndState(username, "1");
	}

}
