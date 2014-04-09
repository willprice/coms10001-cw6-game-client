

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class NetworkWrapper {
	private String host;
    private int port;
    private PrintWriter out;
    private DataInputStream in;
    private Socket server;
    private BufferedReader textInput;
    private boolean printRequests;
    private boolean printResponses;

    
    private NetworkWrapper() {	}
    
    /**
     * Static factory function. Pass in the host and port and it will try and make 
     * a connection on those values. If the connection was made, an initialised 
     * NetworkWrapper is returned
     * @param host The host address
     * @param port The port number
     * @return A valid NetwrokWrapper with an active connection to the server
     */
    public static NetworkWrapper getConnection(String host, int port)
    {
    	try {
			return getConnection(new Socket(host, port), host, port);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    public static NetworkWrapper getConnection(Socket server, String host, int port) {
    	try {
    		NetworkWrapper wrapper = connect(host, port, server);
    		return wrapper;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    private static NetworkWrapper connect(String host, int port, Socket server) {
    	NetworkWrapper wrapper = new NetworkWrapper();
    	wrapper.host = host;
    	wrapper.port = port;
    	try {
    		wrapper.registerWithServer(server);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	return wrapper;
    }

    /**
     * Does the actual registration with server
     * @param server TODO
     * @throws Exception
     */
    private void registerWithServer(Socket server) throws Exception
    {
    	// server registration
    	this.server = server;
    	server.setKeepAlive(true);
    	out = new PrintWriter(server.getOutputStream(), true);
    	in = new DataInputStream(server.getInputStream());
    	textInput = new BufferedReader(
    			new InputStreamReader(server.getInputStream()));


    }


    /**
     * Function to get a text file from the server
     * @param call The string specify the call
     * @param outputFilename The filename of the output
     * @return success or failure
     */
    public boolean getTextFile(String call, String outputFilename)
    {
    	String response = send(call);
    	String[] parts = response.split(",");
    	int success = Integer.parseInt(parts[0]);

    	if(success == 0)
    	{
    		return false;
    	}


    	int stringLength = Integer.parseInt(parts[1]);

    	char [] cbuf = new char[stringLength];
    	try
    	{
    		textInput.read(cbuf, 0, stringLength);
    		String file = new String(cbuf);
    		PrintWriter writer = new PrintWriter(new File(outputFilename));

    		writer.print(file);
    		writer.close();
    	}
    	catch(IOException e)
    	{
    		TextOutput.printError(e.getMessage());

    		return false;
    	}


    	return true;
    }


    /**
     * Function to send a string to the server. This is the only function that
     * you will need to use for your work. The function writes the string call 
     * to the server and then waits for a response. This function will not end
     * until a valid response is given. One common issue in which you may get stuck 
     * in this function is that the server response does not have a new line at the
     * end.
     * @param call String the request that is going to be sent to the server
     * @return a String response from the server
     */
    @SuppressWarnings("deprecation")
    public String send(String call)
    {
    	if(printRequests) TextOutput.printServer(call);


    	out.println(call);
    	String response = "";
    	try 
    	{
    		response = in.readLine();
    	} 
    	catch (IOException e) 
    	{
    		TextOutput.printError("Error Communicating With Server\n");			
    	}

    	if(printResponses) TextOutput.printResponse(response);
    	return response;
    }



    /**
     * Function to get a binary file from the server
     * @param call The text string request
     * @param outputFilename
     * @return
     */
    public boolean getBinaryFile(String call, String outputFilename)
    {
    	String response = send(call);
    	String[] parts = response.split(",");
    	int success = Integer.parseInt(parts[0]);

    	if(success == 0)
    	{
    		return false;
    	}

    	int fileSize = Integer.parseInt(parts[1]);		
    	byte[] data = new byte[fileSize];

    	try
    	{
    		FileOutputStream output = new FileOutputStream(new File(outputFilename));


    		int bytesRead = 0;
    		int read = 0;
    		while(bytesRead < fileSize)
    		{
    			read = in.read(data);
    			if(read < 0)
    			{
    				TextOutput.printError("Error Reading Image File\n");
    				break;
    			}
    			output.write(data, 0, read);
    			bytesRead+=read;
    		}

    		output.close();
    	}
    	catch(IOException e)
    	{
    		TextOutput.printError(e.getMessage());
    		return false;
    	}
    	return true;
    }

    /**
     * @return the printRequests
     */
    public boolean isPrintRequests() {
    	return printRequests;
    }

    /**
     * @param printRequests the printRequests to set
     */
    public void setPrintRequests(boolean printRequests) {
    	this.printRequests = printRequests;
    }

    /**
     * @return the printResponses
     */
    public boolean isPrintResponses() {
    	return printResponses;
    }

    /**
     * @param printResponses the printResponses to set
     */
    public void setPrintResponses(boolean printResponses) {
    	this.printResponses = printResponses;
    }

}
