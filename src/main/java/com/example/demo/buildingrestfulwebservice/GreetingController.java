package com.example.demo.buildingrestfulwebservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This controller is concise and simple, but there is plenty going on under the hood.
 */
@RestController // which marks the class as a controller where every method returns a domain object instead of a view. shorthand @Controller and @ResponseBody
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) { // @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
        // If the name parameter is absent in the request, the defaultValue of World is used.
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /*
    NOTE: There are companion annotations for other HTTP verbs (e.g. @PostMapping for POST).
    There is also a @RequestMapping annotation that they all derive from,
    and can serve as a synonym (e.g. @RequestMapping(method=GET)).
     */

    /*
    Traditional MVC controller:
    - relying on a view technology to perform server-side rendering of the greeting data to HTML
    VS.
    RESTful web service controller:
    - is the way that the HTTP response body is created.
    - it populates and returns a Greeting object data will be written directly to the HTTP response as JSON
     */

}
