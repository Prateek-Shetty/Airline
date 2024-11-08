package com.airline.repository;

import com.airline.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {
    // You can add custom queries here, if needed
}
