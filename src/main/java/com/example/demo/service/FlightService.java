package com.example.demo.service;

import java.time.LocalDate;
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
import com.example.demo.model.SeatMapper;
import com.example.demo.repository.AirlineRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SeatMapperRepository;
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
	//private final AirlineRepository airlineRepository;
	private final ScheduleRepository scheduleRepository;
	private final SeatMapperRepository seatMapperRepository;

	public FlightService(FlightRepository flightRepository, SeatRepository seatRepository, ModelMapper modelMapper,
			AirlineRepository airlineRepository, ScheduleRepository scheduleRepository,
			SeatMapperRepository seatMapperRepository) {
		this.flightRepository = flightRepository;
		this.seatRepository = seatRepository;
		this.modelMapper = modelMapper;
		//this.airlineRepository = airlineRepository;
		this.scheduleRepository = scheduleRepository;
		this.seatMapperRepository = seatMapperRepository;
	}

	public FlightDTOList getAllFlightsWithCriteria(FlightSearchRequest request) {
		System.out
				.println(request.getFromLocation() + " " + request.getDestination() + " " + request.getDepartureDate());
		LocalDate departureDate = request.getDepartureDate();
		Integer day = departureDate.getDayOfWeek().getValue() - 1;
		// scheduleDays sch = new scheduleDays(1);
		System.out.println(day);

		List<Flight> flights = flightRepository.getAllFlightByCriteria(request.getFromLocation(),
				request.getDestination(), request.getDepartureDate());
		if (flights.isEmpty()) {
			throw new FlightNotFoundException("Flight not found for the given criteria. Please refine the criteria");
		}

		List<Flight> Filteredflights = flights.stream().filter(t -> scheduleExists(t.getFlightID(), day))
				.collect(Collectors.toList());
		System.out.println(Filteredflights.size());
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<FlightDTO> flightList = Filteredflights.stream().map(f -> {
			FlightDTO dto = modelMapper.map(f, FlightDTO.class);
			dto.setAirlineName(f.getAirline().getAirlineName());
			return dto;
		}).collect(Collectors.toList());

		return new FlightDTOList(flightList);

		// return filterFutureFlights(flightList);
	}

	private Boolean scheduleExists(Integer flightId, Integer day) {
		if (scheduleRepository.checkifExists(flightId, day) == null) {
			return false;
		}
		return true;
	}

	@Transactional
	public void updateSeatsForBooking(UpdateSeatDTO request) {

		Optional<Flight> flight = flightRepository.findById(request.getFlighId());

		if (!flight.isPresent()) {
			throw new FlightNotFoundException("Flight with the given flightid is not present");
		}
		List<String> availableSeatNumbers = getAvailableSeatNumbers(request.getDepartureDate(), request.getFlighId());

		if (!availableSeatNumbers.containsAll(request.getSeatNumbers())) {
			throw new FlightNotFoundException("Chosen seats are not available");

		}
		request.getSeatNumbers().stream().forEach(s -> {
			SeatMapper sm = new SeatMapper();
			sm.setFlightId(request.getFlighId());
			sm.setSeatNumber(s);
			sm.setDepartureDate(request.getDepartureDate());
			seatMapperRepository.save(sm);
		});

		

	}

	public List<String> getAvailableSeatNumbers(LocalDate departureDate, Integer flightId) {
		Optional<Flight> flight = flightRepository.findById(flightId);
		List<SeatMapper> bookedSeatList = seatMapperRepository.findByFlightIdAndDepartureDate(flightId, departureDate);
		List<String> bookedSeatNumbers = bookedSeatList.stream().map(bs -> bs.getSeatNumber())
				.collect(Collectors.toList());
		List<Seat> allSeats = seatRepository.findByFlight(flight.get());
		List<String> allSeatNumbers = allSeats.stream().map(s -> s.getSeatNumber()).collect(Collectors.toList());
		return allSeatNumbers.stream().filter(s -> !bookedSeatNumbers.contains(s)).collect(Collectors.toList());

	}

	public FlightDTO getFlightDetails(Integer flightId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (flight.isEmpty()) {
			throw new FlightNotFoundException("Selected flight is not available");
		}
		FlightDTO flightDTO = modelMapper.map(flight.get(), FlightDTO.class);
		return flightDTO;
	}

	public String getSeatById(Integer flightId) {
		return seatRepository.getAvailableSeats(flightId).stream().map(s -> s.getSeatNumber())
				.collect(Collectors.joining("|"));
	}

	@Transactional
	public void addSeatsofFlight(UpdateSeatDTO request) {
		Optional<Flight> flight = flightRepository.findById(request.getFlighId());

		if (!flight.isPresent()) {
			throw new FlightNotFoundException("Flight with the given flightid is not present");
		}

		Integer flightId = flight.get().getFlightID();
		LocalDate departureDate = request.getDepartureDate();

		request.getSeatNumbers().stream().forEach(
				s -> seatMapperRepository.deleteByFlightIdAndSeatNumberAndDepartureDate(flightId, s, departureDate));

	}

	

}
