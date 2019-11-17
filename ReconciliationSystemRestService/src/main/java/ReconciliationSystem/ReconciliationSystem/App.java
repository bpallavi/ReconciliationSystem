package ReconciliationSystem.ReconciliationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	Config.load();
    	SpringApplication.run(App.class, args);	
    	//System.out.println( "Hello World!" );
        
    }
}
