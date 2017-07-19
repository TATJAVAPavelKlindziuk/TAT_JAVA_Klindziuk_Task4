package com.klindziuk.parsing.stax;

import com.klindziuk.model.BookCommand;
import com.klindziuk.command.TagEnum;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for STAX parsing XML-file
 */
class CommandStaxHandler {
	private static final String CATEGORY = "category";
	 
	/**
	* process XML-file
	*
	* @param reader - XMLStreamReader reader
	* @return List contains information from XML - file
	* @throws XMLStreamException if problem with parsing
	*/
	static List<BookCommand> process(XMLStreamReader reader) throws XMLStreamException {
		List<BookCommand> bookCommandList = new ArrayList<>();
	    BookCommand bookCommand = null;
	    TagEnum tagEnum = null;
	    String category = null;
	    
	    while (reader.hasNext()) {
	      int type = reader.next();
	      switch (type) {
	        case XMLStreamConstants.START_ELEMENT:
	        	tagEnum = getName(reader);
	          switch (tagEnum) {
	            case SERVICE:
	              category = reader.getAttributeValue(null, CATEGORY);
	              bookCommand = new BookCommand();
	              bookCommand.setCategory(category);
	              break;
			default:
				break;
		        }
	          break;
	        case XMLStreamConstants.CHARACTERS:
	          String text = reader.getText().trim();
	          if (text.isEmpty()) {
	            break;
	          }
	          setParameter(bookCommand, tagEnum, text);
	          break;
	        case XMLStreamConstants.END_ELEMENT:
	        	tagEnum = getName(reader);
	       	addBookCommand(bookCommand, tagEnum, bookCommandList);
	       	break;
	      }
	     
	    }
	    return bookCommandList;
	  }

	 /**
	  * add command to List if tag's name - Command
	  *
	  * @param meal   
	  * @param tagName  
	  * @param mealList 
	  */
	  private static void addBookCommand(BookCommand bookCommand, TagEnum tagEnum, List<BookCommand> bookCommandList) {
	    switch (tagEnum) {
	      case COMMAND:
	     bookCommandList.add(bookCommand);
	     default:
	     break;
	    }
	  }

	 /**
	  * set meal's parameter
	  *
	  * @param bookCommand  - bookCommand
	  * @param tagEnum - parameter's name
	  * @param text    - value of parameter
	  */
	  private static void setParameter(BookCommand bookCommand, TagEnum tagEnum, String text) {
	    switch (tagEnum) {
	    case NAME:
			bookCommand.setName(text);
			break;
		case USERID:
			bookCommand.setUserId(text);
			break;
		case BOOKID:
			bookCommand.setBookId(text);
			break;
		case BOOKAUTHOR:
			bookCommand.setBookAuthor(text);
			break;
		case BOOKNAME:
			bookCommand.setBookName(text);
			break;
		case BOOKYEAR:
			bookCommand.setBookYear(text);
				break;
		default :
			break;
	    }
	  }

	 /**
	  * get tag's name from enum
	  *
	  * @param reader - XMLStreamReader reader
	  * @return tag's name
	  */
	  private static TagEnum getName(XMLStreamReader reader) {
	    TagEnum name = TagEnum.valueOf(reader.getLocalName().toUpperCase());
	    return name;
	  }
	}
