package app;
// Import Statements
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	
	// Global Variables
	static String username, password;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// Class Constructor
	public Login() {
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
		response.setContentType("text/html");
		Connection connection = null;
		String insertSql;
		ResultSet result = null;
		
		// User Information
		getUserInfo(request);
		
		// If any input from login page is blank, throw error and return
		if(!checkErrors(username, password, out))
			return;
		
		// Initializing Variables
		response.setContentType("text/html");
		
		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			// Checking User table for email and password match
			insertSql = "SELECT * FROM User WHERE username = ? AND password = ?";
			
			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			
			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();
			
			// If login information is correct, redirect to user home page
			if(result.next())
			{
				request.setAttribute("username", username);
				RequestDispatcher rd = request.getRequestDispatcher("Home");
				rd.forward(request, response);
			}
			
			// If login information is incorrect, display error and return
			else
			{
				// Alert and redirect
				out.println("<script type=\"text/javascript\">");
		       	out.println("alert('Invalid username or password.');");
		       	out.println("window.location = 'login.html'");
		       	out.println("</script>");
		       	
		       	return;
			}
		
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
	
	// Checks if any input values are empty, and returns boolean value
	public static boolean checkErrors(String username, String password, PrintWriter out)
	{
		// If username or password is blank
		if(username.length() < 1 || password.length() < 1)
		{
			// Alert and redirect
			out.println("<script type=\"text/javascript\">");
	       	out.println("alert('Please fill out all required fields.');");
	       	out.println("window.location = 'login.html'");
	       	out.println("</script>");
			
			return false;
		}
			
		return true;
	}
	
	// Get user info and store in global variables
	static void getUserInfo(HttpServletRequest request)
	{
		// Getting User Information and storing in global variable
		username = request.getParameter("username");
		password = request.getParameter("password");
	}
}
