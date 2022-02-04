package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import com.example.demo.model.Flight;
import com.example.demo.model.Schedules;

public interface ScheduleRepository extends JpaRepository<Schedules, Integer> {

	@Query(value="select * from schedules where flightid=:flightId and scheduledays=:day", nativeQuery=true)
	public Schedules checkifExists(Integer flightId, Integer day);

}
