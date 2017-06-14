package me.alexishaldy.rest;

import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.alexishaldy.db.connection.DBExecutor;
import me.alexishaldy.util.Utils;

@Path("/")
public class RestHandler {
	
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
	
	
	
	
	
	
	
	
	
	
	
	
}
