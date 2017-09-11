package me.alexishaldy.rest;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.enumerator.HttpResponseCode;
import me.alexishaldy.util.Utils;


public class RestHandlerTest {
	RestHandler rh = new RestHandler();
	
	@Test
	public void getResponseWithHeaders() {
		// OK
		try {
			if (RestHandler.getResponseWithHeaders("ça marche", HttpResponseCode.OK)==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (RestHandler.getResponseWithHeaders("ça marche 2", HttpResponseCode.NOK)==null)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			RestHandler.getResponseWithHeaders("ça marche pas", HttpResponseCode.valueOf("nope"));
			fail();
		} catch (Exception e) {}
	}
	
	@Test
	public void getSwagger() {
		// OK
		try {
			if (rh.getSwaggerJson().getStatus()==400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getSwaggerJson("").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addBook() {
		// NOK
		try {
			if (rh.addBook("title", "author", "date", "desc", "edition", "editeur", "lib").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.addBook("title", "author", "1888", "desc", "edition", "editeur", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void editBook() {
		// NOK
		try {
			if (rh.editBook("title", "author", "date", "desc", "edition", "editeur", "lib", "NaN").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.editBook("title", "author", "1888", "desc", "1", "editeur", "5", "150").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void searchBook() {
		// NOK
		try {
		if (rh.searchBook("title", "La vie", "NaN").getStatus()!=400)
			fail();
		} catch (Exception t) {
			fail(t.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("title", "La vie", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("author", "NaN", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("desc", "dsfs", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("title_author", "La vie¨NaN", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("title_author_numedition", "La vie¨NaN¨2", "4").getStatus()!=200)
 				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchBook("year", "2007", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getBookById() {
		// OK
		try {
			if (rh.getBookById("7").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getBookById("dsfuh").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getBook() {
		// OK
		try {
			if (rh.getBook("1", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getBook("dsf", "dfs").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getUser() {
		// OK
		try {
			if (rh.getUser().getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getUserById() {
		// OK
		try {
			if (rh.getUserById("1").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getUserById("Not a Number").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getUserByToken() {
		// OK
		try {
			if (rh.getUserByToken("c59bb7ae26e496c89792d1a09a6c528906349eb96adc9819a0dd1e46c65341b6").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void searchUser() {
		// OK
		try {
			if (rh.searchUser("Tryt", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.searchUser("Tryt", "6").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.searchUser("djkndsfjknds", "dsfds").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void userAdminExist() {
		// OK
		try {
			if (rh.userAdminExist("Try", "0b9c2625dc21ef05f6ad4ddf47c5f203837aa32c", "c6e79e52658d6d7f2dabc28278265e24c41994fdaa8a7b93138c629607a68c02").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.userAdminExist("Tryt", "dsfds", "dshbffuze78iuf").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void userMemberExist() {
		// OK
		try {
			if (rh.userMemberExist("Tryt", "088fb1a4ab057f4fcf7d487006499060c7fe5773", "3636976138e8f4874667065684464f6fc4c5fff5c72250180e7947bdf00efe0f", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.userMemberExist("Tryt", "dsfds", "dshbffuze78iuf", "dsf").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void addUser() {
		int i = (int) Math.round(Math.random()*1000000000);
		// OK
		try {
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "", "5").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void editUser() {
		// OK
		try {
			if (rh.editUser("2testyou@gmail.com", "0000000001", "33").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUser("2testyou@gmail.com", "0000000001", "NaN").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void editUserByAdmin() {
		// OK
		try {
			if (rh.editUserByAdmin("EUBA", "User", "Not Admin", "0", "2testyou@gmail.com", "0998436628", "33").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserByAdmin("EUBA", "User", "Not Admin", "0", "2testyou@gmail.com", "0998436628", "0").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void editUserBySuperAdmin() {
		// OK
		try {
			if (rh.editUserBySuperAdmin("EUBA", "User", "Not Admin", "0", "2testyou@gmail.com", "0998436628", "33", "3").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserBySuperAdmin("EUBA", "User", "Not Admin", "0", "2testyou@gmail.com", "0998436628", "33", "sdf").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserBySuperAdmin("EUBA", "User", "Not Admin", "0", "2testyou@gmail.com", "0998436628", "nan", "3").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void takeBook() {
		// OK
		try {
			if (rh.takeBook("150", "33", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.takeBook("bid", "uid", "lib").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void returnBook() {
		// OK
		try {
			if (rh.returnBook("150", "33", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.returnBook("bid", "uid", "lib").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}	

	@Test
	public void verifyTokenMember() {
		// OK
		try {
			if (rh.verifyTokenMember("c59bb7ae26e496c89792d1a09a6c528906349eb96adc9819a0dd1e46c65341b6").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}	

	@Test
	public void getRenter() {
		// OK
		try {
			if (rh.getRenter("4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getRenter("dsfd").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void verifyTokenAdmin() {
		// OK
		try {
			if (rh.verifyTokenAdmin("2f96be8d5c6dab548db3b0d1227d70593425a5350ef8a36fb5d4591e69c403e9").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteUser() {
		int i = (int) Math.round(Math.random()*1000000000);
		// OK
		try {
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		String sql = "SELECT MAX(id) FROM User";
		Vector<String> list = new Vector<String>();
		try {
			list = DBExecutor.selectQuery(sql);
		} catch (Exception e1) {}
		String id = list.get(0);
		
		try {
			if (rh.deleteUser(id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.deleteUser(id).getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteBook() {
		int i = (int) Math.round(Math.random()*1000000000);
		// OK
		try {
			if (rh.addBook("Not a title", "AuthMe", "2017", "A test book", ""+i, "TestEntreprise", "5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		String sql = "SELECT MAX(id) FROM Book";
		Vector<String> list = new Vector<String>();
		try {
			list = DBExecutor.selectQuery(sql);
		} catch (Exception e1) {}
		String id = list.get(0);
		
		try {
			if (rh.deleteBook(id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.deleteBook(id).getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getLib() {
		// OK
		try {
			if (rh.getLib().getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void listBook() {
		// OK
		try {
			if (rh.listBook("5").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

	@Test
	public void addEditDeleteLib() {
		// OK
		try {
			if (rh.addLib("TestLib", "Rue du test 3248753").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		String sql = "SELECT MAX(id) FROM Library";
		Vector<String> list = new Vector<String>();
		try {
			list = DBExecutor.selectQuery(sql);
		} catch (Exception e1) {}
		String id = list.get(0);
		// OK
		try {
			if (rh.editLib("TestLib2", "Rue du test +1", id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editLib("TestLib2", "Rue du test +1", "Not an ID").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.deleteLib(id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.deleteLib(id).getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	
	
}



























