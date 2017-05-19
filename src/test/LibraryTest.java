package test;

import org.junit.Test;

import me.alexishaldy.classes.Library;
import me.alexishaldy.enumerator.SortType;

public class LibraryTest {
	Library lib = Library.getLibrary();
	
	@Test
	public void testGetLibrary() {
		Library.getLibrary();
	}

	@Test
	public void testGetName() {
		lib.getName();
	}

	@Test
	public void testSetName() {
		lib.setName("");
		lib.setName("TestName");
	}

	@Test
	public void testListBook() {
		lib.listAllBook();
		lib.listAllBook(SortType.Number);
		lib.listAllBook(SortType.year);
	}
	
	@Test
	public void testGetUserByNameOrLastname() {
		lib.getUserIdByUsername("Username");
	}

	@Test
	public void testListUserAndBooks() {
		lib.listUserAndBooks();
	}
}
