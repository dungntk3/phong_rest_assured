package api.test;

import org.junit.After;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import api.helper.HelperMethods;
import api.util.RestUtil;
 
 
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //For Ascending order test execution
public class Example1Test {
 
    //First, I declared Response and JsonPath objects.
    private Response res = null; //Response object
    @Before
    public void setup (){
        //Test Setup
        RestUtil.setBaseURI("http://api.5min.com"); //Setup Base URI
        RestUtil.setBasePath("search"); //Setup Base Path
        RestUtil.setContentType(ContentType.JSON); //Setup Content Type
        RestUtil.createSearchQueryPath("barack obama", "videos.json", "num_of_videos", "4"); //Construct the path
        res = RestUtil.getResponse(); //Get response
        RestUtil.getJsonPath(res);
    }
 
    @Test
    public void T01_StatusCodeTest() {
        HelperMethods.checkStatusIs200(res);
    }
 
    @After
    public void afterTest (){
        RestUtil.resetBaseURI();
        RestUtil.resetBasePath();
    }

}
