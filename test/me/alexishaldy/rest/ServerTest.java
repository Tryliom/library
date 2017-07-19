package me.alexishaldy.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class ServerTest {

	@Test
	public void server() {
		String s[] = "test test".split(" ");
		try {
			Server.main(s);
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {
			fail(e.getMessage());
		}
		try {
			Server.main(s);
			fail();
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {}
	}

}
