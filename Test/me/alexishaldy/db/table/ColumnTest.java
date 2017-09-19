package me.alexishaldy.db.table;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ColumnTest {

	@Test
	public void Column() {
		// OK
		try {
			Column n = new Column();
			if (!n.getColumnName().isEmpty() || n.getValues().size()!=0 || n.getNeighbor()!=null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			Column n = new Column("id");
			if (n.getColumnName().isEmpty() || n.getValues().size()!=0 || n.getNeighbor()!=null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			Column n = new Column("id", "1");
			if (n.getColumnName().isEmpty() || n.getValues().size()!=1 || n.getNeighbor()!=null || !n.getValues().get(0).equalsIgnoreCase("1"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addValue() {
		Column n = null;
		// OK
		try {
			n = new Column();
			n.addValue("name");
			if (n.getValues().size()!=1 || !n.getValues().get(0).equalsIgnoreCase("name"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getValues() {
		// OK
		try {
			Column n = new Column();
			n.addValue("name");
			if (n.getValues().size()!=1 || !n.getValues().get(0).equalsIgnoreCase("name"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getColumnName() {
		// OK
		try {
			Column n = new Column("ColumnName");
			if (!n.getColumnName().equalsIgnoreCase("ColumnName"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void setNeighbor() {
		// OK
		try {
			Column n = new Column("id", "1");
			Column m = new Column("title", "One thing");
			n.setNeighbor(m);
			if (n.getNeighbor()==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getNeighbor() {
		// OK
		try {
			Column n = new Column("id", "1");
			Column m = new Column("title", "One thing");
			n.setNeighbor(m);
			if (n.getNeighbor()==null || !n.getNeighbor().getColumnName().equalsIgnoreCase("title") || !n.getNeighbor().getValues().get(0).equalsIgnoreCase("One thing"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

}
