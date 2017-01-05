package ie.gmit.sw.client;
import java.util.Scanner;
import java.io.*;

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
