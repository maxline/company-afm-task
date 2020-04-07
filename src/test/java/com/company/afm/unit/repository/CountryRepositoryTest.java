package com.company.afm.unit.repository;

import com.company.afm.domain.Country;
import com.company.afm.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryRepositoryTest {

    @Test
    void shouldReturnCountry_whenCodeExists() {
        Assertions.assertThat(CountryRepository.findByCode("ua")).isEqualTo(Country.UA);
    }

    @Test
    void shouldReturnUndefined_whenCodeDoesNotExist() {
        assertThat(CountryRepository.findByCode("wrong_country_code")).isEqualTo(Country.UNDEFINED);
    }
}