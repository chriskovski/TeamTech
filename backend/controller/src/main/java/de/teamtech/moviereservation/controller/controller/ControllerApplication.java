package de.teamtech.moviereservation.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;


@RestController
@SpringBootApplication

public class ControllerApplication {
    
private static final Logger logger = LoggerFactory.getLogger(ControllerApplication.class);

// @Autowired
// private PrometheusMeterRegistry meterRegistry;

// private Counter bookingsCounter;

// @Bean
// public PrometheusMeterRegistry meterRegistry() {
//     return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
// }

// @Bean
// public PrometheusScrapeEndpoint prometheusEndpoint(PrometheusMeterRegistry prometheusMeterRegistry) {
//     return new PrometheusScrapeEndpoint(prometheusMeterRegistry.getPrometheusRegistry());
// }

// @Bean
// @DependsOn("meterRegistry")
// public Counter bookingsCounter(PrometheusMeterRegistry meterRegistry) {
//     bookingsCounter = Counter.builder("bookings_created_total")
//             .description("The total number of bookings created")
//             .register(meterRegistry);
//     return bookingsCounter;
// }


// @Bean
// public PrometheusMeterRegistry meterRegistry() {
//     return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
// }

// @Autowired
// private PrometheusMeterRegistry meterRegistry;

    @PostMapping("/booking")
public ResponseEntity<String> createBooking(@RequestBody String jsonPayload) {

    // meterRegistry.counter("bookings_created_total").increment();

    String usr =  System.getenv("POSTGRES_USR");
    String pwd =  System.getenv("POSTGRES_PWD");
    String host = System.getenv("POSTGRES_HOST");
    try (Connection conn = DriverManager.getConnection(host ,usr ,pwd)) {
        // Extract data from the JSON payload
        JSONObject jsonObj = new JSONObject(jsonPayload);
        String title = jsonObj.getString("title");
        String name = jsonObj.getString("name");
        int numberOfTickets = jsonObj.getInt("numberOfTickets");

        // database table
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "bookings", null);
        if (!rs.next()) {
            // Table does not exist, create it
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE bookings (id SERIAL PRIMARY KEY, title VARCHAR(255), name VARCHAR(255), number_of_tickets INTEGER)";
                stmt.executeUpdate(sql);
            }
        }

        // input data into database
        String sql = "INSERT INTO bookings (title, name, number_of_tickets) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, name);
            pstmt.setInt(3, numberOfTickets);
            pstmt.executeUpdate();
        }

        //  // increment the bookings counter
        //  bookingsCounter.increment();

        // Return a response
        return new ResponseEntity<>("Booking created successfully!", HttpStatus.CREATED);
    } catch (SQLException e) {
        logger.error("An error occured with the database connection",e.getMessage(),e);
        return new ResponseEntity<>("Error: database error", HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        return new ResponseEntity<>("Error: JSON parsing error", HttpStatus.BAD_REQUEST);
    }

}

// @Bean
// @DependsOn("meterRegistry")
// public PrometheusScrapeEndpoint prometheusEndpoint(PrometheusMeterRegistry prometheusMeterRegistry) {
//     return new PrometheusScrapeEndpoint(prometheusMeterRegistry.getPrometheusRegistry());
// }


    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
        
    }

}

