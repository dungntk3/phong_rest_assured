package api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import log.LogHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.path.xml.XmlPath;
import org.slf4j.Logger;

public class SimpleGetTest {
	private Logger log = LogHelper.getLogger();

	@Test
	public void TC_01_verifyResponse() {

		RestAssured.baseURI = "https://maps.googleapis.com";
		given()
		.param("location", "-33.8670522,151.1957362")
		.param("radius", "500")
		.param("type", "restaurant")
		.param("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo")
		
		.when().get("/maps/api/place/nearbysearch/json")
				
		.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				
		.body("results[0].name", equalTo("The Little Snail Restaurant")).and()
				
		.body("results[13].name", equalTo("Din Tai Fung The Star")).and()
				
		.body("results[5].vicinity", equalTo("48 Pirrama Road, Pyrmont")).and().header("server", "scaffolding on HTTPServer2");

	}
	
	@Test
	public void TC_02_verifyResponse(){
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		Response  res = given().
			queryParam("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").
			queryParam("input", "skog%20haus").
			queryParam("inputtype", "textquery").
			queryParam("fields", "name,icon_mask_base_uri,icon_background_color").
			when().
				post("/maps/api/place/findplacefromtext/json").
		 then().assertThat().statusCode(200).
		
		extract().response();
		
		String respose = res.asString();
		log.info(respose);
		
		
		
		JsonPath jsonResponse = new JsonPath(respose);
		String placeId = jsonResponse.get("status");
		log.info(placeId);
		
		
//		given().
//			queryParam("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").
//			body("{"+
//			  "\"place_id\": \""+placeId+"\"" +
//		"}").
//			when().
//			post("/maps/api/place/delete/json").
//			
//			then().assertThat().statusCode(200).and().body("status", equalTo("OK"));
		

	}
	
	@Test
	public void TC_03_verifyResponse(){
		log.info(System.getProperty("user.dir"));
		
		String requestBody = generateString("PostXMLPayload.xml");
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		Response  res = given().
			queryParam("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").
			body(requestBody).
			when().
				post("/maps/api/place/add/xml").
		 then().assertThat().statusCode(200).
		
		extract().response();
		
		String respose = res.asString();
		
		log.info(respose);
		
		XmlPath xmlResponse = new XmlPath(respose);
		String placeId = xmlResponse.get("PlaceAddResponse.place_id");
		log.info("*********************");
		log.info(placeId);

	}
	
	@Test
	public void TC_04_verifyResponse(){
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		Response res = given().
		 param("location", "-33.8670522,151.1957362").
		 param("radius", "500").
		 param("type", "cruise").
		 param("key", "AIzaSyB-ZliaFkPtyfykn7E2nW2yxgBPAvRVUMo").
		 log().all().
		 
		 when().
		 get("/maps/api/place/nearbysearch/json").
		 
		 then().assertThat().statusCode(200).and().
		 contentType(ContentType.JSON).
		 log().all().
		 
		 extract().response();
		
		String response = res.asString();
		
		JsonPath jsonRes = new JsonPath(response);
		
		int arrSize = jsonRes.getInt("results.size()");
		
		for (int i = 0; i < arrSize; i++) {
			String name = jsonRes.getString("results["+i+"].name");
			log.info(name);
		}	 
	}
	
	@Test
	public void TC_05_verifyResponse(){
		
		String requestBody = generateString("JiraLogin.json");
		
		// Login JIRA
		
		RestAssured.baseURI = "http://localhost:8080";
		Response  res = given().
				contentType(ContentType.JSON).
			body(requestBody).
			when().
				post("/rest/auth/1/session").
		 then().assertThat().statusCode(200).
		
		extract().response();
		
		String respose = res.asString();
		
		JsonPath jsonRes = new JsonPath(respose);
		String sessionID = jsonRes.getString("session.value");
		
		//Create Issue
		String createBugBody = generateString("CreateBug.json");
		Response  createResponse = given().
				contentType(ContentType.JSON).
				header("cookie", "JSESSIONID=" + sessionID+"").
			body(createBugBody).
			when().
				post("/rest/api/2/issue").
		 then().assertThat().statusCode(201).log().all().
		
		 extract().response();

		JsonPath createResJson = new JsonPath(createResponse.asString());
		String issueID = createResJson.getString("id");
		
		
		// Add Comment
		String createCmntBody = generateString("AddCmnt.json");
		Response  addCmntResponse = given().
				contentType(ContentType.JSON).
				header("cookie", "JSESSIONID=" + sessionID+"").
			body(createCmntBody).
			when().
				post("/rest/api/2/issue/"+issueID+"/comment").
		 then().assertThat().statusCode(201).log().all().
		
		 extract().response();

		/*JsonPath createResJson = new JsonPath(createResponse.asString());
		String issueID = jsonRes.getString("id");*/
	}
	
	@Test
	public void TC_06_verifyResponse(){
		
		String requestBody = generateString("JiraLogin.json");
		
		// Login JIRA
		
		RestAssured.baseURI = "http://localhost:8080";
		Response  res = given().
				contentType(ContentType.JSON).
			body(requestBody).
			when().
				post("/rest/auth/1/session").
		 then().assertThat().statusCode(200).
		
		extract().response();
		
		String respose = res.asString();
		
		JsonPath jsonRes = new JsonPath(respose);
		String sessionID = jsonRes.getString("session.value");
	
		
		
		// Add Comment
		String createCmntBody = generateString("AddCmnt.json");
		Response  addCmntResponse = given().
				contentType(ContentType.JSON).
				header("cookie", "JSESSIONID=" + sessionID+"").
			body(createCmntBody).
			when().
				//post("/rest/api/2/issue/"+issueID+"/comment").
				post("/rest/api/2/issue/RA-1/comment").
		 then().assertThat().statusCode(201).log().all().
		
		 extract().response();

		JsonPath addCmntResJson = new JsonPath(addCmntResponse.asString());
		String cmntID = addCmntResJson.getString("id");
		
		
		log.info("Comment Added");
		
		//Update Comment
		String UpdateCmntBody = generateString("UpdateCmnt.json");
		Response  updateCmntResponse = given().
				contentType(ContentType.JSON).
				header("cookie", "JSESSIONID=" + sessionID+"").
			body(UpdateCmntBody).
			when().
				put("/rest/api/2/issue/RA-1/comment/" +cmntID+"").
		 then().assertThat().statusCode(200).log().all().
		
		 extract().response();

		/*JsonPath addCmntResJson = new JsonPath(addCmntResponse.asString());
		String cmntID = jsonRes.getString("id");*/
		
//		given().
//		contentType(ContentType.JSON).
//		header("cookie", "JSESSIONID=" + sessionID+"").
//		when().
//		delete("/rest/api/2/issue/RA-1/comment/" +cmntID+"").
//          then().assertThat().statusCode(204).log().all();
		
	}
	
	
	public static String generateString(String filename){
		String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

}
