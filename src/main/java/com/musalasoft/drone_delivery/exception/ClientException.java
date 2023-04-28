package com.musalasoft.drone_delivery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author Warren Zingwena
 */
@Getter
@AllArgsConstructor
public class ClientException extends RuntimeException {
	private final String message;

	public static ClientException of(String message) {
		return new ClientException(message);
	}
}