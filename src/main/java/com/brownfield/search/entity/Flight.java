package com.brownfield.search.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fare_id")
    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String flightDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inv_Id")
    Inventory inventory;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fare_Id")
    private Fare fare;
}
