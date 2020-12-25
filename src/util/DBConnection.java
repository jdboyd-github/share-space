package util;

// Import Statements
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	// Global Variables
	public static Connection connection = null;

	// Method used to make database connection
	public static void getDBConnection() {
		
		// Checking for JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		// Catch block to catch error if driver not found
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found.");
			e.printStackTrace();
			
			return;
		}

		// Initializing connection
		connection = null;
		
		// Trying to login to MySQL database
		try {
			UtilProp.loadProperty();
			connection = DriverManager.getConnection(getURL(), getUserName(), getPassword());
			
		// Throws error if invalid login information, or connection fails
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console.");
			e.printStackTrace();
			
			return;
		}

		// Printing status of connection
		if (connection != null) {
			System.out.println("Connection successful!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	// Getting URL from config.properties
	static String getURL() {
		String url = UtilProp.getProp("url");
		
		return url;
	}

	// Getting Username from config.properties
	static String getUserName() {
		String usr = UtilProp.getProp("user");
		
		return usr;
	}

	// Getting Password from config.properties
	static String getPassword() {
		String pwd = UtilProp.getProp("password");
		
		return pwd;
	}
}
