package com.supershen.example.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.supershen.example.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	Role findByName(String roleName);
}
