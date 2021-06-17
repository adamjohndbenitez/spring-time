package com.example.demo.authenticatingldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * In Spring, REST endpoints are Spring MVC controllers.
 * This class controller handles a GET / request by returning a simple message:
 *
 * The entire class is marked up with @RestController so that Spring MVC can
 * autodetect the controller (by using its built-in scanning features) and
 * automatically configure the necessary web routes.
 *
 * @RestController also tells Spring MVC to write the text directly into the
 * HTTP response body, because there are no views. Instead, when you visit the page,
 * you get a simple message in the browser (because the focus of this guide is
 * securing the page with LDAP).
 */
//@RestController
//public class HomeController {
//    @GetMapping("/welcome")
//    public String index() {
//        return "Welcome to the home page!";
//    }
//}

//Commented-out classes in package com.example.demo.authenticatingldap.*
//FIXME: Caused by: java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs.