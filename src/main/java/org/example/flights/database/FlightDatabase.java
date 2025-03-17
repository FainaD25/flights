package org.example.flights.database;

import java.util.List;

// a fake database, should be replaced by an actual one
public class FlightDatabase {
    public List<Flight> getFlights() {
        return List.of(
                new Flight("Tallinn", "London", 3.0, "17-04-2025", 37.0),
                new Flight("Tallinn", "Copenhagen", 1.5, "28-03-2025", 50.5),
                new Flight("Rome", "London", 2.5, "15-04-2025", 55.0),
                new Flight("Copenhagen", "Tallinn", 1.5, "12-04-2025", 40.5),
                new Flight("Tallinn", "Rome", 3.0, "04-04-2025", 88.0),
                new Flight("London", "Rome", 2.5, "05-04-2025", 55.5),
                new Flight("Rome", "Tallinn", 3.0, "15-04-2025", 88.0),
                new Flight("Tallinn", "Copenhagen", 1.5, "28-03-2025", 45.5),
                new Flight("London", "Tallinn", 3.0, "10-04-2025", 37.0),
                new Flight("Copenhagen", "London", 1.5, "30-03-2025", 40.5)
        );
    }
}
