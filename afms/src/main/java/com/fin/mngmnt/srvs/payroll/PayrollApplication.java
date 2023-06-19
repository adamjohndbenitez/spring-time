package com.fin.mngmnt.srvs.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayrollApplication {

    public static void main(String... args) {
        SpringApplication.run(PayrollApplication.class, args);
    }
}

/*

Running application payroll needs authorization.


Resolutions:
What is username and password when starting Spring Boot with Tomcat?
https://stackoverflow.com/questions/37285016/what-is-username-and-password-when-starting-spring-boot-with-tomcat
How to set the authorization header using cURL:
https://stackoverflow.com/questions/3044315/how-to-set-the-authorization-header-using-curl

 */


//The default interactive shell is now zsh.
//To update your account to use zsh, please run `chsh -s /bin/zsh`.
//For more details, please visit https://support.apple.com/kb/HT208050.
//abenitezMBP:spring-time abenitez$ curl -v localhost:8080/employees
//*   Trying 127.0.0.1:8080...
//* Connected to localhost (127.0.0.1) port 8080 (#0)
//> GET /employees HTTP/1.1
//> Host: localhost:8080
//> User-Agent: curl/7.79.1
//> Accept: */*
//    >
//    * Mark bundle as not supporting multiuse
//< HTTP/1.1 401
//< Vary: Origin
//< Vary: Access-Control-Request-Method
//< Vary: Access-Control-Request-Headers
//< Set-Cookie: JSESSIONID=FB049B16A3014B8C5BC6248D72EA6082; Path=/; HttpOnly
//< WWW-Authenticate: Basic realm="Realm"
//< X-Content-Type-Options: nosniff
//< X-XSS-Protection: 1; mode=block
//< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
//< Pragma: no-cache
//< Expires: 0
//< X-Frame-Options: DENY
//< Content-Type: application/json
//< Transfer-Encoding: chunked
//< Date: Fri, 16 Jun 2023 20:26:23 GMT
//<
//* Connection #0 to host localhost left intact
//    {"timestamp":"2023-06-16T20:26:23.156+00:00","status":401,"error":"Unauthorized","path":"/employees"}abenitezMBP:spring-time abenitez$ curl --user name:30e27aec-d1d5-494a-90ff-64be5042dd43 http:localhost:8080/employees
//    curl: (3) URL using bad/illegal format or missing URL
//    abenitezMBP:spring-time abenitez$ curl --user name:30e27aec-d1d5-494a-90ff-64be5042dd43 localhost:8080/employees
//    abenitezMBP:spring-time abenitez$ curl -v localhost:8080/employees
//    *   Trying 127.0.0.1:8080...
//    * Connected to localhost (127.0.0.1) port 8080 (#0)
//    > GET /employees HTTP/1.1
//    > Host: localhost:8080
//    > User-Agent: curl/7.79.1
//    > Accept: */*
//>
//* Mark bundle as not supporting multiuse
//< HTTP/1.1 401
//< Vary: Origin
//< Vary: Access-Control-Request-Method
//< Vary: Access-Control-Request-Headers
//< Set-Cookie: JSESSIONID=C8E465A14577310D4F87639F569DE1E4; Path=/; HttpOnly
//< WWW-Authenticate: Basic realm="Realm"
//< X-Content-Type-Options: nosniff
//< X-XSS-Protection: 1; mode=block
//< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
//< Pragma: no-cache
//< Expires: 0
//< X-Frame-Options: DENY
//< Content-Type: application/json
//< Transfer-Encoding: chunked
//< Date: Fri, 16 Jun 2023 20:29:17 GMT
//<
//* Connection #0 to host localhost left intact
//{"timestamp":"2023-06-16T20:29:17.095+00:00","status":401,"error":"Unauthorized","path":"/employees"}abenitezMBP:spring-time abenitez$ curl --user name:30e27aec-d1dv localhost:8080/employees
//*   Trying 127.0.0.1:8080...
//* Connected to localhost (127.0.0.1) port 8080 (#0)
//> GET /employees HTTP/1.1
//> Host: localhost:8080
//> User-Agent: curl/7.79.1
//> Accept: */*
//    >
//    * Mark bundle as not supporting multiuse
//< HTTP/1.1 401
//< Vary: Origin
//< Vary: Access-Control-Request-Method
//< Vary: Access-Control-Request-Headers
//< Set-Cookie: JSESSIONID=D9C88D4C6926FA08EC36B1B7737E9430; Path=/; HttpOnly
//< WWW-Authenticate: Basic realm="Realm"
//< X-Content-Type-Options: nosniff
//< X-XSS-Protection: 1; mode=block
//< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
//< Pragma: no-cache
//< Expires: 0
//< X-Frame-Options: DENY
//< Content-Type: application/json
//< Transfer-Encoding: chunked
//< Date: Fri, 16 Jun 2023 20:29:26 GMT
//<
//* Connection #0 to host localhost left intact
//    {"timestamp":"2023-06-16T20:29:26.453+00:00","status":401,"error":"Unauthorized","path":"/employees"}abenitezMBP:spring-time abenitez$ curl --user user:30e27aec-d1d5-494a-90ff-64be5042dd43  localhost:8080/employees
//    abenitezMBP:spring-time abenitez$