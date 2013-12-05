package basic.routes;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dataaccessobjects.RoutesDao;
import model.Route;
import mysql.MySQLAccess;

// Will map the resource to the URL users
@Path("/manipulateroutes")
public class ManipulateRoutes {

	  // Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of users to the user in the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public List<Route> getRoutesBrowser() {
	    List<Route> routes = new ArrayList<Route>();

	    MySQLAccess msa = new MySQLAccess();
	    try {
			msa.createTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ArrayList<String[]> dbroutes = msa.RoutesSelectUserPK(1, true);
	    
	    
	    for(int i = 0; i<dbroutes.size();i++)
	    {
	    	String[] routeInfo = dbroutes.get(i);
	    	Route r = new Route(routeInfo[1],routeInfo[2],routeInfo[3],routeInfo[4],routeInfo[5],routeInfo[8],routeInfo[6]);
	    	routes.add(r);
	    }
	    
	    // no longer depending on hash table
	    //routes.addAll(RoutesDao.instance.getModel().values());
	    
	    return routes;
	  }
	  
	  // Return the list of users for applications
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public List<Route> getRoutes() {
	    List<Route> routes = new ArrayList<Route>();
	    MySQLAccess msa = new MySQLAccess();
	    try {
			msa.createTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ArrayList<String[]> dbroutes = msa.RoutesSelectUserPK(1, true);
	    
	    
	    for(int i = 0; i<dbroutes.size();i++)
	    {
	    	String[] routeInfo = dbroutes.get(i);
	    	Route r = new Route(routeInfo[1],routeInfo[2],routeInfo[3],routeInfo[4],routeInfo[5],routeInfo[8],routeInfo[6]);
	    	routes.add(r);
	    }
	    
	    // no longer depending on hash table
	    //routes.addAll(RoutesDao.instance.getModel().values());
	    
	    return routes; 
	  }
	  
	  
	  // retuns the number of users
	  // use http://localhost:8080/com.liftme.user/rest/manipulateroutes/count
	  // to get the total number of records
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = RoutesDao.instance.getModel().size();
	    return String.valueOf(count);
	  }
	  
	  @POST
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newUser(@FormParam("userID") String userID,
	      @FormParam("startLat") String startLat,
	      @FormParam("startLong") String startLong,
	      @FormParam("endLat") String endLat,
	      @FormParam("endLong") String endLong, // 
	      @FormParam("timeOfStart") String timeOfStart, //SQL TIMESTAMP
	      @FormParam("nameOfRoute") String nameOfRoute,
	      @Context HttpServletResponse servletResponse) throws IOException {
		  
		  Timestamp timeStampedTime = null;
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		  try {
			timeStampedTime = new Timestamp(sdf.parse(timeOfStart).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		System.out.println("We have a connection!");
		
		double dStartLat = Double.valueOf(startLat);
		double dStartLong = Double.valueOf(startLong);
		double dEndLat = Double.valueOf(endLat);
		double dEndLong = Double.valueOf(endLong);

		MySQLAccess msa = new MySQLAccess();
		msa.RoutesInput(1, true, dStartLat, dStartLong, dEndLat, dEndLong, nameOfRoute, timeStampedTime);
		
	    servletResponse.sendRedirect("../addaroute.html");
	    
	  }	  
	  
	  // Defines that the next path parameter after users is
	  // treated as a parameter and passed to the UserResources
	  // Allows to type http://localhost:8080/com.liftme.user/rest/manipulateroutes/route/1
	  // 1 will be treaded as parameter user and passed to UserResource
	  @Path("{route}")
	  public RoutesResource getUser(@PathParam("route") String id) {
	    return new RoutesResource(uriInfo, request, id);
	  }
	  
	} 