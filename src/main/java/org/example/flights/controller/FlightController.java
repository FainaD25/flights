package org.example.flights.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.flights.database.Flight;
import org.example.flights.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Flights", description = "Operations related to flights")
public class FlightController {
    private final FlightService flightService;

    @Operation(
            summary = "Get flights from database based on chosen parameters",
            description = "Retrieves all flights that match the given combination of parameters"
    )
    @ApiResponse(responseCode = "200", description = "The flights have been retrieved successfully")
    @GetMapping("public/flights")
    public ResponseEntity<List<Flight>> getFlights(
            @Parameter(name = "from", description = "Departure location", in = ParameterIn.QUERY)
            @RequestParam(value = "from", required = false) String from,
            @Parameter(name = "to", description = "Destination", in = ParameterIn.QUERY)
            @RequestParam(value = "to", required = false) String to,
            @Parameter(name = "date", description = "Flight date", in = ParameterIn.QUERY)
            @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "departureTime", description = "Time after which the aircraft departs", in = ParameterIn.QUERY)
            @RequestParam(value = "departureTime", required = false) String departureTime,
            @Parameter(name = "arrivalTime", description = "Time by which the aircraft will be at the destination", in = ParameterIn.QUERY)
            @RequestParam(value = "arrivalTime", required = false) String arrivalTime,
            @Parameter(name = "price", description = "Maximum ticket price", in = ParameterIn.QUERY)
            @RequestParam(value = "price", required = false) Double price) {
        return new ResponseEntity<>(flightService.getFlights(from, to, date, departureTime, arrivalTime, price), HttpStatus.OK);
    }

    @Operation(
            summary = "Get flight information",
            description = "Retrieves details for the flight with given id"
    )
    @ApiResponse(responseCode = "200", description = "Flight details have been retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No flight with given id has been found")
    @GetMapping("public/flights/{id}")
    public ResponseEntity<Flight> getFlight(
            @Parameter(name = "id", description = "Flight id", in = ParameterIn.PATH ) @PathVariable("id") long id) {
        Optional<Flight> result = flightService.getFlight(id);
        return result.map(flight -> new ResponseEntity<>(flight, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Book seats",
            description = "Mark given seats as booked"
    )
    @ApiResponse(responseCode = "200", description = "Seats have been booked successfully")
    @ApiResponse(responseCode = "404", description = "No flight with given id has been found")
    @PutMapping("public/flights/{id}/book")
    public ResponseEntity<Void> bookSeats(
            @Parameter(name = "id", description = "Flight id", in = ParameterIn.PATH ) @PathVariable("id") long id,
            @Parameter(name = "seats", description = "Seats to be booked", in = ParameterIn.QUERY) @RequestBody List<String> seats) {
        if (flightService.bookSeats(id, seats)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
