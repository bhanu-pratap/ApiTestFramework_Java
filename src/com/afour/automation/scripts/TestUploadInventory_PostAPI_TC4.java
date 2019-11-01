package com.afour.automation.scripts;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.afour.automation.pages.AkasiaAPIs;

public class TestUploadInventory_PostAPI_TC4 extends BaseAPI{
	 private HashMap<String, String> testData = null;
	 private  HashMap<String, String> actualResponse = null;
	 
	 @BeforeTest
		public void tests(){
			this.testData = jsonData();
		}
	 
	 /* TO DO:
	  * GENERATE THE NEW hyperVisorName EVERYTIME THE TEST RUNS
	  */
	 
	 @Test
		public void testUploadInventory()throws ClientProtocolException, IOException {
			AkasiaAPIs  akasiaAPI = new AkasiaAPIs ();
			actualResponse= akasiaAPI.UploadInventory(testData);
			for (String  key : actualResponse.keySet()) {
			    String value = actualResponse.get(key);
				   System.out.println( key + ": " + value);
		    }
			Assert.assertEquals(actualResponse.get("Status"), "HTTP/1.1 200 OK");
	 }
}
