package com.kows.myproject.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.kows.myproject.filter.JWTTokenValidatorFilter;
import com.kows.myproject.filter.JwtTokenGeneratorFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfiguration {
 
    /* @Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	} */

	/* @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	  	http.authorizeHttpRequests((authorizeHttpRequests) ->
	  		 		authorizeHttpRequests
	  			 		.requestMatchers(&quot;/  &quot;).hasRole(&quot;USER&quot;)
	  			 		.requestMatchers(&quot;/admin/  &quot;).hasRole(&quot;ADMIN&quot;)
	  		 	);
	  		return http.build();
	} */

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        /* http
		.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
            .requestMatchers("/user", "/bank", "/accountDetails", "/loanDetails", "/cardDetails").authenticated()
            .requestMatchers("/contactDetails", "/notices", "/register").permitAll()
        )
        .formLogin(withDefaults())
        .httpBasic(withDefaults());
        return http.build(); */

		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                return config;
            }
                })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/cardDetails").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/notices","/contact","/register","/userLogout","/error").permitAll())
				.logout((logout) -> logout.logoutSuccessUrl("/userLogout"))
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

				/* http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
					// Trigger UnauthorizedException
					throw new UnauthorizedException("Session expired or unauthorized access");
				}); */

        return http.build();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
