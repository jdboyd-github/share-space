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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// Initializing Variables
		response.setContentType("text/html");
		
		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			// Checking User table for email and password match
			insertSql = "SELECT * FROM User WHERE email = ? AND password = ?";
			
			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, password);
			
			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();
			
			// If login information is correct, redirect to user home page
			
			if(result.next())
			{
				request.setAttribute("email", email);
				RequestDispatcher rd = request.getRequestDispatcher("Home");
				rd.forward(request, response);
			}
			
			// If login information is incorrect, display error and redirect to login
			else
			{
				// Alert and redirect
				out.println("<script type=\"text/javascript\">");
		       	out.println("alert('Email or Password incorrect. Please try again.');");
		       	out.println("window.location = 'login.html'");
		       	out.println("</script>");
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
