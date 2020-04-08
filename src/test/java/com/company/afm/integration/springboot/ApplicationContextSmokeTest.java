package com.company.afm.integration.springboot;

import com.company.afm.rest.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationContextSmokeTest {

    @Autowired
    private CustomerController controller;

    @Test
    void applicationContextShouldStartAndCreateController() {
        assertThat(controller).isNotNull();
    }
}
