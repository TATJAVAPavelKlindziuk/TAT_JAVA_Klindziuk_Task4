package com.klindziuk.parsing.sax;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.klindziuk.model.BookCommand;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CommandSaxParser {
	private final static String INVALID_PATH_EXCEPTION_MESSAGE = "System canot find file or path.";
	private List<BookCommand> commandList;

	/**
	 * parse XML file add information to the List
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse(String filePath) throws SAXException, IOException {
		if (null == filePath || filePath.isEmpty()) {
			throw new FileNotFoundException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		XMLReader reader = XMLReaderFactory.createXMLReader();
		CommandSaxHandler handler = new CommandSaxHandler();
		reader.setContentHandler(handler);
		reader.parse(new InputSource(filePath));
		commandList = handler.getBookCommandList();
	}

	public List<BookCommand> getBookCommand() {
		return commandList;
	}
}
