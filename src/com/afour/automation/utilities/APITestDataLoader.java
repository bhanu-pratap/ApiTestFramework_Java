
/* This is a stub for DataLoader.jar in vanilla, until the issue with it are fixed and latest jar is available */

package com.afour.automation.utilities;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class APITestDataLoader {
	
	
	private HashMap<String, String> loadedMap = null;
	  
	  public HashMap<String, String> testDataLoader(String filePath)
	  {
	    if (filePath.contains(".json")) {
	      this.loadedMap = jsonTestDataLoader(filePath);
	    } else if (filePath.contains(".xml")) {
	      this.loadedMap = xmlTestDataLoader(filePath);
	    } else if (filePath.contains(".csv")) {
	      this.loadedMap = csvTestDataLoader(filePath);
	    } else if (filePath.contains(".properties")) {
	      this.loadedMap = propertyTestDataLoader(filePath);
	    }
	    return this.loadedMap;
	  }
	  
	  private HashMap<String, String> jsonTestDataLoader(String filepath)
	  {
	    HashMap<String, String> testData = new HashMap();
	    try
	    {
	      String testDatakey = null;
	      String testDataValue = null;
	      
	      FileReader reader = new FileReader(filepath);
	      JSONParser parser = new JSONParser();
	      JSONObject jsonObject = (JSONObject)parser.parse(reader);
	      
	      Set<String> keySet = jsonObject.keySet();
	      for (String key : keySet)
	      {
	        testDatakey = key;
	        testDataValue = jsonObject.get(key).toString();
	        testData.put(testDatakey, testDataValue);
	      }
	    }
	    catch (FileNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return testData;
	  }
	  
	  private HashMap<String, String> xmlTestDataLoader(String filePath)
	  {
	    HashMap<String, String> testData = new HashMap();
	    String testDataKey = null;
	    String testDataValue = null;
	    try
	    {
	      File xmlFile = new File(filePath);
	      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	      Document doc = dBuilder.parse(xmlFile);
	      
	      NodeList elementNodeList = doc.getElementsByTagName("TestData");
	      for (int temp = 0; temp < elementNodeList.getLength(); temp++)
	      {
	        Node elementNode = elementNodeList.item(temp);
	        
	        NodeList paramNodeList = elementNode.getChildNodes();
	        for (int temp1 = 0; temp1 < paramNodeList.getLength(); temp1++)
	        {
	          Element ele = null;
	          Node paramNode = paramNodeList.item(temp1);
	          if (paramNodeList.item(temp1).getNodeType() == 1)
	          {
	            ele = (Element)paramNode;
	            testDataKey = ele.getAttribute("key");
	            testDataValue = ele.getAttribute("value");
	            testData.put(testDataKey, testDataValue);
	          }
	        }
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return testData;
	  }
	  
	  private HashMap<String, String> csvTestDataLoader(String filePath)
	  {
	    HashMap<String, String> mapOuter = new HashMap();
	    try
	    {
	      CSVReader reader = new CSVReader(new FileReader(filePath));
	      
	      String outerKey = "";
	      String value = "";
	      String[] nextLine;
	      while ((nextLine = reader.readNext()) != null)
	      {
	       // String[] nextLine;
	        outerKey = nextLine[0];
	        value = nextLine[1];
	        mapOuter.put(outerKey, value);
	      }
	      reader.close();
	    }
	    catch (Exception e)
	    {
	      System.out.println(e);
	    }
	    return mapOuter;
	  }
	  
	  public HashMap<String, String> propertyTestDataLoader(String filePath)
	  {
	    HashMap<String, String> HMap = new HashMap();
	    Properties prop = new Properties();
	    InputStream input = null;
	    try
	    {
	      input = new FileInputStream(filePath);
	      prop.load(input);
	      Enumeration<?> e = prop.propertyNames();
	      while (e.hasMoreElements())
	      {
	        String key = (String)e.nextElement();
	        String value = prop.getProperty(key);
	        HMap.put(key, value);
	      }
	    }
	    catch (FileNotFoundException ex)
	    {
	      ex.printStackTrace();
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return HMap;
	  }

}
