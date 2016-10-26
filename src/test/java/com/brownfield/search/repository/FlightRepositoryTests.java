package com.brownfield.search.repository;

import com.brownfield.search.entity.Fare;
import com.brownfield.search.entity.Flight;
import com.brownfield.search.entity.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightRepositoryTests {

    private static final String FLIGHT_NUMBER = "101";
    private static final String FLIGHT_DATE = "16-10-2016";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightRepository target;

    private Flight flightFixture;


    @Before
    public void setUp() {

        Fare fare = new Fare(null, "101", "BRL");
        Inventory inventory = new Inventory(null, 100);

        flightFixture = Flight.builder()
                .flightNumber(FLIGHT_NUMBER)
                .flightDate(FLIGHT_DATE)
                .destination("SPO")
                .origin("RJ")
                .fare(fare)
                .inventory(inventory)
                .build();

        entityManager.persist(flightFixture);

    }

    @Test
    public void findByFlightNumberAndFlightDate_ShouldReturnAValidFlightObject_WhenValidParamsArePassed() throws Exception {
        Optional<Flight> result = target.findByFlightNumberAndFlightDate(FLIGHT_NUMBER, FLIGHT_DATE);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(flightFixture));

    }

    @Test
    public void findByFlightNumberAndFlightDate_ShouldReturnAOptionlEmpty_WhenWrongParamsArePassed() throws Exception {
        Optional<Flight> result = target.findByFlightNumberAndFlightDate("TESTE", FLIGHT_DATE);

        assertThat(result.isPresent(), not(true));
    }

    @Test
    public void findByOriginAndDestinationAndFlightDate_ShouldReturnAListOfAvailableFights_WhenValidParamsArePassed() {

        Optional<List<Flight>> result = target.findByOriginAndDestinationAndFlightDate("RJ", "SPO", FLIGHT_DATE);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().size(), equalTo(1));
        assertThat(result.get(), hasItem(flightFixture));

    }


}