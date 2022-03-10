package com.encora.eci;

import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories("com.encora.eci.persistance.repository")
@EntityScan("com.encora.eci.persistance.model")
@SpringBootApplication
public class EciApplication {

    private static final Logger log = LoggerFactory.getLogger(EciApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EciApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(EmployeeRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, "Mexico", "Jalisco", 1));
            repository.save(new Employee("corpo1@email.com","Chloe", "O'Brian", GenderTypes.Female, "Mexico", "Guerrero", 2));
            repository.save(new Employee("corpo2@email.com","Kim", "Bauer", GenderTypes.Female, "Mexico", "HMO", 3));
            repository.save(new Employee("corpo3@email.com","David", "Palmer", GenderTypes.Other, "Mexico", "Merida", 4));
            repository.save(new Employee("corpo_old@email.com","Sobre", "Rosa", GenderTypes.Other, "USA", "Texas", 6));

            Optional<Employee> one = repository.findById(1);
            Optional<Employee> two = repository.findById(2);
            Optional<Employee> three = repository.findById(3);
            Optional<Employee> four = repository.findById(5);
            one.get().setBirthday(LocalDate.now());
            two.get().setBirthday(LocalDate.now());
            three.get().setBirthday(LocalDate.now().plusDays(3));
            four.get().setDeletedAt(LocalDate.now());
            repository.save(one.get());
            repository.save(two.get());
            repository.save(three.get());
            repository.save(four.get());
            // Complete data
            repository.save(new Employee("corpo_complete@email.com","Majid", "Mazinani", GenderTypes.Male, "Mexico", "Jalisco", 5,
                    "shahin@hotmail.com", "3315997094", "1989-04-03"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Employee customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Optional<Employee> customer = repository.findById(1);
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            /*
            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
             */
            log.info("findByBirthday:");
            LocalDate birthDay =  LocalDate.parse("1989-04-03");
            List<Employee> birthdays = repository.findByBirthday(birthDay);
            for(Employee emplBirthday:birthdays){
                log.info(emplBirthday.toString());
            }
            //
            // }
            log.info("");


        };
    }
}
