package com.tipsontech.employeesapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");

		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,authorization,Authorization,x-auth-token,origin,content-type,accept,UserEmail,x-request-id");
		response.setHeader("Access-Control-Expose-Headers", "olympusError, errorCode");

		String requestHeader = request.getHeader("Authorization");

		log.info(" Header :  {}", requestHeader);

		String username = null;
		String token = null;
		validateHeaders(request, requestHeader, username, token);

		filterChain.doFilter(request, response);

	}

	private void validateHeaders(HttpServletRequest request, String requestHeader, String username, String token) {
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			try {
				username = this.jwtService.extractUsername(token);
			} catch (IllegalArgumentException e) {
				log.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				log.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				log.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			log.info("Invalid Header Value !! ");
		}

		validateUserDetailsAndToken(request, username, token);
	}

	private void validateUserDetailsAndToken(HttpServletRequest request, String username, String token) {
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user details from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtService.validateToken(token, userDetails);
			if (Boolean.TRUE.equals(validateToken)) {

				// set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				log.info("Validation fails !!");
			}

		}
	}

}
