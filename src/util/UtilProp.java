package util;

import java.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UtilProp {
	static final boolean _W = System.getProperty("os.name").toLowerCase().contains("windows");
	static String _PROP_FILENAME_WIN_LOCAL = "C:\\Users\\byZuR\\Desktop\\Software Engineering\\workspace4830\\Individual-Tech-Exercise\\share-space\\WebContent\\config.properties";
	static String _PROP_FILENAME_OSX_LOCAL = "/YOUR_PATH/webproject/WebContent/config.properties";
	// * Remote server path
	static String _PROP_FILENAME_REMOTE = "/var/lib/tomcat7/webapps/share-space/config.properties";
	static Properties prop = new Properties();

	public static void loadProperty() throws Exception {
		FileInputStream inputStream = null;
		if (_W) {
			if (new File(_PROP_FILENAME_WIN_LOCAL).exists()) {
				inputStream = new FileInputStream(_PROP_FILENAME_WIN_LOCAL);
			}
		} else {
			if (new File(_PROP_FILENAME_OSX_LOCAL).exists()) {
				inputStream = new FileInputStream(_PROP_FILENAME_OSX_LOCAL);
			}
		}
		if (new File(_PROP_FILENAME_REMOTE).exists()) {
			inputStream = new FileInputStream(_PROP_FILENAME_REMOTE);
		}
		if (inputStream == null) {
			throw new FileNotFoundException();
		}
		prop.load(inputStream);
	}

	public static String getProp(String key) {
		return prop.getProperty(key).trim();
	}
}
