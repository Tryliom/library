package me.alexishaldy.db.pool;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import me.alexishaldy.db.connection.DBConnection;

public class DBConnectionPoolTest {
	static DBConnectionPool db = null;
	
	@BeforeClass
	public static void DBConnectionPool() {
		try {
			db = DBConnectionPool.getInstance();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void popConnection() {
		try {
			db.popConnection();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void pushConnection() {
		// OK
		try {
			DBConnection conn = DBConnectionPool.getInstance().popConnection();
			db.pushConnection(conn);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			DBConnection conn = DBConnectionPool.getInstance().popConnection();
			db.pushConnection(conn);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
