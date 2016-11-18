package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.service.UserDetailsService;

@EnableWebSecurity
@Configuration
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().exceptionHandling().and().anonymous().and().servletApi().and().headers()
				.cacheControl().and().authorizeRequests()

				/*
				 * ANONYMOUS permission
				 */
				.antMatchers("/").permitAll()
				// allow anonymous to register
				.antMatchers(HttpMethod.POST, "/api/register").permitAll()
				// allow anonymous POSTs to login
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.antMatchers(HttpMethod.HEAD, "/api/send-email").permitAll()
				.antMatchers(HttpMethod.GET, "/api/stats").permitAll()
				
				.antMatchers(HttpMethod.HEAD, "/api/login-guard/check").hasAnyRole("ADMIN","USER")
				
				/*
				 * USER permission
				 */
				
			
				.antMatchers(HttpMethod.GET, "/api/blob/*").hasRole("USER")
				// allow only authenticated user to add, get
				.antMatchers(HttpMethod.POST, "/api/account/*/photoAlbum").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/account/*/clientAlbums").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/my-album/download/original=*").hasRole("USER")
				// photo gfalery
				.antMatchers(HttpMethod.GET, "/api/my-album/*/client-photos/page=*").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/album/*/details").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/photo-number/album=*").hasRole("USER")
				// add new photo
				.antMatchers(HttpMethod.POST, "/api/my-album/*/new-photo").hasRole("USER")
				.antMatchers(HttpMethod.DELETE, "/api/my-album/delete-photo/*").hasRole("USER")
				.antMatchers(HttpMethod.DELETE, "/api/album-collection/delete-album/*").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/search-photo/category=*/date=*/search=*").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/get-category").hasRole("USER")
				
//profile change
				
				.antMatchers(HttpMethod.PUT,"/api/account/change-personal-info").hasAnyRole("ADMIN","USER")
				.antMatchers(HttpMethod.PUT,"/api/account/change-password").hasAnyRole("ADMIN","USER")
				// all other request need to be authenticated
				.anyRequest().hasRole("USER").and()

				// custom JSON based authentication by POST of
				// {"username":"<name>","password":"<password>"} which sets the
				// token header upon authentication
				.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsService,
						authenticationManager()), UsernamePasswordAuthenticationFilter.class)

				// custom Token based authentication based on the header
				// previously given to the client
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}
}
