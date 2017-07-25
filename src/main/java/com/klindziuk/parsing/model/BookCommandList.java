package com.klindziuk.parsing.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BookCommandList {
	@XmlElement(name = "item")
	private List<BookCommand> bookCommands = null;

	public List<BookCommand> getBookCommands() {
		return bookCommands;
	}

	public void setBookCommands(List<BookCommand> bookCommands) {
		this.bookCommands = bookCommands;
	}
}
