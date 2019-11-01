package com.afour.automation.scripts;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.afour.automation.jsonPojo.NewUser;
import com.afour.automation.pages.AkasiaAPIs;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;


public class TestLogin_GetAPI_TC_1 extends BaseAPI{
	
	 private HashMap<String, String> testData = null;
	 private  HashMap<String, String> actualResponse = null;
	@BeforeTest
	public void tests(){
		this.testData = jsonData();
		
	}
	
	
	//public void testUserLogin(String target_url,String username,String password )throws ClientProtocolException, IOException {
	@Test
		public void testUserLogin()throws ClientProtocolException, IOException {	
		
		    AkasiaAPIs  akasiaAPI = new AkasiaAPIs ();
		    actualResponse= akasiaAPI.login(testData);
		    for (String  key : actualResponse.keySet()) {
		    String value = actualResponse.get(key);
			   System.out.println( key + ": " + value);
		    }
		    Assert.assertEquals(actualResponse.get("Status"), "HTTP/1.1 200 OK");
		    
		    
		    
		    
		    
//			String target_url = testData.get("target_url").toString();
//			String username =  testData.get("username").toString();
//			String password =  testData.get("password").toString();
//			
//		String encodedCredentials =username+":"+password;
//		byte[] encryptedUid = Base64.encodeBase64(encodedCredentials.getBytes());
//		     // vars.put("base64HeaderValue",new String(encryptedUid));
//		System.out.println(new String(encryptedUid));
//		HttpUriRequest request = new HttpGet(target_url);
//		request.addHeader("Authorization", "Basic " + new String(encryptedUid));
//		HttpResponse response = HttpClientBuilder.create().build().execute(request);
//		 System.out.println(response.getStatusLine());
//		 
//			//get all headers
//			 Header[] headers = response.getAllHeaders();
//			 for (Header header : headers) {
//			 	System.out.println( header.getName()
//			 	      + " : " + header.getValue());
//			 }
//			 HttpEntity entity = response.getEntity();
//			//String responseString = EntityUtils.toString(entity, "UTF-8");
//			
//			String responseString = EntityUtils.toString(entity);
//			System.out.println(responseString);
			
			
			
	}
}
