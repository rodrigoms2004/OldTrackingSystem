package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataFromModule;
import database.RecordDataIntoDB;

/**
 * Servlet implementation class DataServlet
 * 
 * http://localhost:8080/VMSYS_BackEnd/DataServlet
 * http://localhost:8080/VMSYS_BackEnd/DataServlet?latitude=43.2345&longitude=23.5672&RSSI=21
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuilder str_info = null; // return infos or errors
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }	// end Constructor

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// method get only calls doPost
		this.doPost(request, response);
	}	// end doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		str_info = new StringBuilder(); 
		DataFromModule dfm = new DataFromModule();
		java.util.Date date= new java.util.Date(); // for timestamp uses
		dfm.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		dfm.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		dfm.setTimestamp(new Timestamp(date.getTime()));
		dfm.setRSSI(Integer.parseInt(request.getParameter("RSSI")));
		
		String str = ("Received lat = " + dfm.getLatitude()  +
						 " long = "        + dfm.getLongitude() +
						 " timestamp = "   + dfm.getTimestamp() +
						 " RSSI = "        + dfm.getRSSI());
		
		str_info.append(str);
				
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

		// mount result string
		String result = str_info.toString();
		
		// show data received in console
		System.out.println(result);

		// return data to module
		write(response, result); 
	}	// end doPost
	

	private void write(HttpServletResponse response, String result) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print("Result: " + result);
		writer.flush();
		writer.close();
	}	// end write

}	// end class
