package org.example.flights.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="flights")
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long flightId;
    @Column
    private String from;
    @Column
    private String to;
    @Column
    private LocalDate date;
    @Column
    private int duration;
    @Column
    private double price;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<SeatEntity> seats;
}
