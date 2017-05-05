package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.User;

public class BookTest {
	Book b = new Book("Title", "Author", 2017, new User("Alexis", "Haldy"));
	
	@Test
	public final void testGetId() {
		b.getId();
	}

	@Test
	public final void testSetId() {
		b.setId(1);
	}

	@Test
	public final void testGenerateId() {
		b.generateId();
	}

	@Test
	public final void testGetDate() {
		b.getDate();
	}

	@Test
	public final void testSetDate() {
		b.setDate(666);
	}

	@Test
	public final void testGetAuthor() {
		b.getAuthor();
	}

	@Test
	public final void testSetAuthor() {
		b.setAuthor("Tom Card");
	}

	@Test
	public final void testGetTitle() {
		b.getTitle();
	}

	@Test
	public final void testSetTitle() {
		b.setTitle("Le petit Prince de feu");
	}

	@Test
	public final void testIsTaken() {
		b.isTaken();
	}

	@Test
	public final void testSetTaken() {
		b.setTaken(true);
	}

	@Test
	public final void testGetOwner() {
		b.getOwner();
	}

	@Test
	public final void testSetOwner() {
		b.setOwner(new User("Iada", "Haïdi"));
	}

}
