package com.supershen.example.service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.supershen.example.Constants;
import com.supershen.example.core.UserHelper;
import com.supershen.example.core.mybatisplus.EntityWrapperUtil;
import com.supershen.example.dao.PermMapper;
import com.supershen.example.dao.RoleMapper;
import com.supershen.example.dao.UserMapper;
import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.entity.User;
import com.supershen.example.utils.Digests;
import com.supershen.example.utils.Encodes;

@Component
@Transactional
public class UserService {
	private static final int SALT_SIZE = 8;
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private PermMapper permMapper;

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
	
	/**
	 * 假删除
	 */
	public void delete(Integer id) {
		User entity  = userMapper.selectById(id);
		entity.setState("0");
		userMapper.updateById(entity);
	}
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userMapper.selectList(new EntityWrapper<User>());
	}

	public void save(User entity) {
		userMapper.insert(entity);
	}
	
	@Transactional(readOnly = true)
	public User findByUsernameAndState(String username) {
		User user = new User(username);
		return userMapper.selectOne(user);
	}
	
	@Transactional(readOnly = true)
	public Set<String> findResourceNamesByUserId(Integer userId) {
		Set<String> result = new LinkedHashSet<String>();
		for (Role r : roleMapper.selectListByUserId(userId)) {
			for (Perm p : permMapper.selectListByRoleId(r.getId())) {
				result.add(p.getName());
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<User> findPage(Map<String, Object> searchParams,int pageNumber, int pageSize) {
		Page<User> page = new Page<User>(pageNumber,pageSize);
		//条件转化为ew
		searchParams.put("EQ_state", "1");
		
		EntityWrapper<User> ew = EntityWrapperUtil.mapfiltersToEntityWrapper(searchParams);
		ew =  (EntityWrapper<User>) SqlHelper.fillWrapper(page,  ew);
        page.setRecords(userMapper.selectPage(page, ew));
		return page;
	}

	
	public void save(User entity, String ids) {
		Date time = new Date();
		if(entity.getId() == null){
			if(userMapper.selectOneByUsername(entity.getUsername()) != null){
				throw new RuntimeException("用户名已存在!");
			}
			entity.setPlainPassword(Constants.PASSWORD);
			// 设置hash密码
			entryptPassword(entity);
			entity.setCreater(UserHelper.getCurrentUser().getNickname());
			entity.setCreateTime(time);
			userMapper.insert(entity);
			
		}else{
			if(this.findByUsernameAndStateAndIdNot(entity) != null){
				throw new RuntimeException("用户名已存在!");
			}
			entity.setUpdater(UserHelper.getCurrentUser().getNickname());
			entity.setUpdateTime(time);
			userMapper.updateById(entity);
			//清空原有角色
			roleMapper.deleteRoleByUserId(entity.getId());
		}
		
		//保存用户角色对应关系
		for(String id : ids.split(",")){
			//roles.add(userMapper.findOne(Long.valueOf(id)));
			roleMapper.insertUserAndRole(entity.getId(),Integer.valueOf(id));
		}
		
	}
	
	/**
	 * 获取id不为当前用户id并且用户名相同的实体
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = true)
	private User findByUsernameAndStateAndIdNot(User entity) {
		return userMapper.selectByUsernameAndStateAndIdNot(entity.getUsername(),entity.getId());
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

	/**
	 * 重置密码
	 */
	public void changePassword(Integer id) {
		User user = this.findOne(id);
		user.setPlainPassword(Constants.PASSWORD);
		this.entryptPassword(user);
		userMapper.updateAllColumnById(user);
	}

}
