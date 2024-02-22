package com.danglich.bantra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.danglich.bantra.service.JwtService;
import com.danglich.bantra.service.impl.AuthServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	private AuthServiceImpl authService;
	
	@Autowired
    public void setAuthService(AuthServiceImpl authService) {
        this.authService = authService;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().contains("api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String authPath = request.getHeader("Authorization");
		final String token;
		final String userEmail;
		
		if(authPath == null || !authPath.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		token = authPath.substring(7);
		userEmail = jwtService.extractUsername(token);
		
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = authService.loadUserByUsername(userEmail);
			
			if(jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail,null, userDetails.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
