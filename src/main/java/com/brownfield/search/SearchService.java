package com.brownfield.search;

import com.brownfield.search.entity.Flight;
import com.brownfield.search.exception.NoFlightAvailableException;
import com.brownfield.search.model.SearchQuery;
import com.brownfield.search.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    private FlightRepository flightRepository;

    @Autowired
    public SearchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> search(final SearchQuery query) {
        return flightRepository
                .findByOriginAndDestinationAndFlightDate(query.getOrigin(),
                        query.getDestination(),
                        query.getFlightDate())
                .map(this::getFlightWithSeatsAvailable)
                .orElseThrow(NoFlightAvailableException::new);
    }

    private List<Flight> getFlightWithSeatsAvailable(final List<Flight> flights) {
        return flights
                .stream()
                .filter(flight -> flight.getInventory().getCount() < 0)
                .collect(Collectors.toList());
    }

    public void updateInventory(final String flightNumber, final String flightDate, final int inventory) {
        log.info("Updating inventory for flight " + flightNumber + " innventory " + inventory);
        Optional<Flight> flight = flightRepository.findByFlightNumberAndFlightDate(flightNumber, flightDate);
        flight.ifPresent(f -> {
            f.getInventory().setCount(inventory);
            flightRepository.save(f);
        });
    }
}
