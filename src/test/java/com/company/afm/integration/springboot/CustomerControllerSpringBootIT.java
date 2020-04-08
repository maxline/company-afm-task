package com.company.afm.integration.springboot;

import com.company.afm.domain.Customer;
import com.company.afm.exception.CustomerNotFoundException;
import com.company.afm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerSpringBootIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    @Qualifier("customerServiceImpl")
    private CustomerService customerService;


    @Test
    void canRetrieveByIdWhenExists() {
        // given
        Customer givenCustomer = new Customer("test_name", "test_last_name");
        when(customerService.findById(2)).thenReturn(givenCustomer);

        // when
        Customer actualCustomer = restTemplate.getForObject("/customers/get/2", Customer.class);

        // then
        assertThat(actualCustomer).isEqualTo(givenCustomer);
    }


    @Test
    void canRetrieveByIdWhenDoesNotExist() {
        // given
        when(customerService.findById(997L)).thenThrow(new CustomerNotFoundException(997L));

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/customers/get/997", String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("could not find customer 997");
    }

    @Test
    void canCreateANewCustomer() {
        Customer givenCustomer = new Customer("test_name", "test_last_name");

        ResponseEntity<Customer> response = restTemplate.postForEntity("/customers/post", givenCustomer, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}