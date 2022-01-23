package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Role;

//import com.flightapp.user.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query
	public Role findByRoleName(String rolename);

}
