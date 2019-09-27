package com.v1.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
    // Defining variables to be used across different tests 
	public static RequestSpecification httpRequest;
	public static Response response;
	public static String BusinessURL = "http://localhost:9000/v1/core/businesses";
	public static String SummaryURL = "http://localhost:9000/v1/sales/summary-sales";
	public static String businessId = "7048";

	public Logger logger;

	@BeforeClass
	public void setup() {
        // Logger variables are initiated before class so that they can
		// be used by all the tests
		logger = Logger.getLogger("AveroRestAPI");
		PropertyConfigurator.configure("Log4j.properties");
		logger.setLevel(Level.DEBUG);

	}
}
