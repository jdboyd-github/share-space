
// Import Statements
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> posts = new ArrayList<String>();
		
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
			
			// Checking User table for email match
			insertSql = "SELECT * FROM Post WHERE username = ?";

			// Preparing command statement for console
			preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, username);

			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();

			// Getting logged in user information from MySQL Database
			while(result.next())
			{
				titles.add(result.getString(2));
				posts.add(result.getString(3));
			}

		// Catch block to catch errors
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Printing User Information for Validation
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Split Screen Test</title>");
		out.println("<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap\" rel=\"stylesheet\">");
		out.println("<link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap\" rel=\"stylesheet\">");
		out.println("<link rel=\"stylesheet\" href=\"https://pro.fontawesome.com/releases/v5.10.0/css/all.css\">");
		out.println("<link rel=\"stylesheet\" href=\"home.css\">");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"row no-gutters\">");
		out.println("<div class=\"col\">");
		out.println("<div class=\"left\">");
		out.println("<ul class=\"post\">");
		out.println("<li><p class=\"create\">Create Post</p></li>");
		out.println("<li id=\"post-form\">");
		out.println("<div class=\"container\">");
		out.println("<form action=\"Post\" method=\"POST\">");
		out.println("<div class= \"form-group\">");
		out.println("<label for=\"title\">Title</label>");
		out.println("<input type=\"hidden\" name=\"email\" value=\"" + email + "\"\">");
		out.println("<input type=\"hidden\" name=\"username\" value=\"" + username + "\"\">");
		out.println("<input name=\"title\" type=\"text\" id=\"title\" name=\"Title\">");
		out.println("</div>");
		out.println("<div class= \"form-group\">");
		out.println("<label for=\"status\">Status</label>");
		out.println("<textarea name=\"status\" id=\"status\" cols=\"30\" rows=\"5\"></textarea>");
		out.println("</div>");
		out.println("<button type=\"submit\">Submit</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("</li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("<div class=\"col\">");
		out.println("<div class=\"middle\">");
		out.println("<ul class=\"post\">");
		out.println("<li><p class=\"create\">Timeline</p></li>");
		out.println("<li id=\"post-form\">");
		
		if(titles.size() == 0)
		{
			out.println("<div class=\"container\">	");
			out.println("<div class= \"display-group\">");
			out.println("<p id=\"displaytitle\">Oh no!</p>");
			out.println("</div>");
			out.println("<div class= \"display-group\">");
			out.println("<p id=\"displaystatus\">This user hasn't posted anything yet. Any future posts they make will appear here!</p>");
			out.println("</div>");
			out.println("</div>");
		}
		else
		{
			for(int i = titles.size() - 1; i >= 0; i--)
			{
				out.println("<div class=\"container\">	");
				out.println("<div class= \"display-group\">");
				out.println("<p id=\"displaytitle\">" + titles.get(i) + "</p>");
				out.println("</div>");
				out.println("<div class= \"display-group\">");
				out.println("<p id=\"displaystatus\">" + posts.get(i) + "</p>");
				out.println("</div>");
				out.println("</div>");
			}
		}
		
		out.println("</li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("<div class=\"col\">");
		out.println("<div class=\"right\">");
		out.println("<ul class=\"information\">");
		out.println("<li><img src=\"default-avatar.png\" class=\"avatar\"></li>");
		out.println("<li><p class=\"username\">" + username + "</p></li>");
		out.println("<li><p class=\"name\">" + fullName + "</p></li>");
		out.println("<li>");
		out.println("<form action=\"test.html\" method=\"POST\">");
		out.println("<div class=\"search\">");
		out.println("<input type=\"text\" placeholder=\"Search Sharespace\">");
		out.println("<i class=\"fas fa-search\"></i>");
		out.println("</div>");
		out.println("</form>");
		out.println("</li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");
		out.println("");
		out.println("</body>");
		out.println("</html>");
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