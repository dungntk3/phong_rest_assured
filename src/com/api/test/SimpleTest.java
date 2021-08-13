package api.test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;

import api.helper.Contanst;
import api.util.RestUtil;
import api.util.URL;
import api.verify.BaseAssertion;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import log.LogHelper;
import log.SetupLog;

import org.slf4j.Logger;

public class SimpleTest extends SetupLog {

	public static Response response;

	public final Logger log = LogHelper.getLogger();
	public String sessionID_Test;

	@Test
	public void TC_01_verifyGetRequest() {
		RestAssured.baseURI = URL.URL_GG_MAP;
		response = given().param("location", "-33.8670522,151.1957362").param("radius", "500")
				.param("type", "restaurant").param("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo")
				.contentType(ContentType.JSON).when().get(URL.URL_GG_MAP_PLACE);

		BaseAssertion.verifyStatusCode(response, Contanst.CODE_STATUS_200);
		BaseAssertion.verifyBodyReponseGET(response, "results[0].name", "The Little Snail Restaurant");
		BaseAssertion.verifyHeaderResponseGET(response, "server", "scaffolding on HTTPServer2");
	}

	@Test
	public void TC_02_verifyPostRequest() {

		RestAssured.baseURI = URL.URL_GG_MAP;
		response = given().queryParam("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo")
				.queryParam("input", "skog%20haus").queryParam("inputtype", "textquery")
				.queryParam("fields", "name,icon_mask_base_uri,icon_background_color").when()
				.post("/maps/api/place/findplacefromtext/json");

		String status = RestUtil.getValueStatusResponse(response, "status");
		log.info(status);

		BaseAssertion.verifyStatusCode(response, Contanst.CODE_STATUS_200);

	}

	@Test
	public void TC_03_verifyPostRequest() {
		log.info(System.getProperty("user.dir"));

		String requestBody = RestUtil.generatePayLoadString("PostXMLPayload.xml");

		RestAssured.baseURI = URL.URL_GG_MAP;
		response = given().queryParam("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").body(requestBody).when()
				.post("/maps/api/place/add/xml");

		BaseAssertion.verifyStatusCode(response, Contanst.CODE_STATUS_200);

		String respose = response.asString();
		log.info(respose);

		String placeId = RestUtil.xmlParser(respose).get("PlaceAddResponse.place_id");
		log.info("*********************");
		log.info(placeId);
	}

	@Test
	public void TC_04_verifyGetRequest() {

		RestAssured.baseURI = URL.URL_GG_MAP;

		response = given().param("location", "-33.8670522,151.1957362").param("radius", "500").param("type", "cruise")
				.param("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").log().all().

				when().get("/maps/api/place/nearbysearch/json");

		String strResponse = response.asString();
		JsonPath jsonRes = RestUtil.jsonParser(strResponse);

		BaseAssertion.verifyStatusCode(response, Contanst.CODE_STATUS_200);

		int arrSize = jsonRes.getInt("results.size()");
		String[] arrName = new String[arrSize];

		for (int i = 0; i < arrSize; i++) {
			String name = jsonRes.getString("results[" + i + "].name");
			log.info(name);
			arrName[i] = name;
		}

		Assert.assertEquals(arrName[0], "Sydney");
	}

	@Test
	public void TC_05_verifyPostRequest() {

		// Login Jira
		sessionID_Test = BaseAssertion.doLoginJira();

		// Create Issue
		String createBugBody = RestUtil.generatePayLoadString("CreateBug.json");
		Response createResponse = given().contentType(ContentType.JSON)
				.header("cookie", "JSESSIONID=" + sessionID_Test + "").body(createBugBody).when()
				.post("/rest/api/2/issue");

		BaseAssertion.verifyStatusCode(createResponse, Contanst.CODE_STATUS_201);

		JsonPath createResJson = new JsonPath(createResponse.asString());
		String issueID = createResJson.getString("id");

		// Add Comment
		String createCmntBody = RestUtil.generatePayLoadString("AddCmnt.json");
		Response addCmntResponse = given().contentType(ContentType.JSON)
				.header("cookie", "JSESSIONID=" + sessionID_Test + "").body(createCmntBody).when()
				.post("/rest/api/2/issue/" + issueID + "/comment");

		BaseAssertion.verifyStatusCode(addCmntResponse, Contanst.CODE_STATUS_201);
	}

	@Test
	public void TC_06_verifyDeleteAndPutRequest() {

		// Login Jira
		sessionID_Test = BaseAssertion.doLoginJira();

		// Add Comment
		String createCmntBody = RestUtil.generatePayLoadString("AddCmnt.json");
		Response addCmntResponse = given().contentType(ContentType.JSON)
				.header("cookie", "JSESSIONID=" + sessionID_Test + "").body(createCmntBody).when().
				// post("/rest/api/2/issue/"+issueID+"/comment").
				post("/rest/api/2/issue/RA-1/comment");

		BaseAssertion.verifyStatusCode(addCmntResponse, Contanst.CODE_STATUS_201);

		JsonPath addCmntResJson = new JsonPath(addCmntResponse.asString());
		String cmntID = addCmntResJson.getString("id");

		log.info("Comment Added");

		// Update Comment
		String UpdateCmntBody = RestUtil.generatePayLoadString("UpdateCmnt.json");
		Response updateCmntResponse = given().contentType(ContentType.JSON)
				.header("cookie", "JSESSIONID=" + sessionID_Test + "").body(UpdateCmntBody).when()
				.put("/rest/api/2/issue/RA-1/comment/" + cmntID + "");

		BaseAssertion.verifyStatusCode(updateCmntResponse, Contanst.CODE_STATUS_200);

		// Delete Comment

		String endPointURI = URL.getEndPointJira("/rest/api/2/issue/RA-1/comment/" + cmntID + "");

		Response deleteCmntResponse = RestUtil.DELETERequestWithHeader(endPointURI, "cookie",
				"JSESSIONID=" + sessionID_Test + "");

		BaseAssertion.verifyStatusCode(deleteCmntResponse, Contanst.CODE_STATUS_204);

	}

}
