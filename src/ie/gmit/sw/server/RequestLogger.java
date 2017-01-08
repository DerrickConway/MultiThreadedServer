package ie.gmit.sw.server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.server.Request;

// TODO: Auto-generated Javadoc
/**
 * The Class ReguestLogger.
 */
public class RequestLogger implements Runnable {
	
	/** The fw. */
	private FileWriter fw;
	
	/** The q. */
	@SuppressWarnings("rawtypes")
	private BlockingQueue q;
	 
	
	/**
	 * Instantiates a new reguest logger.
	 *
	 * @param q the q
	 */
	@SuppressWarnings("rawtypes")
	public RequestLogger(BlockingQueue q) {
		
		this.q = q;
		
		try {
			fw = new FileWriter(new File("log.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(true){
			try {
				Request r = (Request) q.take();
				fw.write(r.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
 

}