package me.alexishaldy.rest;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import me.alexishaldy.classes.Book;
import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.enumerator.HttpResponseCode;
import me.alexishaldy.enumerator.SortType;
import me.alexishaldy.util.Utils;

@Path("/")
public class RestHandler {
	
	final String CURR_DIR = this.getClass().getClassLoader().getResource("").getPath();
	
	
	
	/**
	 * This method defines a response with CORS (cross-origin resource sharing) enabled
	 * (in order to avoid problems with requests through swagger).
	 * @param content	Body content
	 * @param status	Response status
	 * @return			Response including the headers to enable CORS and the body.
	 */
	static Response getResponseWithHeaders(String content, HttpResponseCode status) {
		return Response.ok(content).
				status(status.getValue()).
				header("Access-Control-Allow-Origin","*").
				header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE").
				header("Access-Control-Max-Age", "3600").
				header("Access-Control-Allow-Headers", "x-requested-with").build();
	}
	
	
	@GET
	@Path("/swagger.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSwaggerJson(String...file) {
		try {
			String fileName = "";
			if (file!=null && file.length>0)
				fileName = file[0];
			else
				fileName = CURR_DIR + Utils.SEP + ".." + Utils.SEP + ".." + Utils.SEP + Utils.SWAGGER_FILE;
			return getResponseWithHeaders(Utils.readFile(fileName), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@POST // Ne pas mettre les {} dans l'url
	@Path("/book/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("date") String date, @FormParam("description") String desc,
			@FormParam("edition") String edition, @FormParam("editeur") String editeur, @FormParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO book(title, author, date, description, edition, editeur, library_id) VALUES (\""+title+"\", \""+author+"\", \""+date+"\", "
					+ "\""+desc+"\", "+edition+", \""+editeur+"\", "+lib+");");
			if (!b)
				throw new Exception("Insert failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@POST
	@Path("/book/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("date") String date, @FormParam("description") String desc,
			@FormParam("edition") String edition, @FormParam("editeur") String editeur, @FormParam("library_id") String lib, @FormParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET title = \""+title+"\", author = \""+author+"\", date = \""+date+"\", description = \""+desc+"\", edition = "+edition+", editeur = \""+editeur+"\", library_id = "+lib+""
					+ " WHERE id = "+id);
			if (!b)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@POST
	@Path("/book/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBook(@FormParam("type") String type, @FormParam("arg") String arg, @FormParam("library_id") String lib) {
		String[] args = arg.split("¨");
		String sql = "";		
		try {			
			switch (SortType.valueOf(type)) {
			case author:
				sql = "SELECT id FROM book WHERE author = '"+args[0]+"' AND library_id = "+lib;
				break;
			case desc:
				sql = "SELECT id FROM book WHERE description = '"+args[0]+"' AND library_id = "+lib;
				break;
			case title:
				sql = "SELECT id FROM book WHERE title = '"+args[0]+"' AND library_id = "+lib;
				break;
			case title_author:
				sql = "SELECT id FROM book WHERE title = '"+args[0]+"' AND author = '"+args[1]+"' AND library_id = "+lib;
				break;
			case title_author_numedition:
				sql = "SELECT id FROM book WHERE title = '"+args[0]+"' AND author = '"+args[1]+"' AND edition = '"+args[2]+"' AND library_id = "+lib;
				break;
			case year:
				sql = "SELECT id FROM book WHERE date = "+args[0]+" AND library_id = "+lib;
				break;
			default:
				break;				
			}
			Vector<String> list = DBExecutor.selectQuery(sql);
			HashMap<Integer, String> hash = Utils.putInMap("id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}

	
	@GET
	@Path("/book/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookById(@PathParam("id") String id) {
		try {
			Vector<String> list = DBExecutor.selectById("book", id);
			HashMap<Integer, String> hash = Utils.putInMap("id title author date description edition editor user_id library_id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@GET
	@Path("/book/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook() {
		try {
			Vector<String> list = DBExecutor.selectAll("book");
			HashMap<Integer, String> hash = Utils.putInMap("id title author date description edition editor user_id library_id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@GET
	@Path("/user/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		try {
			Vector<String> list = DBExecutor.selectAll("user");
			HashMap<Integer, String> hash = Utils.putInMap("id username name lastname password email tel library_id token level_access".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@GET
	@Path("/user/get/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") String id) {
		try {
			Vector<String> list = DBExecutor.selectById("user", id);
			HashMap<Integer, String> hash = Utils.putInMap("id username name lastname password email tel library_id token level_access".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@GET
	@Path("/user/get/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByToken(@PathParam("token") String token) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT id, username, name, lastname, email, tel, level_access FROM user WHERE token = \""+token+"\"");
			HashMap<Integer, String> hash = Utils.putInMap("id username name lastname email tel level_access".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@POST
	@Path("/user/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUser(@FormParam("username") String pseudo, @FormParam("library_id") String lib) {		
		try {			
			String sql = "SELECT id FROM User WHERE username = '"+pseudo+"' AND library_id = "+lib;
			Vector<String> list = DBExecutor.selectQuery(sql);
			return getResponseWithHeaders(JSONGenerator.getJson("user", list), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@PUT
	@Path("/user/admin/update/{username}/{password}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userAdminExist(@PathParam("username") String pseudo, @PathParam("password") String pass, @PathParam("token") String token) {		
		try {			
			Boolean b = DBExecutor.execQuery("UPDATE user SET token = \""+token+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\" AND level_access >= 7");
			if (!b)
				throw new Exception("Compte inexistant");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@PUT
	@Path("/user/member/update/{username}/{password}/{token}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userMemberExist(@PathParam("username") String pseudo, @PathParam("password") String pass, @PathParam("token") String token, @PathParam("library_id") String lib) {		
		try {			
			Boolean b = DBExecutor.execQuery("UPDATE user SET token = \""+token+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\" AND (library_id = "+lib+" OR level_access >= 7)");
			if (!b)
				throw new Exception("Compte inexistant");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@POST	
	@Path("/user/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("password") String pass, @FormParam("email") String email,
			@FormParam("tel") String tel, @FormParam("token") String token, @FormParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO user(username, name, lastname, password, email, tel, token, library_id) SELECT \""+username+"\", \""+name+"\", \""+lastname+"\", "
					+ "\""+pass+"\", \""+email+"\", \""+tel+"\", \""+token+"\", "+lib+" WHERE (SELECT count(*) FROM user WHERE username = \""+username+"\")=0");
			if (!b)
				throw new Exception("Pseudo d�j� pris");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@PUT
	@Path("/user/edit/{email}/{tel}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUser(@PathParam("email") String email, @PathParam("tel") String tel, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\" WHERE id = "+id+";");
			if (!b)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@POST
	@Path("/user/edit/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUserByAdmin(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, 
			@FormParam("level_access") String level, @FormParam("email") String email, @FormParam("tel") String tel, @FormParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\", lastname = \""+lastname+"\", level_access = "+(Integer.parseInt(level)>6 ? "6" : level)+" WHERE id = "+id+";");
			if (!b)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@POST
	@Path("/user/edit/superadmin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUserBySuperAdmin(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, 
			@FormParam("level_access") String level, @FormParam("email") String email, @FormParam("tel") String tel, @FormParam("id") String id, @FormParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\","
					+ " lastname = \""+lastname+"\", level_access = "+level+", library_id = "+lib+" WHERE id = "+id+";");
			if (!b)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@PUT
	@Path("/user/return/{book_id}/{user_id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBook(@PathParam("book_id") String bid, @PathParam("user_id") String uid, @PathParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET user_id = NULL WHERE id = \""+bid+"\" AND library_id = "+lib+";");
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Boolean c = DBExecutor.execQuery("UPDATE renter SET return_date = '"+formater.format(new Date().getTime())+"' WHERE return_date IS NULL AND book_id = "+bid+" AND user_id = "+uid+" AND library_id = "+lib+";");
			if (!b || !c)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@PUT
	@Path("/user/take/{book_id}/{user_id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response takeBook(@PathParam("book_id") String bid, @PathParam("user_id") String uid, @PathParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET user_id = "+uid+" WHERE id = \""+bid+"\" AND library_id = "+lib+";");
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Boolean c = DBExecutor.execQuery("INSERT INTO renter(book_id, user_id, taken_date, max_return_date, library_id) VALUES ("+bid+", "+uid+", "
					+ "'"+formater.format(new Date().getTime())+"', '"+formater.format(new Date().getTime()+(60000*60*48))+"', "+lib+");");
			if (!b || !c)
				throw new Exception("Update failed");
			else
				return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@GET
	@Path("/user/verif/member/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyTokenMember(@PathParam("token") String token) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT username FROM user WHERE token = \""+token+"\"");
			
			HashMap<Integer, String> hash = Utils.putInMap("username".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@GET
	@Path("/renter/get/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRenter(@PathParam("library_id") String lib) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT (NOW()-max_return_date) AS max_return_date, book_id, user_id FROM renter WHERE return_date IS NULL AND NOW()-max_return_date>0 AND library_id = "+lib);
			
			HashMap<Integer, String> hash = Utils.putInMap("max_return_date book_id user_id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@GET
	@Path("/user/verif/admin/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyTokenAdmin(@PathParam("token") String token) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT username FROM user WHERE token = \""+token+"\" AND level_access >= 7");
			
			HashMap<Integer, String> hash = Utils.putInMap("username".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@PUT
	@Path("/user/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM user WHERE id = "+id+";");
			if (!b)
				throw new Exception("Delete failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@PUT
	@Path("/book/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBook(@PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM book WHERE id = "+id+";");
			if (!b)
				throw new Exception("Delete failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@GET
	@Path("/library/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLib() {
		try {
			Vector<String> list = DBExecutor.selectAll("library");
			
			HashMap<Integer, String> hash = Utils.putInMap("id name adress".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}

	
	@GET
	@Path("/library/list/book/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listBook(@PathParam("library_id") String lib) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT title, author, date, description, edition, editeur FROM book WHERE user_id IS NULL AND library_id = "+lib);
			
			HashMap<Integer, String> hash = Utils.putInMap("title author date description edition editor".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@POST
	@Path("/library/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editLib(@FormParam("name") String name, @FormParam("adress") String adress, @FormParam("library_id") String lib) {
		try {
			String sql = "UPDATE library SET name = \""+name+"\", adress = \""+adress+"\"  WHERE id = "+lib;
			Boolean b = DBExecutor.execQuery(sql);
			if (!b)
				throw new Exception("Update failed: "+sql);
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@PUT
	@Path("/library/delete/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteLib(@PathParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM library WHERE id = "+lib+";");
			if (!b)
				throw new Exception("Delete failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@POST
	@Path("/library/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLib(@FormParam("name") String name, @FormParam("adress") String adress) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO library(name, adress) VALUES (\""+name+"\", \""+adress+"\");");
			if (!b)
				throw new Exception("Insert failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	
//	@GET
//	@Path("/library/clear")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response clearDouble() {
//		try {
//			Boolean bo = DBExecutor.execQuery("DELETE t1 FROM book AS t1, book AS t2 WHERE t1.id > t2.id AND t1.title = t2.title AND t1.library_id = t2.library_id");
//			return getResponseWithHeaders("true", HttpResponseCode.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
//		}
//	}
//	
//	@GET
//	@Path("/library/feed/file/{library_id}/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response feedLibByFile(@PathParam("library_id") String lib) {
//		try {
//			String title = "";
//			String author = "";
//			int date = 0;
//			String desc = "";
//			int edition = 0;
//			String editeur = "";
//			
//			int nbLigne = 0;
//			Vector<Book> listBook = new Vector<Book>();
//			String lastAdd = "";
//			File f = new File("C:/Users/haldy/Desktop/db.txt");
//			if (Utils.currThread==null)
//				Utils.startGetAuthors(f);
//			Scanner sc = new Scanner(f, "UTF-8");
//			Vector<Book> listBookRem = new Vector<Book>();
//			nbLigne = 0;
//			while (sc.hasNext()) {
//				nbLigne++;
//				String l = sc.nextLine();
//				if (nbLigne>1200000) {
//					if (nbLigne%10000==0) {
//						// Utils.findAuthorForBooks(listBook);
//						int i = 0;
//						for (Book b : listBook) {
//							if (!b.getAuthor().startsWith("/")) {
//								listBookRem.add(b);
//								i++;
//								Boolean bb = null;
//								try {
//						        	lastAdd = "INSERT INTO book(title, author, date, description, edition, editeur, library_id) VALUES (\""+b.getTitle().replaceAll("\"", "")+"\", \""+b.getAuthor().replaceAll("\"", "")+"\", "+b.getDate()+", "
//											+ "\""+b.getDesc().replaceAll("\"", "")+"\", "+b.getEdition()+", \""+b.getEditeur().replaceAll("\"", "")+"\", "+lib+")";			            	
//						        	bb = DBExecutor.execQuery(lastAdd);
//						        	
//										Boolean bo = DBExecutor.execQuery("DELETE t1 FROM book AS t1, book AS t2 WHERE t1.id > t2.id AND t1.title = \""+b.getTitle().replaceAll("\"", "")+"\" AND t2.title = \""+b.getTitle().replaceAll("\"", "")+"\"");
//								} catch (Exception e) {}
//					        	System.out.println(i+": "+bb);
//							}
//						}
//						for (Book b : listBookRem) {
//							listBook.remove(b);
//						}
//					}
//					if (nbLigne%100000==0) {
//						HashMap<String, String> li = (HashMap<String, String>) Utils.listAuthors.clone();
//						int i=0;
//						for (Book b : listBookRem) {
//							i++;
//							String author1 = "No authors";
//							author1 = li.get(b.getAuthor());
//							if (!author1.equalsIgnoreCase("No authors")) {
//								JSONObject obj = new JSONObject(author1);
//								String name = "";
//								if (obj.has("name")) {
//									name = obj.getString("name");
//								} else {
//									name = obj.getString("personal_name");
//								}					
//								b.setAuthor(name.replaceAll("\\[u][0-9a-z]{4}", "?"));							
//							}
//							Boolean bb = DBExecutor.execQuery("UPDATE Book SET author = \""+b.getAuthor()+"\" WHERE title = \""+b.getTitle()+"\"");
//						}
//						if (i%50==0)
//							System.out.println("LigneUpdate "+i);
//					}
//					if (l.startsWith("/type/edition")) {
//						String s[] = l.split("\t");
//						l = s[4];
//						JSONObject obj = new JSONObject(l);
//						// String pageName = obj.getJSONObject("pageInfo").getString("pageName");
//						title = obj.has("title") ? obj.getString("title") : "No title";
//						desc = obj.has("subtitle") ? obj.getString("subtitle") : "No description";
//						try{
//							date = Integer.parseInt(obj.has("publish_date") ? obj.getString("publish_date").replaceAll("([^0-9]?) ", "") : "2000");
//						} catch (Exception e) {
//							date = 2000;
//						}
//						edition = Integer.parseInt(obj.has("revision") ? ""+obj.getInt("revision") : "1");
//						if (obj.has("publishers") && !obj.getJSONArray("publishers").isNull(0)) {
//							editeur = (String) obj.getJSONArray("publishers").get(0);
//							for (int i=1;i<obj.getJSONArray("publishers").length();i++) 
//								editeur+=", "+obj.getJSONArray("publishers").get(i);
//						} else
//							editeur="No publishers";
//						author = (obj.has("authors") && !obj.getJSONArray("authors").isNull(0)) ? obj.getJSONArray("authors").getJSONObject(0).getString("key") : "No authors";
//						listBook.add(new Book(title, desc, author, date, editeur, edition));
//						if (nbLigne%100==0)
//							System.out.println("Book: "+listBook.size()+" ("+nbLigne+")");
//					}
//				} else {
//					if (nbLigne%1000==0)
//						System.out.println("L: "+nbLigne);
//				}
//			}		
//			sc.close();
//			try {
//				Boolean bo = DBExecutor.execQuery("DELETE t1 FROM book AS t1, book AS t2 WHERE t1.id > t2.id AND t1.title = t2.title");
//			} catch (Exception e) {}
//			return getResponseWithHeaders("true", HttpResponseCode.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Utils.currThread.stop();
//			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);			
//		}
//	}
	
}
