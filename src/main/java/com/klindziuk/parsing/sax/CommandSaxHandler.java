package com.klindziuk.parsing.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.klindziuk.model.BookCommand;
import com.klindziuk.command.TagEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for SAX parsing XML-file
 */
class CommandSaxHandler extends DefaultHandler {
	private static final String CATEGORY = "category";
	private List<BookCommand> bookCommandList = new ArrayList<BookCommand>();
	private BookCommand bookCommand;
	private String category;
	private TagEnum tagEnum;

	public List<BookCommand> getBookCommandList() {
		return bookCommandList;
	}

	@Override
	public void startElement(String uri, String localName, String queryName, Attributes attribute) throws SAXException {
		tagEnum = getTag(queryName);
		if (tagEnum.equals(TagEnum.SERVICE)) {
			category = attribute.getValue(CATEGORY);
		}
		if (tagEnum.equals(TagEnum.COMMAND)) {
			bookCommand = new BookCommand();
			bookCommand.setCategory(category);
		}
	}

	@Override
	public void characters(char[] buffer, int start, int length) {
		String tagBody = new String(buffer, start, length).trim();
		if (null != tagEnum) {
			switch (tagEnum) {
			case NAME:
				bookCommand.setName(tagBody);
				break;
			case USERID:
				bookCommand.setUserId(tagBody);
				break;
			case BOOKID:
				bookCommand.setBookId(tagBody);
				break;
			case BOOKAUTHOR:
				bookCommand.setBookAuthor(tagBody);
				break;
			case BOOKNAME:
				bookCommand.setBookName(tagBody);
				break;
			case BOOKYEAR:
				bookCommand.setBookYear(tagBody);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String queryName) throws SAXException {
		tagEnum = getTag(queryName);
		if (tagEnum.equals(TagEnum.COMMAND)) {
			bookCommandList.add(bookCommand);
		}
		tagEnum = null;
	}

	/**
	* get tag's name from TagEnum
	*
	* @param queryName
	* @return tag's name
	*/
	private TagEnum getTag(String queryName) {
		TagEnum tagEnum = TagEnum.valueOf(queryName.toUpperCase());
		return tagEnum;
	}
}
