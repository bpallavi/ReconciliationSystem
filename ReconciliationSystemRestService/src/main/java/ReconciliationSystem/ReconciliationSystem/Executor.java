package ReconciliationSystem.ReconciliationSystem;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;



import org.json.JSONObject;

import JythonExecutor.JythonEngine;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;



public class Executor {
	
	private static String workingDirectory;
	static ExecutionService e = new ExecutionService();
	
	

	@SuppressWarnings("resource")
	static HashMap<String,ArrayList<String>> execute(String filepath1, String filepath2) throws IOException, JSONException 
	{
		workingDirectory = System.getenv("WORK_FOLDER");
		System.out.println(filepath1);
		HashMap<String, ArrayList<String>>res = new HashMap<String,ArrayList<String>>();
		res = getUserDefined();
		 JSONObject json = new JSONObject(res);
		 System.out.println("----jSON------");
		 System.out.println(json);
		 
		 PrintWriter out1 = null;
		 try {
		     FileWriter writer = new FileWriter("C:\\ReconciliationSystem\\user_defined.json");
	            BufferedWriter bwr = new BufferedWriter(writer);
	            bwr.write(json.toString());
	            bwr.close();
		 } catch (Exception ex) {
		     System.out.println("error: " + ex.toString());
		 }
		 
		    
		e.setFile1("C:/resources/"+filepath1);
		e.setFile2("C:/resources/"+filepath2);
		//JythonEngine engine = new JythonEngine(workingDirectory,e);		
		//String path = workingDirectory+"script.py";
		//engine.scriptInitialise("script.py");
		System.out.println(e.getOutput());
		String s = null;
		String err = null;
		
		String[] params = new String [5];
		params[0] = "python";
		params[1] = "C:\\ReconciliationSystem\\script.py";
		params[2] = "C:\\ReconciliationSystem\\melbourne.csv";
		params[3] = "C:\\ReconciliationSystem\\new_york.csv";
		params[4] =  "C:\\ReconciliationSystem\\user_defined.json";;

		 
		Process p = Runtime.getRuntime().exec(params);
		 BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
            	System.out.println("-------------");
                System.out.println(s);
                e.setOutput(s);
            
                //System.out.println(res.get("reviews_per_month"));
                
                
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((err = stdError.readLine()) != null) {
                System.out.println(err);
            }
            
          
            res = ExecutionService.jsonToMap(e.getOutput());
           
            //System.exit(0);
            System.out.println("Printing output...."+e.getOutput());
           
		return res;
	}
	
	/*public static void main(String args[]) throws IOException
	{
		
		Executor thexecute = new Executor();
		HashMap<String,ArrayList<String>>result = new HashMap<String,ArrayList<String>>();
		System.out.println("in executor..");
		try {
			result = execute(workingDirectory, workingDirectory);
			//System.out.println(e.getOutput());
			// System.out.println("................."+result.get("reviews_per_month"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	@SuppressWarnings("unchecked")
	public static void setResultInDB(HashMap<String, ArrayList<String>> created) 
	{
	
		OrientGraph txGraph = Config.getFactory().getTx();
		HashMap<String,ArrayList<String>>map = new  HashMap<>();
		String type = "dataset";
		OrientVertex res = getResultFromName(txGraph,type);
		if(res!=null)
		{
			map = res.getProperty("OUTPUT");
		    for (Map.Entry mapElement : created.entrySet()) 
		    { 
	            String key = (String)mapElement.getKey(); 
	           
	           	map.put(key,(ArrayList<String>) mapElement.getValue());	
	        	
	        }
		
			//res.setProperty(res.getProperty("OUTPUT"), map);
			res.setProperty("OUTPUT", map);
		}
		else
		{
			System.out.println("create new vertex");
			String classtype = "class:"+Config.RESULT;
			OrientVertex resultV = txGraph.addVertex(classtype);
			resultV.setProperty(Config.OUTPUT, created);
		}
		
		txGraph.commit();
		txGraph.shutdown();
	}
	
	
	public static OrientVertex getResultFromName(OrientGraph txGraph, String type)
	{
		String q = null;
		if(type.equals("dataset"))
		{
			 q = "select * from RESULT ";
		}
		else
		{
			q = "select * from USERPATTERNS " ;
		}
		
		
		
		@SuppressWarnings("unchecked")
		Iterable<OrientVertex> it = (Iterable<OrientVertex>) txGraph.command(new OCommandSQL(q.toString())).execute(null);
		
		for (OrientVertex pattern: it){
			
			return pattern;
			
		}
		
		return null ;
	}

	public static void setUserDefinedPatterns(HashMap<String, ArrayList<String>> created) 
	{
		
		OrientGraph txGraph = Config.getFactory().getTx();
		HashMap<String,ArrayList<String>>map = new  HashMap<>();
		String type = "user";
		OrientVertex res = getResultFromName(txGraph,type);
		if(res!=null)
		{
			map = res.getProperty("USEROUTPUT");
		    for (Map.Entry mapElement : created.entrySet()) 
		    { 
	            String key = (String)mapElement.getKey(); 
	            if(map!=null && !map.isEmpty() && map.containsKey(key))
	            {
	            	ArrayList<String>arr = new ArrayList<String>();
	            	arr = map.get(key);
	            	ArrayList<String>reverse = new ArrayList<String>();
	            	reverse =(ArrayList) mapElement.getValue();
	            	//arr.add((ArrayList<String>) mapElement.getValue());
	            	arr.add(reverse.get(0));
	            	map.put(key,arr);	
	            }
	            else
	            {
	            	ArrayList<String>arr = new ArrayList<String>();
	            	arr = (ArrayList<String>) mapElement.getValue();
	            	map.put(key,arr);	
	            	System.out.println("key is"+key);
	            	ArrayList<String>reverse = new ArrayList<String>();
	            	reverse.add(key);
	            	map.put(arr.get(0),reverse);
	            }
	        	//map.put(key,(ArrayList<String>) mapElement.getValue());
	        }
		
			res.setProperty("OUTPUT", map);
		}
		else
		{
			System.out.println("create new vertex");
			String classtype = "class:"+Config.USERPATTERNS;
			OrientVertex resultV = txGraph.addVertex(classtype);
			//map = res.getProperty("USEROUTPUT");
			 for (Map.Entry mapElement : created.entrySet()) 
			    { 
		            String key = (String)mapElement.getKey(); 
		            if(map!=null && !map.isEmpty() && map.containsKey(key))
		            {
		            	ArrayList<String>arr = new ArrayList<String>();
		            	arr = map.get(key);
		            	ArrayList<String>reverse = new ArrayList<String>();
		            	reverse =(ArrayList) mapElement.getValue();
		            	//arr.addAll((ArrayList<String>) mapElement.getValue());
		            	arr.add(reverse.get(0));
		            }
		            else
		            {
		            	ArrayList<String>arr = new ArrayList<String>();
		            	System.out.println(key+"----------");
		            	arr = created.get(key);
		            	System.out.println(arr.get(0)+"........");
		            	map.put(key,arr);	
		            	ArrayList<String>reverse = new ArrayList<String>();
		            	reverse.add(key);
		            	map.put(arr.get(0),reverse);
		            }
		        	//map.put(key,(ArrayList<String>) mapElement.getValue());
		        }
		System.out.println(map.toString());
			resultV.setProperty(Config.USEROUTPUT, map);
			
			
		}
		
		txGraph.commit();
		txGraph.shutdown();
	}
	
	public static HashMap<String,ArrayList<String>> getUserDefined()
	{
		//Config.load();
		HashMap<String,ArrayList<String>>patterns = new HashMap<String,ArrayList<String>>();
		OrientGraph txGraph = Config.getFactory().getTx();
		OrientVertex res = getResultFromName(txGraph,"user");
		if(res!=null)
		{
			patterns = res.getProperty("USEROUTPUT");
		}
		System.out.println("printing patterns");
		System.out.println(patterns);
		return patterns;
	}

}
