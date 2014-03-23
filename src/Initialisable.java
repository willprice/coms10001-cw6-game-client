/**
 * Interface to enable Initialising the game
 *
 */
public interface Initialisable {
	

	/**
	 * Enum describing the types of tickets that are available in the game 
	 */
	enum TicketType { Bus, Taxi, Underground, DoubleMove, SecretMove };
	
	/**
	 * Function to initialise the state of the Game given a number of detectives.
	 * To check how to correctly initialise the game check the rules for 'Scotland Yard'
	 * @param numberOfDetectives The number of detectives that will be playing
	 * @return True if the game has been initialised properly, false if not
	 */
	public Boolean initialiseGame(Integer numberOfDetectives);
}
