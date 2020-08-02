package com.mc.code.challenge.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Log LOGGER = LogFactory.getLog(RestResponseEntityExceptionHandler.class);

	public RestResponseEntityExceptionHandler() {
		super();
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final String bodyOfResponse = "Http MediaType Not Supported";
		AppError appError = null;
		if (request == null) {
			appError = populateFieldErrorResource(status.toString(), bodyOfResponse, "", ex.getMessage());
		} else {
			appError = populateFieldErrorResource(status.toString(), bodyOfResponse, getUri(request), ex.getMessage());
		}

		LOGGER.error("message", ex);

		return handleExceptionInternal(ex, appError, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final String bodyOfResponse = "Http MediaType Not Acceptable";
		AppError appError = null;
		if (request == null) {
			appError = populateFieldErrorResource(status.toString(), bodyOfResponse, "", ex.getMessage());
		} else {
			appError = populateFieldErrorResource(status.toString(), bodyOfResponse, getUri(request), ex.getMessage());
		}

		LOGGER.error("message", ex);

		return handleExceptionInternal(ex, appError, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		final String bodyOfResponse = "This Request Will Not be Handled";
		AppError appError = null;
		if (request == null) {
			appError = populateFieldErrorResource(HttpStatus.NOT_FOUND.toString(), bodyOfResponse, "", ex.getMessage());
		} else {
			appError = populateFieldErrorResource(HttpStatus.NOT_FOUND.toString(), bodyOfResponse, getUri(request),
					ex.getMessage());
		}

		LOGGER.error("message", ex);

		return handleExceptionInternal(ex, appError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	private AppError populateFieldErrorResource(String httpStatusString, String bodyResponse, String requestString,
			String errorMessage) {
		AppError appError = new AppError();
		appError.setStatus(httpStatusString);
		appError.setMessage(bodyResponse);
		if (requestString != null) {
			appError.setResource(requestString);
		}
		return appError;
	}

	private String getUri(WebRequest request) {
		return request.toString().substring(0, request.toString().indexOf(";"));
	}

}