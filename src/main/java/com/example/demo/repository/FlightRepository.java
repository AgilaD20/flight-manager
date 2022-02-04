package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Flight;

public interface FlightRepository extends JpaRepository<Flight,Integer> {
	
	
	@Query(value="select * from flight where fromlocation=:fromLocation and destination=:destination and departuredate<=:departure and enddate>=:departure and isblocked=0 and availableseats>0",nativeQuery=true)
	public List<Flight> getAllFlightByCriteria(String fromLocation, String destination, LocalDate departure);
	
	/*
	 * @Modifying
	 * 
	 * @Query(
	 * value="update flight set availableseats=:availableSeats where flightid=:flightId "
	 * ,nativeQuery=true) public void UpdateAvailableSeats(Integer availableSeats,
	 * Integer flightId);
	 */
	

}
