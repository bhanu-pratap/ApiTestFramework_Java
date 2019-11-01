package com.afour.automation.pages;

import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.*;

import testData.TestDataLoader;

import com.afour.automation.jsonPojo.NewUser;
import com.afour.automation.jsonPojo.Plan4cloud;
import com.afour.automation.utilities.APITestDataLoader;
import com.google.common.base.Splitter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class AkasiaAPIs {
	
	public HashMap<String, String> restUrlmap = null;
	public static String currentDir = System.getProperty("user.dir");
	
	public AkasiaAPIs(){
		restUrlmap= getRestUrls();
	}
	
	public HashMap<String, String> getRestUrls() {
		HashMap<String, String> map = null;
		map = new APITestDataLoader().testDataLoader("RestUrls.properties");
		return map;
	}
	
	
	// Create New User Rest API
	
	public HashMap<String, String> CreateNewUser (HashMap<String, String> testData) throws ClientProtocolException, IOException {
		
		HashMap<String, String> output_Response = new HashMap ();
		String target_url = restUrlmap.get("createNewUser_target_url");
		
		NewUser newUser= new NewUser(testData);
		
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(target_url );
		StringEntity postingString = new StringEntity(gson.toJson(newUser)); //gson.tojson() converts your pojo to json
	    post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		 output_Response= GetOutputResponse(response);
		 
		 return output_Response;
	
	}

	
	// Login Rest API
	public HashMap<String, String> login (HashMap<String, String> testData) throws ClientProtocolException, IOException {
		
		HashMap<String, String> output_Response = new HashMap ();
		String target_url = restUrlmap.get("login_target_url");
		
		//String target_url = testData.get("target_url").toString();
		String username =  testData.get("username").toString();
		String password =  testData.get("password").toString();
		
	String encodedCredentials =username+":"+password;
	byte[] encryptedUid = Base64.encodeBase64(encodedCredentials.getBytes());
	     // vars.put("base64HeaderValue",new String(encryptedUid));
	System.out.println(new String(encryptedUid));
	HttpUriRequest request = new HttpGet(target_url);
	request.addHeader("Authorization", "Basic " + new String(encryptedUid));
	HttpResponse response = HttpClientBuilder.create().build().execute(request);
	
	
	 //System.out.println(response.getStatusLine());
	 output_Response.put("Status",response.getStatusLine().toString());
		//get all headers
		 Header[] headers = response.getAllHeaders();
		 for (Header header : headers) {
		 	//System.out.println( header.getName()
		 	  //    + " : " + header.getValue());
		 	if (header!= null) {
			 	try{
			 		output_Response.put(header.getName(), header.getValue());
				 }
				 
				 catch(Exception e){
					 System.out.println(e.getMessage());
				 }
		 	}
		 }
		 HttpEntity entity = response.getEntity();
		//String responseString = EntityUtils.toString(entity, "UTF-8");
		String responseString = EntityUtils.toString(entity);
		HashMap<String, String> respMap = ConvertToHashMap(responseString);
		for (String  key : respMap.keySet()){
			output_Response.put(key, respMap.get(key));
		}
	//	System.out.println(responseString);
		
		return output_Response;
		
	}
	
public HashMap<String, String> GetListOFHyperVisors (HashMap<String, String> testData) throws ClientProtocolException, IOException {
		
		HashMap<String, String> output_Response = new HashMap ();
		HashMap<String, String> login_Response = new HashMap();
		String target_url = restUrlmap.get("getListofHyperVisors_target_url");
		
		login_Response= this.login(testData);
		String encryptedUid = login_Response.get("authKey");
			
	HttpUriRequest request = new HttpGet(target_url);
	request.addHeader("Authorization", "Basic " + encryptedUid);
	HttpResponse response = HttpClientBuilder.create().build().execute(request);
	 //System.out.println(response.getStatusLine());
	 output_Response.put("Status",response.getStatusLine().toString());
		//get all headers
		 Header[] headers = response.getAllHeaders();
		 for (Header header : headers) {
		 	//System.out.println( header.getName()
		 	  //    + " : " + header.getValue());
		 	if (header!= null) {
			 	try{
			 		output_Response.put(header.getName(), header.getValue());
				 }
				 
				 catch(Exception e){
					 System.out.println(e.getMessage());
				 }
		 	}
		 }
		 HttpEntity entity = response.getEntity();
		//String responseString = EntityUtils.toString(entity, "UTF-8");
		String responseString = EntityUtils.toString(entity);
		
		//HashMap<String, String> respMap = ConvertToHashMap(responseString);
		
		//HashMap<String, String> respMap= null;
		//Type collectionType  = new TypeToken<Collection<HashMap<String, String>>>(){}.getType();
		//Collection<HashMap<String, String>> enums =new Gson().fromJson(responseString, collectionType);
		//for (String  key : respMap.keySet()){
//			output_Response.put(key, respMap.get(key));
//		}
	//	System.out.println(responseString);
		output_Response.put("Response String",responseString);
		
		return output_Response;
		
	}


/* Upload Inventory */
	
public HashMap<String, String> UploadInventory (HashMap<String, String> testData) throws ClientProtocolException, IOException {
	HashMap<String, String> output_Response = new HashMap ();
	HashMap<String, String> login_Response = new HashMap();
	
	String target_url = restUrlmap.get("uploadInventory_url");
	
	String hyperVisorName =  testData.get("hyperVisorName").toString();
	String uploadedHostFile = testData.get("uploadedHostFile").toString();
	String uploadedVmFile =  testData.get("uploadedVmFile").toString();
	String uploadedDataStoreFile = testData.get("uploadedDataStoreFile").toString();
	String uploadedHistoryFile =  testData.get("uploadedHistoryFile").toString();
	
	login_Response= this.login(testData);
	String encryptedUid = login_Response.get("authKey");
	
	
	HttpClient   httpClient    = HttpClientBuilder.create().build();
	HttpPost     post          = new HttpPost(target_url );
	
       
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.addTextBody("hyperVisorName", hyperVisorName)  ; 
    
    builder.addPart("uploadedHostFile", new FileBody(new File(currentDir +"/TestData/"+uploadedHostFile)));
    builder.addPart("uploadedVmFile", new FileBody(new File(currentDir +"/TestData/"+uploadedVmFile)));
    builder.addPart("uploadedDataStoreFile", new FileBody(new File(currentDir +"/TestData/"+uploadedDataStoreFile)));
    builder.addPart("uploadedHistoryFile", new FileBody(new File(currentDir +"/TestData/"+uploadedHistoryFile )));
    HttpEntity reqentity = builder.build();
    
    post.setEntity(reqentity);
    post.setHeader("Authorization", "Basic " + encryptedUid);
	
	HttpResponse  response = httpClient.execute(post);
	
	output_Response= GetOutputResponse(response);
	return output_Response;
}

/* Get List Of Hosts Using Hypervisor ID
   1. login and get the auth Key
   2. Get the Hypervisor ID
   3. Send the Request
*/
public HashMap<String, String> GetListOfHostsUsingHypervisorID(HashMap<String, String> testData) throws ClientProtocolException, IOException {
	HashMap<String, String> output_Response = new HashMap ();
	HashMap<String, String> login_Response = new HashMap();
	HashMap<String, String> getListOfHypervisors_response = new HashMap();
	
	
	login_Response= this.login(testData);
	String encryptedUid = login_Response.get("authKey");
	
	getListOfHypervisors_response = this.GetListOFHyperVisors(testData);
	String hypervisorResponseString = getListOfHypervisors_response.get("Response String");
	
//	HashMap<Integer, HashMap<String,String>> gens = new HashMap<Integer,HashMap<String,String>>();
//	gens = ParseExtractTheJSONresponse(hypervisorResponseString);
//	String hypervisorId = gens.get(1).get("id");
	
	String hypervisorId=GetTheFirstHypervisorid(hypervisorResponseString);
	String target_url = restUrlmap.get("getListOfHost_target_url");
	
	String target_urlWithQueryString = AddQueryString(target_url, "hypervisorid", hypervisorId);
	
//	List<NameValuePair> params = new ArrayList<NameValuePair>();
//	params.add(new BasicNameValuePair("hypervisorid", hypervisorId));
//	
//	StringBuilder requestUrl = new StringBuilder(target_url);
//	String querystring = URLEncodedUtils.format(params, "utf-8");
//	requestUrl.append("?");
//	requestUrl.append(querystring);

	    
	HttpUriRequest request = new HttpGet(target_urlWithQueryString);
	request.addHeader("Authorization", "Basic " + encryptedUid);
	HttpResponse response = HttpClientBuilder.create().build().execute(request);
	output_Response = GetOutputResponse(response);
	
	return output_Response;
}


/* Get List Of Vms Using Hypervisor ID
1. login and get the auth Key
2. Get the Hypervisor ID
3. Send the Request
*/

public HashMap<String, String> GetListOfVmsUsingHypervisorID(HashMap<String, String> testData) throws ClientProtocolException, IOException {
	
	HashMap<String, String> output_Response = new HashMap ();
	HashMap<String, String> login_Response = new HashMap();
	HashMap<String, String> getListOfHypervisors_response = new HashMap();
	
	
	login_Response= this.login(testData);
	String encryptedUid = login_Response.get("authKey");
	
	getListOfHypervisors_response = this.GetListOFHyperVisors(testData);
	String hypervisorResponseString = getListOfHypervisors_response.get("Response String");
	
	String hypervisorId=GetTheFirstHypervisorid(hypervisorResponseString);
	
	String target_url = restUrlmap.get("getListOfVms_target_url");
	
	String target_urlWithQueryString = AddQueryString(target_url, "hypervisorid", hypervisorId);
	
	    
	HttpUriRequest request = new HttpGet(target_urlWithQueryString);
	request.addHeader("Authorization", "Basic " + encryptedUid);
	HttpResponse response = HttpClientBuilder.create().build().execute(request);
	output_Response = GetOutputResponse(response);
	
	return output_Response;
}


public HashMap<String, String> MapOnPremVmstoPublicClouds (HashMap<String, String> testData) throws Exception ,ClientProtocolException, IOException{
	HashMap<String, String> output_Response = new HashMap ();
	HashMap<String, String> login_Response = new HashMap();
	HashMap<String, String> getListOfVmsUsingHypervisorID_response =new HashMap();
	
	String target_url = restUrlmap.get("MapOnPremVmsToPublicClouds_target_url");
	
	String cloudName =  testData.get("cloudName").toString();
	
	boolean rightSizeBoolean = false;
	String rightSizetext = testData.get("rightSize").toString();
	if(rightSizetext=="true"){
		rightSizeBoolean=true;
	}
	
	String cloudProvider =  testData.get("cloudProvider").toString();
	
	boolean chargeableboolean = false;
	String chargeabletext = testData.get("chargeable").toString();
	if(chargeabletext=="true"){
		chargeableboolean=true;
	}
	
	
	
	login_Response= this.login(testData);
	String encryptedUid = login_Response.get("authKey");
	
	getListOfVmsUsingHypervisorID_response = this.GetListOfVmsUsingHypervisorID(testData);
	String vmsResponseString = getListOfVmsUsingHypervisorID_response.get("Response String");
	List<String> vmsId = ParseExtractTheJSONresponse(vmsResponseString,"vms","vmId");
	String hypervisorId =ParseExtractTheJSONresponse(vmsResponseString,"vms","hypervisorId").get(1);
	
	
	JSONArray mJSONArray = new JSONArray(vmsId);
	
	String ReqJson ="{\"vmsToBeMapped\":{\""+hypervisorId+"\":"+vmsId +"},\"cloudName\":\""+ cloudName +"\",\"rightSize\":false,\"cloudProvider\":\""+cloudProvider+"\",\"chargeable\":false}";
	// JSONObject jsonObj = new JSONObject(ReqJson);
	
	HttpClient   httpClient    = HttpClientBuilder.create().build();
	HttpPost     post          = new HttpPost(target_url );
	
    post.setHeader("Authorization", "Basic " + encryptedUid);
    
    StringEntity postingString = new StringEntity(ReqJson); //gson.tojson() converts your pojo to json
    post.setEntity(postingString);
	post.setHeader("Content-type", "application/json");
	HttpResponse  response = httpClient.execute(post);
	 output_Response= GetOutputResponse(response);
	
	return output_Response;
}

/*
 * Get OnPremise Cost by hypervisor id
 * /rest/admin/1/inventory/getOnPremCost?hypervisorid= <id>
 */

public HashMap<String, String> GetOnPremiseCostByHypervisorId (HashMap<String, String> testData) throws Exception ,ClientProtocolException, IOException{

	HashMap<String, String> output_Response = new HashMap ();
	HashMap<String, String> login_Response = new HashMap();
	HashMap<String, String> getListOfHypervisors_response = new HashMap();
	
	
	login_Response= this.login(testData);
	String encryptedUid = login_Response.get("authKey");
	
	getListOfHypervisors_response = this.GetListOFHyperVisors(testData);
	String hypervisorResponseString = getListOfHypervisors_response.get("Response String");
	
	String hypervisorId=GetTheFirstHypervisorid(hypervisorResponseString);
	
	String target_url = restUrlmap.get("getOnPremiseCostByHhypervisorid_target_url");
	
	String target_urlWithQueryString = AddQueryString(target_url, "hypervisorid", hypervisorId);
	
	    
	HttpUriRequest request = new HttpGet(target_urlWithQueryString);
	request.addHeader("Authorization", "Basic " + encryptedUid);
	HttpResponse response = HttpClientBuilder.create().build().execute(request);
	output_Response = GetOutputResponse(response);
		
	return output_Response;
}


//Extract the first HypervisorId from the List of Hypervisors

private String GetTheFirstHypervisorid (String responseString){
	String hypervisorId = null;
	HashMap<Integer, HashMap<String,String>> gens = new HashMap<Integer,HashMap<String,String>>();
	gens = ParseExtractTheJSONresponse(responseString);
	hypervisorId = gens.get(1).get("id");
	return hypervisorId;
}

private String AddQueryString(String target_url, String QueryStringName, String QueryStringValue){
	
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair(QueryStringName, QueryStringValue));
	
	StringBuilder requestUrl = new StringBuilder(target_url);
	String querystring = URLEncodedUtils.format(params, "utf-8");
	requestUrl.append("?");
	requestUrl.append(querystring);
	
	return requestUrl.toString();
	
}

// GET OUTPUT RESPONSE 
	
	private  HashMap<String, String> GetOutputResponse(HttpResponse  response) throws IOException{
		HashMap<String, String> output_Response = new HashMap();
		
		output_Response.put("Status",response.getStatusLine().toString());
		//get all headers
		 Header[] headers = response.getAllHeaders();
		 for (Header header : headers) {
		 	//System.out.println( header.getName()  + " : " + header.getValue());
		 	if (header!= null) {
			 	try{
			 		output_Response.put(header.getName(), header.getValue());
				 }
				 
				 catch(Exception e){
					 System.out.println(e.getMessage());
				 }
		 	}
		 }
		 HttpEntity resentity = response.getEntity();
		
		String responseString = EntityUtils.toString(resentity);
		
		output_Response.put("Response String",responseString);
		
		return output_Response;
		
	}
	
	
	//Parse the JSON Response String and extract the values 
	
	private  List<String> ParseExtractTheJSONresponse(String responseString, String fieldNametoLook, String fieldsKeytoLook) throws Exception{
		 JsonFactory f = new MappingJsonFactory();
		  
		   String JsonString = responseString;
		   List<String> fieldValueExtracted = new ArrayList<String>();;
		   // JsonParser jp = f.createJsonParser(new File(JsonString));
		    JsonParser jp = f.createJsonParser(JsonString);

		    JsonToken current;

		    current = jp.nextToken();
		    if (current != JsonToken.START_OBJECT) {
		      System.out.println("Error: root should be object: quiting.");
		      return null;
		    }

		    while (jp.nextToken() != JsonToken.END_OBJECT) {
		      String fieldName = jp.getCurrentName();
		      // move from field name to field value
		      current = jp.nextToken();
		      if (fieldName.equals(fieldNametoLook)) {
		        if (current == JsonToken.START_ARRAY) {
		          // For each of the records in the array
		          while (jp.nextToken() != JsonToken.END_ARRAY) {
		            // read the record into a tree model,
		            // this moves the parsing position to the end of it
		            JsonNode node = jp.readValueAsTree();
		            // And now we have random access to everything in the object
		            fieldValueExtracted.add(node.get(fieldsKeytoLook).toString());
		            System.out.println(fieldsKeytoLook+": " + node.get(fieldsKeytoLook).getTextValue());
		            //System.out.println("field2: " + node.get("field2").getValueAsText());
		          }
		        } else {
		          System.out.println("Error: records should be an array: skipping.");
		          jp.skipChildren();
		        }
		      } else {
		        System.out.println("Unprocessed property: " + fieldName);
		        jp.skipChildren();
		      }
		    }  
		    return fieldValueExtracted;
	}
	
	
	// ParseExtractTheJSONresponse has been overloaded
	private  HashMap<Integer,HashMap<String, String>> ParseExtractTheJSONresponse(String responseString) {
			
		Pattern pattern = Pattern.compile("(?:\\\"|\\')(?<key>[^\"]*)(?:\\\"|\\')(?=:)(?:\\:\\s*)(?:\\\"|\\')?(?<value>true|false|[0-9a-zA-Z\\+\\-\\\\.\\$]*)");
		Matcher m = pattern.matcher(responseString);
		
		HashMap<String,String> hypervisorInfo = new HashMap<String,String>();
		HashMap<Integer, HashMap<String,String>> gens = new HashMap<Integer,HashMap<String,String>>();
	    int count=1;
	    
		while (m.find()){
				if(!gens.containsKey(count)){
				        if(!hypervisorInfo.containsKey(m.group(1))){
				    	 hypervisorInfo.put(m.group(1), m.group(2));
				    	 System.out.println(m.group(1) + "->" + m.group(2));
				     }
				     else{
				    	 gens.put(count, hypervisorInfo);
				    	  hypervisorInfo = new HashMap<String,String>();
				    	  hypervisorInfo.put(m.group(1), m.group(2));
				    	  System.out.println(m.group(1) + "->" + m.group(2));
				     count++;
				     }
			   }
				
			 }
		gens.put(count, hypervisorInfo);     

		return gens;
		
	}

//Convert to entity String to HashMap
	private HashMap<String, String> ConvertToHashMap(String responseString) {
			HashMap<String, String> retMap = new Gson().fromJson(responseString, new TypeToken<HashMap<String, String>>() {}.getType());
		    return retMap;
		}
	

    }


