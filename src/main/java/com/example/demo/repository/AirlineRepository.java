package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {
	

	@Modifying
	@Query(value="delete from airline where airlinename=:airlinename",nativeQuery=true)
	void deleteByairlineId(String airlinename);
	
	

}
