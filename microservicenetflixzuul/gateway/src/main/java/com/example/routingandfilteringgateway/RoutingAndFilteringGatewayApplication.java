package com.example.routingandfilteringgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.example.routingandfilteringgateway.filters.pre.SimpleFilter;

// Create an Edge Service Here -
@EnableZuulProxy // Spring Cloud Netflix includes an embedded Zuul proxy, which you can enable with the @EnableZuulProxy annotation. This will turn the Gateway application into a reverse proxy that forwards relevant calls to other services — such as our book application.
@SpringBootApplication
public class RoutingAndFilteringGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoutingAndFilteringGatewayApplication.class, args);
  }

  @Bean
  public SimpleFilter simpleFilter() {
    return new SimpleFilter();
  }

}
