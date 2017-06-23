package me.alexishaldy.rest;

import java.util.HashMap;
import java.util.Vector;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.enumerator.SortType;
import me.alexishaldy.util.Utils;

@Path("/")
public class RestHandler {

	enum BookEnum {
		title(0),
		author(1),
		date(2),
		description(3),
		edition(4),
		editor(5);
		private int key;
		private BookEnum(int key) {this.key = key;};
		public int getKey() { return key;};
	};
	
	final String CURR_DIR = this.getClass().getClassLoader().getResource("").getPath();
	
	private enum HttpResponseCode {
		OK(200), 
		NOK(400);
		
		private int code;
		
		/**
		 * Class Constructor
		 * @param code	Http status value
		 */
		HttpResponseCode(int code) {
			this.code = code;
		}
		
		/**
		 * Get the associated status
		 * @return Http status
		 */
		public int getValue() {
			return this.code;
		}
	}
	
	/**
	 * This method defines a response with CORS (cross-origin resource sharing) enabled
	 * (in order to avoid problems with requests through swagger).
	 * @param content	Body content
	 * @param status	Response status
	 * @return			Response including the headers to enable CORS and the body.
	 */
	private static Response getResponseWithHeaders(String content, HttpResponseCode status) {
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
	public Response getSwaggerJson() {
		try {
			final String fileName = CURR_DIR + Utils.SEP + ".." + Utils.SEP + ".." + Utils.SEP + Utils.SWAGGER_FILE;
			return getResponseWithHeaders(Utils.readFile(fileName), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	
	@SuppressWarnings("static-method")
	@GET
	@Path("/alldbs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDBsJSON() {
		try {
			Vector<String> dbs = DBExecutor.getDBs();
			return getResponseWithHeaders(JSONGenerator.getJson("databases", dbs), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@GET
	@Path("/alltables/{dbName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTablesJson(@PathParam("dbName") String dbName) {
		try {
			Vector<String> tables = DBExecutor.getTables(dbName);
			return getResponseWithHeaders(JSONGenerator.getJson("tables", tables), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@POST	
	@Path("/book/add/{title}/{author}/{date}/{description}/{edition}/{editeur}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(@PathParam("title") String title, @PathParam("author") String author, @PathParam("date") String date, @PathParam("description") String desc,
			@PathParam("edition") String edition, @PathParam("editeur") String editeur, @PathParam("library_id") String lib) {
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
	
	@SuppressWarnings("static-method")
	@PUT
	@Path("/book/edit/{title}/{author}/{date}/{description}/{edition}/{editeur}/{library_id}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBook(@PathParam("title") String title, @PathParam("author") String author, @PathParam("date") String date, @PathParam("description") String desc,
			@PathParam("edition") String edition, @PathParam("editeur") String editeur, @PathParam("library_id") String lib, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET title = \""+title+"\", author = \""+author+"\", date = \""+date+"\", description = \""+desc+"\", edition = "+edition+", editeur = \""+editeur+"\", library_id = "+lib+")"
					+ " WHERE library_id = "+lib+" AND id = ");
			if (!b)
				throw new Exception("Update failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@POST
	@Path("/book/search/{type}/{arg}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBook(@PathParam("type") String type, @PathParam("arg") String arg, @PathParam("library_id") String lib) {
		String[] args = arg.split("¨");
		
		try {
			String sql = "";
			switch (SortType.valueOf(type)) {
			case Number:
				break;
			case author:
				sql = "SELECT id FROM Book WHERE author = '"+args[0]+"'"+" AND library_id = "+lib;
				break;
			case desc:
				sql = "SELECT id FROM Book WHERE description = '"+args[0]+"'"+" AND library_id = "+lib;
				break;
			case title:
				sql = "SELECT id FROM Book WHERE title = '"+args[0]+"'"+" AND library_id = "+lib;
				break;
			case title_author:
				sql = "SELECT id FROM Book WHERE title = '"+args[0]+"' AND author = '"+args[1]+"'"+" AND library_id = "+lib;
				break;
			case title_author_numedition:
				sql = "SELECT id FROM Book WHERE title = '"+args[0]+"' AND author = '"+args[1]+"' AND edition = "+args[2]+" AND library_id = "+lib;
				break;
			case year:
				sql = "SELECT id FROM Book WHERE date = "+args[0]+" AND library_id = "+lib;
				break;				
			}
			Vector<String> list = DBExecutor.selectQuery(sql);
			HashMap<Integer, String> hash = Utils.putInMap("id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}

	@SuppressWarnings("static-method")
	@GET
	@Path("/book/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookById(@PathParam("id") String id) {
		try {
			Vector<String> list = DBExecutor.selectById("book", id);
			HashMap<Integer, String> hash = Utils.putInMap("id".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
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
	
	@SuppressWarnings("static-method")
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
			HashMap<Integer, String> hash = Utils.putInMap("id".split(" "));	
			
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
	@Path("/user/search/{username}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUser(@PathParam("username") String pseudo, @PathParam("library_id") String lib) {		
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
	public Response userExist(@PathParam("username") String pseudo, @PathParam("password") String pass, @PathParam("token") String token) {		
		try {			
			Boolean b = DBExecutor.execQuery("UPDATE user SET SELECT token = \""+token+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\"");
			if (!b)
				throw new Exception("Compte inexistant");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@POST	
	@Path("/user/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("password") String pass, @FormParam("email") String email,
			@FormParam("tel") String tel, @FormParam("token") String token, @FormParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO user(username, name, lastname, password, email, tel, token, library_id) SELECT \""+username+"\", \""+name+"\", \""+lastname+"\", "
					+ "\""+pass+"\", \""+email+"\", \""+tel+"\", \""+token+"\", "+lib+" WHERE (SELECT count(*) FROM user WHERE username = \""+username+"\")=0");
			if (!b)
				throw new Exception("Pseudo déjà pris");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
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
	
	@SuppressWarnings("static-method")
	@DELETE
	@Path("/user/delete/{id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("library_id") String lib, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE user WHERE id = "+id+" AND library_id = "+lib+";");
			if (!b)
				throw new Exception("Delete failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@DELETE
	@Path("/book/delete/{id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBook(@PathParam("library_id") String lib, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE book WHERE id = "+id+" AND library_id = "+lib+";");
			if (!b)
				throw new Exception("Delete failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
	@POST
	@Path("/library/add/{name}/{adress}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLib(@PathParam("name") String name, @PathParam("adress") String adress) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO library(name, adress) VALUES (\""+name+"\", \""+adress+"\");");
			if (!b)
				throw new Exception("Insert failed");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	@SuppressWarnings("static-method")
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

	@SuppressWarnings("static-method")
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
	
	
}
