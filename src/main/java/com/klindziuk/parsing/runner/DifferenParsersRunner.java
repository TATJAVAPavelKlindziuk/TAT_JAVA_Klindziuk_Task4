package com.klindziuk.parsing.runner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.klindziuk.parsing.manage.ParserManager;

public class DifferenParsersRunner {
	private final static Logger logger = LogManager.getRootLogger();
	private static final String XMLFILEPATH = "xml/bookcommands.xml";

	public static void main(String[] args) throws IllegalArgumentException {
		logger.info(ParserManager.parseWithDom(XMLFILEPATH).toString());
		logger.info(ParserManager.parseWithSax(XMLFILEPATH).toString());
		logger.info(ParserManager.parseWithStax(XMLFILEPATH).toString());
	}
}
