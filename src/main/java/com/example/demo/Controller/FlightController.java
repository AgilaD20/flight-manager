package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FlightService;
import com.example.demo.ui.ApiResponse;
import com.example.demo.ui.FlightDTO;
import com.example.demo.ui.FlightDTOList;
import com.example.demo.ui.FlightSearchRequest;
import com.example.demo.ui.SearchAvailableSeats;
import com.example.demo.ui.UpdateSeatDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/common/flight")
public class FlightController {

	private final FlightService flightService;

	//private final ModelMapper modelMapper;

	public FlightController(FlightService flightService, ModelMapper modelMapper) {
		this.flightService = flightService;
		//this.modelMapper = modelMapper;
	}

	@PostMapping("/flights")
	public ResponseEntity<FlightDTOList> searchFlight(@RequestBody FlightSearchRequest request) {
        
		return ResponseEntity.ok(flightService.getAllFlightsWithCriteria(request));
	}

	@PostMapping("/updateSeats")
	public ResponseEntity<ApiResponse> UdpateSeatsOfFlight(@RequestBody UpdateSeatDTO request) {
		flightService.updateSeatsForBooking(request);
		log.info("Seat got updated successfully");
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Seats got updated successfully"));
	}

	@PostMapping("/addseatsback")
	public ResponseEntity<ApiResponse> addSeatsOfFlightback(@RequestBody UpdateSeatDTO request) {
		log.info("Seat number {} needs to be added back to the schedule",request.getSeatNumbers());
		flightService.addSeatsofFlight(request);
		log.info("Seats are added back after ticket cancellation");
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Seats got added successfully"));
	}

	@GetMapping("/{flightId}")
	public ResponseEntity<FlightDTO> getFlightDetails(@PathVariable Integer flightId) {
		return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightDetails(flightId));

	}

	@PostMapping("/availableseats")
	public ResponseEntity<String> getSeatByFlightID(@RequestBody SearchAvailableSeats search) {
		log.info("Receieved request to get all the available seats on the {} on {}", search.getFlightId(), search.getDepartureDate());
		List<String> seatNumbers = flightService.getAvailableSeatNumbers(search.getDepartureDate(),
				search.getFlightId());
		String seats = seatNumbers.stream().collect(Collectors.joining("|"));
		return ResponseEntity.status(HttpStatus.OK).body(seats);

	}

}
