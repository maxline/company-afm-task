package com.company.afm.unit.service;

import com.company.afm.domain.Country;
import com.company.afm.domain.Customer;
import com.company.afm.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorServiceTest {
    private static final boolean IS_BLACKLISTED = true;
    private static final boolean NOT_BLACKLISTED = false;
    private static final int ORDERS_QUANTITY_LIMIT = 3;
    private static final int ORDERS_QUANTITY_TIME_FRAME = 1000;

    private ValidatorService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new ValidatorService(ORDERS_QUANTITY_LIMIT, ORDERS_QUANTITY_TIME_FRAME);
    }

    @Test
    void validateCustomer_shouldReturnTrue_whenCustomerIsNotInBlacklist() {
        Customer customer = new Customer("test_name", "test_surname");
        customer.setBlacklisted(NOT_BLACKLISTED);
        assertThat(testInstance.validateCustomer(customer)).isTrue();
    }

    @Test
    void validateCustomer_shouldReturnFalse_whenCustomerIsInBlacklist() {
        Customer customer = new Customer("test_name", "test_surname");
        customer.setBlacklisted(IS_BLACKLISTED);
        assertThat(testInstance.validateCustomer(customer)).isFalse();
    }

    @Test
    void validateRequestsQuantity_shouldReturnTrue_whenOrdersQuantityLessThanLimit() {
        // orders quantity limit is 3
        testInstance.validateOrdersQuantity(Country.UA);
        testInstance.validateOrdersQuantity(Country.UA);
        testInstance.validateOrdersQuantity(Country.LV);
        assertThat(testInstance.validateOrdersQuantity(Country.UA)).isTrue();
    }

    @Test
    void validateRequestsQuantity_shouldReturnFalse_whenOrdersQuantityExceedsThanLimit() {
        // orders quantity limit is 3
        testInstance.validateOrdersQuantity(Country.UA);
        testInstance.validateOrdersQuantity(Country.UA);
        testInstance.validateOrdersQuantity(Country.UA);
        assertThat(testInstance.validateOrdersQuantity(Country.UA)).isFalse();
    }
}