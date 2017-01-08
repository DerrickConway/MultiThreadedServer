package ie.gmit.sw.server;

import java.io.Serializable;
import java.util.Date;
import java.net.*;

public abstract class Request implements Serializable, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Date d;
	private String clientIp;
	private Socket socket;
	
	private String host;
	private int port;
	private String command;
	private String username;
	
	public Request(String clientIp) {
		d = new Date();
		this.clientIp = clientIp;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}