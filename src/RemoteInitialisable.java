

public interface RemoteInitialisable {
    /**
     * This is the function that will initialise a a game session on the 
     * server. 
     * @param sessionName A given name of the session
     * @param numDetectives The number of detectives that are going to player
     * @return A boolean indicator depending on the result of the initialisation
     */
	public boolean initialiseServerGame(String sessionName, int numDetectives, int filesId);
	
	/**
	 * Function to get the current graph file from the server
	 * @return The filename of the graph file
	 */
	public String getServerGraph();
	
	/**
	 * Function to get the map file from the server
	 * @return The filename of the map file
	 */
	public String getServerMap();
	
	/**
	 * Function to get the saved game file from the server
	 * @return The filename of the saved game
	 */
	public String getServerGame();
	
	/**
	 * Function to get the node positions file from the server
	 * @return The filename of the node positions file
	 */
	public String getServerNodePositions();
}
