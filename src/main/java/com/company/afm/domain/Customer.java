package com.company.afm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private boolean isBlacklisted;

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.isBlacklisted = false;
    }

    public Customer(String name, String surname, boolean isBlacklisted) {
        this.name = name;
        this.surname = surname;
        this.isBlacklisted = isBlacklisted;
    }
}