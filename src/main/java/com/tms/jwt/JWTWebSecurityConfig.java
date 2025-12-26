package com.tms.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTWebSecurityConfig {

	@Autowired
	private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

	@Value("${jwt.get.token.uri}")
	private String authenticationPath;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

//	@Bean
//	public PasswordEncoder passwordEncoderBean() {
//		return new BCryptPasswordEncoder();
//	}
//	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.exceptionHandling(
				exceptionHandling->
		exceptionHandling.authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint)
		)
		.sessionManagement(sessionManagement->
		sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->auth
				.requestMatchers(HttpMethod.POST,authenticationPath).permitAll()
				.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated());		
		
		http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers(headers->headers.frameOptions(
				frameOptions->frameOptions.sameOrigin()
				).cacheControl(cache->cache.disable())
				); // disable caching
		return http.build();
	}

	/*@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web)->web.ignoring()
				.requestMatchers(HttpMethod.POST,authenticationPath)
				.requestMatchers(HttpMethod.OPTIONS,"/**");
				//.and()
				//.ignoring()
				//.requestMatchers(HttpMethod.GET,"/");
				//.and()
				//.ignoring()
				//.requestMatchers("/h2-console");
	}*/
}
