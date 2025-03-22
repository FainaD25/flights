package org.example.flights.controller;

import lombok.RequiredArgsConstructor;
import org.example.flights.database.Flight;
import org.example.flights.database.Seat;
import org.example.flights.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class FlightController {
    private final FlightService flightService;

    @GetMapping("public/flights")
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "departureTime", required = false) String departureTime,
            @RequestParam(value = "arrivalTime", required = false) String arrivalTime,
            @RequestParam(value = "price", required = false) Double price) {
        return new ResponseEntity<>(flightService.getFlights(from, to, date, departureTime, arrivalTime, price), HttpStatus.OK);
    }

    @GetMapping("public/flights/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable("id") long id) {
        Optional<Flight> result = flightService.getFlight(id);
        return result.map(flight -> new ResponseEntity<>(flight, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("public/flights/{id}/book")
    public ResponseEntity<Void> bookSeats(@PathVariable("id") long id, @RequestBody List<String> seats) {
        if (flightService.bookSeats(id, seats)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
