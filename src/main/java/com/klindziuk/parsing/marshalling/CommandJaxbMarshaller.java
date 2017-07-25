package com.klindziuk.parsing.marshalling;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.klindziuk.parsing.model.BookCommand;

public final class CommandJaxbMarshaller {
	private final static Logger logger = LogManager.getRootLogger();
	private final static String QNAME = "bookcommands";
	private static final String INVALID_PATH_EXCEPTION_MESSAGE = "System canot find file or path.";
	private static final String LIST_EXCEPTION_MESSAGE = "List of commands cannot be null or empty.";
	private static final String MARSHALLING_PREPARE_MESSAGE = "Starting marshalling to file - ";
	private static final String MARSHALLING_SUCCESS_MESSAGE = "Marshalling completed successfullyl to file - ";
	private static final String MARSHALLING_ERROR_MESSAGE = "Cannot perform marshalling to file -";
	private static final String IO_EXCEPTION_MESSAGE = "Error during reading file - ";

	private CommandJaxbMarshaller() {
	}

	public static void marshall(String filePath, List<BookCommand> commandList) throws IllegalArgumentException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		if (null == commandList || commandList.isEmpty()) {
			throw new IllegalArgumentException(LIST_EXCEPTION_MESSAGE);
		}
		try {
			logger.info(MARSHALLING_PREPARE_MESSAGE + filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(BookCommand[].class);
			JAXBElement<BookCommand[]> root = new JAXBElement<BookCommand[]>(new QName(QNAME), BookCommand[].class,
					commandList.toArray(new BookCommand[commandList.size()]));
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(root, writer);
			writeTofile(writer, filePath);
			logger.info(MARSHALLING_SUCCESS_MESSAGE + filePath);
		} catch (JAXBException jxbex) {
			logger.error(MARSHALLING_ERROR_MESSAGE + filePath, jxbex);
		} catch (IOException ioex) {
			logger.error(IO_EXCEPTION_MESSAGE + filePath, ioex);
		}
	}

	private static void writeTofile(StringWriter writer, String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.write(path, writer.toString().getBytes());
	}
}
