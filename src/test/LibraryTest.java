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
		lib.listBook();
		lib.listBook(SortType.Number);
		lib.listBook(SortType.Year);
	}

	@Test
	public void testAddBook() {
		lib.addBook(new Book("Title", "Marc", 2017));
		lib.addBook(new Book("Title", "Marc", 2017, new User("Jovial", "Nain", "Pudent", "jovialdu19@gmail.com", "0796588843")));
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
		lib.getUserByNameOrLastname("User name");
		lib.getUserByNameOrLastname("User last name");
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
