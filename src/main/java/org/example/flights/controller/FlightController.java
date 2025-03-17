package org.example.flights.controller;

import lombok.RequiredArgsConstructor;
import org.example.flights.database.Flight;
import org.example.flights.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestParam(value = "duration", required = false) double duration,
            @RequestParam(value = "price", required = false) double price) {
        return new ResponseEntity<>(flightService.getFlights(from, to, date, duration, price), HttpStatus.OK);
    }
}
