package org.example.flights.repository;

import org.example.flights.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<FlightEntity, Long> {
}
