package com.klindziuk.parsing.parser.dom;

import com.klindziuk.parsing.model.BookCommand;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM parser XML-file
 */
@SuppressWarnings("restriction")
public class CommandDomParser {
	private static final String SERVICE = "service";
	private static final String CATEGORY = "category";
	private static final String COMMAND = "command";
	private static final String NAME = "name";
	private static final String USER_ID = "userId";
	private static final String BOOK_ID = "bookId";
	private static final String BOOK_NAME = "bookName";
	private static final String BOOK_AUTHOR = "bookAuthor";
	private static final String BOOK_YEAR = "bookYear";
		
	private List<BookCommand> commandList = new ArrayList<>();

	public List<BookCommand> getCommandList() {
		return commandList;
	}

	/**
	 * parse XML-file
	 * @param filePath 
	 *
	 * @throws SAXException if problem with parsing
	 * @throws IOException  if problem with file
	 */
	public void parse(String filePath) throws SAXException, IOException {
		Document document = getDocument(filePath);
		Element root = document.getDocumentElement();
		NodeList partNodes = root.getElementsByTagName(SERVICE);
		BookCommand command = null;
		for (int i = 0; i < partNodes.getLength(); i++) {
			command = new BookCommand();
			Element partElement = (Element) partNodes.item(i);
			command.setCategory(partElement.getAttribute(CATEGORY));
			setParameter(command, partElement);
		}
	}

	/**
	 * get DOM document
	 *
	 * @return document
	 * @throws SAXException if problem with parsing
	 * @throws IOException if problem with file
	 */
	private static Document getDocument(String filePath) throws SAXException, IOException {
		DOMParser parserDOM = new DOMParser();
		parserDOM.parse(filePath);
		Document document = parserDOM.getDocument();
		return document;
	}

	/**
	 * @param command
	 * @param partElement
	 */
	private void setParameter(BookCommand command, Element partElement) {
		Element bookElement = getChild(partElement, COMMAND);
		NodeList mealNode = partElement.getElementsByTagName(COMMAND);
		for (int j = 0; j < mealNode.getLength(); j++) {
			command.setName(getChild(bookElement, NAME).getTextContent());
			command.setCategory(partElement.getAttribute(CATEGORY));
			command.setBookId(getChild(bookElement, BOOK_ID).getTextContent());
			command.setUserId(getChild(bookElement, USER_ID).getTextContent());
			command.setBookName(getChild(bookElement, BOOK_NAME).getTextContent());
			command.setBookAuthor(getChild(bookElement, BOOK_AUTHOR).getTextContent());
			command.setBookYear(getChild(bookElement, BOOK_YEAR).getTextContent());
			commandList.add(command);
		}
	}
	
	/***
	 * get child element
	 *
	 * @param element - from which get a child
	 * @param childName - child's name
	 * @return child element
	 */
	private static Element getChild(Element element, String childName) {
		NodeList childList = element.getElementsByTagName(childName);
		Element child = (Element) childList.item(0);
		return child;
	}
}
