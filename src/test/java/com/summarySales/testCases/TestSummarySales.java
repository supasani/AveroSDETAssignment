package com.summarySales.testCases;

import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.v1.base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TestSummarySales extends TestBase {
	/*
	 * This method gets data for all summary sales
	 */
	@Test(groups = { "smokeTests" })
	public void getSummarySales() {
		logger.info("****************Started getSummarySales*****************");
		// Specify base URI
		RestAssured.baseURI = SummaryURL;

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET);

		// Response body
		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
		// Verify that response body is not null
		Assert.assertNotNull(responseBody);

		// Status code validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Status line verification
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		// response header verification
		String responseHeader = response.getContentType();
		Assert.assertEquals(responseHeader, "application/json; charset=utf-8");

		// Get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Store all business names in a list
		List<String> businessNames = jsonPathEvaluator.getList("data.businessDay");
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

		JSONObject res = new JSONObject(responseBody);
		JSONArray data = res.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			// Parse and verify each and every data value and sub-values
			JSONObject result = data.getJSONObject(i);
			JSONObject jsonObject = new JSONObject(result.toString());
			JSONObject organizations = jsonObject.getJSONObject("organization");
			Assert.assertNotNull(organizations);
			String name = organizations.getString("name");
			String level = organizations.getString("level");
			String id = organizations.getString("id");
			// Assert that values in organization are not null
			Assert.assertNotNull(name);
			Assert.assertNotNull(level);
			Assert.assertNotNull(id);

			JSONObject jsonObject2 = new JSONObject(organizations.toString());
			JSONObject parent = jsonObject2.getJSONObject("parent");
			Assert.assertNotNull(parent);
			String parentName = parent.getString("name");
			String parentLevel = parent.getString("level");
			String parentId = parent.getString("id");
			// Assert that values in parent are not null
			Assert.assertNotNull(parentName);
			Assert.assertNotNull(parentLevel);
			Assert.assertNotNull(parentId);

			JSONObject metrics = jsonObject.getJSONObject("metrics");
			Assert.assertNotNull(metrics);
			String grossSales = metrics.getString("grossSales");
			String netSales = metrics.getString("netSales");
			int coverCount = metrics.getInt("coverCount");
			int checkCount = metrics.getInt("checkCount");
			// Assert that values in metrics are not null
			Assert.assertNotNull(grossSales);
			Assert.assertNotNull(netSales);
			Assert.assertNotNull(coverCount);
			Assert.assertNotNull(checkCount);
		}
		logger.info("****************Ended getSummarySales*****************");
	}

	/*
	 * This method verifies data returned for an invalid or incorrect URL
	 */
	@Test(groups = { "negativeTests" })
	public void getSummarySalesByIncorrectURL() {
		logger.info("****************Started getSummarySalesByIncorrectURL*****************");
		// Edit the URL to make it incorrect
		RestAssured.baseURI = "http://localhost:9000/v1/sales/summary-sale";

		// HttpRequest and Response
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET);

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
		logger.info("****************Ended getSummarySalesByIncorrectURL*****************");
	}
}