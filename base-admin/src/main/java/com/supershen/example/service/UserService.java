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
import com.supershen.example.entity.User;
import com.supershen.example.entity.Role;
import com.supershen.example.repository.UserDao;
import com.supershen.example.repository.RoleDao;
import com.supershen.example.utils.UserHelper;

@Component
@Transactional
public class UserService {
	
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao addressDao;

	@Transactional(readOnly = true)
	public Page<User> findPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Sort sort = new Sort(Direction.DESC, "id");
		searchParams.put("EQ_state", "1");
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);

		return addressDao.findAll(spec, pageRequest);
	}
	
	public void save(User entity, String ids) {
		Date time = new Date();
		if(entity.getId() == null){
			if(addressDao.findByUsernameAndState(entity.getUsername(), "1") != null){
				throw new ServiceException("用户名已存在!");
			}
			entity.setCreater(UserHelper.getCurrentUser().getUsername());
			entity.setCreateTime(time);
			entity.setPassword(SysConstants.PASS);
		}else{
			if(addressDao.findByUsernameAndStateAndIdNot(entity.getUsername(), "1", entity.getId()) != null){
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
		addressDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public User findOne(Long id) {

		return addressDao.findOne(id);
	}

	
	public void delete(Long id) {
		User entity = addressDao.findOne(id);
		entity.setState("0");
		entity.setUpdater(UserHelper.getCurrentUser().getUsername());
		entity.setUpdateTime(new Date());
		addressDao.save(entity);
	}

	@Transactional(readOnly = true)
	public User findUserByName(String username) {

		return addressDao.findByUsername(username);
	}

	public void changePassword(Long id) {
		
		User user = this.findOne(id);
		user.setPassword(SysConstants.PASS);
		addressDao.save(user);
	}

	@Transactional(readOnly = true)
	public List<Role> findRoleAll() {

		return (List<Role>) roleDao.findAll();
	}
}
