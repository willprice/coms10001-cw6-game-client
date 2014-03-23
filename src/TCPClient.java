import java.util.List;




public class TCPClient extends ClientBase {
	
	NetworkWrapper network;
	
	
	/**
	 * TCPClient constructor. Intiailises the 
	 * network wrapper
	 */
	public TCPClient()
	{
		// initialise the network
		network = NetworkWrapper.getConnection("localhost", 8124);
		network.setPrintRequests(true);
		network.setPrintResponses(true);
		if(network == null) 
		{
			TextOutput.printError("Couldn't establish Connection with server\n");
			System.exit(1);
		}
	}
	
   
 

    
    /**
     * Function to tell the server to reset itself
     * @return If the reset request was a success or not
     */
    public boolean resetServerGame()
    {
    	// CODE GOES HERE
    	return false;
    }
    
    /**
     * This function will query the server to try and join the current game. If 
     * this is possible, the server should return the list of player ids that 
     * this client will take control of
     * @return List of integers representing the players ids assigned to this client
     */
    public List<Integer> joinServerGame()
    {
    	// CODE GOES HERE
    	return null;
    }
    
    /**
     * This function should send a command to the server to make a move. If the move is possible
     * the server will make the move and write it to the db and this function should return true
     * @param playerId The id of the player
     * @param targetLocation The target location
     * @param ticket The type of ticket
     * @return true if the move was successful
     */
    public boolean makeServerMove(int playerId, int targetLocation, Initialisable.TicketType ticket)
    {
    	// CODE GOES HERE
		return true;
    }
    
    /**
     * This function is used to get the next player to move.
     * @return The id of the next player to move
     */
    public int getServerNextPlayer()
    {
    	// CODE GOES HERE
    	return 0;
    }
    
    /**
     * This function will query the server to ask it is the current game is
     * over. This function should then return an appropriate value
     * @return True is game is over, false if not
     */
    public boolean getServerGameOver()
    {
    	// CODE GOES HERE
    	return false;
    }
    
    /**
     * This function is used to get the winning player from the server. This should
     * then return the id if the game is over. 
     * @return
     */
    public int getServerWinningPlayer()
    {
    	// CODE GOES HERE
    	return 0;
    }
    
    
    
    
    
    
    
    /*
     * Functions from RemoteInitialisable
     */	
	public String getServerGraph()
	{
		return getTextFile("graph", "graph.txt");
	}
	

	private String getTextFile(String type, String filename)
	{
		String serverCall = "get_file," + type;
		boolean success = network.getTextFile(serverCall, filename);
		if(success) return filename;
		else return null;
	}
	
	public String getServerMap()
	{
		String serverCall = "get_file,map";
		String filename = "map.jpg";
		boolean success = network.getBinaryFile(serverCall, filename);
		
		if(success) return filename;
		else return null;
	}
	
	public String getServerGame()
	{
		return getTextFile("game", "game.txt");
	}
	
	
	public String getServerNodePositions()
	{
		return getTextFile("pos", "pos.txt");
	}

 
    public boolean initialiseServerGame(String sessionName, int numDetectives, int filesId)
    {
    	String serverCall = "initialise," + 
				Integer.toString(numDetectives) + 
				","+ sessionName + "," + 
				Integer.toString(filesId);
				
				
		String response = network.send(serverCall);
		
		String[] parts = response.split(",");
		int returnCode = Integer.parseInt(parts[1]);
		System.out.println(returnCode);
		if(returnCode == 0)
		{
			return false;
		}
		
		return true;
    }
	



}
	
	

	
	
	
