package me.alexishaldy.rest;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServerTest {

	@Test
	public void server() {
		String s[] = "test test".split(" ");
		try {
			Server.main(s);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			Server.main(s);
			fail();
		} catch (Exception e) {}
		Server.stopServer();
	}

}
