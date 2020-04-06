// todo add apply for loan
// black list validation
// loan app0y or apply? or both
// todo loanService add findById
// todo loan statuses instead of boolean
// geo ip


// todo unit tests
// todo check all nullpointers possible, id from controller etc

// todo add property file
// update README.md

// Loan
// todo country
//    @ManyToOne  //todo check
//    @JoinColumn(name = "country_id")
//    private Country country;
//    private String country;

// todo equals hashcode for entities

// todo check type id for entities
@GeneratedValue(strategy = GenerationType.IDENTITY) //todo check type
    private Long id;

// todo hateoas

http://localhost:8080/customers/get/all shows all customers
http://localhost:8080/customers/get/1 shows customer found by id

http://localhost:8080/loans/get/all shows all loans
http://localhost:8080/loans/get/approved  shows all approved loans
http://localhost:8080/loans/get/customer/2 shows all approved loans by customer


Problem definition
Create a tiny RESTful web service with the following business requirements:

Application must expose REST API endpoints for the following functionality:
- apply for loan (loan amount, term, name, surname and personal id must be provided);
- list all approved loans;
- list all approved loans by user.

Service must perform loan application validation according to the following rules and reject application if:
- application comes from blacklisted personal id;
- N application / second are received from a single country (essentially we want to limit number of loan applications coming from a country in a given time frame).

Service must perform origin country resolution using a web service (you should choose one) and store country code together with the loan application. Because network is unreliable and services tend to fail, let's agree on default country code - "lv".

Technical requirements:
You have total control over framework and tools, as long as application is written in Java. Feel free to write tests in any JVM language.

What gets evaluated:
- conformance to business requirements;
- code quality, including testability;
- how easy it is to run and deploy the service (don't make us install Oracle database please ;)
