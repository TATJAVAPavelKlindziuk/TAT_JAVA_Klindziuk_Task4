package com.klindziuk.parsing.reflection.mock;

import org.apache.log4j.Logger;

/**
 * Mock for class MarkReturn
 * @author Pavel_Klindziuk
 *
 */
public class MarkReturn implements Command {
	private static final Logger logger = Logger.getLogger(MarkReturn.class);
	private static final String MOCK_MESSAGE = "Performing MOCK command for - ";

	@Override
	public String execute(String request) {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		logger.info(MOCK_MESSAGE + this.getClass().getSimpleName());
		String response = MOCK_MESSAGE;
		return response;
	}
}
