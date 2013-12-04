package basic;

import java.io.IOException;
import java.net.URI;
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

import dataaccessobjects.RegisterUserDao;
import model.RegisterUser;

// Will map the resource to the URL users
@Path("/registerusers")
public class RegisterUsers {

	  // Allows to insert contextual objects into the class, 
	  // e.g. ServletContext, Request, Response, UriInfo
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of users to the user in the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public List<RegisterUser> getUsersBrowser() {
	    List<RegisterUser> users = new ArrayList<RegisterUser>();
	    users.addAll(RegisterUserDao.instance.getModel().values());
	    return users; 
	  }
	  
	  // Return the list of users for applications
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public List<RegisterUser> getUsers() {
	    List<RegisterUser> users = new ArrayList<RegisterUser>();
	    users.addAll(RegisterUserDao.instance.getModel().values());
	    return users; 
	  }
	  
	  
	  // retuns the number of users
	  // use http://localhost:8080/com.liftme.user/rest/users/count
	  // to get the total number of records
	  @GET
	  @Path("count")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCount() {
	    int count = RegisterUserDao.instance.getModel().size();
	    return String.valueOf(count);
	  }
	  
	  @POST
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newUser(@FormParam("name") String name,
	      @FormParam("email") String email,
	      @FormParam("password") String password,
	      @Context HttpServletResponse servletResponse) throws IOException {
		  
		  System.out.println("asdf we have a connection!");
		RegisterUser ru = new RegisterUser(name,email,password);
	    //if (description!=null){
	    //  user.setDescription(description);
	    //}
		RegisterUserDao.instance.getModel().put(name, ru);
	    
	    servletResponse.sendRedirect("../registerauser.html");
	  }
	  
	  
	  // Defines that the next path parameter after users is
	  // treated as a parameter and passed to the UserResources
	  // Allows to type http://localhost:8080/com.liftme.user/rest/users/1
	  // 1 will be treaded as parameter user and passed to UserResource
	  @Path("{register}")
	  public UsersResource getUser(@PathParam("register") String id) {
	    return new UsersResource(uriInfo, request, id);
	  }
	  
	} 