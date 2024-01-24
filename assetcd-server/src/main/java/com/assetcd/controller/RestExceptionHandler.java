package com.assetcd.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assetcd.exception.AlreadyProcessedException;
import com.assetcd.exception.CodeAlreadyExistsException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @ExceptionHandler(CodeAlreadyExistsException.class)
	   protected ResponseEntity<Map<String, Object>> handleResourceAccess(
			   CodeAlreadyExistsException ex) {
		 
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("error", "The code already exists. (" + ex.getMessage() + ")");
			return new ResponseEntity<>(map, HttpStatus.OK);
	   }
	 
	 @ExceptionHandler(AlreadyProcessedException.class)
	   protected ResponseEntity<Map<String, Object>> handleResourceAccess(
			   AlreadyProcessedException ex) {
		 
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("error", "Already processed. (" + ex.getMessage() + ")");
			return new ResponseEntity<>(map, HttpStatus.OK);
	   }
	 
	 /*@ExceptionHandler(DataIntegrityViolationException.class)
	   protected ResponseEntity<Map<String, Object>> handleResourceAccess(
			   DataIntegrityViolationException ex) {
		 
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("error", ex.getCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.OK);
	   }*/
	 
	 /*@ExceptionHandler(EmptyResultDataAccessException.class)
	   protected ResponseEntity<Object> handleEmptyResultDataAccess(
			   EmptyResultDataAccessException ex) {
		 
		 return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
	   }*/

}
