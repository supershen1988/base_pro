package com.supershen.example.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.supershen.example.common.SysConstants;
import com.supershen.example.common.persistence.DynamicSpecifications;
import com.supershen.example.common.persistence.SearchFilter;
import com.supershen.example.entity.Role;
import com.supershen.example.entity.User;
import com.supershen.example.repository.RoleDao;
import com.supershen.example.repository.UserDao;
import com.supershen.example.utils.Digests;
import com.supershen.example.utils.Encodes;
import com.supershen.example.utils.UserHelper;

@Component
@Transactional
public class UserService {
	
	private static final int SALT_SIZE = 8;
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;

	@Transactional(readOnly = true)
	public Page<User> findPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Sort sort = new Sort(Direction.DESC, "id");
		searchParams.put("EQ_state", "1");
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);

		return userDao.findAll(spec, pageRequest);
	}
	
	public void save(User entity, String ids) {
		Date time = new Date();
		if(entity.getId() == null){
			if(userDao.findByUsernameAndState(entity.getUsername(), "1") != null){
				throw new ServiceException("用户名已存在!");
			}
			entity.setPlainPassword(SysConstants.PASS);
			// 设置hash密码
			entryptPassword(entity);
			entity.setCreater(UserHelper.getCurrentUser().getUsername());
			entity.setCreateTime(time);
		}else{
			if(userDao.findByUsernameAndStateAndIdNot(entity.getUsername(), "1", entity.getId()) != null){
				throw new ServiceException("用户名已存在!");
			}
		}
		
		Set<Role> roles = new HashSet<>();
		for(String id : ids.split(",")){
			roles.add(roleDao.findOne(Long.valueOf(id)));
		}
		entity.setRoles(roles);
		entity.setUpdater(UserHelper.getCurrentUser().getUsername());
		entity.setUpdateTime(time);
		userDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public User findOne(Long id) {

		return userDao.findOne(id);
	}

	
	public void delete(Long id) {
		User entity = userDao.findOne(id);
		entity.setState("0");
		entity.setUpdater(UserHelper.getCurrentUser().getUsername());
		entity.setUpdateTime(new Date());
		userDao.save(entity);
	}

	@Transactional(readOnly = true)
	public User findUserByName(String username) {

		return userDao.findByUsername(username);
	}

	public void changePassword(Long id) {
		
		User user = this.findOne(id);
		user.setPlainPassword(SysConstants.PASS);
		this.entryptPassword(user);
		userDao.save(user);
	}

	@Transactional(readOnly = true)
	public List<Role> findRoleAll() {

		return (List<Role>) roleDao.findAll();
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public String entryptOldPassword(User user) {
		byte[] salt = Encodes.decodeHex(user.getSalt());
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		//user.setPassword(Encodes.encodeHex(hashPassword));
		return Encodes.encodeHex(hashPassword);
	}
}
