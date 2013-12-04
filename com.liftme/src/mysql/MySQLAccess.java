package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MySQLAccess {
	// MySQL root credentials = root:password port:3306 servicename:MySQL56
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public MySQLAccess() {
		// This will load the MySQL driver, each DB has its own driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup the connection with the DB
		try {
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/LiftDatabase?"
							+ "user=root&password=password");

			statement = connect.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createTables() throws SQLException {
		
		// if this returns 0 rows don't do the following show tables like 'comments';
		ResultSet showTablesResult = statement.executeQuery("show tables like 'users'");

		// Messy but seems to be the only way to get number of rows in a ResultSet without processing each.
		showTablesResult.last();
		int x = showTablesResult.getRow(); // to get the total number of rows
		showTablesResult.beforeFirst();
		System.out.println("Result set provided: " + x + " rows.");
		if(x <= 0)
		{
			System.out.println("Creating database..");
			statement = connect.createStatement();
			String loginTableCreateCommand = "CREATE TABLE USERS (PK_USER INT NOT NULL AUTO_INCREMENT,"
					+ "DEVICE_ID_STRING VARCHAR(64),"
					+ "NAME VARCHAR(100) NOT NULL,"
					+ "EMAIL VARCHAR(100),"
					+ "PASSWORD VARCHAR(100) NOT NULL, "
					+ "PRIMARY KEY (PK_USER))";
			
			String ratingsTableCreateCommand = "CREATE TABLE REQUESTS (PK_REQUEST INT NOT NULL AUTO_INCREMENT,"
					+ "FK_USER INTEGER NOT NULL ,"
					+ "FK_ROUTE INTEGER NOT NULL,"
					+ "RATING INTEGER,"
					+ "REVIEW VARCHAR(1000),"
					+ "TYPE_OF_USER INTEGER,"
					+ "DATE DATETIME NOT NULL,"
					+ "CONFIRMED BOOLEAN NOT NULL,"
					+ "FOREIGN KEY (FK_ROUTE) REFERENCES ROUTES (PK_ROUTE),"
					+ "FOREIGN KEY (FK_USER) REFERENCES USERS(PK_USER),"
					+ "PRIMARY KEY (PK_REQUEST))";
			
			String routesTableCreateCommand = "CREATE TABLE ROUTES (PK_ROUTE INT NOT NULL AUTO_INCREMENT,"
					+ "FK_USER INTEGER NOT NULL,"
					+ "START_LAT DOUBLE NOT NULL,"
					+ "START_LONG DOUBLE NOT NULL,"
					+ "END_LAT DOUBLE NOT NULL,"
					+ "END_LONG DOUBLE NOT NULL,"
					+ "ROUTE_NAME VARCHAR (40),"
					+ "ACTIVE BOOLEAN NOT NULL,"
					+ "DEPARTURE BOOLEAN NOT NULL,"
					+ "START_DATETIME DATETIME,"
					+ "FOREIGN KEY (FK_USER) REFERENCES USERS(PK_USER)," 
					+ "PRIMARY KEY (PK_ROUTE))";
			
			String friendsTableCreateCommand = "CREATE TABLE FRIENDS(PK_FRIEND INT NOT NULL AUTO_INCREMENT,"
					+ "FK_USER1 INTEGER NOT NULL,"
					+ "FK_USER2 INTEGER NOT NULL,"
					+ "FOREIGN KEY (FK_USER1) REFERENCES USERS(PK_USER)," 
					+ "FOREIGN KEY (FK_USER2) REFERENCES USERS(PK_USER),"
					+ "PRIMARY KEY (PK_FRIEND))";
			
			statement.executeUpdate(loginTableCreateCommand);
			statement.executeUpdate(routesTableCreateCommand);
			statement.executeUpdate(ratingsTableCreateCommand);
			statement.executeUpdate(friendsTableCreateCommand);
		}
		else
		{
			//printTableContents();
			System.out.println("Table already created.");
		}
		
	}

	//|Users| input
	  public boolean UserInput(String name, String email, String password)
	  {
		 try {
			  //check for rows with same name
			  preparedStatement = connect
						.prepareStatement("SELECT * FROM USERS WHERE NAME = ?");
			  preparedStatement.setString(1, name);
			  
			  ResultSet showTablesResult = preparedStatement.executeQuery();
			  showTablesResult.last();
			  int x = showTablesResult.getRow();
			  
			  if(x <= 0)
			  {
				  //input data
				  preparedStatement = connect
							.prepareStatement("INSERT INTO USERS(PK_USER,NAME,EMAIL,PASSWORD)   "
									+ "VALUES (default, ?, ?, ? )");
				  
				  preparedStatement.setString(1, name);
				  preparedStatement.setString(2, email);
				  preparedStatement.setString(3, password);	  
				  preparedStatement.executeUpdate();
				  
				  return true;
			  }
			  else
			  {
				  return false;
			  }
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	  	  
	  } 
	
	//|Users| delete
	  public boolean UserDeleteByName(String name)
	  {
		  try {
			preparedStatement = connect
						.prepareStatement("DELETE FROM USERS WHERE NAME = ?");
			preparedStatement.setString(1, name);
			
			int rowEffected = preparedStatement.executeUpdate();
			
			//check the effect
			if (rowEffected ==0)
				return false;
			else
				return true;			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		  
		  
	  }
	  
	//|Users| alter
	  public boolean UserUpdate(int PK, String email, String password)
	  {
		  try {
			preparedStatement = connect
						.prepareStatement("UPDATE USERS SET EMAIL = ?, PASSWORD = ? "
								+" WHERE PK_USER = ?");
			
			//parameters
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			//name for row to alter!!!
			preparedStatement.setInt(3, PK);
			
			int rowEffected = preparedStatement.executeUpdate();
			
			//check the effect
			if (rowEffected ==0)
				return false;
			else
				return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	  }
	  
	//|Users| select
	  public ArrayList<String[]> UserSelect(String name)
	  {
		  
		  try {
			  
			 preparedStatement = connect
						.prepareStatement("SELECT * FROM USERS WHERE NAME = ?");
			
			 preparedStatement.setString(1, name);
			 
			 ResultSet showTablesResult = preparedStatement.executeQuery();
			 
			 //create array for results
			 ArrayList<String[]> resultsStrings = new ArrayList<String[]>();
			 
			 //write results to array
			 String[] stringRow = new String[4];
			 
			 while (showTablesResult.next()) 
			 {
				 stringRow[0] = showTablesResult.getString("PK_USER");
				 stringRow[1] = showTablesResult.getString("NAME");	 
				 stringRow[2] = showTablesResult.getString("EMAIL");
				 stringRow[3] = showTablesResult.getString("PASSWORD");
				 
				 resultsStrings.add(stringRow);
			 }
			 
			 return resultsStrings;
			 
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		 
	  }
	
	//|Routes| input
	  public boolean RoutesInput(int PKUser, boolean active, double  START_LAT, double START_LONG, 
			  double END_LAT,double END_LONG, String ROUTE_NAME, java.sql.Timestamp START_DATETIME)
	  {
		  try {
			  //check for rows with same name
			  preparedStatement = connect
						.prepareStatement("SELECT * FROM ROUTES WHERE ROUTE_NAME = ?");
			  preparedStatement.setString(1, ROUTE_NAME);
			  
			  ResultSet showTablesResult = preparedStatement.executeQuery();
			  showTablesResult.last();
			  int x = showTablesResult.getRow();
			  
			  if(x <= 0)
			  {
				  //input data
				  preparedStatement = connect
					.prepareStatement("INSERT INTO ROUTES(PK_ROUTE, ACTIVE, " 
									 +"START_LAT, START_LONG, END_LAT, END_LONG, ROUTE_NAME, START_DATETIME,FK_USER) "
									 +"VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)");
				  
				  preparedStatement.setBoolean(1, active);
				  preparedStatement.setDouble(2, START_LAT);
				  preparedStatement.setDouble(3, START_LONG);
				  preparedStatement.setDouble(4, END_LAT);
				  preparedStatement.setDouble(5, END_LONG);
				  preparedStatement.setString(6, ROUTE_NAME);
				  preparedStatement.setTimestamp(7, START_DATETIME);
				  preparedStatement.setInt(8, PKUser);
				  
				  preparedStatement.executeUpdate();
				  
				  return true;
			  }
			  else
			  {
				  return false;
			  }
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	  	  
	  }
	
	//|Routes| delete
	  public boolean RoutesDeleteByName(String routeName)
	  { 
		  try {
				preparedStatement = connect
							.prepareStatement("DELETE FROM ROUTES WHERE ROUTE_NAME = ?");
				preparedStatement.setString(1, routeName);
				
				int rowEffected = preparedStatement.executeUpdate();
				
				//check the effect
				if (rowEffected ==0)
					return false;
				else
					return true;			
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
	  }
	  
	//|Routes| select
	  public ArrayList<String[]> RoutesSelectUserPK(int userPK, boolean active)
	  {
		  
		  try {
			  
			 preparedStatement = connect
						.prepareStatement("SELECT * FROM ROUTES WHERE (FK_USER = ?)AND(ACTIVE = ?)");
			
			 preparedStatement.setInt(1, userPK);
			 preparedStatement.setBoolean(2, active);
			 
			 ResultSet showTablesResult = preparedStatement.executeQuery();
			 
			 //create array for results
			 ArrayList<String[]> resultsStrings = new ArrayList<String[]>();
			 
			 //write results to array
			 String[] stringRow;
			 
			 while (showTablesResult.next()) 
			 {
				 stringRow = new String[9];
				 stringRow[0] = showTablesResult.getString("PK_ROUTE");
				 stringRow[1] = showTablesResult.getString("FK_USER");
				 stringRow[2] = showTablesResult.getString("START_LAT");
				 stringRow[3] = showTablesResult.getString("START_LONG");
				 stringRow[4] = showTablesResult.getString("END_LAT");
				 stringRow[5] = showTablesResult.getString("END_LONG");
				 stringRow[6] = showTablesResult.getString("ROUTE_NAME");
				 stringRow[7] = showTablesResult.getString("ACTIVE");
				 stringRow[8] = showTablesResult.getString("START_DATETIME");
				 
				 resultsStrings.add(stringRow);
			 }
			 
			 return resultsStrings;
			 
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		  
}
	
	//|Routes| user for the PK of route
	  
	//|REQUESTS| input
	  public boolean REQUESTSInput(int FK_USER, int FK_ROUTE, int RATING, String REVIEW, int TYPE_OF_USER, 
			  				     java.sql.Timestamp DATE)
	  {
		  
		  try {
			  //check for rows with same name
			 
				  //input data
				  preparedStatement = connect
					.prepareStatement("INSERT INTO REQUESTS(PK_REQUEST, FK_USER, FK_ROUTE, " 
									 +"RATING, REVIEW, TYPE_OF_USER, DATE, CONFIRMED) "
									 +"VALUES (default, ?, ?, ?, ?, ?, ?, ?)");
				  
				  preparedStatement.setInt(1, FK_USER);
				  preparedStatement.setInt(2, FK_ROUTE);
				  preparedStatement.setInt(3, RATING);
				  preparedStatement.setString(4, REVIEW);
				  preparedStatement.setInt(5, TYPE_OF_USER);
				  preparedStatement.setTimestamp(6, DATE);
				  preparedStatement.setBoolean(7, false);
				  
				  preparedStatement.executeUpdate();
				  
				  return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	  	  
	  }
	  
	//|REQUESTS| delete
	  public boolean REQUESTSDelete(int PK_REQUEST)
	  {
		  try {
				preparedStatement = connect
							.prepareStatement("DELETE FROM REQUESTS WHERE PK_REQUEST = ?");
				
				preparedStatement.setInt(1, PK_REQUEST);
				
				int rowEffected = preparedStatement.executeUpdate();
				
				//check the effect
				if (rowEffected ==0)
					return false;
				else
					return true;			
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
		  
	  }
	  
	//|REQUESTS| select
	  public ArrayList<String[]> REQUESTSSelectForRoute(int FK_ROUTE)
	  {
		  try {
			  
				 preparedStatement = connect
							.prepareStatement("SELECT * FROM REQUESTS WHERE (FK_ROUTE = ?)");
				
				 preparedStatement.setInt(1, FK_ROUTE);
				 
				 ResultSet showTablesResult = preparedStatement.executeQuery();
				 
				 //create array for results
				 ArrayList<String[]> resultsStrings = new ArrayList<String[]>();
				 
				 //write results to array
				 String[] stringRow;
				 
				 while (showTablesResult.next()) 
				 {
					 stringRow = new String[7];
					 stringRow[0] = showTablesResult.getString("PK_REQUEST");
					 stringRow[1] = showTablesResult.getString("FK_USER");
					 stringRow[2] = showTablesResult.getString("FK_ROUTE");
					 stringRow[3] = showTablesResult.getString("RATING");
					 stringRow[4] = showTablesResult.getString("REVIEW");
					 stringRow[5] = showTablesResult.getString("TYPE_OF_USER");
					 stringRow[6] = showTablesResult.getString("DATE");
					 
					 resultsStrings.add(stringRow);
				 }
				 
				 return resultsStrings;
				 
			} catch (SQLException e) {
				
				e.printStackTrace();
				return null;
			}
	  }
	
	  //|REQUESTS| alter
	  
	  //|FRIENDS| input
	  public boolean FRIENDSInput(int FK_USER1,int FK_USER2)
	  {
		  try {
			  //check for rows with same name
			  preparedStatement = connect
						.prepareStatement("SELECT * FROM FRIENDS WHERE ((FK_USER1 = ?)AND(FK_USER2 = ?))");
			  preparedStatement.setInt(1, FK_USER1);
			  preparedStatement.setInt(2, FK_USER2);
			  
			  ResultSet showTablesResult = preparedStatement.executeQuery();
			  showTablesResult.last();
			  int x = showTablesResult.getRow();
			  
			  if(x <= 0)
			  {
				  //input data
				  preparedStatement = connect
							.prepareStatement("INSERT INTO FRIENDS(PK_FRIEND, FK_USER1, FK_USER2)   "
									+ "VALUES (default, ?, ?)");
				  
				  preparedStatement.setInt(1, FK_USER1);
				  preparedStatement.setInt(2, FK_USER2);  
				  preparedStatement.executeUpdate();
				  
				  return true;
			  }
			  else
			  {
				  return false;
			  }
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	  	  
	  }
	  
	  public boolean FRIENDSDeleteByPk(int PK_FRIEND)
	  { 
		  try {
				preparedStatement = connect
							.prepareStatement("DELETE FROM FRIENDS WHERE PK_FRIEND = ?");
				preparedStatement.setInt(1, PK_FRIEND);
				
				int rowEffected = preparedStatement.executeUpdate();
				
				//check the effect
				if (rowEffected ==0)
					return false;
				else
					return true;			
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
	  }

	// You need to close the resultSet
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void performAccess() {
		// TODO Auto-generated method stub

	}

}
