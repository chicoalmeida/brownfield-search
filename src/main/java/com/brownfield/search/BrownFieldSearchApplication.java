package com.brownfield.search;

import com.brownfield.search.entity.Fare;
import com.brownfield.search.entity.Flight;
import com.brownfield.search.entity.Inventory;
import com.brownfield.search.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class BrownFieldSearchApplication implements CommandLineRunner {

    @Autowired
    private FlightRepository flightRepository;


    public static void main(String[] args) {
        SpringApplication.run(BrownFieldSearchApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Flight> flights = asList(
                new Flight(null, "BF100", "SEA", "SFO", "22-JAN-16", new Fare(null, "100", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF101", "NYC", "SFO", "22-JAN-16", new Fare(null, "101", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF105", "NYC", "SFO", "22-JAN-16", new Fare(null, "105", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF106", "NYC", "SFO", "22-JAN-16", new Fare(null, "106", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF102", "CHI", "SFO", "22-JAN-16", new Fare(null, "102", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF103", "HOU", "SFO", "22-JAN-16", new Fare(null, "103", "USD"), new Inventory(null, 100)),
                new Flight(null, "BF104", "LAX", "SFO", "22-JAN-16", new Fare(null, "104", "USD"), new Inventory(null, 100))
        );

        flightRepository.save(flights);

        log.info("Looking to load flights...");
        Optional<List<Flight>> result = flightRepository.findByOriginAndDestinationAndFlightDate("NYC", "SFO", "22-JAN-16");
        if (result.isPresent()) {
            result.get().forEach(System.out::println);
        }
    }

}
