package app;
// Import Statements
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.regex.Pattern;

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
	
	// Gloabl Variables
	static String email, username, firstName, lastName, password, verification;
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
		
		// Get User Information
		getUserInfo(request);

		// Checks for errors in user input, throws errors and returns if any occur
		if(!checkErrors(email, username, firstName, lastName, password, verification, out))
			return;
		
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
			
			// If user already exists, throw error and return
			if(result.next())
			{
				// Alert and redirectt
				out.println("<script type=\"text/javascript\">");
		       	out.println("alert('Username or Email is already in use.');");
		       	out.println("window.location = 'signup.html'");
		       	out.println("</script>");
		       	
		       	return;
			}
			
			// If account does not exist, continue
			else if(!result.next())
			{
				// Checking to see if password does not match verification
				if(!password.equals(verification))
				{
					// Alert and redirect
					out.println("<script type=\"text/javascript\">");
			       	out.println("alert('Passwords do not match.');");
			       	out.println("window.location = 'signup.html'");
			       	out.println("</script>");
			       	
			       	return;
				}
				
				// Creating new account in MySQL database
				insertSql = "INSERT INTO User (firstName, lastName, email, username, password, avatar) VALUES (?, ?, ?, ?, ?, ?)";
				
				// Preparing command statement for console
				preparedStmt = connection.prepareStatement(insertSql);
				preparedStmt.setString(1, firstName);
				preparedStmt.setString(2, lastName);
				preparedStmt.setString(3, email);
				preparedStmt.setString(4, username);
				preparedStmt.setString(5, password);
				preparedStmt.setString(6, getAvatar());
				
				// Executing MySQL command
				preparedStmt.execute();
		       	
		       	// Redirect to new profile homepage
				RequestDispatcher rd = request.getRequestDispatcher("Home");
		       	request.setAttribute("error", "success");
		       	request.setAttribute("username", username);
				rd.forward(request, response);
				
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
	
	// Checks to see if an email provided is valid
	public static boolean validateEmail(String email)
	{
		// Instance Variables
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 
		Pattern pat = Pattern.compile(emailRegex); 
		
		if (email == null) 
			return false; 
		
		return pat.matcher(email).matches(); 
	}
	
	// Checks to see if a username is within required length
	public static boolean validateUsername(String username)
	{
		if(username.length() < 16 && username.length() > 2)
			return true;
		
		return false;
	}
	
	// Checks to see if a password is within required length
	public static boolean validatePassword(String password)
	{
		if(password.length() > 5)
			return true;
		
		return false;
	}
	
	// Checks if any input values are empty, and returns boolean value
	public static boolean checkEmptyStrings(String email, String username, String firstName, String lastName, String password, String verification)
	{
		if(email.length() < 1 || username.length() < 1 || firstName.length() < 1 || lastName.length() < 1
		|| password.length() < 1 || verification.length() < 1)
			return false;
		
		return true;
	}
	
	static boolean checkErrors(String email, String username, String firstName, String lastName,
			String password, String verification, PrintWriter out)
	{
		// If any values from signup page are empty, throw error and return
		if(!checkEmptyStrings(email, username, firstName, lastName, password, verification))
		{
			// Alert and redirect
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Please fill out all required fields.');");
			out.println("window.location = 'signup.html'");
			out.println("</script>");
			       	
			return false;
		}
				
		// Checking if email is valid, returning if invalid
		else if(!validateEmail(email))
		{
			// Alert and redirect
			out.println("<script type=\"text/javascript\">");
			out.println("alert('The email you have entered is invalid.');");
			out.println("window.location = 'signup.html'");
			out.println("</script>");
						       	
			return false;
		}
				
		// Checking if username is valid, returning if invalid
		else if(!validateUsername(username))
		{
			// Alert and redirect
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username must be between 3 and 16 characters.');");
			out.println("window.location = 'signup.html'");
			out.println("</script>");
									       	
			return false;
		}
				
		// Checking if password is valid, returning if invalid
		else if(!validatePassword(password))
		{
			// Alert and redirect
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Password must be atleast 6 characters.');");
			out.println("window.location = 'signup.html'");
			out.println("</script>");
									       	
			return false;
		}
		
		return true;
	}
	
	static void getUserInfo(HttpServletRequest request)
	{
		// Getting User Information and storing in global variable
		email = request.getParameter("email");
		username = request.getParameter("username");
		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName");
		password = request.getParameter("password");
		verification = request.getParameter("verification");
	}
	
	// Return random int between 1 and 8
	public static int randomInt()
	{
		// Instance Variables
		Random rand = new Random();
		
		return rand.nextInt(8) + 1;
	}
	
	// Returns a random avatar string based on a random integer value
	public static String getAvatar()
	{
		switch(randomInt())
		{
			case 1:
				return "Avatars/1.png";
			case 2:
				return "Avatars/2.png";
			case 3:
				return "Avatars/3.png";
			case 4:
				return "Avatars/4.png";
			case 5:
				return "Avatars/5.png";
			case 6:
				return "Avatars/6.png";
			case 7:
				return "Avatars/7.png";
			case 8:
				return "Avatars/8.png";
		}
		
		return "Avatars/default-avatar.png";
	}
}
