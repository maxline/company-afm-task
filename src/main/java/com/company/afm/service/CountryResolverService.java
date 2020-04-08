package com.company.afm.service;

import com.company.afm.domain.Country;
import com.company.afm.domain.CountryResponse;
import com.company.afm.exception.CountryRestClientException;
import com.company.afm.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryResolverService {

    private static final String SUCCESS_STATUS = "success";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RestTemplate restTemplate;
    @Value("${country.resolver.endpoint}")
    private String countryResolutionEndpoint;
    @Value("${country.default.code}")
    private String defaultCountryCode;

    public CountryResolverService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Country resolveCountryByIpAddress(String ipAddress) {
        String countryCode;
        String url = countryResolutionEndpoint + ipAddress;

        log.info("Customer IP address: " + ipAddress);

        try {
            CountryResponse countryResponse = getCountryResponse(url);
            countryCode = countryResponse.getCountryCode();
        } catch (CountryRestClientException e) {
            log.error("Can't process the response from the url: " + url + ". The default country will be applied");
            log.error(e.getMessage());
            countryCode = defaultCountryCode;
        }

        return CountryRepository.findByCode(countryCode);
    }

    private CountryResponse getCountryResponse(String url) throws CountryRestClientException {
        CountryResponse countryResponse;
        try {
            countryResponse = restTemplate.getForObject(url, CountryResponse.class);
        } catch (RuntimeException e) {
            throw new CountryRestClientException(e.getMessage());
        }
        if (!SUCCESS_STATUS.equals(countryResponse.getStatus())) {
            throw new CountryRestClientException("Web service returns error message: " + countryResponse.getMessage());
        }

        return countryResponse;
    }
}