package me.alexishaldy.util;

import static org.junit.Assert.fail;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void utils() {
		// OK
		Utils u = new Utils();
	}

	@Test
	public void EncryptPassTest() {
		// NOK
		try {
			if (!Utils.encryptPassword(null).isEmpty())	
				fail();
		} catch (Exception e) {}
	}
	
}
