package com.example.demo.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.FlightNotFoundException;
import com.example.demo.model.Airline;
import com.example.demo.model.Flight;
import com.example.demo.model.Seat;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.ui.FlightDTO;
import com.example.demo.ui.FlightDTOList;
import com.example.demo.ui.FlightSearchRequest;
import com.example.demo.ui.UpdateSeatDTO;

@Service
public class FlightService {
	
	private final FlightRepository flightRepository;
	private final SeatRepository seatRepository;
	private final ModelMapper modelMapper;
	private final AirlineRepository airlineRepository;

	public FlightService(FlightRepository flightRepository, SeatRepository seatRepository, ModelMapper modelMapper, AirlineRepository airlineRepository) {
		this.flightRepository = flightRepository;
		this.seatRepository = seatRepository;
		this.modelMapper = modelMapper;
		this.airlineRepository = airlineRepository;
	}
	
	
 public FlightDTOList getAllFlightsWithCriteria(FlightSearchRequest request)
 {
	 System.out.println(request.getFromLocation()+" "+request.getDestination()+" "+request.getDepartureDate());
	 List<Flight> flights = flightRepository.getAllFlightByCriteria(request.getFromLocation(), request.getDestination(), request.getDepartureDate());
	 if(flights.isEmpty())
		{
			throw new FlightNotFoundException("Flight not found for the given criteria. Please refine the criteria");
		}
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<FlightDTO> flightList = flights.stream().map(f->{
			FlightDTO dto = modelMapper.map(f, FlightDTO.class);
			dto.setAirlineName(f.getAirline().getAirlineName());
			return dto;}).collect(Collectors.toList());
		FlightDTOList flightDTOList = new FlightDTOList(flightList);
		System.out.println(flightDTOList);
	 
	 return flightDTOList;
 }


 @Transactional
public void updateSeatsForBooking(UpdateSeatDTO request) {
	
	Optional<Flight> flight = flightRepository.findById(request.getFlighId());
	
	

	if(!flight.isPresent())
	{
		throw  new FlightNotFoundException("Flight with the given flightid is not present");
	}
	Integer availableCount = flight.get().getAvailableSeats();
	
	List<Seat> availableSeats = seatRepository.getAvailableSeats(request.getFlighId());
	
	List<String> availableSeatNumbers = availableSeats.stream().map(s->s.getSeatNumber()).collect(Collectors.toList());
	
	
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


public String getSeatById(Integer flightId) {
	return seatRepository.getAvailableSeats(flightId).stream().map(s->s.getSeatNumber()).collect(Collectors.joining("|"));
}

@Transactional
public void addSeatsofFlight(UpdateSeatDTO request) {
Optional<Flight> flight = flightRepository.findById(request.getFlighId());


	if(!flight.isPresent())
	{
		throw  new FlightNotFoundException("Flight with the given flightid is not present");
	}
	Integer availableCount = flight.get().getAvailableSeats();
	request.getSeatNumbers().stream().forEach(s-> seatRepository.addSeatsback(request.getFlighId(), s));
	flightRepository.UpdateAvailableSeats(availableCount+request.getSeatCount(),request.getFlighId());
	
}





	
	
	

}
