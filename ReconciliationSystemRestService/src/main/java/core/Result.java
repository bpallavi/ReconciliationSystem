package core;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	
	@JsonProperty
	String id;
	@JsonProperty
	String value;
	
	HashMap<String,String>results;
	
	
	public HashMap<String, String> getResults() {
		return results;
	}

	public void setResults(HashMap<String, String> results) {
		this.results = results;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}
