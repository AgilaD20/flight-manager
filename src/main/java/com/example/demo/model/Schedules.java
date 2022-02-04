package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="schedules")
public class Schedules {
	
	@Id
	@Column(name="scheduleid")
	private Integer scheduleid;
	
	@ManyToOne
	@JoinColumn(name="flightid")
	private Flight flight;
	
	@Column(name="scheduledays")
	private scheduleDays scheduledDays;

}
