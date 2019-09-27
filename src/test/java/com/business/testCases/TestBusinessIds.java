package com.business.testCases;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.v1.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TestBusinessIds extends TestBase {
	/*
	 * This method gets data for a specific business
	 */
	@Test(groups = { "smokeTests" })
	public void getBusinessesById() {
		logger.info("****************Started getBusinessesById*****************");
		// Specify base URI
		RestAssured.baseURI = BusinessURL;

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET, "/" + businessId);

		// Response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Status code validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Status line verification
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		// Response body
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
		// Verify that response body is not null
		Assert.assertNotNull(responseBody);
		JSONObject res = new JSONObject(responseBody);
		JSONArray data = res.getJSONArray("data");
		JSONObject result = data.getJSONObject(0);

		String businessId = result.getString("businessId");
		// Verify that business id is not null
		Assert.assertNotNull(businessId);

		String businessName = result.getString("businessName");
		// Verify that business name is not null
		Assert.assertNotNull(businessName);

		String currencyCode = result.getString("currencyCode");
		// Verify that currency code is not null
		Assert.assertNotNull(currencyCode);

		JSONObject jsonObject = new JSONObject(result.toString());
		JSONArray revenueCenters = jsonObject.getJSONArray("revenueCenters");
		// Verify that currency code is not null
		Assert.assertNotNull(revenueCenters);
		String revenueCenterName = "";
		String revenueCenterId = "";

		List<String> expectedRevenueCenterNames = new ArrayList<String>();
		List<String> expectedRevenueCenterIds = new ArrayList<String>();

		// Use for loop to collect all revenueCenterName and revenueCenterId
		// under a given revenueCenter
		for (int n = 0; n < revenueCenters.length(); n++) {
			JSONObject object = revenueCenters.getJSONObject(n);
			revenueCenterName = object.getString("revenueCenterName");
			expectedRevenueCenterNames.add(revenueCenterName);
			revenueCenterId = object.getString("revenueCenterId");
			expectedRevenueCenterIds.add(revenueCenterId);
		}

		// Verify that revenue center name is not null
		Assert.assertNotNull(expectedRevenueCenterNames);
		// Verify that revenue center Id is not null
		Assert.assertNotNull(expectedRevenueCenterIds);
		logger.info("****************Ended getBusinessesById*****************");
	}

	/*
	 * This method verifies data returned for a non existing business Id
	 */
	@Test(groups = { "negativeTests" })
	public void getBusinessesByNonExistingIds() {
		logger.info("****************Started getBusinessesByNonExistingIds*****************");
		// Specify base URI
		RestAssured.baseURI = BusinessURL;

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		// Enter a non existing Id
		response = httpRequest.request(Method.GET, "/9999");

		// Response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Response body
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
		// Verify that response body is not null
		Assert.assertNotNull(responseBody);

		JSONObject res = new JSONObject(responseBody);
		JSONArray data = res.getJSONArray("data");
		// Assert that length of data for a non existing id is zero
		Assert.assertEquals(data.length(), 0);

		// Status code validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 400);
		logger.info("****************Ended getBusinessesByNonExistingIds*****************");
	}

	/*
	 * This method verifies data returned for an invalid or incorrect URL
	 */
	@Test(groups = { "negativeTests" })
	public void getBusinessesByIncorrectURL() {
		logger.info("****************Started getBusinessesByIncorrectURL*****************");
		// Specify base URI
		RestAssured.baseURI = BusinessURL;

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		// Make the URL incorrect by adding additional values
		response = httpRequest.request(Method.GET, "/xyz/9999");

		// Response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Response body
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
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