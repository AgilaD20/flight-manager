package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SeatMapper;

@Repository
public interface SeatMapperRepository extends JpaRepository<SeatMapper, Integer> {

	@Query
	public List<SeatMapper> findByFlightIdAndDepartureDate(Integer flightId, LocalDate departureDate);


	@Query(value="delete from seatmapper where flightId=:flightId and seatNumber=:seatNumber and departureDate=:departureDate",nativeQuery=true)
	@Modifying
	public void deleteByFlightIdAndSeatNumberAndDepartureDate(Integer flightId, String seatNumber, LocalDate departureDate);
	


}
