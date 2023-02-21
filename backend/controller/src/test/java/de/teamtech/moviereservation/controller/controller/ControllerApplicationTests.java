// package de.teamtech.moviereservation.controller.controller;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;

// import org.json.JSONObject;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class ControllerApplicationTests {

//     @LocalServerPort
//     private int port;

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @Test
//     public void createBookingTest() throws Exception {
//         // Prepare request
//         String title = "Avengers";
//         String name = "John";
//         int numberOfTickets = 2;
//         JSONObject requestBody = new JSONObject();
//         requestBody.put("title", title);
//         requestBody.put("name", name);
//         requestBody.put("numberOfTickets", numberOfTickets);
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

//         // Make request
//         ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/booking", request, String.class);

//         // Verify response
//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//         assertThat(response.getBody()).isEqualTo("Booking created successfully!");

//         // Verify database
//         Class.forName("org.postgresql.Driver");
//         String url = "jdbc:postgresql://20.4.203.253/postgres";
//         Connection conn = DriverManager.getConnection(url, "root", "mypassword");
//         Statement stmt = conn.createStatement();
//         ResultSet rs = stmt.executeQuery("SELECT * FROM bookings WHERE title='" + title + "'");
//         assertTrue(rs.next());
//         assertThat(rs.getString("name")).isEqualTo(name);
//         assertThat(rs.getInt("number_of_tickets")).isEqualTo(numberOfTickets);
//     }
// }
