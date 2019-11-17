package ReconciliationSystem.ReconciliationSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ExecutionService {

	private String output;
	private HashMap<String,ArrayList<String>>result = new HashMap<String,ArrayList<String>>();
	

	String file1;
	
	String file2;

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	public HashMap<String, ArrayList<String>> getResult() {
		return result;
	}

	public void setResult(HashMap<String, ArrayList<String>> result) {
		this.result = result;
	}

	

	public static  HashMap<String, ArrayList<String>> jsonToMap(String t) throws JSONException {
		
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
      
        System.out.println("----------------t--------------"+t);
        JSONObject jObject = new JSONObject(t);
        
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
        	ArrayList <String>arr = new ArrayList<String>();
        	//System.out.println(keys.next());
            String key = (String)keys.next();
            JSONArray value = jObject.getJSONArray(key); 
           
            
           for(int i =0;i<value.length();i++)
           {
        	   System.out.println(value);
        	   arr.add(value.getString(i));
           }
           System.out.println(arr);
            map.put(key, arr);
            //System.out.println("map : "+key);
        }

        
     
        System.out.println(map.get("reviews_per_month"));
        return map;
    }
	
	

	 
}
