package com.klindziuk.parsing.manage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.klindziuk.parsing.model.BookCommand;
import com.klindziuk.parsing.parser.dom.CommandDomParser;
import com.klindziuk.parsing.parser.sax.CommandSaxParser;
import com.klindziuk.parsing.parser.stax.CommandStaxParser;

/**
 * @author Pavel_Klindziuk
 * Manage xml parsers
 */
public final class ParserManager {
	private final static Logger logger = LogManager.getRootLogger();
	private final static String DOM_SUCCESSFUL_MESSAGE = "File parsed with DOM successfully.";
	private final static String DOM_EXCEPTION_MESSAGE = "Failed to parse file with DOM.";
	private final static String SAX_SUCCESSFUL_MESSAGE = "File parsed with SAX successfully.";
	private final static String SAX_EXCEPTION_MESSAGE = "Failed to parse file with SAX.";
	private final static String STAX_SUCCESSFUL_MESSAGE = "File parsed with STAX successfully.";
	private final static String STAX_EXCEPTION_MESSAGE = "Failed to parse file with STAX.";
	private final static String INVALID_PATH_EXCEPTION_MESSAGE = "System canot find file or path.";

	/**
	 * parse xml file with DOM
	 * @param filePath - file path to parse
	 * @return - list of commands
	 * @throws FileNotFoundException 
	 */
	public static List<BookCommand> parseWithDom(String filePath) throws IllegalArgumentException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		List<BookCommand> result = Collections.emptyList();
		try {
			CommandDomParser domParser = new CommandDomParser();
			domParser.parse(filePath);
			logger.info(DOM_SUCCESSFUL_MESSAGE);
			result = domParser.getCommandList();
		} catch (SAXException | IOException saxioex) {
			logger.error(DOM_EXCEPTION_MESSAGE, saxioex);
		}
		return result;
	}

	/**
	 * parse xml file with SAX
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<BookCommand> parseWithSax(String filePath) throws IllegalArgumentException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		List<BookCommand> result = Collections.emptyList();
		try {
			CommandSaxParser saxParser = new CommandSaxParser();
			saxParser.parse(filePath);
			logger.info(SAX_SUCCESSFUL_MESSAGE);
			result = saxParser.getBookCommand();
		} catch (SAXException | IOException saxioex) {
			logger.error(SAX_EXCEPTION_MESSAGE, saxioex);
		}
		return result;
	}

	/**
	 * parse xml file with STAX
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<BookCommand> parseWithStax(String filePath) throws IllegalArgumentException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		List<BookCommand> result = Collections.emptyList();
		try {
			CommandStaxParser staxParser = new CommandStaxParser();
			staxParser.parse(filePath);
			logger.info(STAX_SUCCESSFUL_MESSAGE);
			result = staxParser.getBookCommand();
		} catch (XMLStreamException | IOException xmlsioex) {
			logger.error(STAX_EXCEPTION_MESSAGE, xmlsioex);
		}
		return result;
	}
}
