package com.apple;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.apple.searchAPILib.*;

public class SearchAPI extends searchAPILib{

	
	public static String termvalid,countryvalid,mediavalid,limitvalid,termnull,countryinvalid,mediainvalid,limitinvalid;
	
	@BeforeSuite
	public void load() throws IOException{
	
		loadproperties();
		termvalid = prop.getProperty("termvalid");
		countryvalid = prop.getProperty("countryvalid");
		mediavalid = prop.getProperty("mediavalid");
		limitvalid = prop.getProperty("limitvalid");
		termnull = prop.getProperty("termnull");
		countryinvalid = prop.getProperty("countryvalid");
		mediainvalid = prop.getProperty("mediavalid");
		limitinvalid = prop.getProperty("limitvalid");
		String mediaString = prop.getProperty("medialist");
		String countryString = prop.getProperty("countrycodes");
		
		makelist(mediaString,'m');
		makelist(countryString,'c');
		
	}
	
	@Test
	public void searchAPI_ValidParams() throws IOException {
		searchServiceValid(termvalid,countryvalid,mediavalid,limitvalid);
	}
	
	@Test
	public void searchAPI_InValidCounty() throws IOException {
		searchServiceInValidOptional(termvalid,countryinvalid,mediavalid,limitvalid);
	}
	
	@Test
	public void searchAPI_InValidMedia() throws IOException {
		searchServiceInValidOptional(termvalid,countryvalid,mediainvalid,limitvalid);
	}

	@Test
	public void searchAPI_InValidLimit() throws IOException {
		searchServiceInValidOptional(termvalid,countryvalid,mediavalid,limitinvalid);
	}

	@Test
	public void searchAPI_NullTerm() throws IOException {
		searchServiceValid(termnull,countryvalid,mediavalid,limitvalid);
	}
	
	@Test
	public void searchAPI_EmptyTerm() throws IOException {
		searchServiceEmptyTerm(countryvalid,mediavalid,limitvalid);
	}

	@Test
	public void searchAPI_EmptyOptionalParams() throws IOException {
		searchServiceEmptyOptional(termvalid);
	}
	
	@Test
	public void searchAPI_EmptyParams() throws IOException {
		searchServiceEmptyParams();
	}
	
}
