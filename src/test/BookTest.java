package test;

import org.junit.Test;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.User;

public class BookTest {
	Book b = new Book("Title", "Author", 2017, new User("Alexis", "Haldy"));
	
	@Test
	public  void testGetId() {
		b.getId();
	}

	@Test
	public  void testSetId() {
		b.setId(1);
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
	public  void testSetDate() {
		b.setDate(666);
	}

	@Test
	public  void testGetAuthor() {
		b.getAuthor();
	}

	@Test
	public  void testSetAuthor() {
		b.setAuthor("Tom Card");
	}

	@Test
	public  void testGetTitle() {
		b.getTitle();
	}

	@Test
	public  void testSetTitle() {
		b.setTitle("Le petit Prince de feu");
	}

	@Test
	public  void testIsTaken() {
		b.isTaken();
	}

	@Test
	public  void testSetTaken() {
		b.setTaken(true);
	}

	@Test
	public  void testGetOwner() {
		b.getOwner();
	}

	@Test
	public  void testSetOwner() {
		b.setOwner(new User("Iada", "Haïdi"));
	}

}
