package com.supershen.example.service;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.supershen.example.entity.User;
import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.repository.UserDao;

@Component
@Transactional
public class AccountService {
	
	@Resource
	private UserDao addressDao;
	
	@Transactional(readOnly = true)
	public User findUserById(Long id) {
		return addressDao.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public User findUserByUsername(String username) {
		return addressDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Set<String> findResourceNamesByUserId(Long id) {
		Set<String> result = new LinkedHashSet<String>();

		User user = addressDao.findUserById(id);
		for (Role r : user.getRoles()) {
			for (Perm rr : r.getPerms()) {
				result.add(rr.getName());
			}
		}
		return result;
	}

	public void updateUser(User address) {
		 User entity = addressDao.findOne(address.getId());
		 addressDao.save(entity);
	}

	public int changePassword(Long userId, String old_password, String new_password) {
		 User entity = addressDao.findOne(userId);
		 
		 if (!old_password.equals(entity.getPassword())) {
			 return 0;
		 }
		 
		 entity.setPassword(new_password);
		 addressDao.save(entity);
		 
		return 1;
	}
	
	@Transactional(readOnly = true)
	public User findByUsernameAndState(String username) {
		return addressDao.findByUsernameAndState(username, "1");
	}

}
