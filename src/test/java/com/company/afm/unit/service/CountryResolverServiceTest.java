package com.company.afm.unit.service;

import com.company.afm.domain.Country;
import com.company.afm.domain.CountryResponse;
import com.company.afm.service.CountryResolverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CountryResolverServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private CountryResolverService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new CountryResolverService(restTemplate);
    }

    /**
     * 104.24.108.15 USA
     * 212.42.76.15 UA
     * 212.168.0.15 DE
     */
    @Test
    void shouldResolveCountryByIpAddress() {

        String ipAddress = "104.24.108.15";
        CountryResponse givenCountryResponse = new CountryResponse();
        givenCountryResponse.setStatus("success");
        givenCountryResponse.setCountryCode("DE");
        givenCountryResponse.setCountryName("Germany");

        when(restTemplate.getForObject(anyString(), eq(CountryResponse.class))).thenReturn(givenCountryResponse);

        assertThat(testInstance.resolveCountryByIpAddress(ipAddress)).isEqualTo(Country.DE);
    }
}