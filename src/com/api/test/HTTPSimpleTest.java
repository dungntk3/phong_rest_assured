package api.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import api.helper.Contanst;
import api.util.RestUtil;
import base.rest.test.TestBase;
import rest.client.HTTPClient;

public class HTTPSimpleTest extends TestBase {

	TestBase objTestBase;
	HTTPClient driver;
	String APIURI;
	CloseableHttpResponse APIResponse;

	@BeforeTest
	public void setUp() {
		objTestBase = new TestBase();
		String URL = prop.getProperty("URI");
		String pathparm = prop.getProperty("ServiceURI");

		APIURI = URL + pathparm;
	}

	@Test
	public void TC_01_testGetCallStatusCode() throws ClientProtocolException, IOException, JSONException {
		driver = new HTTPClient();
		APIResponse = driver.getResponseHTTPGet(APIURI);
		Assert.assertEquals(APIResponse.getStatusLine().getStatusCode(), Contanst.CODE_STATUS_200);
	}

	@Test
	public void TC_02_testGetCallResponse() throws ClientProtocolException, IOException, JSONException {
		driver = new HTTPClient();
		APIResponse = driver.getResponseHTTPGet(APIURI);
		String responseString = EntityUtils.toString(APIResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);

		String responseValue = RestUtil.getValueByJPath(responseJson, "/per_page");
		Assert.assertEquals(responseValue, "3");

		System.out.println(RestUtil.getValueByJPath(responseJson, "/data[0]/first_name"));
		Assert.assertEquals(RestUtil.getValueByJPath(responseJson, "/data[0]/first_name"), "George");

		System.out.println(RestUtil.getValueByJPath(responseJson, "/data[1]/last_name"));
		Assert.assertEquals(RestUtil.getValueByJPath(responseJson, "/data[1]/last_name"), "Weaver");
		Assert.assertEquals(RestUtil.getValueByJPath(responseJson, "/data[2]/id"), "3");
	}

}
