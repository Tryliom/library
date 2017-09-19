package me.alexishaldy.classes;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {
	
	@Test
	public void TestBook() {
		// OK
		Book b = new Book("title", "desc", "author", 2017, "editeur", 2);
		if (!b.getTitle().equalsIgnoreCase("title"))
			fail();
		if (!b.getDesc().equalsIgnoreCase("desc"))
			fail();
		if (!b.getAuthor().equalsIgnoreCase("author"))
			fail();
		if (b.getDate()!=2017)
			fail();
		if (!b.getEditeur().equalsIgnoreCase("editeur"))
			fail();
		if (b.getEdition()!=2)
			fail();
		b.setAuthor("Darius");
		b.setDate(3424);
		b.setDesc("sdf");
		b.setEditeur("je suis un bon editeur");
		b.setEdition(5);
		b.setTitle("Good title");
	}
}
