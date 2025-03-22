package org.example.flights.database;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Schema(description = "Data transfer object for flight")
public class Flight {
    private static final Random random = new Random();
    private static int flightId = 0;
    @Schema(description = "Unique flight id", example = "1")
    private final int id;
    @Schema(description = "Departure location", example = "Tallinn")
    private final String from;
    @Schema(description = "Destination", example = "London")
    private final String to;
    @Schema(description = "Flight date", example = "28-03-2025")
    private final String date;
    @Schema(description = "Departure time", example = "10:00")
    private final String departureTime;
    @Schema(description = "Arrival time", example = "13:00")
    private final String arrivalTime;
    @Schema(description = "Ticket price", example = "37")
    private final double price;
    @Schema(description = "Seats list")
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
