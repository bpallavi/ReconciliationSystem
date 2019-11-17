package ReconciliationSystem.ReconciliationSystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//import com.esi.vdss.th.core.THConfig;

/**
 * 
 * @author ary
 *
 */
@Configuration
@RequestMapping("/RS/")
public class StaticResourceHandler  extends WebMvcConfigurerAdapter {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	 //String resPath = "file:" + THConfig.getWorkfolder()+ "//";
     //registry.addResourceHandler("/THM/File/**").addResourceLocations(resPath);
    
     
    }
}
