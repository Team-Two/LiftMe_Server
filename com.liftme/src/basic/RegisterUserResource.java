package basic;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.RegisterUser;

@Path("/register")
public class RegisterUserResource {
	
	  // This method is called if XMLis request
	  @GET
	  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	  public RegisterUser getXML() {
		
		SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat dateDateFormat = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strTime = timeDateFormat.format(now);
		String strDate = dateDateFormat.format(now);
		
		RegisterUser ru = new RegisterUser();
	    ru.setName("Mikey");
	    ru.setEmail("pmsorhaindo@gmail.com");
	    ru.setPassword("asdfasdfasdf");
	    ru.setDate(strDate);
	    ru.setTime(strTime);
	    return ru;
	  }
	  
	  // This can be used to test the integration with the browser
	  @GET
	  @Produces({ MediaType.TEXT_XML })
	  public RegisterUser getHTML() {
		
		SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat dateDateFormat = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strTime = timeDateFormat.format(now);
		String strDate = dateDateFormat.format(now);
		
		RegisterUser ru = new RegisterUser();
	    ru.setName("Mikey");
	    ru.setEmail("pmsorhaindo@gmail.com");
	    ru.setPassword("asdfasdfasdf");
	    ru.setDate(strDate);
	    ru.setTime(strTime);
	    return ru;
	  }

}
