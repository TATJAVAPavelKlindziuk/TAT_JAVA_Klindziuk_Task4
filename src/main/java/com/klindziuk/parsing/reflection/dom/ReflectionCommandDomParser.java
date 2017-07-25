package com.klindziuk.parsing.reflection.dom;

import com.klindziuk.parsing.reflection.mock.Command;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * DOM parser XML-file
 */
@SuppressWarnings("restriction")
public class ReflectionCommandDomParser {
	private static final Logger logger = LogManager.getRootLogger();
	private static final String COMMAND = "command";
	private static final String PACKAGE = "com.klindziuk.parsing.reflection.mock.";
	private static final String COMMAND_MESSAGE = "Adding new class to map - ";
	private static final String INVALID_PATH_EXCEPTION_MESSAGE = "System cannot find file or path.";
	private static final String INSTANTIATION_EXCEPTION_MESSAGE = "Error during instantiation";
	private static final String SAX_EXCEPTION_MESSAGE = "Cannot parse file.Bad document format of file - ";
	private static final String CLASS_NOT_FOUND_EXCEPTION_MESSAGE = "Cannot find class to initialize on the classpath at runtime - ";
	private static final String IO_EXCEPTION_MESSAGE = "Error during reading file - ";
	private static final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE = "Cannot get access to class at runtime.";
	private Map<String, Command> commandMap;

	public Map<String, Command> getCommandMap() {
		return commandMap;
	}

	/**
	 * parse XML-file and fill map with new instances
	 * 
	 * @param filePath
	 * @throws FileNotFoundException 
	 *
	 * @throws SAXException if problem with parsing
	 * @throws IOException  if problem with file
	 * @throws ClassNotFoundException  if class cannot be found on the classpath at runtime
	 * @throws IllegalAccessException if problem with access
	 * @throws InstantiationException if problem with instantiation
	 */
	public void parse(String filePath) throws FileNotFoundException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		commandMap = new HashMap<>();
		String commandName = "";
		try {
			Document document = getDocument(filePath);
			Element root = document.getDocumentElement();
			NodeList partNodes = root.getElementsByTagName(COMMAND);
			for (int i = 0; i < partNodes.getLength(); i++) {
				Element partElement = (Element) partNodes.item(i);
				commandName = partElement.getTextContent();
				// create instance of class with reflection
				commandMap.put(commandName.toUpperCase(), (Command) Class.forName(PACKAGE + commandName).newInstance());
				logger.info(COMMAND_MESSAGE + PACKAGE + commandName);
			}
		} catch (InstantiationException insex) {
			logger.error(INSTANTIATION_EXCEPTION_MESSAGE, insex);
		} catch (IllegalAccessException iacex) {
			logger.error(ILLEGAL_ACCESS_EXCEPTION_MESSAGE, iacex);
		} catch (ClassNotFoundException cnfex) {
			logger.error(CLASS_NOT_FOUND_EXCEPTION_MESSAGE + PACKAGE + commandName, cnfex);
		} catch (SAXException saxex) {
			logger.error(SAX_EXCEPTION_MESSAGE + PACKAGE + filePath, saxex);
		} catch (IOException ioex) {
			logger.error(IO_EXCEPTION_MESSAGE + PACKAGE + filePath, ioex);
		}
	}

	/**
	 * get DOM document
	 *
	 * @return document
	 * @throws SAXException
	 *             if problem with parsing
	 * @throws IOException
	 *             if problem with file
	 */
	private static Document getDocument(String filePath) throws SAXException, IOException {
		DOMParser parserDOM = new DOMParser();
		parserDOM.parse(filePath);
		Document document = parserDOM.getDocument();
		return document;
	}
}
