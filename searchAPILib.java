package com.apple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.util.URIUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class searchAPILib {

	public static ArrayList<String> medialist = new ArrayList<String>();
	public static ArrayList<String> countrylist = new ArrayList<String>();
	public static Properties prop = new Properties();
	
	public void searchServiceValid(String term, String country, String media, String limit) throws ClientProtocolException, IOException {
		
		String requestURL = prop.getProperty("requestURL")+"?";
		String searchURL = requestURL;
		
		if(term == null || term.trim().equals("")){
			Assert.fail("Mandatory paramter term is not available");
		}else {
			searchURL = requestURL+"term="+URLEncoder.encode(term,"UTF-8");
		}
		try{
		if(country != null){
			if(country.length() > 2 || (!countrylist.contains(country)))
				Assert.fail("Invalid Country Code");
			searchURL = searchURL+"&country="+country;
			
		}
				
		if(media != null){
			if(!medialist.contains(media))
				Assert.fail("Invalid media specified");
			searchURL = searchURL+"&media="+media;
		}
		
		if(limit != null){
			if(Integer.parseInt(limit) < 0 || Integer.parseInt(limit) > 200)
				Assert.fail("Limit should be between 1 to 200");
			searchURL = searchURL+"&limit="+limit;
		}
		}
		catch(Exception e){
			System.out.println("Exception : "+e.getMessage());
		}

		HttpResponse Response = httpRequest(searchURL);
		if(Response.getStatusLine().getStatusCode() != 200){
			Assert.fail("Should return proper results");
			//System.out.println("Status Code : "+httpResponse.getStatusLine().getStatusCode());
		}
	}

	public void searchServiceInValidOptional(String term, String country, String media, String limit) throws ClientProtocolException, IOException {
		
		String requestURL = prop.getProperty("requestURL")+"?";
		String searchURL = requestURL;
		
		//System.out.println("Term : "+term);
		if(term == null || term.trim().equals("")){
			Assert.fail("Mandatory paramter term is not available");
		}else {
			searchURL = requestURL+"term="+URLEncoder.encode(term,"UTF-8");
		}
			searchURL = searchURL+"&country="+country;
			searchURL = searchURL+"&media="+media;
			searchURL = searchURL+"&limit="+limit;

		HttpResponse Response = httpRequest(searchURL);
		if(Response.getStatusLine().getStatusCode() == 200){
			Assert.fail("Should return error code forinvalid optional parameters");
			//System.out.println("Status Code : "+httpResponse.getStatusLine().getStatusCode());
		}

	}

public void searchServiceEmptyTerm(String country, String media, String limit) throws ClientProtocolException, IOException {
		
		String requestURL = prop.getProperty("requestURL")+"?";
		String searchURL = requestURL;
	
		searchURL = searchURL+"&country="+country;
		searchURL = searchURL+"&media="+media;
		searchURL = searchURL+"&limit="+limit;
			
		HttpResponse Response = httpRequest(searchURL);
		if(Response.getStatusLine().getStatusCode() == 200){
			Assert.fail("Should return error code for missing mandatory parameter");
			//System.out.println("Status Code : "+httpResponse.getStatusLine().getStatusCode());
		}
	}
	
	public void searchServiceEmptyOptional(String term) throws ClientProtocolException, IOException {
	
		String requestURL = prop.getProperty("requestURL")+"?";
		String searchURL = requestURL;
		
		if(term == null || term.trim().equals("")){
			Assert.fail("Mandatory paramter term is not available");
		}else {
			searchURL = requestURL+"term="+URLEncoder.encode(term,"UTF-8");
		}
	
		HttpResponse Response = httpRequest(searchURL);
		if(Response.getStatusLine().getStatusCode() != 200){
			Assert.fail("Should return proper results");
			//System.out.println("Status Code : "+httpResponse.getStatusLine().getStatusCode());
		}
	}

	public void searchServiceEmptyParams() throws ClientProtocolException, IOException {
	
		String requestURL = prop.getProperty("requestURL")+"?";
		String searchURL = requestURL;
		
		HttpResponse Response = httpRequest(searchURL);
		if(Response.getStatusLine().getStatusCode() == 200){
			Assert.fail("Should return error code for missing mandatory parameter");
			//System.out.println("Status Code : "+httpResponse.getStatusLine().getStatusCode());
		}
		
	}
	
	public static HttpResponse httpRequest(String searchURL) throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet(searchURL);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		return(httpResponse);
	}
	
	public void makelist(String str, char c){
		
		String[] strArr = str.split(",");
		//System.out.println("Length : "+strArr.length);
		for(String strEle : strArr){
			if(c == 'm')
				medialist.add(strEle);		

			if(c == 'c')
				countrylist.add(strEle);
		}
		
	}
	
	public void loadproperties() throws IOException{
		File file1 = new File("SearchParams.properties");
		FileInputStream fis = new FileInputStream(file1);
		prop.load(fis);	
	}
	
}
