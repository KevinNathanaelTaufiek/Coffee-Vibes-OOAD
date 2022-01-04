package connector;

import java.sql.*;

public class Connector {

	private Connection connection;
	private static Connector connector;
	private Statement st;
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	
	private Connector() {
		try {  
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffeevibes", "root", "");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/coffeevibes", "root", "");
            st = connection.createStatement(); 
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database, the system is terminated!");
        	System.exit(0);
        }
	}
	
	public static Connector connect() {
		if(connector == null) {
			connector = new Connector();
		}
		return connector;
	}
	
	
	
	// untuk SELECT
	public ResultSet executeQuery(String query){
		rs=null;
		try {
			rs=st.executeQuery(query);
			rsm=rs.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// untuk INSERT UPDATE DELETE
	public void executeUpdate(String query) {
    	try {
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public PreparedStatement preparedStatement(String query){
		PreparedStatement ps=null;
		try{
			ps=connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		}catch (Exception e){
			e.printStackTrace();
		}
		return ps;
	}

}
