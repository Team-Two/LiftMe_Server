package basic;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dataaccessobjects.RegisterUserDao;
import model.RegisterUser;


public class UsersResource {

	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String id;
	  public UsersResource(UriInfo uriInfo, Request request, String id) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.id = id;
	  }
	  
	  //Application integration     
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public RegisterUser getUser() {
	    RegisterUser user = RegisterUserDao.instance.getModel().get(id);
	    if(user == null)
	      throw new RuntimeException("Get: User with " + id +  " not found");
	    return user;
	  }
	  
	  // for the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public RegisterUser getUserHTML() {
		RegisterUser user = RegisterUserDao.instance.getModel().get(id);
	    if(user==null)
	      throw new RuntimeException("Get: User with id " + id +  " not found");
	    return user;
	  }
	  
	  @PUT
	  @Consumes(MediaType.APPLICATION_XML)
	  public Response putTodo(JAXBElement<RegisterUser> user) {
		RegisterUser c = user.getValue();
	    return putAndGetResponse(c);
	  }
	  
	  @DELETE
	  public void deleteUser() {
		RegisterUser c = RegisterUserDao.instance.getModel().remove(id);
	    if(c==null)
	      throw new RuntimeException("Delete: Todo with " + id +  " not found");
	  }
	  
	  private Response putAndGetResponse(RegisterUser user) {
	    Response res;
	    if(RegisterUserDao.instance.getModel().containsKey(user.getName())) {
	      res = Response.noContent().build();
	    } else {
	      res = Response.created(uriInfo.getAbsolutePath()).build();
	    }
	    RegisterUserDao.instance.getModel().put(user.getName(), user);
	    return res;
	  }

	} 
