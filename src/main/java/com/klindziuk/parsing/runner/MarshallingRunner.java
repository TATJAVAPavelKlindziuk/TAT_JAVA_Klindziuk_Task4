package com.klindziuk.parsing.runner;

import java.util.List;

import com.klindziuk.parsing.manage.ParserManager;
import com.klindziuk.parsing.marshalling.CommandJaxbMarshaller;
import com.klindziuk.parsing.marshalling.CommandJaxbUnMarshaller;
import com.klindziuk.parsing.model.BookCommand;

public class MarshallingRunner {
	private static final String SOURCE_FILE_PATH = "xml/bookcommands.xml";
	private static final String MARSHALLING_FILE_PATH = "xml/marshall.xml";

	public static void main(String[] args) throws IllegalArgumentException {
		List<BookCommand> commandList = ParserManager.parseWithDom(SOURCE_FILE_PATH);
		CommandJaxbMarshaller.marshall(MARSHALLING_FILE_PATH, commandList);
		CommandJaxbUnMarshaller.unMarshal(MARSHALLING_FILE_PATH).toString();
	}
}
