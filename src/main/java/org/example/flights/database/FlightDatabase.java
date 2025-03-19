package org.example.flights.database;

import lombok.Getter;

import java.util.List;

// a fake database, should be replaced by an actual one
@Getter
public class FlightDatabase {
    private final List<Flight> flights;
    public FlightDatabase() {
        this.flights = List.of(
                new Flight("Tallinn", "London", "17-04-2025", "12:30","15:30", 37.0),
                new Flight("Tallinn", "Copenhagen", "28-03-2025",  "14:45","16:15", 50.5),
                new Flight("Rome", "London", "15-04-2025",  "10:15","12:45", 55.0),
                new Flight("Copenhagen", "Tallinn", "12-04-2025",  "17:30","19:00", 40.5),
                new Flight("Tallinn", "Rome", "04-04-2025",  "9:00","12:00", 88.0),
                new Flight("London", "Rome", "05-04-2025",  "18:15","20:45", 55.5),
                new Flight("Rome", "Tallinn", "15-04-2025",  "11:30","14:30", 88.0),
                new Flight("Tallinn", "Copenhagen", "28-03-2025",  "15:45","17:15", 45.5),
                new Flight("London", "Tallinn", "10-04-2025",  "19:00","22:00", 37.0),
                new Flight("Copenhagen", "London", "30-03-2025",  "13:30","15:00", 40.5));
    }
}
