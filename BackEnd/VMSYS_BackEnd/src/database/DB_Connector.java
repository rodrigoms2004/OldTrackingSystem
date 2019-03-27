package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {

	//private String classNameDriver = "com.mysql.jdbc.Driver";
	private String jDriver = "jdbc:mysql://";
	//private String serverURL_IP = "localhost";
	private String serverURL_IP = "54.207.109.49";
	private int serverPort = 3306;
	private String databaseName;
	private String username;
	private String password;
	private String URL = null;
	private Connection conn = null;

	public DB_Connector(String databasename, String username, String password) {
		this.databaseName = databasename;
		this.username = username;
		this.password = password;
	}	// end constructor
	
	public Connection getConn() throws SQLException {
		this.URL = this.jDriver + this.serverURL_IP + ":" + 
				   this.serverPort + "/" + 
	               this.databaseName +
				   "?user=" + this.username +
				   "&password=" + this.password;
	
		//Class.forName(this.classNameDriver).newInstance(); // old
		DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
		conn = DriverManager.getConnection(URL);
		
		if(conn != null)
			return conn;
		else
			return null;
	}	// end getConn
	
	public String getServerURL_IP() {
		return serverURL_IP;
	}

	public void setServerURL_IP(String serverURL_IP) {
		this.serverURL_IP = serverURL_IP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	
	
}	// end class
