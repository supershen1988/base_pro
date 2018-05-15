package com.supershen.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.supershen.example.entity.Perm;

public interface PermDao extends PagingAndSortingRepository<Perm, Long>, JpaSpecificationExecutor<Perm> {
	@Query("from Perm o where o.name like 'perm_%'")
	List<Perm> findAllTopPerms();
}
