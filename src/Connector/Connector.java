package connector;

import java.sql.*;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Connector {

	private static Connection connection;
	public ResultSet rs;
	public Statement st;
	public ResultSetMetaData rsm;
	
	public static Connection connect() {
		if(connection == null) {
			MysqlDataSource source = new MysqlDataSource();
			source.setServerName("localhost");
			source.setUser("root");
			source.setPassword("");
			source.setDatabaseName("coffeevibes");
			
			try {
				return source.getConnection();
			}catch(SQLException e) {
				return null;
			}
		}
		return connection;
	}
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
	public PreparedStatement preparedStatement(String query){
		PreparedStatement ps=null;
		try{
			ps=connection.prepareStatement(query);
		}catch (Exception e){
			e.printStackTrace();
		}
		return ps;
	}

}
