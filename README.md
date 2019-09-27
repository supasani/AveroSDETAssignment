# Project Title

This project is to test the APIs provided by Avero for its SDET assignment. All tests are written using the Rest Assured Framework using Java and TestNG.

## Getting Started

Clone the AveroSDETAssignment project from the following link: https://github.com/supasani/AveroSDETAssignment

### Prerequisites

1. Install an IDE Eclipse or IntelliJ IDEA to open and run this project.
2. Have Java installed on the system. 
3. Right click on pom.xml file and select Maven. The select Maven clean and install. This will install all dependencies for this project.

### Running the tests
Run the complete test suite by right clicking on the testng.xml file. This will execute tests for all three API's that are provided in the assignment. 
To execute only the positive tests, run the smokeTests.xml file in similar way.

Logs are generated for each test run and stored in a log file. 

## Project Structure: 
The project consists of following packages or files:

com.business.testCases
- TestBusinesses: Contains all tests related to /v1/core/businesses

- TestBusinessids: Contains all tests related to /v1/core/businesses/{businessIds}

com.summarySales.testCases
- TestSummarySales: Contains all tests related to /v1/sales/summary-sales

com.v1.base
- TestBase: Contains variables and other values to be used throughout different tests in this project 

log4j.properties: Contains all log4j related information 

pom.xml: Contains all dependencies for this project

README.md: It is this file and has all set up related data and project related information.

testng.xml: Set of all tests both positive and negative in this project

smokeTests.xml: Set of positive tests or happy path tests in this project


## Versioning

Used github for versioning. 


