package api.helper;

import static org.junit.Assert.assertEquals;
import com.jayway.restassured.response.Response;

public class HelperMethods {


    public static void checkStatusIs200 (Response res) {
        assertEquals("Status Check Failed!", 200, res.getStatusCode());
    }
}
