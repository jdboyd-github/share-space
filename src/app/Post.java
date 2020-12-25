package app;
// Import Statements
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
@WebServlet("/Post")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// Class Constructor
	public Post() {
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
		response.setContentType("text/html");
		Connection connection = null;
		String insertSql;
		
		// User Information
		String title = request.getParameter("title");
		String message = request.getParameter("status");
		String username = request.getParameter("username");
		
		// If any input from post form is blank, throw error and return
		if(title.length() < 1 || message.length() < 1)
		{
			// Instance Variables
			RequestDispatcher rd = request.getRequestDispatcher("Home");
			
			// Setting variables and forwarding
	       	request.setAttribute("username", username);
	       	request.setAttribute("error", "required_fields");
			rd.forward(request, response);
			
			return;
		}
		
		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			// Creating post in MySQL database
			insertSql = "INSERT INTO Post (title, message, username) VALUES (?, ?, ?)";
			
			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, title);
			preparedStmt.setString(2, message);
			preparedStmt.setString(3, username);
				
			// Executing MySQL command
			preparedStmt.execute();
		       	
		    // Redirect to home page
		    request.setAttribute("username", username);
		    RequestDispatcher rd = request.getRequestDispatcher("Home");
			rd.forward(request, response);
			
		// Catch block to catch errors
		} catch (Exception e) {
			e.printStackTrace();
		}
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
