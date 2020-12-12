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
 * Servlet implementation class MyServlet
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// Class Constructor
	public Signup() {
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
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verification = request.getParameter("verification");
		

		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			// Checking User table for email or username match
			insertSql = "SELECT * FROM User WHERE email = ? OR username = ?";
			
			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, username);
			
			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();
			
			// If account does not already exist, continue
			if(!result.next())
			{
				// Checking to see if password matches verification
				if(!password.equals(verification))
				{
					// Alert and redirect if they dont match
					out.println("<script type=\"text/javascript\">");
			       	out.println("alert('Your passwords do not match. Please try again.');");
			       	out.println("window.location = 'signup.html'");
			       	out.println("</script>");
				}
				
				// Creating new account in MySQL database
				insertSql = "INSERT INTO User (firstName, lastName, email, username, password, birthday) VALUES (?, ?, ?, ?, ?, ?)";
				
				// Preparing command statement for console
				preparedStmt = connection.prepareStatement(insertSql);
				preparedStmt.setString(1, "first");
				preparedStmt.setString(2, "last");
				preparedStmt.setString(3, email);
				preparedStmt.setString(4, username);
				preparedStmt.setString(5, password);
				preparedStmt.setString(6, "2020-01-01");
				
				// Executing MySQL command
				preparedStmt.execute();
				
				// Notify success, redirect user to home page
				out.println("<script type=\"text/javascript\">");
		       	out.println("alert('Account Successfully Created!');");
		       	out.println("</script>");
		       	
		       	// Redirect to home page
		       	request.setAttribute("email", email);
				RequestDispatcher rd = request.getRequestDispatcher("Home");
				rd.forward(request, response);
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

}
