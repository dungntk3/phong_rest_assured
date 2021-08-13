package api.verify;

import org.slf4j.Logger;
import org.testng.Assert;

import com.jayway.restassured.path.json.JsonPath;

import api.util.RestUtil;
import api.util.URL;
import io.restassured.response.Response;
import log.LogHelper;

public class BaseAssertion {
	public final static Logger log = LogHelper.getLogger();
	
	public static void verifyTrue(boolean flag){
		Assert.assertTrue(flag);
	}
	
	public static void verifyFalse(boolean flag){
		Assert.assertFalse(flag);
	}

	/**
	 * Verify status code of response
	 * 
	 * @param response : response expect
	 * @param status : status of response
	 */
	public static void verifyStatusCode(Response response, int status){
		Assert.assertEquals(RestUtil.getStatusCode(response), status);
	}
	
	public static void verifyStatusMessage(Response response, String status){
		Assert.assertEquals(RestUtil.getStatusMessage(response), status);
	}
	
	public static void verifyBodyReponseGET(Response response, String actualBody, String expectBody) {
		String strResponseBody = RestUtil.getResposeString(response);
		JsonPath jsonResBody = RestUtil.jsonParser(strResponseBody);
		String actualValueBody = jsonResBody.getString(actualBody);
		
		log.info("Compare " + actualValueBody + " and " + expectBody);
		Assert.assertEquals(actualValueBody, expectBody);
	}
	
	public static void verifyHeaderResponseGET(Response response, String actualHeader, String expectHeader) {
		String strHeader = response.getHeader(actualHeader);
		log.info("Compare " + strHeader + " and " + expectHeader);
		Assert.assertEquals(strHeader, expectHeader);
	}
	
	public static String doLoginJira(){
		Response response;
		log.info("Starting Test Case : doLogin");
		String loginPayload = RestUtil.generatePayLoadString("JiraLogin.json");
		
		String endPointURI = URL.getEndPointJira("/rest/auth/1/session");
		response = RestUtil.POSTRequest(endPointURI, loginPayload);
		log.info(response.getBody().asString());
		String strResponse = RestUtil.getResposeString(response);
		JsonPath jsonRes = RestUtil.jsonParser(strResponse);
		String sessionID = jsonRes.getString("session.value");
		log.info("JIRA JSession ID : " + sessionID);
		return sessionID;
	}
}
