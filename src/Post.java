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
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Connection connection = null;
		String insertSql;
		ResultSet result = null;
		
		// User Information
		String title = request.getParameter("title");
		String message = request.getParameter("status");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		System.out.println(username);
		System.out.println(email);
		System.out.println(title);
		System.out.println(message);
		

		// Try block for database connection
		try {
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			// Creating new account in MySQL database
			insertSql = "INSERT INTO Post (title, message, username) VALUES (?, ?, ?)";
			
			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, title);
			preparedStmt.setString(2, message);
			preparedStmt.setString(3, username);
				
			// Executing MySQL command
			preparedStmt.execute();
				
			// Notify success
			out.println("<script type=\"text/javascript\">");
	       	out.println("alert('Account Successfully Created!');");
	       	out.println("</script>");
		       	
		    // Redirect to home page
		    request.setAttribute("email", email);
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
