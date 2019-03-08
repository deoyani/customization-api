package gov.nist.oar.custom.updateapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class UpdateapiApplication {

    public static void main(String[] args) {
	System.out.println("MAIN CLASS *******************");
	SpringApplication.run(UpdateapiApplication.class, args);
    }

//    /**
//     * Add CORS
//     * 
//     * @return
//     */
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//	return new WebMvcConfigurerAdapter() {
//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**");
//	    }
//	};
//    }

}
