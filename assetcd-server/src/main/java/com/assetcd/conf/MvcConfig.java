package com.assetcd.conf;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories({"com.assetcd"})
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	
	/*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/403").setViewName("403");
    }*/

	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS", "HEAD").allowedHeaders("*").allowCredentials(true);
    }


}

@Component
class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

	@Autowired
    private EntityManager entityManager;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    //config.exposeIdsFor(AttrTab.class, MappingCode.class, MappingNewCode.class);
	config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
	//config.setBasePath("/api");
	config.getCorsRegistry().addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS", "HEAD").allowedHeaders("*").allowCredentials(true);
  }
}

@Configuration
//@EnableScheduling
class ScheduleConfig {
	
}
