package app;
// Import Statements
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet("/User")
public class User extends HttpServlet {
	
	// Global Variables
	String firstName, lastName, username, avatar, fullName;
	String u_firstName, u_lastName, u_username, u_avatar, u_fullName;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	// Class Constructor
	public User() {
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
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> posts = new ArrayList<String>();
		
		// Initializing Variables
		response.setContentType("text/html");

		// If username input from search is empty, return with error
		if(request.getParameter("uusername").length() < 1)
		{
			// Instance Variables
			RequestDispatcher rd = request.getRequestDispatcher("Home");
			
			// Setting request attributes and forwarding
	       	request.setAttribute("error", "required_fields");
	       	request.setAttribute("username", username);
			rd.forward(request, response);
			
			return;
		}
		
		// Try block for database connection
		try {
			
			// Connecting to database through JDBC
			DBConnection.getDBConnection();
			connection = DBConnection.connection;

			// Checking User table for username match
			insertSql = "SELECT * FROM User WHERE username = ?";

			// Preparing command statement for console
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, request.getParameter("username"));

			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();

			// If username exists, getting information and setting to global variables
			if(result.next())
			{
				// User Information
				firstName = result.getString(2);
				lastName = result.getString(3);
				username = result.getString(5);
				avatar = result.getString(7);
				fullName = firstName + " " + lastName;
			}
			
			// Resetting command statement for next username check
			insertSql = "SELECT * FROM User WHERE username = ?";
			
			// Preparing command statement for console
			preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, request.getParameter("uusername"));
			
			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();
			
			// If username exists, getting information and setting to global variables
			if(result.next())
			{
				// User Information
				u_firstName = result.getString(2);
				u_lastName = result.getString(3);
				u_username = result.getString(5);
				u_avatar = result.getString(7);
				u_fullName = u_firstName + " " + u_lastName;
			}
			
			// If username does not exist, throw error
			else
			{
				// Instance Variables
				RequestDispatcher rd = request.getRequestDispatcher("Home");
				
				// Setting request attributes and forwarding
				request.setAttribute("error", "invalid_user");
		       	request.setAttribute("username", username);
				rd.forward(request, response);
				
				return;
			}
			
			// If searched username is equal to logged in username, return to home
			if(username.equals(u_username))
			{
				// Setting request attributes and forwarding
				request.setAttribute("username", request.getParameter("username"));
				RequestDispatcher rd = request.getRequestDispatcher("Home");
				rd.forward(request, response);
				
				return;
			}
			
			// Getting user posts from post table
			insertSql = "SELECT * FROM Post WHERE username = ?";

			// Preparing command statement for console
			preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, u_username);

			// Executing MySQL command and storing output in result
			result = preparedStmt.executeQuery();

			// If username exists, adding posts to global arralists
			while(result.next())
			{
				titles.add(result.getString(2));
				posts.add(result.getString(3));
			}

		// Catch block to catch errors
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// User Interface
		out.println("<html>");
		out.println("<head>");
		out.println("<title>ShareSpace \u0095 " + u_username + "</title>");
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
		out.println("<ul class=\"information\">");
		out.println("<li><img src=\"" + u_avatar + "\" class=\"avatar\"></li>");
		out.println("<li><p class=\"username\">" + u_username + "</p></li>");
		out.println("<li><p class=\"name\">" + u_fullName + "</p></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("<div class=\"col\">");
		out.println("<div class=\"middle\">");
		out.println("<ul class=\"post\">");
		out.println("<li><p class=\"create\">Timeline</p></li>");
		out.println("<li id=\"post-form\">");
		
		// If there are no posts, print default post
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
		
		// If user has posts, print all posts
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
		out.println("<li><img class=\"avatar\" src=\"" + avatar + "\"");
		out.println("<li class=\"space\"></li>");
		out.println("<li><p class=\"username\">" + username + "</p></li>");
		out.println("<li><p class=\"name\">" + fullName + "</p></li>");
		out.println("<li>");
		out.println("<form action=\"User\" method=\"POST\">");
		out.println("<div class=\"search\">");
		out.println("<input type=\"hidden\" name=\"username\" value=\"" + username + "\"\">");
		out.println("<input autocomplete=\"off\" type=\"text\" name=\"uusername\" placeholder=\"Search Sharespace\">");
		out.println("<i class=\"fas fa-search\"></i>");
		out.println("</div>");
		out.println("</form>");
		out.println("</li>");
		out.println("</ul>");
		out.println("<nav>");
		out.println("<ul class=\"nav_links\">");
		out.println("<li>");
		out.println("<form action=\"Home\" method=\"POST\">");
		out.println("<input type=\"hidden\" name=\"username\" value=\"" + username + "\"\">");
		out.println("<button class=\"link\" type=\"submit\" class=\"clear\">My Profile</button>");
		out.println("</form>");
		out.println("</li>");
		out.println("<li>");
		out.println("<form action=\"login.html\">");
		out.println("<button class=\"link\" type=\"submit\" class=\"clear\">Logout</button>");
		out.println("</form>");
		out.println("</li>");
		out.println("</ul>");
		out.println("</nav>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");
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