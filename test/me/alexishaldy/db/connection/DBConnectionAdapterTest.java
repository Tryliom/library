package me.alexishaldy.db.connection;

import static org.junit.Assert.fail;

import java.util.Map;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import me.alexishaldy.db.table.Column;
import me.alexishaldy.exception.DBException;

public class DBConnectionAdapterTest {
	public static DBConnectionAdapter db = null;
	
	@BeforeClass
	public static void dbco() {
		if (db == null)
			try {
				db = new DBConnectionAdapter("localhost", "3307", "root", "");
			} catch (DBException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void DBConnectionAdapterConstructor() {
		// OK
		try {
			DBConnectionAdapter db2 = new DBConnectionAdapter("localhost", "3307", "root", "");			
		} catch (DBException e) {
			fail();
		}
		// NOK
		try {
			DBConnectionAdapter db2 = new DBConnectionAdapter("178.88.56.98", "3307", "root", "");
			fail();
		} catch (DBException e) {}		
	}
	
	@Test
	public void getList() {
		// OK
		try {
			Vector<String> list = db.getList("SELECT * FROM book");
			if (list==null)
				fail();
		} catch (DBException e) {
			fail(e.getMessage());
		}
		// OK
		try {
			Vector<String> list = db.getList("SELECT id FROM book");
			if (list==null)
				fail();
		} catch (DBException e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			db.getList("SELECT existnot FROM book");
			fail();
		} catch (DBException e) {}
	}
	
	@Test
	public void execQuery() {
		// OK
		try {
			if (!db.execQuery("UPDATE book SET id = 7 WHERE id = 7")) 
				fail();
		} catch (DBException e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (db.execQuery("SELECT existnot FROM book"))
				fail();
		} catch (DBException e) {}
		// NOK
		try {
			if (db.execQuery("UPDATE book SET ids = 1 WHERE ids = 1"))
				fail();
		} catch (DBException e) {}
	}
	
	@Test
	public void selectQuery() {
		// OK
		try {
			Vector<String> list = db.selectQuery("SELECT * FROM book");
			if (list==null)
				fail();
		} catch (DBException e) {
			fail(e.getMessage());
		}
		// OK
		try {
			Vector<String> list = db.selectQuery("SELECT id FROM book");
			if (list==null)
				fail();
		} catch (DBException e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			Vector<String> list = db.selectQuery("SELECT existnot FROM book");
			fail();
		} catch (DBException e) {}
		// NOK
		try {
			Vector<String> list = db.selectQuery("UPDATE book SET id = 0 WHERE id = 1");
			fail();
		} catch (DBException e) {}

	}
	
	@Test
	public void getTable() {
		// OK
		try {
			Column c = db.getTable("book");
			if (c==null)
				fail();
		} catch (DBException e) {
			fail();
		}
		// OK
		try {
			Column c = db.getTable("library");
			if (c==null)
				fail();
		} catch (DBException e) {
			fail();
		}
		// NOK
		try {
			Column c = db.getTable("SELECT");
			fail();
		} catch (DBException e) {}
	}
	
	@Test
	public void getSystemDBs() {
		// OK
		try {
			Map<String, Boolean> mp = db.getSystemDBs();
		} catch (DBException e) {
			fail();
		}
	}
	

}
