package org.example.flights.service;

import org.example.flights.database.Flight;
import org.example.flights.database.FlightDatabase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private static final FlightDatabase flightDatabase = new FlightDatabase();

    public List<Flight> getFlights(String from, String to, String date,
                                   Double duration, Double price) {
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
        if (duration != null) {
            result = result.stream().filter(flight -> flight.getDuration() == duration).toList();
        }
        if (price != null) {
            result = result.stream().filter(flight -> flight.getPrice() == price).toList();
        }
        return result;
    }

}
