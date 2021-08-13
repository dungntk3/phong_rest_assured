package api.util;

import org.apache.log4j.LogManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.path.xml.XmlPath;

import org.apache.log4j.Logger;

import com.jayway.restassured.path.json.JsonPath;

public class RestUtil {

	public static String testCaseName = "";
	public static String LINE_BREAK = "\r\n";

	private static Logger log = LogManager.getLogger(RestUtil.class.getName());

	public static Response GETRequest(String uRI) {

		log.info("Inside GETRequest call");
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.get(uRI);
		log.debug(requestSpecification.log().all());
		return response;
	}

	public static Response POSTRequest(String uRI, String strJSON) {
		log.info("Inside POSTRequest call");
		RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.post(uRI);
		log.debug(requestSpecification.log().all());
		return response;
	}

	public static Response POSTRequest(String uRI, String strJSON, String sessionID) {
		log.info("Inside POSTRequest call");
		RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.header("cookie", "JSESSIONID=" + sessionID + "");
		Response response = requestSpecification.post(uRI);
		log.debug(requestSpecification.log().all());
		return response;
	}

	public static Response PUTRequest(String uRI, String strJSON) {
		log.info("Inside PUTRequest call");
		RequestSpecification requestSpecification = RestAssured.given().body(strJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.put(uRI);
		log.debug(requestSpecification.log().all());
		return response;
	}

	public static Response DELETERequestWithHeader(String uRI, String headerName, Object headerValue) {
		log.info("Inside DELETERequest call");
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.header(headerName, headerValue);
		Response response = requestSpecification.delete(uRI);
		log.debug(requestSpecification.log().all());
		return response;
	}

	public static String getResposeString(Response response) {
		log.info("Converting Response to String");
		String strResponse = response.getBody().asString();
		log.debug(strResponse);
		return strResponse;
	}

	public static JsonPath jsonParser(String response) {
		log.info("Parsing String Response to JSON");
		JsonPath jsonResponse = new JsonPath(response);
		log.debug(jsonResponse);
		return jsonResponse;
	}

	public static XmlPath xmlParser(String response) {
		log.info("Parsing String Response to XML");
		XmlPath xmlResponse = new XmlPath(response);
		log.debug(xmlResponse);
		return xmlResponse;
	}

	public static int getStatusCode(Response response) {
		log.info("Getting Response Code");
		int statusCode = response.getStatusCode();
		log.info(statusCode);
		return statusCode;
	}

	public static String getStatusMessage(Response response) {
		log.info("Getting Response Message");
		String responseMessage = response.getStatusLine();
		log.info(responseMessage);
		return responseMessage;
	}

	public static String getValueStatusResponse(Response response, String status) {
		log.info("Getting Response " + status);
		String responseSTT = response.asString();
		JsonPath jsonResponse = new JsonPath(responseSTT);
		String statusValue = jsonResponse.get(status);
		log.info(statusValue);
		return statusValue;
	}

	public static String generatePayLoadString(String filename) {
		log.info("Inside PayloadConverter function");
		String filePath = System.getProperty("user.dir") + "\\resources\\" + filename;
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}