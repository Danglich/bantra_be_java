package com.danglich.bantra.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.danglich.bantra.security.oauth2.CustomOAuth2UserService;
import com.danglich.bantra.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.danglich.bantra.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.danglich.bantra.security.oauth2.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final CustomOAuth2UserService customOAuth2UserService;
	
	 private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	 private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				configure -> configure.requestMatchers("/error").permitAll().requestMatchers("/api/auth/**", "/oauth2/**").permitAll()
						.requestMatchers("/api/product_medias/**").permitAll().requestMatchers("/api/product_medias/**")
						.permitAll().requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/product_categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/news_categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/news/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/files/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/contacts").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/addresses").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/news_comments/**").permitAll()
						.requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
						// .requestMatchers("/api/**").permitAll()
						.anyRequest().authenticated())
				.oauth2Login(oAuth -> oAuth.authorizationEndpoint(c -> c.baseUri("/oauth2/authorize")
						.authorizationRequestRepository(cookieAuthorizationRequestRepository())

				).redirectionEndpoint(r -> r.baseUri("/oauth2/callback/*"))
						.userInfoEndpoint(u -> u.userService(customOAuth2UserService))
						
						.successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Đặt cấu hình CORS sau các quy tắc
																					// authorizeHttpRequests
				.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*")); // Thêm "Authorization" vào danh sách allowedHeaders

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

}
