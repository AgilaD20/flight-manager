package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline {
	
	@Id
	@GeneratedValue
	@Column(name="AIRLINEID")
	private Integer airlineId;
	@Column(name="AIRLINENAME", unique=true)
	private String airlineName;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="CONTACT")
	@Pattern(regexp = "^[1-9]\\d{9}")
	private String contact;
	@Column(name="LOGOURL")
	private String logoUrl;
	
	@OneToMany(mappedBy="airline")
	private List<Flight> flight;


}
