
import java.util.List;


public interface RemoteControllable extends RemoteInitialisable {
    /**
     * Function to trigger the reset functionality of the server.
     * @return If the game was rest OK or not
     */
    public boolean resetServerGame();

    
    /**
     * This function will query the server to try and join the current game. If 
     * this is possible, the server should return the list of player ids that 
     * this client will take control of
     * @return List of integers representing the players ids assigned to this client
     */
    public List<Integer> joinServerGame();
  
    
    /**
     * This function should send a command to the server to make a move. If the move is possible
     * the server will make the move and write it to the db and this function should return true
     * @param playerId The id of the player
     * @param targetLocation The target location
     * @param ticket The type of ticket
     * @return true if the move was successful
     */
    public boolean makeServerMove(int playerId, int targetLocation, Initialisable.TicketType ticket);
 
    /**
     * This function is used to get the next player to move.
     * @return The id of the next player to move
     */
    public int getServerNextPlayer();

    
    /**
     * This function will query the server to ask it is the current game is
     * over. This function should then return an appropriate value
     * @return True is game is over, false if not
     */
    public boolean getServerGameOver();

    
    /**
     * This function is used to get the winning player from the server. This should
     * then return the id if the game is over. 
     * @return
     */
    public int getServerWinningPlayer();

}
