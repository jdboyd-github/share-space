// Package
package JUnit;

// Import Statements
import static org.junit.jupiter.api.Assertions.*;
import app.*;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

// Main Class
class TestSignup {
	@SuppressWarnings("deprecation")
	@Test
	// Checking validateEmail, which returns a boolean weather the email is valid or not
	void testValidateEmail() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		String input1 = "test@test.com";
		boolean expected = true;
		boolean result = signup.validateEmail(input1);
		Assert.assertEquals(expected, result);
		
		// Test 2
		input1 = "test@test..com";
		expected = false;
		result = signup.validateEmail(input1);
		Assert.assertEquals(expected, result);
		
		// Test 3
		input1 = "string";
		expected = false;
		result = signup.validateEmail(input1);
		Assert.assertEquals(expected, result);
		
		// Test 4
		input1 = "test@@test.com";
		expected = false;
		result = signup.validateEmail(input1);
		Assert.assertEquals(expected, result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	// Testing vadidateUsername, which is used to check if username is proper length
	void testValidateUsername() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		String input1 = "a";
		boolean expected = false;
		boolean result = signup.validateUsername(input1);
		Assert.assertEquals(expected, result);
		
		// Test 2
		input1 = "username";
		expected = true;
		result = signup.validateUsername(input1);
		Assert.assertEquals(expected, result);
		
		// Test 3
		input1 = "namegreaterthan16characters";
		expected = false;
		result = signup.validateUsername(input1);
		Assert.assertEquals(expected, result);
		
		// Test 4
		input1 = "asd";
		expected = true;
		result = signup.validateUsername(input1);
		Assert.assertEquals(expected, result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	// Testing checkEmptyStrings, which is used to make sure no input strings are blank
	void testCheckEmptyStrings() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		String input1 = "email", input2 = "username", input3 = "firstName", input4 = "lastName", input5 = "password", input6 = "verification";
		boolean expected = true;
		boolean result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 2
		input1 = ""; input2 = "username"; input3 = "firstName"; input4 = "lastName"; input5 = "password"; input6 = "verification";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 3
		input1 = "email"; input2 = ""; input3 = "firstName"; input4 = "lastName"; input5 = "password"; input6 = "verification";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 4
		input1 = "email"; input2 = "username"; input3 = ""; input4 = "lastName"; input5 = "password"; input6 = "verification";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 5
		input1 = "email"; input2 = "username"; input3 = "firstName"; input4 = ""; input5 = "password"; input6 = "verification";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 6
		input1 = "email"; input2 = "username"; input3 = "firstName"; input4 = "lastName"; input5 = ""; input6 = "verification";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 7
		input1 = "email"; input2 = "username"; input3 = "firstName"; input4 = "lastName"; input5 = "password"; input6 = "";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
		
		// Test 8
		input1 = ""; input2 = ""; input3 = ""; input4 = ""; input5 = ""; input6 = "";
		expected = false;
		result = signup.checkEmptyStrings(input1, input2, input3, input4, input5, input6);
		Assert.assertEquals(expected, result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	// Testing randomInt to make sure integers are within the proper range
	void testRandomIntValid() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		boolean expected = true;
		int result = signup.randomInt();
		if(result > 0 && result < 9)
			Assert.assertEquals(expected, expected);
	}
	
	@Test
	// Testing to make sure randomInt does not retuurn number out of range
	void testRandomIntInvalid() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		boolean expected = false;
		int result = signup.randomInt();
		if(result < 1 && result > 8)
			assertFalse(expected == true);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	// Testing getAvatar to make sure avatars are being returned with random input
	void testGetAvatar() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		String expected = "Avatars/1.png";
		String result = signup.getAvatar();
		if(result.contains(expected))
			Assert.assertEquals(expected, expected);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	// Testing validatePassword, which is a boolean result if the password is proper length
	void testValidatePassword() {
		// Instance Variables
		Signup signup = new Signup();
		
		// Test 1
		String input1 = "password";
		boolean expected = true;
		boolean result = signup.validatePassword(input1);
		Assert.assertEquals(expected, result);
		
		// Test 2
		input1 = "abcd";
		expected = false;
		result = signup.validatePassword(input1);
		Assert.assertEquals(expected, result);
	}
}
