package com.company.afm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryResponse {

    private String status;
    private String message;
    private String countryCode;
    @JsonProperty(value = "country")
    private String countryName;
}