package com.example.demo.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.FlightNotFoundException;
import com.example.demo.model.Flight;
import com.example.demo.model.Seat;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.ui.FlightDTO;
import com.example.demo.ui.FlightSearchRequest;
import com.example.demo.ui.UpdateSeatDTO;

@Service
public class FlightService {
	
	private final FlightRepository flightRepository;
	private final SeatRepository seatRepository;
	private final ModelMapper modelMapper;

	public FlightService(FlightRepository flightRepository, SeatRepository seatRepository, ModelMapper modelMapper) {
		this.flightRepository = flightRepository;
		this.seatRepository = seatRepository;
		this.modelMapper = modelMapper;
	}
	
	
 public List<Flight> getAllFlightsWithCriteria(FlightSearchRequest request)
 {
	 //System.out.println(request.getFromLocation()+" "+request.getDestination()+" "+Date.valueOf(request.getDepartureTime())+" "+Date.valueOf(request.getArrivalTime()));
	 return flightRepository.getAllFlightByCriteria(request.getFromLocation(), request.getDestination(), request.getDepartureTime(),request.getArrivalTime());
 }


 @Transactional
public void updateSeatsForBooking(UpdateSeatDTO request) {
	
	Optional<Flight> flight = flightRepository.findById(request.getFlighId());
	
	

	if(!flight.isPresent())
	{
		throw  new FlightNotFoundException("Flight with the given flightid is not present");
	}
	Integer availableCount = flight.get().getAvailableSeats();
	System.out.println(availableCount);
	List<Seat> availableSeats = seatRepository.getAvailableSeats(request.getFlighId());
	System.out.println(availableSeats);
	List<String> availableSeatNumbers = availableSeats.stream().map(s->s.getSeatNumber()).collect(Collectors.toList());
	System.out.println(availableSeatNumbers);
	
	if(!availableSeatNumbers.containsAll(request.getSeatNumbers()))
	{
		throw  new FlightNotFoundException("Chosen seats are not available");
		
	}
	request.getSeatNumbers().stream().forEach(s-> seatRepository.updateBookedSeats(request.getFlighId(), s));
	flightRepository.UpdateAvailableSeats(availableCount-request.getSeatCount(),request.getFlighId());
	
	
}


public FlightDTO getFlightDetails(Integer flightId) {
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	Optional<Flight> flight = flightRepository.findById(flightId);
	if(flight.isEmpty())
	{
		throw  new FlightNotFoundException("Selected flight is not available");
	}
	FlightDTO flightDTO = modelMapper.map(flight.get(), FlightDTO.class);
	return flightDTO;
}


	
	
	

}
