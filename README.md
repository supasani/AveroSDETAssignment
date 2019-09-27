# Project Title

This project is to test the APIs provided by Avero for its SDET assignment. All tests are written using the Rest Assured Framework using Java and TestNG.

## Getting Started

Clone the AveroSDETAssignment project from the following link: https://github.com/supasani/AveroSDETAssignment

### Prerequisites

1. You will need the latest stable docker release. See the Docker install instructions for more information.
2. Install an IDE Eclipse or IntelliJ IDEA to open and run this project.
3. Have Java installed on the system. 
4. Right click on pom.xml file and select Maven. The select Maven clean and install. This will install all dependencies for this project.

### Running the APIs locally
1. Pull the Docker image: docker pull avero/sdet-coding-exercise
2. Run the Docker image, opening up port 9000: docker run -p 9000:9000 avero/sdet-coding-exercise
3. You're all set. You can hit http://localhost:9000/v1/core/businesses in a browser and sanity check you get a response back

### Running the tests
1. Go to the AveroSDETAssignment project that you cloned in the first step.
2. Run the complete test suite by right clicking on the testng.xml file. This will execute tests for all three API's that are provided in the assignment. 

Note: Here 3/7 tests will pass and 4/7 will fail as per the expectation set in the response code requirements. Response code for incorrect and invalid URLs are 200 instead of 400 and 404 and this is why some tests will fail. 
3. To execute only the positive tests, run the smokeTests.xml file in similar way.

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


