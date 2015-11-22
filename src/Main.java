import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
public class Main{
    
    void run()
    {

        Socket client = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        
        try{
            client = new Socket(JOptionPane.showInputDialog("Podaj adres serwera"), 9666);
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());
            
            try {
				System.out.println("server: " + (String)input.readObject());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
            
            sendMessage(genNumber(true), output);
            sendMessage(genNumber(false), output);
            
            try {
            	String message = (String)input.readObject();
				System.out.println("server: " + message);
				JOptionPane.showMessageDialog(null, message);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
            
            sendMessage("bye", output);
            
            try {
				System.out.println("server: " + (String)input.readObject());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
            
            
        }
        catch(UnknownHostException unknownHost){
            unknownHost.printStackTrace();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            try{
                input.close();
                output.close();
                client.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    
    void sendMessage(String message, ObjectOutputStream out)
    {
        try{
            out.writeObject(message);
            out.flush();
            System.out.println("client: " + message);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void main(String args[])
    {
        Main client = new Main();
        client.run();
    }
    
    String genNumber(boolean First){
    	Integer n = null;
    	
    	while(n == null){
    	
    		try{
    			n = Integer.valueOf(JOptionPane.showInputDialog(First?"Podaj pierwszą liczbę" : "Podaj drugą liczbę"));
    		} catch (NumberFormatException nE){
    			JOptionPane.showMessageDialog(null, "Niewłaściwa wartość!");
    		}
    		
    		
    	}
    	
    	return n.toString();
    	
    }
}
