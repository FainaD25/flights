package org.example.flights.database;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Flight {
    private static final Random random = new Random();
    private static int flightId = 0;
    private final int id;
    private final String from;
    private final String to;
    private final String date;
    private final String departureTime;
    private final String arrivalTime;
    private final double price;
    private final List<Seat> seats;
    private static final List<String> columns = List.of("A", "B", "C", "D", "E", "F");

    public Flight (String from, String to, String date, String departureTime, String arrivalTime, double price) {
        this.id = flightId++;
        this.from = from;
        this.to = to;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

        this.price = price;
        this.seats = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            for (String letter: columns) {
                seats.add(new Seat(i,letter));
            }
        }
        markRandomSeatsAsTaken();
    }

    private void markRandomSeatsAsTaken() {
        int totalSeats = seats.size();
        int seatsToMark = random.nextInt(totalSeats / 2);

        for (int i = 0; i < seatsToMark; i++) {
            int randomIndex = random.nextInt(totalSeats);
            Seat seat = seats.get(randomIndex);
            if (!seat.isTaken()) {
                seat.setTaken(true);
            }
        }
    }
}
