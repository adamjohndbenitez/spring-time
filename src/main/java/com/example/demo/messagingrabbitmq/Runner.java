package com.example.demo.messagingrabbitmq;

import com.example.demo.DemoApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final ReceiverRabbitMQ receiver;

    public Runner(ReceiverRabbitMQ receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(DemoApplication.TOPIC, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

/* FIXME: already fixed by starting the rabbitmq-server, look below for resolution.
Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2021-06-08 23:30:39.570 ERROR 83551 --- [           main] o.s.boot.SpringApplication               : Application run failed

java.lang.IllegalStateException: Failed to execute CommandLineRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:807) ~[spring-boot-2.4.3.jar:2.4.3]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:788) ~[spring-boot-2.4.3.jar:2.4.3]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:333) ~[spring-boot-2.4.3.jar:2.4.3]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1311) ~[spring-boot-2.4.3.jar:2.4.3]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1300) ~[spring-boot-2.4.3.jar:2.4.3]
	at com.example.messagingrabbitmq.MessagingRabbitmqApplication.main(MessagingRabbitmqApplication.java:52) ~[classes/:na]
Caused by: org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused (Connection refused)
	at org.springframework.amqp.rabbit.support.RabbitExceptionTranslator.convertRabbitAccessException(RabbitExceptionTranslator.java:61) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.createBareConnection(AbstractConnectionFactory.java:602) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.createConnection(CachingConnectionFactory.java:724) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.connection.ConnectionFactoryUtils.createConnection(ConnectionFactoryUtils.java:214) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.core.RabbitTemplate.doExecute(RabbitTemplate.java:2132) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.core.RabbitTemplate.execute(RabbitTemplate.java:2105) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.core.RabbitTemplate.send(RabbitTemplate.java:1049) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.core.RabbitTemplate.convertAndSend(RabbitTemplate.java:1114) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.core.RabbitTemplate.convertAndSend(RabbitTemplate.java:1107) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at com.example.messagingrabbitmq.Runner.run(Runner.java:23) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:804) ~[spring-boot-2.4.3.jar:2.4.3]
	... 5 common frames omitted
Caused by: java.net.ConnectException: Connection refused (Connection refused)
	at java.base/java.net.PlainSocketImpl.socketConnect(Native Method) ~[na:na]
	at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:399) ~[na:na]
	at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:242) ~[na:na]
	at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:224) ~[na:na]
	at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:403) ~[na:na]
	at java.base/java.net.Socket.connect(Socket.java:609) ~[na:na]
	at com.rabbitmq.client.impl.SocketFrameHandlerFactory.create(SocketFrameHandlerFactory.java:60) ~[amqp-client-5.10.0.jar:5.10.0]
	at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1137) ~[amqp-client-5.10.0.jar:5.10.0]
	at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:1087) ~[amqp-client-5.10.0.jar:5.10.0]
	at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.connectAddresses(AbstractConnectionFactory.java:638) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.connect(AbstractConnectionFactory.java:613) ~[spring-rabbit-2.3.5.jar:2.3.5]
	at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.createBareConnection(AbstractConnectionFactory.java:565) ~[spring-rabbit-2.3.5.jar:2.3.5]
	... 14 common frames omitted */
}
