package me.alexishaldy.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import me.alexishaldy.classes.BookTest;
import me.alexishaldy.db.connection.DBConnectionAdapterTest;
import me.alexishaldy.db.connection.DBExecutorTest;
import me.alexishaldy.db.pool.DBConnectionPoolTest;
import me.alexishaldy.db.table.ColumnTest;
import me.alexishaldy.rest.JSONGeneratorTest;
import me.alexishaldy.rest.RestHandlerTest;
import me.alexishaldy.rest.ServerTest;
import me.alexishaldy.util.UtilsTest;




@RunWith(Suite.class)
@SuiteClasses({
  // Test class
	ServerTest.class,
	DBConnectionAdapterTest.class,
	DBExecutorTest.class,
	DBConnectionPoolTest.class,
	ColumnTest.class,
	JSONGeneratorTest.class,
	RestHandlerTest.class,
	UtilsTest.class,
	BookTest.class
})
public class AllTest {}