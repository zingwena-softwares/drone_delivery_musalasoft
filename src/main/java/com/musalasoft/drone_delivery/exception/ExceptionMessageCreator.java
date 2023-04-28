package com.musalasoft.drone_delivery.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
/**
 * @author Warren Zingwena
 */
@Component
@RequiredArgsConstructor
public class ExceptionMessageCreator {
	private final MessageSource messageSource;
	private final Locale locale;
	public String createMessage(String code) {
		return messageSource.getMessage(code, null, locale);
	}
}