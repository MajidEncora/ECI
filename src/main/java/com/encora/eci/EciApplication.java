package com.encora.eci;

import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.repository.AddressRepository;
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
    public CommandLineRunner demo(EmployeeRepository repository, AddressRepository addressRepository) {
        return (args) -> {

            Address add1 = addressRepository.save(new Address("Calle", "#1", "44630", "Mexico", "Jalisco"));
            Address add2 = addressRepository.save(new Address("Calle2", "#11", "55667", "Mexico", "Guerrero"));
            Address add3 = addressRepository.save(new Address("Calle3", "#12", "66532", "Mexico", "HMO"));
            Address add4 = addressRepository.save(new Address("Calle4", "#133", "12345", "Mexico", "Merida"));
            Address add5 = addressRepository.save(new Address("Calle5", "#145", "56789", "Mexico", "Merida"));

            // save a few customers
            Employee one = repository.save(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, add1.getId()));
            Employee two = repository.save(new Employee("corpo1@email.com","Chloe", "O'Brian", GenderTypes.Female, add2.getId()));
            Employee three = repository.save(new Employee("corpo2@email.com","Kim", "Bauer", GenderTypes.Female, add3.getId()));
            Employee four = repository.save(new Employee("corpo3@email.com","David", "Palmer", GenderTypes.Other, add4.getId()));
            repository.save(new Employee("corpo_old@email.com","Sobre", "Rosa", GenderTypes.Other,  add5.getId()));

            one.setBirthday(LocalDate.now());
            two.setBirthday(LocalDate.now());
            three.setBirthday(LocalDate.now().plusDays(3));
            four.setDeletedAt(LocalDate.now());
            repository.save(one);
            repository.save(two);
            repository.save(three);
            repository.save(four);
            // Complete data
            repository.save(new Employee("corpo_complete@email.com","Majid", "Mazinani", GenderTypes.Male, 5,
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
