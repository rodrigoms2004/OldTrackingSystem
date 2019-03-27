package socket;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer implements Runnable {
	private final int		numberOfThreads = 10;
	private int				serverPort		= 5005; 	// default port
	private ServerSocket 	serverSocket	= null;
	private boolean			isStopped		= false;
	protected Thread		runningThread	= null;
	// defines thread pools
    private ExecutorService threadPool 		= Executors.newFixedThreadPool(numberOfThreads);
    private String			serverString	= "Thread Pooled Server";
    
	public ThreadPoolServer(int port) {
		this.serverPort = port;
	}	// end constructor ThreadPoolServer

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this) {
			this.runningThread = Thread.currentThread(); // gets current thread
		}	// end synchronized
		
		openServerSocket(); // opens server to receive connections
		
		while(! isStopped()) { // while is not stopped
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped");
					return;
				}	// end if
				throw new RuntimeException(
						"Error accepting client connection", e);
			}	// end catch
			
			this.threadPool.execute(
		             new WorkerData(clientSocket, serverString));
		    
		}	// end while
		System.out.println("Server Stopped");
	}	// end run()
	

	
	public synchronized boolean isStopped() {
		return this.isStopped;
	}	// end isStopped
	
	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}	// end catch
	}	// end stop
	
	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + String.valueOf(this.serverPort), e);
		}	// end catch
	}	// end openServerSocket

}	// end class
