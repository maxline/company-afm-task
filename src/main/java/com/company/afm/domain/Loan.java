package com.company.afm.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer term;
    private Integer amount;
    private String country;

    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    @ManyToOne(cascade = CascadeType.ALL) // todo or PERSIST?
    private Customer customer;

    private Boolean isApproved;

    public Loan(Integer term, Integer amount, String country, Customer customer) {
        this.term = term;
        this.amount = amount;
        this.country = country;
        this.customer = customer;
    }

    public Loan(Integer term, Integer amount, String country, Customer customer, Boolean isApproved) {
        this.term = term;
        this.amount = amount;
        this.country = country;
        this.customer = customer;
        this.isApproved = isApproved;
    }
}