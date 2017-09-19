package me.alexishaldy.rest;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.db.table.Column;
import me.alexishaldy.util.Utils;

public class JSONGeneratorTest {

	@Test
	public void getJson() {
		// OK
		try {
			Vector<String> list = new Vector<String>();
			list.add("id");
			list.add("name");
			if (JSONGenerator.getJson("library", list).isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getJsonWithTable() {
		// OK
		try {
			String json = JSONGenerator.getJsonWithTable(DBExecutor.selectAll("library"), Utils.putInMap("id name adress".split(" ")));
			if (json==null || json.isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getXml() {
		// OK
		try {
			if (JSONGenerator.getXml("library", "id", DBExecutor.selectAll("library")).isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getJson2() {
		// OK
		try {
			if (JSONGenerator.getJson("library", new Column("id")).isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			Column c = new Column("id");
			c.addValue("1");
			if (JSONGenerator.getJson("library", c).isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getXml2() {
		// OK
		try {
			if (JSONGenerator.getXml("library", "id", new Column("library")).isEmpty())
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
