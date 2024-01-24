package com.assetcd.conf;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.assetcd.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	/*@RequestMapping({ "/user" })
	public Principal user1(Principal principal) {
		return principal;
	}*/
	
	//@CrossOrigin
	@RequestMapping({ "/user2" })
	public Map<String, Object> user2(Principal user) { 
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (user != null) {
			CustomUserDetailsService.UserRepositoryUserDetails ud = (CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)user).getPrincipal();
			String name = ud.getUsername();
			map.put("name", name);
			map.put("firstName", ud.getFirstName());
			map.put("lastName", ud.getLastName());
			//map.put("name", user.getName());
			map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
					.getAuthorities()));
		}
		return map;
	}
	
	@RequestMapping({ "/loginfailure" })
	public Map<String, Object> loginfailure(Principal user) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("error", "Incorrect credentials");
		return map;
		//return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping({ "/sessionexpired" })
	public ResponseEntity sessionExpire(Principal user) {
		//Map<String, Object> map = new LinkedHashMap<String, Object>();
		//map.put("error", "Session Expired");
		//return map; 
		return new ResponseEntity<>("Session Expired", HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * For basic authentication
	 * @param user
	 * @return
	 */
	//@CrossOrigin
	/*@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/login1", method = RequestMethod.POST)
	public Map<String, Object> login1(Principal user) {
		return this.user2(user);
	}*/
	
	/*@CrossOrigin
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public Map<String, Object> login2(Principal user) {
		return this.user2(user);
	}*/
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.formLogin().failureForwardUrl("/loginfailure")//.successForwardUrl("/user2")
							.defaultSuccessUrl("/user2")
		//.httpBasic()
		//.and().exceptionHandling().accessDeniedPage("/403")
				.and().logout().invalidateHttpSession(false).logoutSuccessUrl("/user2").permitAll()
				.and().authorizeRequests()
					.antMatchers("/login**", "/user*", "/sessionexpired").permitAll()
					.antMatchers("/", "/**.js", "/**.css", "/**.html", "/**.ico", "/assets/**").permitAll()
					.antMatchers(HttpMethod.HEAD, "/**").permitAll()
					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.antMatchers(HttpMethod.GET, "/company/**").permitAll()
					.antMatchers(HttpMethod.GET, "/ruser/**").permitAll()
					.antMatchers(HttpMethod.PATCH, "/ruser/**").permitAll()
					.antMatchers(HttpMethod.POST, "/u/changepwd/**").permitAll()
					.antMatchers(HttpMethod.GET, "/project/search/**").hasAnyAuthority("AssetIssuer", "SystemUser", "HOD", "Administrator")
					.antMatchers(HttpMethod.GET, "/g*/**").hasAnyAuthority("AssetIssuer", "SystemUser", "Administrator")
					.antMatchers(HttpMethod.GET, "/report/**").hasAnyAuthority("HOD", "Administrator")
					.antMatchers(HttpMethod.POST, "/report/**").hasAnyAuthority("HOD", "Administrator")
					.antMatchers(HttpMethod.GET, "/tx/**", "/t*/search/**", "/mapping*/search/**", "/acm*/search/**").hasAnyAuthority("AssetIssuer", "Administrator")
					//.antMatchers(HttpMethod.GET, "/mappingcode/search/**", "/mappingnewcode/search/**", "/project/search/**", "/mappingrejcode/search/**").hasAnyAuthority("Employee", "UserMat", "UserMat2", "SystemUser", "Administrator")
					.antMatchers(HttpMethod.POST, "/tx/umrc").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.POST, "/tx/rmrc").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.POST, "/tx/**", "/acm*/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.PUT, "/tx/**", "/acm*/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.PATCH, "/tx/**", "/acm*/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.DELETE, "/tx/**", "/acm*/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.GET, "/masdata/**", "/t*/**", "/project/**").hasAnyAuthority("SystemUser", "Administrator")
					.antMatchers(HttpMethod.POST, "/masdata/**", "/t*/**", "/g*/**", "/project/**").hasAnyAuthority("SystemUser", "Administrator")
					.antMatchers(HttpMethod.PUT, "/masdata/**", "/t*/**", "/g*/**", "/project/**").hasAnyAuthority("SystemUser", "Administrator")
					.antMatchers(HttpMethod.PATCH, "/masdata/**", "/t*/**", "/g*/**", "/project/**").hasAnyAuthority("SystemUser", "Administrator")
					.antMatchers(HttpMethod.DELETE, "/masdata/**", "/t*/**", "/g*/**", "/project/**").hasAnyAuthority("SystemUser", "Administrator")
					.antMatchers(HttpMethod.GET, "/sv/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.POST, "/sv/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.PUT, "/sv/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.PATCH, "/sv/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.DELETE, "/sv/**").hasAnyAuthority("AssetIssuer", "Administrator")
					.antMatchers(HttpMethod.GET, "/**").hasAuthority("Administrator")
					.antMatchers(HttpMethod.POST, "/**").hasAuthority("Administrator")
					.antMatchers(HttpMethod.PUT, "/**").hasAuthority("Administrator")
					.antMatchers(HttpMethod.PATCH, "/**").hasAuthority("Administrator")
					.antMatchers(HttpMethod.DELETE, "/**").hasAuthority("Administrator")
					.anyRequest().authenticated()
				.and().cors()
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());//.disable();//
		// @formatter:on
		
		// @formatter:off
		//handle timeout
		http.sessionManagement()
				//.maximumSessions(1).expiredUrl("/sessionexpired")
				//.and()
				.invalidSessionUrl("/sessionexpired");
				
		
		// @formatter:on
	}


	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());;
	}
	
	@Bean
	public  PasswordEncoder passwordEncoder() {
	    return NoOpPasswordEncoder.getInstance();
	}*/
	
	/*@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}*/
	
	/*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }*/
	
	/*@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8090", "http://dc.com.s3-website-us-east-1.amazonaws.com"));
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
		//configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "X-XSRF-TOKEN"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/

	
	/*@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }*/



}
