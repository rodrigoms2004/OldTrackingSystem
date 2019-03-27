package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class RecordDataIntoDB {

	private Connection conn = null;
	private PreparedStatement preparedStmt = null;
	private DataFromModule dfm;
	private String db_name = "db_vmsys";
	private String db_user = "vmsys";
	private String db_pass = "vmsyspass";
	private String sql = "";
	
	public RecordDataIntoDB() {} // end simple constructor 
	
	public RecordDataIntoDB(DataFromModule dfm) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		this.dfm = dfm;
		DB_Connector db_Connector = new DB_Connector(this.db_name, this.db_user, this.db_pass);
		this.conn = db_Connector.getConn();		
	}	// end constructor with dfm parameter
	
	
	// got from http://alvinalexander.com/java/java-mysql-insert-example-preparedstatement
	public void recordData() throws SQLException {
		sql = "INSERT INTO monitor(latitude, longitude, timestp, RSSI) VALUES (?, ? , ?, ?)";
		
		preparedStmt = this.conn.prepareStatement(sql);
		preparedStmt.setDouble(1, dfm.getLatitude());
		preparedStmt.setDouble(2, dfm.getLongitude());
		preparedStmt.setTimestamp(3, dfm.getTimestamp());
		preparedStmt.setInt(4, dfm.getRSSI());
		
		preparedStmt.execute();
					
		try {
			conn.close();
			preparedStmt.close();	
		} catch(Exception e) {
			e.printStackTrace();
		}	// end catch

	}	// end recordData
}	// end class
