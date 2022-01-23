package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Integer> {
	
	@Modifying
	@Query(value="update seat set isbooked=1 where flightid=:flightId and seatnumber=:SeatNumber",nativeQuery=true)
	public void updateBookedSeats(Integer flightId, String SeatNumber);
	
	@Query(value="select * from seat where isbooked=0 and flightId=:flightId",nativeQuery=true)
	public List<Seat> getAvailableSeats(Integer flightId);

}
