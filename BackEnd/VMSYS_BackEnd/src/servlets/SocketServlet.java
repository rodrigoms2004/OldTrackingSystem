package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socket.ThreadPoolServer;

/**
 * Servlet implementation class SocketServlet
 */
@WebServlet("/SocketServlet")
public class SocketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ThreadPoolServer server;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SocketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Creating ThreadPool");
		server = new ThreadPoolServer(5005);
		System.out.println("Starting Server");
		new Thread(server).start();

		/*
		 *http://www.easywayserver.com/blog/java-run-program-automatically-on-tomcat-startup/
		 * */
		
		/*
		try {
		    Thread.sleep(200 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		*/
	}	// end init

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("Stopping Server");
		server.stop();
	}	// end destroy

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}	// end service

}	// end class
