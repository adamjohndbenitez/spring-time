package com.fin.mngmnt.srvs.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {

        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Adam", "John", "admin")));
            log.info("Preloading " + employeeRepository.save(new Employee("Leah", "Maranan", "admin")));
        };
    }
}
