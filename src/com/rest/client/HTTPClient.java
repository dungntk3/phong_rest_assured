package rest.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTPClient {
	
	// 1. GET Call
		public void getCall(String url) throws ClientProtocolException, IOException, JSONException {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet getCall = new HttpGet(url); // REST get Call
			CloseableHttpResponse response = httpClient.execute(getCall);

			//A. Extracting the Response Code
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("Status Code is : " + statusCode);

			//B. Getting Response JSON
			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONObject responseJson = new JSONObject(responseString);
			System.out.println("API Respose JSON : " + responseJson);

			// C. Getting Response Headers
			Header[] responseHeaders = response.getAllHeaders();

			HashMap<String, String> headerAll = new HashMap<String, String>();

			for (Header header : responseHeaders) {
				headerAll.put(header.getName(), header.getValue());
			}

			System.out.println("All Headers are : " + headerAll);
		}
		
		public CloseableHttpResponse getResponseHTTPGet(String url) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet getCall = new HttpGet(url); // REST get Call
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(getCall);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;
		}

}
