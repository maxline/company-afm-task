package com.company.afm.service;

import com.company.afm.domain.Country;
import com.company.afm.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class ValidatorService {

    @Value("${orders.quantity.limit}")
    private int ordersQuantityLimit;

    @Value("${orders.quantity.timeframe}")
    private long ordersQuantityTimeFrame;

    private HashMap<Country, Queue<Long>> recentRequests = new HashMap<>();

    public boolean validateCustomer(Customer customer) {
        return !customer.isBlacklisted();
    }

    public boolean validateOrdersQuantity(Country country) {
        Long time = System.currentTimeMillis();

        if (isFirstOrderForCountry(country)) {
            recentRequests.put(country, new LinkedList<>());
            recentRequests.get(country).add(time);
            return true;
        }

        if (isOrdersQuantityLessThanLimit(country)) {
            recentRequests.get(country).add(time);
            return true;
        }

        if (isFirstOrderMoreThanTimeFrameAgo(country, time)) {
            recentRequests.get(country).poll();
            recentRequests.get(country).add(time);
            return true;
        } else
            return false;
    }

    private boolean isFirstOrderForCountry(Country country) {
        return !recentRequests.containsKey(country);
    }

    private boolean isOrdersQuantityLessThanLimit(Country country) {
        return recentRequests.get(country).size() < ordersQuantityLimit;
    }

    private boolean isFirstOrderMoreThanTimeFrameAgo(Country country, Long time) {
        return time - recentRequests.get(country).peek() > ordersQuantityTimeFrame;
    }
}