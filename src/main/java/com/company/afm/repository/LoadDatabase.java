package com.company.afm.repository;

import com.company.afm.domain.Customer;
import com.company.afm.domain.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final boolean STATUS_APPROVED = true;
    private static final boolean STATUS_DECLINED = false;
    private static final boolean IS_BLACKLISTED = true;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public CommandLineRunner initLoanDB(LoanRepository loanRepository, CustomerRepository customerRepository) {
        return args -> {
            Customer customer1 = new Customer("Andrii", "Shevchenko");
            Customer customer2 = new Customer("Bred", "Pitt");
            customerRepository.save(customer1);
            customerRepository.save(customer2);

            customerRepository.save(new Customer("Johny", "Depp", IS_BLACKLISTED));
            customerRepository.save(new Customer("Tom", "Hanks"));
            customerRepository.save(new Customer("Tom", "Cruise"));

            log.info("Preloading " + loanRepository.save(new Loan(30, 3000, "UA", customer1, STATUS_APPROVED)));
            log.info("Preloading " + loanRepository.save(new Loan(40, 4000, "UA", customer1, STATUS_APPROVED)));
            log.info("Preloading " + loanRepository.save(new Loan(50, 5000, "USA", customer2, STATUS_APPROVED)));
            log.info("Preloading " + loanRepository.save(new Loan(60, 6000, "USA", customer2, STATUS_DECLINED)));
        };
    }
}