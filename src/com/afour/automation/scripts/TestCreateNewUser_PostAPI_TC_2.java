package com.afour.automation.scripts;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.afour.automation.jsonPojo.NewUser;
import com.afour.automation.pages.AkasiaAPIs;
import com.google.gson.Gson;
import org.testng.Assert;

public class TestCreateNewUser_PostAPI_TC_2 extends BaseAPI{
	
	private HashMap<String, String> testData = null;
	 private  HashMap<String, String> actualResponse = null;
	 
	
		@BeforeTest
		public void tests(){
			this.testData = jsonData();
		}
	
	/*	TO DO:
	 *  GENERATE A NEW EMAIL AND NAME EVERYTIME THE TEST RUNS
	 */
	//{"email":"user11@gmail.com","password":"@four123","company":"Afour","phone":"9878987675","firstName":"user11","lastName":"lastname","address":"undefined"}
		@Test	
		public  void testCreateNewUser()throws ClientProtocolException, IOException{
			 AkasiaAPIs  akasiaAPI = new AkasiaAPIs ();
			    actualResponse= akasiaAPI.CreateNewUser(testData);
			    for (String  key : actualResponse.keySet()) {
			    String value = actualResponse.get(key);
				   System.out.println( key + ": " + value);
			    }
			    Assert.assertEquals(actualResponse.get("Status"), "HTTP/1.1 200 OK");
			
			
			
			
			
			
			
			//				NewUser newUser= new NewUser(testData);
//				String target_url = testData.get("target_url").toString();
//				Gson         gson          = new Gson();
//				HttpClient   httpClient    = HttpClientBuilder.create().build();
//				HttpPost     post          = new HttpPost(target_url );
//				StringEntity postingString = new StringEntity(gson.toJson(newUser)); //gson.tojson() converts your pojo to json
//			    post.setEntity(postingString);
//				post.setHeader("Content-type", "application/json");
//				HttpResponse  response = httpClient.execute(post);
//				 System.out.println(response.getStatusLine());
//				 
//				//get all headers
//				 Header[] headers = response.getAllHeaders();
//				 for (Header header : headers) {
//				 	System.out.println( header.getName()
//				 	      + " : " + header.getValue());
//				 }
//				 HttpEntity entity = response.getEntity();
//				 String responseString = EntityUtils.toString(entity);
//				 System.out.println(responseString);
//				
				      
			}


}
