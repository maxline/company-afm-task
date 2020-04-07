# Simple spring boot RESTful loan application

## Run the Application
If you use Maven, run the following command in a terminal window (in the project) directory:
```
./mvnw spring-boot:run
```
or just run `RunApplication.java` class from your IDE.

## Links for testing how app is working
http://localhost:8080/customers/get/all shows all customers<br>
http://localhost:8080/customers/get/1 shows customer found by id<br>

http://localhost:8080/loans/get/all shows all loans (loans ids : 1, 2, 3, 4)<br>
http://localhost:8080/loans/get/approved  shows all approved loans (loans ids : 1, 2, 3)<br>
http://localhost:8080/loans/get/customer/2 shows all approved loans by customer (loans ids : 1)<br>

## POST requests demo
### Create a new Customer record
```
$ curl --header "Content-Type: application/json" --request POST --data @demo-data/customer.json http://localhost:8080/loans/post
```
where `customer.json` is file in `demo-data` package

### Create a new Loan record approved
```
$ curl --header "Content-Type: application/json" --request POST --data @demo-data/loan_new_customer.json http://localhost:8080/loans/post
```
where `loan_new_customer.json` is file in `demo-data` package
will also create a new customer record.

### Create a new Loan record declined
```
$ curl --header "Content-Type: application/json" --request POST --data @demo-data/loan_blacklisted_customer.json http://localhost:8080/loans/post
```
where `loan_blacklisted_customer.json` is file in `demo-data` package

to check the new records was created go to the http://localhost:8080/loans/get/all

## Problem definition
Create a tiny RESTful web service with the following business requirements:

Application must expose REST API endpoints for the following functionality:
- apply for loan (loan amount, term, name, surname and personal id must be provided);
- list all approved loans;
- list all approved loans by user.

Service must perform loan application validation according to the following rules and reject application if:
- application comes from blacklisted personal id;
- N application / second are received from a single country (essentially we want to limit number of loan applications coming from a country in a given time frame).

Service must perform origin country resolution using a web service (you should choose one) and store country code together with the loan application.
Because network is unreliable and services tend to fail, let's agree on default country code - "lv".

Technical requirements:
You have total control over framework and tools, as long as application is written in Java. Feel free to write tests in any JVM language.

What gets evaluated:
- conformance to business requirements;
- code quality, including testability;
- how easy it is to run and deploy the service (don't make us install Oracle database please ;)
