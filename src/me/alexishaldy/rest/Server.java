package me.alexishaldy.rest;

import java.io.IOException;
import java.net.URI;

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
 	
    public static void main(String[] args) throws IOException {

    	// Get the host from the properties file, or set a default one
    	String host;
    	try {
    		host = "localhost";
    	} catch (Exception e) {
    		System.err.println("[ERROR] " + e.getMessage() + "\nUsing default host <127.0.0.1>");
    		host = "127.0.0.1";
    	}

    	// Get the port from the properties file, or set a default one if it is not found or not correct
    	int port;    	
    	try {
    		port = 8080;
    	} catch (Exception e) {
    		System.err.println("[ERROR] " + e.getMessage() + "\nUsing a default one <8080>");
    		port = 8080;
    	}

        // Define handler path
        ResourceConfig resourceConfig = new PackagesResourceConfig("main.java.rest");

        // Set the handlers for the REST server
        URI uri = getURI(host, port);
        HttpServer httpServer =  HttpServerFactory.create(uri, resourceConfig);
        
        // Start the server
        System.out.println("Starting server on " + uri.toString() + "... (see application.wadl for further details)");
        httpServer.start();
    }
 
    /**
     * Get the URI for the application
     * @param host	Host where the application is running
     * @param port	Port where the application is listening
     * @return		Returns the URI for the application
     */
    private static URI getURI(String host, int port) {
        return UriBuilder.fromUri("http://" + host + "/DBtoREST").port(port).build();
    }
 
}