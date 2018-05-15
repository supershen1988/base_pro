package com.supershen.example.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.supershen.example.common.ShiroDbRealm.ShiroUser;
import com.supershen.example.common.persistence.DynamicSpecifications;
import com.supershen.example.common.persistence.SearchFilter;
import com.supershen.example.entity.Perm;
import com.supershen.example.entity.Role;
import com.supershen.example.repository.PermDao;
import com.supershen.example.repository.RoleDao;
import com.supershen.example.utils.UserHelper;

/**
 * 角色管理业务类.
 */
@Component
@Transactional
public class RoleService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermDao permDao;

	@Transactional(readOnly = true)
	public Page<Role> findPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Sort sort = new Sort(Direction.DESC, "id");
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<Role> spec = DynamicSpecifications.bySearchFilter(filters.values(), Role.class);

		return roleDao.findAll(spec, pageRequest);
	}

	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return (List<Role>) roleDao.findAll();
	}

	@Transactional(readOnly = true)
	public Role findOne(Long id) {
		return roleDao.findOne(id);
	}

	public void save(Role entity) {
		// 取得当前登录人
		ShiroUser shiroUser = UserHelper.getCurrentUser();

		roleDao.save(entity);
		
		logger.info(shiroUser + ",添加角色:" + entity);
	}

	public void update(Role entity) {
		// 取得当前登录人
		ShiroUser shiroUser = UserHelper.getCurrentUser();

		roleDao.save(entity);

		logger.info(shiroUser + ",更新角色:" + entity);
	}

	public void savePerm(Role entity, Long[] permIds) {
		Role role = roleDao.findOne(entity.getId());
		// 清空该角色下已有的资源
		role.getPerms().clear();
		Set<Perm> perms = Sets.newLinkedHashSet();

		// 保存新的资源
		for (Long id : permIds) {
			Perm perm = permDao.findOne(id);
			perms.add(perm);
			
			if (null != perm.getParent()) {
				perms.add(perm.getParent());
				
				if (null != perm.getParent().getParent()) {
					perms.add(perm.getParent().getParent());
				}
			}
		}

		role.setPerms(perms);

		roleDao.save(role);
	}

	public void delete(final Long id) {
		Role entity = roleDao.findOne(id);
		
		roleDao.delete(entity);
	}

	@Transactional(readOnly = true)
	public Role findRoleByName(String roleName) {
		return roleDao.findByName(roleName);
	}

	@Transactional(readOnly = true)
	public List<Perm> findAllPerms() {
		return (List<Perm>) permDao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Perm> getPerms() {
		return permDao.findAllTopPerms();
	}
}
