package com.musalasoft.drone_delivery.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.musalasoft.drone_delivery.service.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

	/*handler for any kind of a custom business exception */
	@ExceptionHandler(ClientException.class)
	public ResponseEntity<ExceptionResponseDto> handleClientException(ClientException e) {
		return ResponseEntity
		.status(HttpStatus.NOT_FOUND)
		.body(new ExceptionResponseDto(e.getMessage()));
	}

	/*handler for the exception thrown by the  failing @Size, @Pattern or @Min/@Max */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return ResponseEntity
		.status(HttpStatus. NOT_ACCEPTABLE)
		.body(new ExceptionResponseDto(e.getMessage()));
	}

	/*handler for the exception thrown by the wrong Enum value */
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ExceptionResponseDto> handleInvalidFormatException(InvalidFormatException e) {
		return ResponseEntity
		.status(HttpStatus. NOT_ACCEPTABLE)
		.body(new ExceptionResponseDto(e.getMessage()));
	}
}