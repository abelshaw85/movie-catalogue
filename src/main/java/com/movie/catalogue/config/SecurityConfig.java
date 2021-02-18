package com.movie.catalogue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.movie.catalogue.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserService userService;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.authenticationProvider(authenticationProvider()); // authenticationProvider() is our bean defined below.
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/home", "/resources/*")
			.permitAll()
		.antMatchers("/movie-catalogue/**").hasRole("USER")
			.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/auth")
			.permitAll()
			.and()
		.logout()
			.permitAll()
			.and()
		.exceptionHandling()
			.accessDeniedPage("/access-denied");
	}
	
	// Defines a bean that can be used to encode a plaintext password to BCrypt.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); // Service to set the User's details, in this case UserService.
		auth.setPasswordEncoder(passwordEncoder()); // Our encoder is the BCrypt encoder defined above. Passwords will be checked using this.
		return auth;
	}
}