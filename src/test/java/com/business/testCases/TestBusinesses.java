package com.business.testCases;

import java.util.HashSet;
import java.util.List;
import com.v1.base.TestBase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TestBusinesses extends TestBase {

	/*
	 * This method gets data for all businesses 
	 */
	@Test(groups = { "smokeTests" })
	public void getBusinesses() {
		logger.info("****************Started getBusinesses*****************");
		// Specify base URI
		RestAssured.baseURI = BusinessURL;

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET);

		// Response body
		String responseBody = response.getBody().asString();
		// Verify that response body is not null
		Assert.assertNotNull(responseBody);

		// Status code validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Status line verification
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		// Response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
		// Get all business Ids and store it in a list
		List<String> businessIds = jsonPathEvaluator.getList("data.businessId");
		// Assert that the list is not null
		Assert.assertNotNull(businessIds);

		// Store all business names in a list
		List<String> businessNames = jsonPathEvaluator.getList("data.businessName");
		// Assert that the list is not null
		Assert.assertNotNull(businessNames);

		// Store all currencyCode in a list
		List<String> currencyCode = jsonPathEvaluator.getList("data.currencyCode");
		// Create a hashset to store unique currency values
		HashSet<String> hs = new HashSet<String>();
		for (String currency : currencyCode)
			hs.add(currency);
		// Assert that the set has size equal to two
		Assert.assertEquals(hs.size(), 2);

		// Use JSONObject to parse the returned data
		JSONObject res = new JSONObject(responseBody);
		// Create a json array to store the data from response body
		JSONArray data = res.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			JSONObject result = data.getJSONObject(i);
			JSONObject jsonObject = new JSONObject(result.toString());
			// Store revenue centers in a JSONArray
			JSONArray revenueCenters = jsonObject.getJSONArray("revenueCenters");
			// Assert that revenueCenters is not null
			Assert.assertNotNull(revenueCenters);
			String revenueCenterName = "";
			String revenueCenterId = "";

			for (int n = 0; n < revenueCenters.length(); n++) {
				// Extract revenuecenterId and revenuecenterName from
				// revenueCenters
				// and verify that both are not null
				JSONObject object = revenueCenters.getJSONObject(n);
				revenueCenterId = object.getString("revenueCenterId");
				Assert.assertNotNull(revenueCenterId);
				revenueCenterName = object.getString("revenueCenterName");
				Assert.assertNotNull(revenueCenterName);
			}
		}
		logger.info("****************Ended getBusinesses*****************");
	}

	/*
	 * This method verifies data returned for an invalid or incorrect URL
	 */
	@Test(groups = { "negativeTests" })
	public void getBusinessesByIncorrectURL() {
		logger.info("****************Started getBusinessesByIncorrectURL*****************");
		// Make the URL incorrect by remove 'es' from 'businesses'
		RestAssured.baseURI = "http://localhost:9000/v1/core/business";

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET);

		// Response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Response body
		String responseBody = response.getBody().asString();
		// Verify that response body is not null
		Assert.assertNotNull(responseBody);

		JSONObject res = new JSONObject(responseBody);
		String error = res.getString("err");
		// Assert the value of err in response body
		Assert.assertEquals(error, "not found");

		// Status code validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 404);
		logger.info("****************Ended getBusinessesByIncorrectURL*****************");
	}
}