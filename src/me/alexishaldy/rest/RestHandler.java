package me.alexishaldy.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.enumerator.HttpResponseCode;
import me.alexishaldy.enumerator.SortType;
import me.alexishaldy.util.Utils;

/**
 * This class is used for all request for RESTFUL-API
 * @author Alexis Haldy
 * @version 0.1
 *
 */
@Path("/")
public class RestHandler {
	
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
				header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE").
				header("Access-Control-Max-Age", "3600").
				header("Access-Control-Allow-Headers", "x-requested-with").build();
	}	
	
	/**
	 * This method is used for adding a book to a library
	 * @param title		The title of book
	 * @param author	The author's name(s) of book
	 * @param date		The date in years of book
	 * @param desc		The description of book
	 * @param edition	The edition number of book
	 * @param editeur	The editor of book
	 * @param lib		ID of the library where book was set
	 * @return			Response with the message and code
	 */
	@POST // Ne pas mettre les {} dans l'url
	@Path("/book/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("date") String date, @FormParam("description") String desc,
			@FormParam("edition") String edition, @FormParam("editeur") String editeur, @FormParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO book(title, author, date, description, edition, editeur, library_id) VALUES (\""+title+"\", \""+author+"\", \""+date+"\", "
					+ "\""+desc+"\", "+ edition+", \""+editeur+"\", "+lib+");");
			if (!b)
				throw new Exception("Paramètres concernant le livre incorrect ou mal entré");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for edit a book by an admin
	 * @param title		The title of book
	 * @param author	The author's name(s) of book
	 * @param date		The date in years of book
	 * @param desc		The description of book
	 * @param edition	The edition number of book
	 * @param editeur	The editor of book
	 * @param lib		ID of the library where book was set
	 * @param id		ID of book
	 * @return			Response with the message and code
	 */
	@POST
	@Path("/book/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("date") String date, @FormParam("description") String desc,
			@FormParam("edition") String edition, @FormParam("editeur") String editeur, @FormParam("library_id") String lib, @FormParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET title = \""+title+"\", author = \""+author+"\", date = \""+date+"\", description = \""+desc+"\", edition = "+edition+", editeur = \""+editeur+"\", library_id = "+lib+""
					+ " WHERE id = "+id);
			if (!b)
				throw new Exception("Un des paramètres est erroné ou le livre n'a pas été trouvé");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for searching book with different options
	 * @param type	Which choose for search book ? (author, desc, title, title_author, title_author_numedition, year)
	 * @param arg	Different args for search book, separate by a ¨
	 * @param lib 	ID of library of book search
	 * @return		Response with the message and code
	 */
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

	/**
	 * This method is used to get a book with his ID
	 * @param id	ID of book
	 * @return		1 book get by his id
	 */
	@GET
	@Path("/book/getid/{id}")
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
	
	/**
	 * this method is used to get all books pages per pages
	 * @param nb_page 	Number page for the list of book from this library
	 * @param lib		ID of library
	 * @return			One page of all books in a library
	 */
	@GET
	@Path("/book/get/{nb_page}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("nb_page") String nb_page, @PathParam("library_id") String lib) {
		try {
			/**
			 * Number of 
			 */
			final int NBBOOKS = 20;
			int nbPage = Integer.parseInt(nb_page);
			String sql = "SELECT * FROM book WHERE library_id = "+lib+" OR "+lib+" = -1 LIMIT "+(NBBOOKS*(nbPage-1))+", "+NBBOOKS;
			Vector<String> list = DBExecutor.selectQuery(sql);
			HashMap<Integer, String> hash = Utils.putInMap("id title author date description edition editor user_id library_id".split(" "));
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method return all users
	 * @return All users
	 */
	@GET
	@Path("/user/get")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getUser() {
		try {
			Vector<String> list = DBExecutor.selectAll("user");
			HashMap<Integer, String> hash = Utils.putInMap("id username name lastname password email tel token level_access".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get an user specific with his ID
	 * @param id	ID of user
	 * @return		1 user
	 */
	@GET
	@Path("/user/get/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getUserById(@PathParam("id") String id) {
		try {
			Vector<String> list = DBExecutor.selectById("user", id);
			HashMap<Integer, String> hash = Utils.putInMap("id username name lastname password email tel token level_access".split(" "));	
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get an user specific with his token
	 * @param token	Active token of a user
	 * @return		The current user which correspond to the token
	 */
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
	
	/**
	 * This method is used to search an user with his username
	 * @param pseudo	Username of user
	 * @return			The current where correspond with the pseudo
	 */
	@POST
	@Path("/user/search")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response searchUser(@FormParam("username") String pseudo) {		
		try {			
			String sql = "SELECT id FROM User WHERE username = '"+pseudo+"'";
			Vector<String> list = DBExecutor.selectQuery(sql);
			if (!list.isEmpty())
				return getResponseWithHeaders(JSONGenerator.getJson("user", list), HttpResponseCode.OK);
			else
				return getResponseWithHeaders("Pseudo inexistant", HttpResponseCode.NOK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to update an admin token with his username and password hash
	 * @param pseudo	Username of user 
	 * @param pass		Password hash of user
	 * @param token		Token of user
	 * @return			Success or not
	 */
	@PUT
	@Path("/user/admin/update/{username}/{password}/{token}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response userAdminExist(@PathParam("username") String pseudo, @PathParam("password") String pass, @PathParam("token") String token) {		
		try {			
			Boolean exist = DBExecutor.execQuery("UPDATE user SET username = \""+pseudo+"\" WHERE username = \""+pseudo+"\"");
			
			Boolean auth = DBExecutor.execQuery("UPDATE user SET username = \""+pseudo+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\"");
			
			Boolean level = DBExecutor.execQuery("UPDATE user SET token = \""+token+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\" AND level_access >= 7");
			
			if (!exist)
				throw new Exception("Compte inexistant (Le pseudo n'existe pas)");
			else if (!auth)
				throw new Exception("Le mot de passe est erroné !");
			else if (!level)
				throw new Exception("Ce compte n'a pas les autorisations necessaire !");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to update a member token with his username and password hash
	 * @param pseudo	Username of user 
	 * @param pass		Password hash of user
	 * @param token		Token of user
	 * @return			Success or not
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	@Path("/user/member/update/{username}/{password}/{token}")	
	public Response userMemberExist(@PathParam("username") String pseudo, @PathParam("password") String pass, @PathParam("token") String token) {		
		try {			
			Boolean exist = DBExecutor.execQuery("UPDATE user SET username = \""+pseudo+"\" WHERE username = \""+pseudo+"\"");			
			Boolean auth = DBExecutor.execQuery("UPDATE user SET token = \""+token+"\" WHERE username = \""+pseudo+"\" AND password = \""+pass+"\"");
			if (!exist)
				throw new Exception("Compte inexistant (Le pseudo n'existe pas)");
			else if (!auth)
				throw new Exception("Le mot de passe est erroné !");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to create a new user
	 * @param username 	Username of user
	 * @param name		Name of user
	 * @param lastname	Lastname of user
	 * @param pass		Password hash of user
	 * @param email		Email of user
	 * @param tel		Phone number of user
	 * @param token		Actual token of user
	 * @return			Success or not
	 */
	@POST	
	@Path("/user/add")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response addUser(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("password") String pass, @FormParam("email") String email,
			@FormParam("tel") String tel, @FormParam("token") String token) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO user(username, name, lastname, password, email, tel, token) SELECT \""+username+"\", \""+name+"\", \""+lastname+"\", "
					+ "\""+pass+"\", \""+email+"\", \""+tel+"\", \""+token+"\" WHERE (SELECT count(*) FROM user WHERE username = \""+username+"\")=0");
			if (!b)
				throw new Exception("Pseudo déjà pris");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to edit an user only email and phone number
	 * @param email Email of user to change
	 * @param tel	Phone number of use to change
	 * @param id	ID of user
	 * @return		Success or not
	 */
	@PUT
	@Path("/user/edit/{email}/{tel}/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response editUser(@PathParam("email") String email, @PathParam("tel") String tel, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\" WHERE id = "+id);
			if (!b)
				throw new Exception("ID non trouvé");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for change password of an user
	 * @param pass	Password hash of user to change 
	 * @param id	ID of user
	 * @return		Success or not
	 */
	@PUT
	@Path("/user/change/{pass}/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response changePassUser(@PathParam("pass") String pass, @PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE user SET password = \""+pass+"\" WHERE id = "+id);
			if (!b)
				throw new Exception("ID non trouvé");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to edit an user with an admin and his password if given
	 * @param username	Username of user to change
	 * @param name		name of user to change
	 * @param lastname	Lastname of user to change
	 * @param pass		Password of user to change
	 * @param level		Level access of user to change (max 6)
	 * @param email		Email of user to change
	 * @param tel		Phone number of user to change
	 * @param id		ID of user
	 * @return			Success or not
	 */
	@POST
	@Path("/user/edit/admin")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response editUserByAdmin(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("password") String pass,
			@FormParam("level_access") String level, @FormParam("email") String email, @FormParam("tel") String tel, @FormParam("id") String id) {
		try {
			String sql = "";
			if (pass.isEmpty()) {
				sql = "UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\","
						+ " lastname = \""+lastname+"\", level_access = "+level+" WHERE id = "+id;
			} else {
				sql = "UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\","
						+ " lastname = \""+lastname+"\", password=\""+Utils.encryptPassword(pass)+"\",level_access = "+level+" WHERE id = "+id;
			}
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\", "
					+ "lastname = \""+lastname+"\", level_access = "+(Integer.parseInt(level)>6 ? "6" : level)+" WHERE id = "+id);
			if (!b)
				throw new Exception("Champs insérés erronés");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to edit an user with a superadmin, he can be superadmin too with this
	 * @param username	Username of user to change
	 * @param name		name of user to change
	 * @param lastname	Lastname of user to change
	 * @param pass		Password of user to change
	 * @param level		Level access of user to change (max 6)
	 * @param email		Email of user to change
	 * @param tel		Phone number of user to change
	 * @param id		ID of user
	 * @return			Success or not
	 */
	@POST
	@Path("/user/edit/superadmin")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response editUserBySuperAdmin(@FormParam("username") String username, @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("password") String pass,
			@FormParam("level_access") String level, @FormParam("email") String email, @FormParam("tel") String tel, @FormParam("id") String id) {
		try {;
			Boolean b = DBExecutor.execQuery("UPDATE user SET email = \""+email+"\", tel = \""+tel+"\", username = \""+username+"\", name = \""+name+"\","
					+ " lastname = \""+lastname+"\", "+(pass.isEmpty() ? "" : "password=\""+Utils.encryptPassword(pass)+"\", ")+"level_access = "+level+" WHERE id = "+id);
			if (!b)
				throw new Exception("Données insérées erronées");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get page per page list of renter
	 * @param nb_page 	Number of page of renter list
	 * @param lib		ID of library for this renter list
	 * @return			Table with renter list
	 */
	@GET
	@Path("/renter/get/{nb_page}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRenter(@PathParam("nb_page") String nb_page, @PathParam("library_id") String lib) {
		try {
			int nbPage = Integer.parseInt(nb_page);
			String sql = "SELECT * FROM book WHERE library_id = "+lib+" OR "+lib+" = -1 AND user_id IS NOT NULL LIMIT "+(100*(nbPage-1))+", 100";
			Vector<String> list = DBExecutor.selectQuery(sql);
			HashMap<Integer, String> hash = Utils.putInMap("id title author date description edition editor user_id library_id".split(" "));
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used by the user to return a book
	 * @param bid 	ID of book
	 * @param uid	ID of user
	 * @param lib	ID of library
	 * @return		Success or not
	 */
	@PUT
	@Path("/user/return/{book_id}/{user_id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response returnBook(@PathParam("book_id") String bid, @PathParam("user_id") String uid, @PathParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("UPDATE book SET user_id = NULL WHERE id = \""+bid+"\" AND library_id = "+lib);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Boolean c = DBExecutor.execQuery("UPDATE renter SET return_date = '"+formater.format(new Date().getTime())+"' WHERE return_date IS NULL AND book_id = "+bid+" AND user_id = "+uid+" AND library_id = "+lib+";");
			if (!b || !c)
				throw new Exception("Données insérées erronées");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for user to get a book
	 * @param bid 	ID of book
	 * @param uid	ID of user
	 * @param lib	ID of library
	 * @return		Success or not
	 */
	@PUT
	@Path("/user/take/{book_id}/{user_id}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response takeBook(@PathParam("book_id") String bid, @PathParam("user_id") String uid, @PathParam("library_id") String lib) {
		try {
			Boolean c = DBExecutor.execQuery("INSERT INTO renter(book_id, user_id, status, library_id) VALUES ("+bid+", "+uid+", "
					+ "3, "+lib+")");
			if (!c)
				throw new Exception("Données insérées erronées");
			else
				return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for the admin to valid a request from a user to get a book
	 * @param rid 	ID of renter
	 * @return		Success or not
	 */
	@PUT
	@Path("/admin/valid/{renter_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response validRenter(@PathParam("renter_id") String rid) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Boolean c = DBExecutor.execQuery("UPDATE renter SET taken_date = '"+formater.format(new Date().getTime())+"', "
					+ "max_return_date = '"+formater.format(new Date().getTime()+(60000*60*24*14))+"', status = 2 WHERE id = "+rid);
			if (!c)
				throw new Exception("Données insérées erronées");
			else
				return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for the user to renew a book his get
	 * @param rid 	ID of renter
	 * @return		Success or not
	 */
	@PUT
	@Path("/renter/renew/{renter_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response renewRenter(@PathParam("renter_id") String rid) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Vector<String> list = DBExecutor.selectQuery("SELECT max_return_date FROM renter WHERE id = "+rid);
			Date d = formater.parse(list.firstElement().substring(0, list.firstElement().length()-2));
			Boolean c = DBExecutor.execQuery("UPDATE renter SET max_return_date = '"+formater.format(d.getTime() + (60000*60*24*7))+"', status = 1 WHERE id = "+rid);
			if (!c)
				throw new Exception("Données insérées erronées");
			else
				return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to cancel a request about to take a book
	 * @param id 	renter ID to delete
	 * @return		Sucess or not
	 */	
	@GET
	@Path("/renter/cancel/{renter_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelRenter(@PathParam("renter_id") String id) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT COUNT(*) FROM renter WHERE id = "+id);
			if (list.size()>0) {
				Boolean b = DBExecutor.execQuery("DELETE FROM renter WHERE id = "+id);
				return getResponseWithHeaders("true", HttpResponseCode.OK);
			} else {
				return getResponseWithHeaders("Cet emprunt n'existe pas !", HttpResponseCode.OK);
			}
			
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for the system to know if the user has a book he must return
	 * @param uid 	ID of user
	 * @return		Success or not
	 */
	@GET
	@Path("/renter/hasalert/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response alertRenter(@PathParam("user_id") String uid) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Vector<String> list = DBExecutor.selectQuery("SELECT true FROM renter WHERE max_return_date<NOW() AND user_id = "+uid);
			if (list.size()>0)
				return getResponseWithHeaders("true", HttpResponseCode.OK);
			else
				throw new Exception("Pas d'alertes");
		} catch (Exception e) {
			e.printStackTrace();
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used for the system to know if the max_return_date of renter ID is old
	 * @param rid 	ID of renter
	 * @return		Success or not
	 */
	@GET
	@Path("/renter/mustreturn/{renter_id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response mustReturn(@PathParam("renter_id") String rid) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Vector<String> list = DBExecutor.selectQuery("SELECT true FROM renter WHERE max_return_date<NOW() AND id = "+rid);
			if (list.size()>0)
				return getResponseWithHeaders("true", HttpResponseCode.OK);
			else
				throw new Exception("Il n'a pas dépassé la date limite");
		} catch (Exception e) {
			e.printStackTrace();
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get the entire list of renters
	 * @return	List with the return date maximum for a book, his book ID and user ID
	 */	
	@GET
	@Path("/renter/getall")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getRenter() {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT max_return_date, book_id, user_id FROM renter WHERE status != 0");
			
			HashMap<Integer, String> hash = Utils.putInMap("max_return_date book_id user_id".split(" "));
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get last renter with an user ID and book ID gave
	 * @param user_id	User ID
	 * @param book_id	Book ID
	 * @return			List of renter obtained by give the user ID and book ID of renter(s)
	 */	
	@GET
	@Path("/renter/user/get/{user_id}/{book_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLastRenterForUserAndBook(@PathParam("user_id") String user_id, @PathParam("book_id") String book_id) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT * FROM renter WHERE user_id = "+user_id+" AND book_id = "+book_id);
			
			HashMap<Integer, String> hash = Utils.putInMap("id book_id user_id taken_date max_return_date return_date status library_id".split(" "));
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get the list of request for a book to valid
	 * @param page	The page number (int)
	 * @param id	The library ID
	 * @return 		Get the list of renter with max_return_date, book_id and user_id from the table
	 */
	@GET
	@Path("/renter/get/tovalid/{page}/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRenterListToValid(@PathParam("page") String page, @PathParam("library_id") String id) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT id, book_id, user_id FROM renter WHERE status = 3 AND library_id = "+id+" LIMIT "+(100*(Integer.parseInt(page)-1))+", 100");
			
			HashMap<Integer, String> hash = Utils.putInMap("id book_id user_id".split(" "));
			
			return getResponseWithHeaders(JSONGenerator.getJsonWithTable(list, hash), HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to verif a token from a member account and return his username
	 * @param	token Token of a member user account
	 * @return	Username of account verified
	 */
	@GET
	@Path("/user/verif/member/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyTokenMember(@PathParam("token") String token) {
		try {
			Vector<String> list = DBExecutor.selectQuery("SELECT username FROM user WHERE token = \""+token+"\"");
			
			HashMap<Integer, String> hash = Utils.putInMap("username".split(" "));	
			
			return getResponseWithHeaders(hash.size()>0 ? JSONGenerator.getJsonWithTable(list, hash) : "", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to verif a token from an admin account and return his username
	 * @param token	Token of an admin user account
	 * @return		Username from the verified account
	 */
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
	
	/**
	 * This method is used to delete an user
	 * @param id	ID of user
	 * @return		Success or not
	 */
	@PUT
	@Path("/user/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM user WHERE id = "+id);
			if (!b)
				throw new Exception("Utilisateur inconnu");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to delete a book
	 * @param id	ID of book
	 * @return		Success or not
	 */
	@PUT
	@Path("/book/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBook(@PathParam("id") String id) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM book WHERE id = "+id+";");
			if (!b)
				throw new Exception("Livre inconnu");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to get all libraries
	 * @return	Table with all libraries (id, name and adress)
	 */
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

	/**
	 * This method is used to get all book in a library which anyone has rent
	 * @param lib	ID of library
	 * @return		Table with all library (title author date description edition editor)
	 */
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
	
	/**
	 * This method is used to edit a library
	 * @param name		Name of library
	 * @param adress	Adress of library
	 * @param lib		ID of library
	 * @return			Success or not
	 */
	@POST
	@Path("/library/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editLib(@FormParam("name") String name, @FormParam("adress") String adress, @FormParam("library_id") String lib) {
		try {
			String sql = "UPDATE library SET name = \""+name+"\", adress = \""+adress+"\"  WHERE id = "+lib;
			Boolean b = DBExecutor.execQuery(sql);
			if (!b)
				throw new Exception("Données insérées erronées");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to delete a library
	 * @param lib	ID of library
	 * @return		Success or not
	 */
	@PUT
	@Path("/library/delete/{library_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteLib(@PathParam("library_id") String lib) {
		try {
			Boolean b = DBExecutor.execQuery("DELETE FROM library WHERE id = "+lib+";");
			if (!b)
				throw new Exception("Bibliothèque inconnue");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
	
	/**
	 * This method is used to add a library
	 * @param name		Name of library
	 * @param adress	Adress of library
	 * @return			Success or not
	 */
	@POST
	@Path("/library/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLib(@FormParam("name") String name, @FormParam("adress") String adress) {
		try {
			Boolean b = DBExecutor.execQuery("INSERT INTO library(name, adress) VALUES (\""+name+"\", \""+adress+"\");");
			if (!b)
				throw new Exception("Impossible d'ajouter la bibliothèque");
			return getResponseWithHeaders("true", HttpResponseCode.OK);
		} catch (Exception e) {
			return getResponseWithHeaders(e.getMessage(), HttpResponseCode.NOK);
		}
	}
}
