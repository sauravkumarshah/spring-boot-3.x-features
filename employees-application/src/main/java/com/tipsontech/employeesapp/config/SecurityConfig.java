package com.tipsontech.employeesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.tipsontech.employeesapp.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails user = User.builder().username("user@gmail.com").password(passwordEncoder().encode("userpassword"))
//				.roles("USER").build();
//		UserDetails admin = User.builder().username("admin@gmail.com")
//				.password(passwordEncoder().encode("adminpassword")).roles("ADMIN").build();
//		UserDetails adminuser = User.builder().username("adminuser@gmail.com")
//				.password(passwordEncoder().encode("adminuserpassword")).roles("ADMIN", "USER").build();
//
//		return new InMemoryUserDetailsManager(user, admin, adminuser);

		return new CustomUserDetailsService();
	}

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//
//		return httpSecurity.csrf(CsrfConfigurer::disable)
//				.authorizeHttpRequests(
//						authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/api/v1/user").permitAll())
//				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//						.requestMatchers("/api/v1/employees", "/api/v1/employees/**").authenticated())
//				.formLogin(Customizer.withDefaults()).build();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
