package com.klindziuk.parsing.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.klindziuk.model.BookCommand;

public class CommandStaxParser {
	private final static String INVALID_PATH_EXCEPTION_MESSAGE = "System canot find file or path.";
	private List<BookCommand> bookCommands;

	/**
	 * parse XML file add information to the List
	 * @throws XMLStreamException if problem with parsing
	 * @throws FileNotFoundException if file not found
	 */
	public void parse(String filePath) throws FileNotFoundException, XMLStreamException {
		if(null == filePath || filePath.isEmpty()){
			throw new FileNotFoundException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream input = new FileInputStream(filePath);
		XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
		bookCommands = CommandStaxHandler.process(reader);
	}

	public List<BookCommand> getBookCommand() {
		return bookCommands;
	}
}
