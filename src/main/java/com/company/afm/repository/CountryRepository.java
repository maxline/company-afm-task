package com.company.afm.repository;

import com.company.afm.domain.Country;

public class CountryRepository {

    public static Country findByCode(String code) {
        try {
            return Country.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Country.UNDEFINED;
        }
    }
}