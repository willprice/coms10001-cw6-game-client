import java.util.ArrayList;
import java.util.List;

import Arr.string.split;


public class TCPClient extends ClientBase implements RemoteControllable {

	NetworkWrapper network;


	/**
	 * TCPClient constructor. Initialises the 
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
		List<Integer> response = sendMessageToServer("reset");
		if (response == null) return false;
		int status = response.get(1);

		if (status == 1) return true;
		else return false;
	}

	/**
	 * This function will query the server to try and join the current game. If 
	 * this is possible, the server should return the list of player ids that 
	 * this client will take control of
	 * @return List of integers representing the players ids assigned to this client
	 */
	public List<Integer> joinServerGame()
	{
		List<Integer> response = sendMessageToServer("join");

		if (response == null) return null;

		int status = response.get(1);
		boolean gameHasBeenJoined = (status == 1);

		if (gameHasBeenJoined) {
			List<Integer> players = response.subList(2, response.size());
			return players;
		}
		else return null;
	}

	private List<Integer> splitStringAndParseToIntegerList(String stringResponse) {
		if (stringResponse.equals("")) {
			return null;
		} 
		String[] splitResponse = stringResponse.split(",");
		List<Integer> response = new ArrayList<>();

		for (String element : splitResponse) {
			response.add(Integer.parseInt(element));
		}
		return response;
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
		String moveCall = constructMoveCall(playerId, targetLocation, ticket);
		List<Integer> response = sendMessageToServer(moveCall);
		if (response == null) return false;
		
		int status = response.get(1);
		boolean gameIsNotCurrentlyRunning = (status == -1);
		boolean moveIsInvalid = (status == 0);

		if (gameIsNotCurrentlyRunning || moveIsInvalid) return false;
		else return true;
	}

	private String constructMoveCall(int playerId, int targetLocation, TicketType ticket) {
		String ticketType = "invalidTicketType";
		ticketType = ticket.toString();
		return "move," + playerId + "," + targetLocation + "," + ticketType;
	}

	/**
	 * This function is used to get the next player to move.
	 * @return The id of the next player to move
	 */
	public int getServerNextPlayer()
	{
		int errorCode = 0;
		List<Integer> response = sendMessageToServer("next_player");

		if (response == null) return errorCode;
		int status = response.get(1);
		boolean gameIsNotInProgress = (status == -1);
		boolean gameHasBeenWon = (status == 0);

		if (gameIsNotInProgress || gameHasBeenWon) {
			return errorCode;
		}
		else {
			Integer nextPlayerId = response.get(2);
			return nextPlayerId;
		}
	}


	private List<Integer> sendMessageToServer(String call) {
		String result = network.send(call);
		List<Integer> response = splitStringAndParseToIntegerList(result);
		return response;
	}

	/**
	 * This function will query the server to ask it is the current game is
	 * over. This function should then return an appropriate value
	 * @return True is game is over, false if not
	 */
	public boolean getServerGameOver()
	{
		List<Integer> response = sendMessageToServer("game_over");
		if (response == null) return false;
		
		int status = response.get(1);
		boolean gameIsOver = (status == 1);

		if (gameIsOver) return true;
		else return false;
	}

	/**
	 * This function is used to get the winning player from the server. This should
	 * then return the id if the game is over. 
	 * @return
	 */
	public int getServerWinningPlayer()
	{
		int errorCode = 0;
		List<Integer> response = sendMessageToServer("winning_player");
		if (response == null) return errorCode;

		Integer status = response.get(1);
		boolean gameIsOver = (status == 1);

		if (gameIsOver) return response.get(2);
		else return errorCode;
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
		if(returnCode == 0) return false;
		else return true;
	}




}






