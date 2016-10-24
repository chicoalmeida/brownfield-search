package com.brownfield.search.repository;


import com.brownfield.search.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<List<Flight>> findByOriginAndDestinationAndFlightDate(String origin, String destination, String flightDate);

    Optional<Flight> findByFlightNumberAndFlightDate(String flightNumber, String flightDate);
} 