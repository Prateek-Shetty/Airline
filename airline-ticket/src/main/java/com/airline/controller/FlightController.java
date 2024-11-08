package com.airline.controller;

import com.airline.model.Flight;
import com.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    // Get all flights
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Get a specific flight by ID
    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable String id) {
        return flightRepository.findById(id).orElse(null);
    }

    // Create a new flight
    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightRepository.save(flight);
    }

    // Update an existing flight
    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable String id, @RequestBody Flight flight) {
        flight.setId(id);
        return flightRepository.save(flight);
    }

    // Delete a flight
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightRepository.deleteById(id);
    }
}
