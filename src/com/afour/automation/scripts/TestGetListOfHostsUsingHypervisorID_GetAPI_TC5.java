package com.afour.automation.scripts;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.afour.automation.pages.AkasiaAPIs;

public class TestGetListOfHostsUsingHypervisorID_GetAPI_TC5 extends BaseAPI {
	 private HashMap<String, String> testData = null;
	 private  HashMap<String, String> actualResponse = null;
	@BeforeTest
	public void tests(){
		this.testData = jsonData();
  }
	
	@Test
	public void testGetListOfHostsUsingHypervisorID()throws ClientProtocolException, IOException {
		AkasiaAPIs  akasiaAPI = new AkasiaAPIs ();
	    actualResponse= akasiaAPI.GetListOfHostsUsingHypervisorID(testData);
	    for (String  key : actualResponse.keySet()) {
	    String value = actualResponse.get(key);
		   System.out.println( key + ": " + value);
	    }
	    Assert.assertEquals(actualResponse.get("Status"), "HTTP/1.1 200 OK");
	}
}
