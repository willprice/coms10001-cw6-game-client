
/**
 * Interface that enables certain controllable functionality to the game
 */
public interface Controllable extends Initialisable {
	
	/**
	 * Function that will cause a player to move given a player id, a target position and a type of ticket.
	 * @param playerId This is the id of the player that is going to be moved.
	 * @param targetNodeId This is the position or nodeId that you wish the player to move to
	 * @param ticketType The type of ticket that is being used to move the player.
	 * @return True if the player is able to move to the target using the given ticket type. False if not. 
	 * the reasons that they may not be able to move are that the target is too far away or the player, the ticket is
	 * not the correct type to make that move, or the player does not have any of that ticket left. 
	 */
	public Boolean movePlayer(Integer playerId, Integer targetNodeId, TicketType ticketType);

	/**
	 * Function that provides the Id of a node given a location. In this function you will need to 
	 * find the node whose location is closest to the location provided 
	 * @param xPosition The x position 
	 * @param yPosition The y position
	 * @return The id of the node that is closest to the given position
	 */
	public Integer getNodeIdFromLocation(Integer xPosition, Integer yPosition);
	
	
	/**
	 * Function that when called will save the game to a file
	 * @param filename The filename that you wish the game to be saved to
	 * @return True if the file was saved OK. False if there was a problem saving the file
	 */
	public Boolean saveGame(String filename);
	
	/**
	 * Function that when called will load the game state from a file
	 * @param filename The filename that contains the saved game state
	 * @return True if the game was loaded OK, false if there was a problem
	 */
	public Boolean loadGame(String filename);
	
	
}
