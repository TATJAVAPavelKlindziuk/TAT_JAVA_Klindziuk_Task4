package com.klindziuk.parsing.marshalling;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.klindziuk.parsing.model.BookCommandList;

@XmlRegistry
public final class CommandJaxbUnMarshaller {
	private final static Logger logger = LogManager.getRootLogger();
	private static final String INVALID_PATH_EXCEPTION_MESSAGE = "System canot find file or path.";
	private static final String UNMARSHALLING_PREPARE_MESSAGE = "Starting unmarshalling from file - ";
	private static final String UNMARSHALLING_SUCCESS_MESSAGE = "Unmarshalling completed successfullyl from file - ";
	private static final String UNMARSHALLING_ERROR_MESSAGE = "Cannot perform unmarshalling to file -";
	
 private CommandJaxbUnMarshaller () {}
	
	public static BookCommandList unMarshal(String filePath) throws IllegalArgumentException {
		if (null == filePath || filePath.isEmpty()) {
			throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
		}
		logger.info(UNMARSHALLING_PREPARE_MESSAGE + filePath);
		BookCommandList list = null;
		try {
			File file = new File(filePath);
			Source source = new StreamSource(file);
			JAXBContext jaxbContext = JAXBContext.newInstance(BookCommandList.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<BookCommandList> root = jaxbUnmarshaller.unmarshal(source, BookCommandList.class);
			 list = root.getValue();
			 logger.info(UNMARSHALLING_SUCCESS_MESSAGE + filePath);
		} catch (JAXBException jxbex) {
			logger.error(UNMARSHALLING_ERROR_MESSAGE + filePath, jxbex);	
		}
		return list;
	}
}
