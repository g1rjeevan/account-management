package com.management.account.accountmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.management.account.accountmanagement.model")
@EnableJpaRepositories("com.management.account.accountmanagement.repository")
public class AccountManagementApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(AccountManagementApplication.class, args);
        LOGGER.info("Account Management Micro-service Application :: Running.");
    }

}
