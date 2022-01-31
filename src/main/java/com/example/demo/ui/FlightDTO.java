package com.example.demo.ui;

import java.time.LocalDate;

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
	
	private Integer flightID;
	
	private Integer availableSeats;
	
	private String fromLocation;
	
	private String destination;
	
	private Double price;
	
	private TripType tripType;
	
	private LocalDate departureDate;
	
	private String airlineName;

	@Override
	public String toString() {
		return "FlightDTO [flightName=" + flightName + ", availableSeats=" + availableSeats + ", fromLocation="
				+ fromLocation + ", destination=" + destination + ", price=" + price + ", tripType=" + tripType
				+ ", departureDate=" + departureDate + ", airlineName=" + airlineName + "]";
	}
	
	
	

}
