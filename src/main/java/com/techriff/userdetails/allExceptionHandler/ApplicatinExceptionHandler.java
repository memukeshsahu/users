  package com.techriff.userdetails.allExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.techriff.userdetails.Exception.AgeLimitNotReachedException;
import com.techriff.userdetails.Exception.DuplicateResourceException;
import com.techriff.userdetails.Exception.InCorrectException;
import com.techriff.userdetails.Exception.PasswordNotMatchedException;
import com.techriff.userdetails.Exception.RoleCanNotBeDeletedException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.Exception.ResourceNotFoundException;

@RestControllerAdvice

public class ApplicatinExceptionHandler {
	
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String, String> errorMap =new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		
		return errorMap;
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleUserNotFound(ResourceNotFoundException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(AgeLimitNotReachedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleAgeNotReachedException(AgeLimitNotReachedException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(DuplicateResourceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleDuplicateMailxception(DuplicateResourceException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(RoleNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleRoleNotFound(RoleNotFoundException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(RoleCanNotBeDeletedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleRoleDelete(RoleCanNotBeDeletedException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(InCorrectException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<String, String> handleInvalidUser(InCorrectException ex)
	{
		Map<String, String> errorMap =new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	
	}
	@ExceptionHandler(PasswordNotMatchedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlePassword(PasswordNotMatchedException ex)
    {
        Map<String, String> errorMap =new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    
    }


}
