
// Import Statements
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// Class Constructor
	public Home() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// Get Method
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Instance Variables
		PrintWriter out = response.getWriter();
		Connection connection = null;
		String insertSql;
		ResultSet result = null;
		String firstName = null, lastName = null, email = null, username = null, birthday = null, fullName = null; 
		
		// Initializing Variables
		response.setContentType("text/html");

		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;

			// Checking User table for email match
			insertSql = "SELECT * FROM User WHERE email = ?";

			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, request.getParameter("email"));

			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();

			// Getting logged in user information from MySQL Database
			if(result.next())
			{
				// User Information
				firstName = result.getString(2);
				lastName = result.getString(3);
				email = result.getString(4);
				username = result.getString(5);
				birthday = result.getString(7);
				fullName = firstName + " " + lastName;
			}

		// Catch block to catch errors
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Printing User Information for Validation
		out.println("Current User Information: <br><br>");
		out.println("First Name: " + firstName);
		out.println("<br>Last Name: " + lastName);
		out.println("<br>Email: " + email);
		out.println("<br>Username: " + username);
		out.println("<br>Birthday: " + birthday);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// Post Method
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}