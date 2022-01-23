package com.example.demo.ui;

import java.time.LocalDateTime;

import com.example.demo.model.TripType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
	

	private String flightName;
	
	private Integer availableSeats;
	
	private String fromLocation;
	
	private String destination;
	
	private Double price;
	
	private TripType tripType;
	
	private LocalDateTime departureTime;
	
	private LocalDateTime arrivalTime;
	

}
