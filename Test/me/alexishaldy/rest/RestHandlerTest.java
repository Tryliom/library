package me.alexishaldy.rest;

import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.enumerator.HttpResponseCode;
import me.alexishaldy.util.Utils;


public class RestHandlerTest {
	RestHandler rh = new RestHandler();
	private String pseudo = "TryTest";
	private String pass = "640ab2bae07bedc4c163f679a746f7ab7fb5d1fa";
	private String email = "Try@catch.ex";
	private String tel = "0797776669";
	private String token = "c6e79e52658d6d7f2dabc28278265e24c41994fdaa8a7b93138c629607a68c02";
	private String id = "-1";
	
	
	private void prepareTestValue() {
		rh.addUser(pseudo, pseudo, pseudo, pass, email, tel, token);
		String sql = "SELECT id FROM User WHERE username = '"+pseudo+"'";
		try {
			Vector<String> list = DBExecutor.selectQuery(sql);
			if (list.size()>=1) {
				id = list.get(0);
			}
		} catch (Exception e) {}
	}
	
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
	public void addBook() {
		// NOK
		try {
			if (rh.addBook("title", "author", "date", "desc", "edition", "editeur", "lib").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.addBook("title", "author", "date", "desc", "90", "editeur", "lib").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}		
		// OK
		try {
			if (rh.addBook("title", "author", "1888", "desc", "5", "editeur", "5").getStatus()!=200)
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
	public void getRenter1() {
		// NOK
		try {
			if (rh.getRenter("1", "-1").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// OK
		try {
			if (rh.getRenter("title", "author").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void searchBook() {
		try {
			DBExecutor.execQuery("INSERT INTO Book(title, author, date, description, edition, editeur, library_id) VALUES(\"La vie\",\"NaN\",2007,\"For testing the life, this men make all...\",2,\"TestDum\",4)");
		} catch (Exception e1) {}
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
			if (rh.searchBook("title_author", "La vie¨NaN", "2").getStatus()!=200)
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
			if (rh.searchUser("Try").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.searchUser("djkndsfjknds").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void userAdminExist() {
		this.prepareTestValue();
		try {
			System.out.println(rh.editUserBySuperAdmin(pseudo, pseudo, pseudo, "", "7", email, tel, id).getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//OK
		try {
			if (rh.userAdminExist(pseudo, pass, token).getStatus()!=200) {
				System.out.println(rh.userAdminExist(pseudo, pass, token).getEntity());
				fail();
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		rh.editUserByAdmin(pseudo, pseudo, pseudo, "", "0", email, tel, id);
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
		this.prepareTestValue();
		// OK
		try {
			if (rh.userMemberExist(pseudo, pass, token).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.userMemberExist("Tryt", "dsfds", "dshbffuze78iuf").getStatus()!=400)
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
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void editUser() {
		this.prepareTestValue();
		// OK
		try {
			if (rh.editUser(email, tel, id).getStatus()!=200)
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
		this.prepareTestValue();
		// OK
		try {
			if (rh.editUserByAdmin(pseudo, pseudo, pseudo, "", "0", email, tel, id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserByAdmin("EUBA", "User", "Not Admin", "", "0", "2testyou@gmail.com", "0998436628", "0").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void editUserBySuperAdmin() {
		this.prepareTestValue();
		// OK
		try {
			if (rh.editUserBySuperAdmin(pseudo, pseudo, pseudo, "", "0", email, tel, id).getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserBySuperAdmin(pseudo, pseudo, pseudo, "", "0", email, tel, "dsfd").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.editUserBySuperAdmin(pseudo, pseudo, pseudo, "", "0", email, tel, "This is a word, not a number, plz fail").getStatus()!=400)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void takeBook() {
		this.prepareTestValue();
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
			if (rh.returnBook("bisadasdsad", "uisadsd", "lisdab").getStatus()!=400)
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
			if (rh.getRenter("1", "4").getStatus()!=200)
				fail();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// NOK
		try {
			if (rh.getRenter("dsfd", "").getStatus()!=400)
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
			if (rh.addUser("UserTest"+i, "M.", "N.", Utils.encryptPassword("Test"), "anTest@gmail.com", "0000000000", "").getStatus()!=200)
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



























