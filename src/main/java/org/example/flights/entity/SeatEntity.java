package org.example.flights.entity;

import jakarta.persistence.*;
import org.example.flights.database.Flight;

@Entity
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private boolean isOccupied;
    private boolean isWindow;
    private boolean hasExtraLegroom;
    private boolean isNearExit;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
