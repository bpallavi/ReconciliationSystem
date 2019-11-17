package ReconciliationSystem.ReconciliationSystem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import io.swagger.annotations.ApiOperation;
import service.ReconciliationService;



@RestController

@RequestMapping("/RS/")

public class Controller {
	
	private static String UPLOADED_FOLDER = "F://temp//";
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ApiOperation(value = "Generic File upload , typically icon files", notes = "")
	@ResponseBody
	public ResponseEntity<THFile> uploadFile( 
			@RequestParam("uploadfile") MultipartFile uploadfile,
			@RequestParam(value="path",required=false) String path
			) throws IOException {
		
		System.out.println("in controller..upload");
		String directory = "C:/resources" ;
		String filename = uploadfile.getOriginalFilename();
		String filepath = Paths.get(directory, filename).toString()  ;		
		File  parentFolder = new  File(directory   ) ;
		System.out.println(filepath);
	
		
		 if(parentFolder.canRead() == false) {
				parentFolder.mkdirs();
		 }

		

		try(FileOutputStream fstream = new FileOutputStream(new File(filepath)  ))
	     {
		
			BufferedOutputStream stream = new BufferedOutputStream(fstream);
	        stream.write(uploadfile.getBytes());
	        stream.close();
	        
	      }
	      catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>(null,null,HttpStatus.BAD_REQUEST);
	    }
	    
	    THFile  file  = new THFile();
	    file.setPath(filepath);
	    System.out.println("filepath is"+filepath);
	    file.setUrl("RS/File/resources" + "/" + filename);
	    
	    System.out.println("url is"+file.getUrl());
		HttpHeaders responseHeaders = new HttpHeaders();
	 	return  new ResponseEntity<>(file, responseHeaders, HttpStatus.OK);

	 } 
	
	
	
	@RequestMapping( value = "Results/", method=RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "get result strings ", notes = "Returns json ")

	public  ResponseEntity<HashMap<String,ArrayList<String>>>getPatterns( 
								@RequestParam String filepath1,
								@RequestParam String filepath2
								
					)
	 {
	
		HttpHeaders responseHeaders = new HttpHeaders();
		try  {
			HashMap<String,ArrayList<String>>created  = new HashMap<String,ArrayList<String>>();		
			created = Executor.execute(filepath1,filepath2);
			Executor.setResultInDB(created);
			//created.put("add", "value");
			System.out.println(created);
		 	return  new ResponseEntity<>(created, responseHeaders, HttpStatus.OK);
		 	
		} catch ( Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(null,responseHeaders,HttpStatus.BAD_REQUEST);
		}
		
		
	 }
	
	@RequestMapping( value = "setPatterns/", method=RequestMethod.POST,consumes = "application/json")
	@ApiOperation(value = "set result strings ", notes = "Returns json ")

	public  ResponseEntity<String>setPatterns( 
								@RequestBody String json													
					)
	 {
	
		HttpHeaders responseHeaders = new HttpHeaders();
		try  {
			HashMap<String,ArrayList<String>>created  = new HashMap<String,ArrayList<String>>();		
			created = ExecutionService.jsonToMap(json);
			Executor.setUserDefinedPatterns(created);
			//created.put("add", "value");
			System.out.println("printing results");
			System.out.println(created);
		 	return  new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
		 	
		} catch ( Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(null,responseHeaders,HttpStatus.BAD_REQUEST);
		}
		
		
	 }
	
	/*@RequestMapping( value = "Products/", method=RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "get all prodcuts accessible to given user ", notes = "Returns json of all products")

	public  ResponseEntity<List<Product>>   getProduct( 
								@PathVariable String user
					)
	 {
	
		HttpHeaders responseHeaders = new HttpHeaders();
		try  {
			List<Product>	products;
			products = THProductService.getProducts(user);			
		 	return  new ResponseEntity<>(products, responseHeaders, HttpStatus.OK);
		 	
		} catch ( THException e) {
			 return new ResponseEntity<>(null,responseHeaders,HttpStatus.BAD_REQUEST);
		}
		
		
	 }
*/

}
