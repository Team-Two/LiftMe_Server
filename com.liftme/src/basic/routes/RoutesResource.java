package basic.routes;

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

import dataaccessobjects.RoutesDao;
import model.Route;


public class RoutesResource {

	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String id;
	  public RoutesResource(UriInfo uriInfo, Request request, String id) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.id = id;
	  }
	  
	  //Application integration     
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public Route getUser() {
	    Route route = RoutesDao.instance.getModel().get(id);
	    if(route == null)
	      throw new RuntimeException("Get: Route with " + id +  " not found");
	    return route;
	  }
	  
	  // for the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public Route getUserHTML() {
		Route route = RoutesDao.instance.getModel().get(id);
	    if(route==null)
	      throw new RuntimeException("Get: User with id " + id +  " not found");
	    return route;
	  }
	  
	  @PUT
	  @Consumes(MediaType.APPLICATION_XML)
	  public Response putTodo(JAXBElement<Route> user) {
		Route r = user.getValue();
	    return putAndGetResponse(r);
	  }
	  
	  @DELETE
	  public void deleteUser() {
		Route c = RoutesDao.instance.getModel().remove(id);
	    if(c==null)
	      throw new RuntimeException("Delete: Todo with " + id +  " not found");
	  }
	  
	  private Response putAndGetResponse(Route route) {
	    Response res;
	    if(RoutesDao.instance.getModel().containsKey(route.getNameOfRoute())) {
	      res = Response.noContent().build();
	    } else {
	      res = Response.created(uriInfo.getAbsolutePath()).build();
	    }
	    RoutesDao.instance.getModel().put(route.getNameOfRoute(), route);
	    return res;
	  }
	  
	  

	} 
