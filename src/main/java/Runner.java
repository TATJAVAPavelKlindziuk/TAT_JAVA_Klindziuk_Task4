import java.io.FileNotFoundException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.klindziuk.manage.ParserManager;

public class Runner {
	private final static Logger logger = LogManager.getRootLogger();
	private static final String XMLFILEPATH = "book.xml";

	public static void main(String[] args) throws FileNotFoundException {
		logger.info(ParserManager.parseWithDom(XMLFILEPATH).toString());
		logger.info(ParserManager.parseWithSax(XMLFILEPATH).toString());
		logger.info(ParserManager.parseWithStax(XMLFILEPATH).toString());
	}
}
