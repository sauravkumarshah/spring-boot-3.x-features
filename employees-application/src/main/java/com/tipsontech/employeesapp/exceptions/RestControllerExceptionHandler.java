package com.tipsontech.employeesapp.exceptions;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestControllerExceptionHandler {

//	@ExceptionHandler(value = NoSuchElementException.class)
//
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ErrorResponse noSuchElement(NoSuchElementException e) {
//		return ErrorResponse.builder().message(e.getMessage()).errorCode(404).status(HttpStatus.NOT_FOUND).build();
//	}

	@ExceptionHandler(value = NoSuchElementException.class)
	public ProblemDetail onNoSuchElement(NoSuchElementException e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.employees.com/errors/not-found"));
		problemDetail.setTitle("Element Not Found");
		problemDetail.setProperty("errorCategory", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ProblemDetail onAccessDenied(AccessDeniedException e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.employees.com/errors/access-denied"));
		problemDetail.setTitle("You are not authorized to access this.");
		problemDetail.setProperty("errorCategory", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ProblemDetail onBadCredentialsException(BadCredentialsException e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.employees.com/errors/unauthorize"));
		problemDetail.setTitle("Invalid User Details");
		problemDetail.setProperty("errorCategory", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@ExceptionHandler(value = Exception.class)
	public ProblemDetail onAnyException(Exception e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
				e.getMessage());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.employees.com/errors/internal-server-error"));
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setProperty("errorCategory", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}
}