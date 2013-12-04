package dataaccessobjects;

import java.util.HashMap;
import java.util.Map;

import model.Route;

public enum RoutesDao {
  instance;
  
  private Map<String, Route> contentProvider = new HashMap<String, Route>();
  
  private RoutesDao() {
    
    Route r = new Route("Mikey", "111", "222", "333", "444", "Friday, Dec 14, 2013 12:10:56", "Route1");
    //todo.setDescription("llama duck!!!");
    contentProvider.put("Mikey", r);
    r = new Route("Mark", "123","321", "321", "123", "Friday, Dec 14, 2013 12:10:56", "Route2");
    //todo.setDescription("asdfasdfasdfadsfasdfasdfadsf");
    contentProvider.put("Mark", r);
    
  }
  public Map<String, Route> getModel(){
    return contentProvider;
  }
  
} 