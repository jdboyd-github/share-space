package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection connection = null;

	public static void getDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found.");
			e.printStackTrace();
			return;
		}

		connection = null;
		
		try {
			UtilProp.loadProperty();
			connection = DriverManager.getConnection(getURL(), getUserName(), getPassword());
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console.");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Connection successful!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	static String getURL() {
		String url = UtilProp.getProp("url");
		return url;
	}

	static String getUserName() {
		String usr = UtilProp.getProp("user");
		return usr;
	}

	static String getPassword() {
		String pwd = UtilProp.getProp("password");
		return pwd;
	}
}
