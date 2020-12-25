// Package
package JUnit;

// Import Statements
import static org.junit.jupiter.api.Assertions.*;
import app.*;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

// Main Class
class TestHome {
	@SuppressWarnings("deprecation")
	@Test
	// Tests getError for valid inputs
	void testGetErrorValid() {
		Home home = new Home();
		
		String input1 = "required_fields";
		String expected = "Please fill out all required fields.";
		String result = home.getError(input1);
		Assert.assertEquals(expected, result);
		
		input1 = "invalid_user";
		expected = "User does not exist.";
		result = home.getError(input1);
		Assert.assertEquals(expected, result);
		
		input1 = "success";
		expected = "Account Successfully Created!";
		result = home.getError(input1);
		Assert.assertEquals(expected, result);
		
		input1 = "login_error";
		expected = "An error has occured. Please re-login!";
		result = home.getError(input1);
		Assert.assertEquals(expected, result);
		
		input1 = "error";
		expected = "An unknown error has occured.";
		result = home.getError(input1);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	// Tests getError for invalid inputs
	void testGetErrorInvalid() {
		Home home = new Home();
		
		String input1 = "error";
		String expected = "Please fill out all required fields.";
		String result = home.getError(input1);
		assertFalse(expected.equals(result));
		
		input1 = "error";
		expected = "User does not exist.";
		result = home.getError(input1);
		assertFalse(expected.equals(result));
		
		input1 = "error";
		expected = "Account Successfully Created!";
		result = home.getError(input1);
		assertFalse(expected.equals(result));
		
		input1 = "error";
		expected = "An error has occured. Please re-login!";
		result = home.getError(input1);
		assertFalse(expected.equals(result));
		
		input1 = "invalid_user";
		expected = "An unknown error has occured.";
		result = home.getError(input1);
		assertFalse(expected.equals(result));
	}
}
