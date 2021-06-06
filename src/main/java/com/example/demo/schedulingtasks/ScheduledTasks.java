package com.example.demo.schedulingtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create a Scheduled Task
 *
 * This example uses fixedRate, which specifies the interval between method invocations,
 * measured from the start time of each invocation. There are other options, such as fixedDelay,
 * which specifies the interval between invocations measured from the completion of the task.
 * You can also use @Scheduled(cron=". . .") expressions for more sophisticated task scheduling.
 *
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled
 */
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
