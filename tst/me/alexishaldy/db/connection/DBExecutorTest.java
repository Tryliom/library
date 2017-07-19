package me.alexishaldy.db.connection;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

public class DBExecutorTest {
	
	@Test
	public void DBExecutor() {
		// OK
		try {
			DBExecutor db = new DBExecutor();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void execQuery() {
		// OK
		try {
			if (!DBExecutor.execQuery("UPDATE book SET id = 7 WHERE id = 7"))
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (DBExecutor.execQuery("SELECT existnot FROM book"))
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.execQuery("UPDATE book SET ids = 1 WHERE ids = 1"))
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.execQuery(null))
				fail();
		} catch (Exception e) {}
	}
	
	
	@Test
	public void selectQuery() {
		// OK
		try {
			if (DBExecutor.selectQuery("SELECT * FROM book")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (DBExecutor.selectQuery("SELECT id FROM book")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (DBExecutor.selectQuery("SELECT existnot FROM book")!=null)
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.selectQuery("UPDATE book SET id = 0 WHERE id = 1")!=null)
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.selectQuery(null)!=null)
				fail();
		} catch (Exception e) {}		

	}
	
	@Test
	public void selectAll() {
		// OK
		try {
			if (DBExecutor.selectAll("book")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (DBExecutor.selectAll("library")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (DBExecutor.selectAll("sdajhjhsdfdsjn")!=null)
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.selectAll(null)!=null)
				fail();
		} catch (Exception e) {}		
	}
	
	@Test
	public void selectById() {
		// OK
		try {
			if (DBExecutor.selectById("book", "1")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (DBExecutor.selectById("library", "1")==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (DBExecutor.selectById("book", "sad")!=null)
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.selectById("sjksaduihda", "1")!=null)
				fail();
		} catch (Exception e) {}
		// NOK
		try {
			if (DBExecutor.selectById(null, null)!=null)
				fail();
		} catch (Exception e) {}		
	}
	
}
