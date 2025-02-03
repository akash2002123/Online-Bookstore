package com.example.demo.exception;

/**
 * Custom exception class to handle resource not found scenarios. 
 * Extends RuntimeException to allow unchecked exceptions.
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Constructs a new ResourceNotFoundException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}