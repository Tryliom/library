package me.alexishaldy.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import me.alexishaldy.util.Utils;
 
/**
 * This is the standalone service, i.e. in order to run the service service from the console.
 * It creates a http server in the host and port specified in the properties file, or sets
 * a default host and/or port if they are not found or are not properly set.
 * @author Ronald T. Fernandez
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class Server {
	private static HttpServer hs;
 	/**
 	 * This method is used to open the webserver for all
 	 * @param args Argument
 	 * @exception	Exception	Exception if the adress is already used
 	 */
    public static void main(String[] args) throws Exception {
    	
		Properties pro = Utils.getDBProperties("." + System.getProperty("file.separator"));
    	
    	// Get the host from the properties file, or set a default one
    	String host =  pro.getProperty("host");

    	// Get the port from the properties file, or set a default one if it is not found or not correct
    	int port = Utils.getInt(pro.getProperty("port"), 6080);    	

        // Define handler path
        ResourceConfig resourceConfig = new PackagesResourceConfig("me.alexishaldy.rest");
        final Map<String, Object> config = new HashMap<String, Object>();
        config.put("com.sun.jersey.api.json.POJOMappingFeature", true);
        resourceConfig.setPropertiesAndFeatures(config);

        // Set the handlers for the REST server
        URI uri = getURI(host, port);
        hs =  HttpServerFactory.create(uri, resourceConfig);
        
        // Start the server
        //System.out.println("Starting server on " + uri.toString() + "... (see application.wadl for further details)");
        hs.start();
    }
 
    /**
     * Get the URI for the application
     * @param host	Host where the application is running
     * @param port	Port where the application is listening
     * @return		Returns the URI for the application
     */
    private static URI getURI(String host, int port) {
        return UriBuilder.fromUri("http://" + host + "/").port(port).build();
    }
    
    /**
     * This method is used to stop the webserver
     */
    public static void stopServer() {
    	if (hs!=null) {
    		hs.stop(0);
    		hs = null;
    	}    	
    }
 
}