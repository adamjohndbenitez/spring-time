package com.example.demo;

import com.example.demo.accessingdataneo4j.Person;
import com.example.demo.accessingdataneo4j.PersonRepository;
import com.example.demo.consumingrest.Quote;
//import com.example.demo.messagingrabbitmq.Receiver;
import com.example.demo.messagingrabbitmq.ReceiverRabbitMQ;
import com.example.demo.messagingredis.ReceiverRedis;
import com.example.demo.messagingredis.ReceiverRedis;
import com.example.demo.relationaldataaccess.Customer;
import com.example.demo.uploadingfiles.storage.StorageProperties;
import com.example.demo.uploadingfiles.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import org.springframework.jdbc.core.JdbcOperations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Test the Service
 * Now that the service is up,
 * visit http://localhost:8080/greeting,
 * where you should see: {"id":1,"content":"Hello, World!"}
 * Provide a name query string parameter by visiting http://localhost:8080/greeting?name=User.
 * Notice how the value of the content attribute changes from Hello, World!
 * to Hello, User!, as the following listing shows:{"id":2,"content":"Hello, User!"}
 * This change demonstrates that the @RequestParam arrangement in GreetingController
 * is working as expected. The name parameter has been given a default value of World
 * but can be explicitly overridden through the query string.
 * Notice also how the id attribute has changed from 1 to 2.
 * This proves that you are working against the same GreetingController instance across
 * multiple requests and that its counter field is being incremented on each call as expected.
 * </p>
 * <p>
 *     @SpringBootApplication is a convenience annotation that adds all of the following:
 *     - @Configuration: Tags the class as a source of bean definitions for the application context.
 *     - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
 *     - @ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.
 * </p>
 */
@SpringBootApplication
@EnableScheduling //The @EnableScheduling annotation ensures that a background task executor is created. Without it, nothing gets scheduled.
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication implements CommandLineRunner {

	/* https://github.com/spring-guides/gs-consuming-rest#finishing-the-application
	   to show quotations from our RESTful source. You need to add:
	   - A logger, to send output to the log (the console, in this example).
	   - A RestTemplate, which uses the Jackson JSON processing library to process the incoming data.
	   - A CommandLineRunner that runs the RestTemplate (and, consequently, fetches our quotation) on startup. */
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(DemoApplication.class, args).close();
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		ReceiverRedis receiverRedis = ctx.getBean(ReceiverRedis.class);

		while (receiverRedis.getCount() == 0) {
			log.info("Sending message...");
			template.convertAndSend("chat", "Hello from Redis!");
			Thread.sleep(500L);
		}

		System.exit(0);
	}

	// Consuming a RESTful Web Service
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// https://github.com/spring-guides/gs-consuming-rest#fetching-a-rest-resource
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://quoters.apps.pcfone.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}

	// Accessing Relational Data using JDBC with Spring
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	JdbcOperations jdbcOperations;

	@Override
	public void run(String... args) throws Exception {
		log.info("Creating tables");

		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(" +
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

		// Split up the array of whole names into an array of first/last names.
		List<Object[]> splitUpNames = Arrays.asList("Adam Benitez", "Leah Maranan", "Melvin Guadez", "Tina Nguyen")
				.stream().map(name -> name.split(" ")).collect(Collectors.toList());

		// Use a Java 8 stream to print out each tuple of the list.
		splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

		// Uses JdbcTemplate's batchUpdate operation to bulk load data.
		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

		log.info("Querying for customer records where first_name = 'Josh':");
		jdbcOperations.query( //FIXME: Replace deprecated query method.
				"SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
				new Object[] { "Adam" }, (rs, rowNum) ->
						new Customer(
								rs.getLong("id"),
								rs.getString("first_name"),
								rs.getString("last_name")
						)
		).forEach(customer -> log.info(customer.toString()));
	}
	/* https://github.com/spring-guides/gs-relational-data-access#store-and-retrieve-data
	   Spring Boot supports H2 (an in-memory relational database engine) and automatically creates a connection. Because
	   we use spring-jdbc, Spring Boot automatically creates a JdbcTemplate. The @Autowired JdbcTemplate field automatically
	   loads it and makes it available. This Application class implements Spring Boot’s CommandLineRunner, which means
	   it will execute the run() method after the application context is loaded. First, install some DDL by using the
	   execute method of JdbcTemplate. Second, take a list of strings and, by using Java 8 streams, split them into
	   firstname/lastname pairs in a Java array. Then install some records in your newly created table by using the
	   batchUpdate method of JdbcTemplate. The first argument to the method call is the query string. The last argument
	   (the array of Object instances) holds the variables to be substituted into the query where the ? characters are.
	   For single insert statements, the insert method of JdbcTemplate is good. However, for multiple inserts, it is
	   better to use batchUpdate. Use ? for arguments to avoid SQL injection attacks by instructing JDBC to bind variables.
	   Finally, use the query method to search your table for records that match the criteria. You again use the ? arguments
	   to create parameters for the query, passing in the actual values when you make the call. The last argument is a
	   Java 8 lambda that is used to convert each result row into a new Customer object. */


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args -> {
			storageService.deleteAll();
			storageService.init();
		});
	}
	/* That runs the server-side piece that receives file uploads. Logging output is displayed.
	   The service should be up and running within a few seconds. With the server running,
	   you need to open a browser and visit http://localhost:8080/ to see the upload form.
	   Pick a (small) file and press Upload. You should see the success page from the controller.
	   If you choose a file that is too large, you will get an ugly error page. You should then
	   see a line resembling the following in your browser window:
	   “You successfully uploaded <name of your file>!” */

	// Messaging with Redis
	@Bean
	RedisMessageListenerContainer containerRedis(RedisConnectionFactory connectionFactory, org.springframework.data.redis.listener.adapter.MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener((org.springframework.data.redis.connection.MessageListener) listenerAdapter, new PatternTopic("chat"));
		return container;
	}
	/* The bean defined in the listenerAdapter method is registered as a message listener
	   in the message listener container defined in container and will listen for messages
	   on the chat topic. */

	@Bean
	org.springframework.data.redis.listener.adapter.MessageListenerAdapter listenerAdapterRedis(ReceiverRedis receiver) {
		return new org.springframework.data.redis.listener.adapter.MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	ReceiverRedis receiver() {
		return new ReceiverRedis();
	}
	/* The message listener adapter is also configured to call the receiveMessage() method
	   on Receiver when a message arrives. */

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
	/* The connection factory and message listener container beans are all you need to listen
	   for messages. To send a message, you also need a Redis template. Here, it is a bean
	   configured as a StringRedisTemplate, an implementation of RedisTemplate that is focused
	   on the common use of Redis, where both keys and values are String instances. */

	// Messaging with RabbitMQ
	public static final String TOPIC = "spring-boot-exchange";
	public static final String QUEUE = "spring-boot";

	@Bean
	Queue queue() {
		return new Queue(QUEUE, false);
	}
	/* The queue() method creates an AMQP queue.  */

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC);
	}
	/* The exchange() method creates a topic exchange. */

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}
	/* The binding() method binds these two together,
	   defining the behavior that occurs when RabbitTemplate publishes to an exchange. */
	/* Spring AMQP requires that the Queue, the TopicExchange,
	   and the Binding be declared as top-level Spring beans in order to be set up properly. */
	/* In this case, we use a topic exchange, and the queue is bound with a routing key of foo.bar.#,
	   which means that any messages sent with a routing key that begins with foo.bar. are routed to the queue. */

	@Bean
	SimpleMessageListenerContainer containerRabbitMQ(ConnectionFactory connectionFactory,
											 org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	/* The bean defined in the listenerAdapter() method is registered as a message listener
	   in the container (defined in container()). It listens for messages on the spring-boot
	   queue. Because the Receiver class is a POJO, it needs to be wrapped in the MessageListenerAdapter,
	   where you specify that it invokes receiveMessage. */

	@Bean
	org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter listenerAdapterRabbitMQ(ReceiverRabbitMQ receiver) {
		return new org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter(receiver, "receiveMessage");
	} //'listenerAdapter(Receiver)' is already defined in 'com.example.demo.DemoApplication' under from rabbitmq.
	/* Because the Receiver class is a POJO, it needs to be wrapped in
	   a message listener adapter that implements the MessageListener interface
	   (which is required by addMessageListener()). The message listener adapter is also
	   configured to call the receiveMessage() method on Receiver when a message arrives. */

	//TODO: we can do more - https://docs.spring.io/spring-amqp/reference/html/#preface

	@Bean
	CommandLineRunner demo(PersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll();

			Person jestoni = new Person("Jestoni Baguio");
			Person armon = new Person("Armon Abrenica");
			Person mikyu = new Person("Michelle Maglasang");

			List<Person> team = Arrays.asList(jestoni, armon, mikyu);

			log.info("Before linking up with Neo4j...");

			team.forEach(person -> log.info("\t" + person.toString()));

			personRepository.save(jestoni);
			personRepository.save(mikyu);
			personRepository.save(armon);

			jestoni = personRepository.findByName(jestoni.getName());
			jestoni.worksWith(armon);
			jestoni.worksWith(mikyu);
			personRepository.save(jestoni);

			armon = personRepository.findByName(armon.getName());
			armon.worksWith(mikyu);
			// We already know that armon works with jestoni.
			personRepository.save(armon);

			// We already know craig works with mikyu and jestoni.

			log.info("Lookup each person by name...");
			team.stream().forEach(person -> log.info(
					"\t" + personRepository.findByName(person.getName()).toString()));

			List<Person> teammates = personRepository.findByTeammatesName(jestoni.getName());
			log.info("The following have Greg as a teammate...");
			teammates.forEach(person -> log.info("\t" + person.getName()));
		};
	}
}

/*
Build an executable JAR
You can run the application from the command line with Gradle or Maven.
You can also build a single executable JAR file that contains all the necessary dependencies,
classes, and resources and run that. Building an executable jar makes it easy to ship,
version, and deploy the service as an application throughout the development lifecycle,
across different environments, and so forth.

If you use Gradle, you can run the application by using
	./gradlew bootRun
Alternatively, you can build the JAR file by using
	./gradlew build
and then run the JAR file, as follows:
	java -jar build/libs/gs-rest-service-0.1.0.jar

If you use Maven, you can run the application by using
	./mvnw spring-boot:run
Alternatively, you can build the JAR file with
	./mvnw clean package
and then run the JAR file, as follows:
	java -jar target/gs-rest-service-0.1.0.jar
*/