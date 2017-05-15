package test;

import org.junit.Test;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.User;

public class BookTest {
	Book b = new Book("Title", "Marc", 2017, "Ma description est géniale", 3, "Dupuis", new User("Jovial", "Nain", "Pudent", "jovialdu19@gmail.com", "0796588843").getIdentityId());
	
	@Test
	public  void testGetId() {
		b.getId();
	}
	
	@Test
	public  void testGenerateId() {
		b.generateId();
	}

	@Test
	public  void testGetDate() {
		b.getDate();
	}
	
	@Test
	public  void testGetAuthor() {
		b.getAuthor();
	}
	
	@Test
	public  void testGetTitle() {
		b.getTitle();
	}

}
