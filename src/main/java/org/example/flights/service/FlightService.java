package org.example.flights.service;

import org.example.flights.database.Flight;
import org.example.flights.database.FlightDatabase;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private static final FlightDatabase flightDatabase = new FlightDatabase();

    public List<Flight> getFlights(String from, String to, String date, String departureTime, String arrivalTime,
                                   Double price) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        List<Flight> result = flightDatabase.getFlights();
        if (from != null) {
            result = result.stream().filter(flight -> flight.getFrom().equals(from)).toList();
        }
        if (to != null) {
            result = result.stream().filter(flight -> flight.getTo().equals(to)).toList();
        }
        if (date != null) {
            result = result.stream().filter(flight -> flight.getDate().equals(date)).toList();
        }
        if (departureTime != null) {
            LocalTime filterTime = LocalTime.parse(departureTime, timeFormatter);
            result = result.stream()
                    .filter(flight -> !LocalTime.parse(flight.getDepartureTime(), timeFormatter).isBefore(filterTime))
                    .toList();
        }
        if (arrivalTime != null) {
            LocalTime filterTime = LocalTime.parse(arrivalTime, timeFormatter);

            result = result.stream()
                    .filter(flight -> !LocalTime.parse(flight.getArrivalTime(), timeFormatter).isAfter(filterTime))
                    .toList();
        }
        if (price != null) {
            result = result.stream().filter(flight -> flight.getPrice() == price).toList();
        }
        return result;
    }

    public Optional<Flight> getFlight(long id) {
        for (Flight flight: flightDatabase.getFlights()) {
            if (flight.getId() == id) {
                return Optional.of(flight);
            }
        }
        return Optional.empty();
    }
}
