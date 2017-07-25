package com.klindziuk.parsing.reflection;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.klindziuk.parsing.reflection.dom.ReflectionCommandDomParser;
import com.klindziuk.parsing.reflection.mock.Command;

public class ReflectionCommandName {
	private static final Logger logger = LogManager.getRootLogger();
	private static final String REFLECTION_FILE_PATH = "xml/reflection.xml";
	private static final String UNSUPPORTED_OPERATION_MESSAGE = " - this command unfortunately unsupported.";
	private static final String INITIALIZING_EXCEPTION_MESSAGE = "Cannot initialize map of commands. Invalid source path or file - ";
	private static Map<String, Command> commandMap;

	static {
		initializeMap(REFLECTION_FILE_PATH);
	}

	public static Command getCommandByName(String name) throws OperationNotSupportedException {
		if (!commandMap.containsKey(name.toUpperCase()) || name.isEmpty()) {
			throw new OperationNotSupportedException(name + UNSUPPORTED_OPERATION_MESSAGE);
		}
		return commandMap.get(name.toUpperCase());
	}

	//for testing purposes
	public static Set<String> getCommands() {
		return commandMap.keySet();
	}

	private static void initializeMap(String filePath) {
		try {
			ReflectionCommandDomParser parser = new ReflectionCommandDomParser();
			parser.parse(filePath);
			commandMap = parser.getCommandMap();
		} catch (FileNotFoundException e) {
			logger.error(INITIALIZING_EXCEPTION_MESSAGE + filePath);
			e.printStackTrace();
		}
	}
}
