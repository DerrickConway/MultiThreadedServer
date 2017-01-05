package ie.gmit.sw.client;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class Runner {
	
	String ss ; 
	//connecting socket 
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	//parser
	Parser p;
	//objest config file  
	Context ctx;
	//message to server 
	String message = "";
	
	//scanner caling from keyboard 
	UI tab = new UI(); 
	String s = ""; 
	Boolean flag = true;
	
	
	
	
	
	
	
	//Main method 
	public static void main(String args[]) throws Throwable {
		Runner r = new Runner();
		r.run();
	}
	

	public void run() throws Throwable {
		try {
			while (flag) {
				System.out.println("----------MENU---------------");
				System.out.println("1. Connect to Server");
				System.out.println("2. Print File Listing");
				System.out.println("3. Download File");
				System.out.println("4. Quit");
				System.out.println("-----------------------------");
				s = tab.getString();
				
				
				//loop
				while (true) {
					if (s.equals("1")) {
						if (ss == null) {
							System.out.println("CONNECTED ");
							connection();
							
							System.out.println();
						} else {
							
							System.out.println(" You have already connected");
						
						}
						break;

						
						
					} else if (s.equals("2")) {
						if (ss == null) {
							System.out.println("<<ERROR>>");
							System.out.println("You need connect first>");
							System.out.println();
						} else {
							getList();
						}
						break;
					} else if (s.equals("3")) {
						if (ss == null) {
							System.out.println("<<ERROR>>");
							System.out.println("You need connect first>");
							System.out.println();
						} else {
							downloadFile();
						}
						break;
					} else if (s.equals("4")) {
						System.out.println("NOW EXITING");
						flag = false;
						break;
					} else {
						System.out.println("SELECT CORRECT OPTION");
						
						break;
					}
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				// ioException.printStackTrace();
			}
		}
	}

	
	
	
	

	void connection() throws Throwable {
		try {

			// get context object from conf.xml
			p = new Parser(new Context());
			p.init();
			ctx = p.getCtx();
			String ipaddress = ctx.getServer_host();
			
			
			// socket 
			requestSocket = new Socket(ipaddress, 7777);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			System.out.println();
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3. Communicating with the server
			message = (String) in.readObject();
			System.out.println("<<<<< " + message + " >>>>>");
			ss = "ok"; // used to ignore repeat connection

		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			// ioException.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		} catch (IOException ioException) {
			// ioException.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	void getList() throws ClassNotFoundException, IOException {
		sendMessage("list");
		message = (String) in.readObject();
		int sum = Integer.parseInt(message); // get the amount of files
		System.out.println("file list :");
		System.out.println();
		for (int i = 0; i < sum; i++) {
			message = (String) in.readObject();
			System.out.println(message);
		}
		System.out.println();
	}

	
	
	
	
	
	
	
	void downloadFile() throws ClassNotFoundException, IOException {
		sendMessage("download");
		while (true) {
			System.out.println("Please input the fileName you want to download>");
			s = tab.getString();
			sendMessage(s);
			message = (String) in.readObject();
			if (message.equals("ok")) {
				break;
			}
			if (message.equals("error")) {
				System.out.println("==---------NO FILE !!!--------==");
			}
		}
		sendMessage(s);
		receiveFile();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void receiveFile() throws IOException {
		byte[] inputByte = null;
		int length = 0;
		try {

			FileOutputStream fout = new FileOutputStream(new File(ctx.getDownload_dir() + in.readUTF()));
			inputByte = new byte[1024];
			System.out.println("started  ");
		
			while (true) {
				if (in != null) {
					length = in.read(inputByte, 0, inputByte.length);
				}
				if (length == -1) {
					break;
				}
				System.out.println(length);
				fout.write(inputByte, 0, length);
				fout.flush();
			}
			System.out.println("complete download");
			System.out.println("...................");
			fout.close();
			flag = false;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public class UI {
		
		
		public String getString(){
		Scanner tab = new Scanner(System.in);
		return tab.nextLine();
		}
		
		public int getInt(){
		Scanner tab = new Scanner(System.in);
		return tab.nextInt();
		}
		
	}//UI

	

}