package dataaccessobjects;

import java.util.HashMap;
import java.util.Map;

import model.RegisterUser;

public enum RegisterUserDao {
  instance;
  
  private Map<String, RegisterUser> contentProvider = new HashMap<String, RegisterUser>();
  
  private RegisterUserDao() {
    
    RegisterUser ru = new RegisterUser("Mikey", "pmsorhaindo@gmail.com", "asdfasdf");
    //todo.setDescription("llama duck!!!");
    contentProvider.put("Mikey", ru);
    ru = new RegisterUser("Mark", "mark@gmail.com","asdfasdfasdfasdf");
    //todo.setDescription("asdfasdfasdfadsfasdfasdfadsf");
    contentProvider.put("Mark", ru);
    
  }
  public Map<String, RegisterUser> getModel(){
    return contentProvider;
  }
  
} 