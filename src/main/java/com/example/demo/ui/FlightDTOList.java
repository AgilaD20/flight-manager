package com.example.demo.ui;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FlightDTOList {
	
	@Override
	public String toString() {
		return "FlightDTOList [flightList=" + flightList + "]";
	}

	private List<FlightDTO> flightList;
	
	public FlightDTOList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}
	
	public List<FlightDTO> getFlightList()
	{
		return this.flightList;
	}
	
	public void setFlightList(List<FlightDTO> flightList)
	{
		this.flightList = flightList;
	}

}
