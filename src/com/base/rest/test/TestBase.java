package base.rest.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public Properties prop;

	public TestBase() {
		prop = new Properties();
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\com\\base\\httpclient\\config\\config.properties");
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
