package org.example.flights.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FlightDto {
    private long flightId;
    private String from;
    private String to;
    private LocalDate date;
    private int duration;
    private double price;
    private List<SeatDto> seats;
}
