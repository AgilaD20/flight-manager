package com.example.demo.Controller;



import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.FlightNotFoundException;
import com.example.demo.model.Flight;
import com.example.demo.service.FlightService;
import com.example.demo.ui.ApiResponse;
import com.example.demo.ui.FlightDTO;
import com.example.demo.ui.FlightDTOList;
import com.example.demo.ui.FlightSearchRequest;
import com.example.demo.ui.UpdateSeatDTO;

@RestController
@RequestMapping("/api/v1.0/common/flight")
public class FlightController {
	
	private final FlightService flightService;
	
	private final ModelMapper modelMapper;
	
	
	public FlightController(FlightService flightService, ModelMapper modelMapper) {
		this.flightService = flightService;
		this.modelMapper = modelMapper;
	}


	@PostMapping("/flights")
	public ResponseEntity<FlightDTOList> searchFlight(@RequestBody FlightSearchRequest request)
	{
		System.out.println("Exchange made it here");
		List<Flight> flights = flightService.getAllFlightsWithCriteria(request);
		System.out.println("Exchange made it here 2");
		if(flights.isEmpty())
		{
			throw new FlightNotFoundException("Flight not found for the given criteria. Please refine the criteria");
		}
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<FlightDTO> flightList = flights.stream().map(f->modelMapper.map(f, FlightDTO.class)).collect(Collectors.toList());
		FlightDTOList FlightDTOList = new FlightDTOList(flightList);
		return ResponseEntity.ok(FlightDTOList);
	}
	
	@PostMapping("/udpateSeats")
	public ResponseEntity<ApiResponse> UdpateSeatsOfFlight(@RequestBody UpdateSeatDTO request)
	{
		flightService.updateSeatsForBooking(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Seats got updated successfully"));
	}
	
	@GetMapping("/{flightId}")
	public ResponseEntity<FlightDTO> getFlightDetails(@PathVariable Integer flightId)
	{
		return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightDetails(flightId));
		
	}

}
