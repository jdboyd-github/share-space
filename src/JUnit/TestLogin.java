// Package
package JUnit;

// Import Statements
import static org.junit.jupiter.api.Assertions.*;
import app.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

// Main Class
class TestLogin {
	@SuppressWarnings("deprecation")
	@Test
	// Tests checkErrors to check valid output
	void testCheckErrorsValid() throws FileNotFoundException {
		// Instance Variables
		Login login = new Login();
		PrintWriter out = new PrintWriter("output.txt");
		
		// Test 1
		String input1 = "username";
		String input2 = "password";
		boolean expected = true;
		boolean result = login.checkErrors(input1, input2, out);
		Assert.assertEquals(expected, result);
		
		// Test 2
		input1 = "asd123";
		input2 = "asd123";
		expected = true;
		result = login.checkErrors(input1, input2, out);
		Assert.assertEquals(expected, result);
		
		// Test 3
		input1 = "";
		input2 = "asd123";
		expected = false;
		result = login.checkErrors(input1, input2, out);
		Assert.assertEquals(expected, result);
		
		// Test 4
		input1 = "asd123";
		input2 = "";
		expected = false;
		result = login.checkErrors(input1, input2, out);
		Assert.assertEquals(expected, result);
	}
	
	// Tests checkErrors to check invalid output
	@Test
	void testCheckErrorsInvalid() throws FileNotFoundException {
		// Instance Variables
		Login login = new Login();
		PrintWriter out = new PrintWriter("output.txt");
		
		// Test 1
		String input1 = "username";
		String input2 = "password";
		boolean expected = false;
		boolean result = login.checkErrors(input1, input2, out);
		assertFalse(expected == result);
		
		// Test 2
		input1 = "";
		input2 = "asd123";
		expected = true;
		result = login.checkErrors(input1, input2, out);
		assertFalse(expected == result);
	}
}
