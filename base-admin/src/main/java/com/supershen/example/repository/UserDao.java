package com.supershen.example.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.supershen.example.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findByUsername(String username);

	@Query("select u from User u left join fetch u.roles r left join fetch r.perms where u.id=? ")
	User findUserById(Long id);

	User findByUsernameAndStateAndIdNot(String username, String state, Long id);
	
	User findByUsernameAndState(String username, String state);
}
