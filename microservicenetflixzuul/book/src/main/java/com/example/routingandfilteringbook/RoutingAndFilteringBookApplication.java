package com.example.routingandfilteringbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Set up a Microservice Here -
@RestController // The @RestController annotation marks the class as a controller class and ensures that return values from @RequestMapping methods in this class are automatically and appropriately converted and written directly to the HTTP response.
@SpringBootApplication
public class RoutingAndFilteringBookApplication {

  /*
  Speaking of @RequestMapping methods, we have added two: available() and checkedOut().
  They handle requests to the /available and /checked-out paths,
  each of which returns the String name of a book.
   */
  @RequestMapping(value = "/available")
  public String available() {
    return "Spring in Action";
  }

  @RequestMapping(value = "/checked-out")
  public String checkedOut() {
    return "Spring Boot in Action";
  }

  public static void main(String[] args) {
    SpringApplication.run(RoutingAndFilteringBookApplication.class, args);
  }
}
