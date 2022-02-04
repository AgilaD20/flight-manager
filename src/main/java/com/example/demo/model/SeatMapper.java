package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="seatmapper")
public class SeatMapper {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="seatmapperid")
	private Integer seatId;
	
	@Column(name="flightid")
	private Integer flightId;
	
	@Column(name="seatnumber")
	private String seatNumber;
	
	@Column(name="departuredate")
	private LocalDate departureDate;
	

}
