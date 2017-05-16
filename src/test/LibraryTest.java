package test;

import org.junit.Test;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.Library;
import me.alexishaldy.classes.SortType;
import me.alexishaldy.classes.User;

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
	public void testAddBook() {
		lib.addBook(new Book("Wolverine", "Marc", 2017, "Ma description est géniale", 3, "Marvel"));
		lib.addBook(new Book("Wolverine", "Marc", 2017, "Ma description est géniale", 3, "Marvel", new User("Jovial", "Nain", "Pudent", "jovialdu19@gmail.com", "0796588843").getIdentityId()));
	}

	@Test
	public void testRemoveBook() {
		lib.removeBook();
		lib.removeBook("Title");
		lib.removeBook("Marc");
		lib.removeBook("2017");
	}

	@Test
	public void testAddUser() {
		lib.addUser(new User("Jovial", "Nain", "Pudent", "jovialdu19@gmail.com", "0796588843"));
	}

	@Test
	public void testGetUserByNameOrLastname() {
		lib.getUserByUsername("Username");
	}

	@Test
	public void testListUserAndBooks() {
		lib.listUserAndBooks();
	}

	@Test
	public void testGetBookList() {
		lib.getBookList();
	}

}
