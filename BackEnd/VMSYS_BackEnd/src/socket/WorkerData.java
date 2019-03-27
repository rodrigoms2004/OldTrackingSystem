package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.DataFromModule;
import database.RecordDataIntoDB;

public class WorkerData implements Runnable {

	
	private Socket	clientSocket	= null;
	private String	serverText		= null;
	private String	gps_data		= null;
	private StringBuilder str_info = null; // return infos or errors
	
	
	public WorkerData(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		str_info = new StringBuilder();
	}	// end constructor
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			
			java.util.Date date= new java.util.Date(); // for timestamp uses
			
			
			
			output.write(("Connected " + 
						   this.serverText + " - " + 
						   new Timestamp(date.getTime()) + "").getBytes());
			
			gps_data = getStringFromInputStream(input);
			
			output.close();	
			input.close();
			this.saveDataInDB(gps_data);
			System.out.println("Request processed: " + new Timestamp(date.getTime()));
			
			
		} catch(IOException e) {
			// report exception somewhere
			e.printStackTrace();
		}	// end catch

	}	// end run

	// convert InputStream to String
	// http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
	private static String getStringFromInputStream(InputStream is) {
	 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}	// end while
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	// end catch
			}	// end if
		}	// end finally
		return sb.toString();
	}	// end getStringFromInputStream
	
	private void saveDataInDB(String GPS_Data) {
		System.out.println("Received :" + GPS_Data);
		DataFromModule dfm = new DataFromModule();
		java.util.Date date= new java.util.Date(); // for timestamp uses
		
		String[] GPS_data_array = GPS_Data.split(",");
		
		dfm.setLatitude(Double.parseDouble(GPS_data_array[0]));
		dfm.setLongitude(Double.parseDouble(GPS_data_array[1]));
		dfm.setTimestamp(new Timestamp(date.getTime()));
		dfm.setRSSI(Integer.parseInt(GPS_data_array[2]));

		synchronized(this) {
		// here I will save information in db
		
			// Record data into DB.
			try {
				RecordDataIntoDB rd_DB = new RecordDataIntoDB(dfm);
				rd_DB.recordData();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				str_info.append(e.toString());
			}	// end catch
			catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				str_info.append(e.toString());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				str_info.append(e.toString());
			}	// end catch
		}	// end synchronized
		
		// mount result string
		String result = str_info.toString();
		
		// show data received in console
		System.out.println(result);
		
				
		/*
		for (int i = 0; i < GPS_data_array.length; i++)
			System.out.println(GPS_data_array[i]);
		*/

	}	// end saveDataInDB
}	// end class
