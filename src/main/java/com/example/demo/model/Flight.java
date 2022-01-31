package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FLIGHTID")
	private Integer flightID;
	@Column(name="FLIGHTNAME")
	private String flightName;
	@Column(name="AVAILABLESEATS")
	private Integer availableSeats;
	@Column(name="FROMLOCATION")
	private String fromLocation;
	@Column(name="DESTINATION")
	private String destination;
	@Column(name="PRICE")
	private Double price;
	@Column(name="TRIPTYPE")
	private TripType tripType;
	@Column(name="DEPARTUREDATE")
	private LocalDate departureDate;
	
	@Column(name="ISBLOCKED")
	private Boolean isBlocked;
	/*
	 * @Column(name="seats") private String seats;
	 */
	
	@ManyToOne
	@JoinColumn(name="airlineid")
	private Airline airline;
	

	 @OneToMany(mappedBy="flight")
	 private List<Seat> seats;
}
