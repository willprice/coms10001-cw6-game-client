public abstract class ClientBase implements Controllable, RemoteControllable {
	
	// pass the move player through the RemoteControllable Interface
	public Boolean movePlayer(Integer playerId, Integer targetNodeId, TicketType ticketType)
	{
		return this.makeServerMove(playerId, targetNodeId, ticketType);
	}
	
	// function not relevant to a remote client
	public Boolean initialiseGame(Integer numDetectives)
	{
		return false;
	}

	// function not relevant to a remote client
	public Integer getNodeIdFromLocation(Integer xPosition, Integer yPosition)
	{
		return 0;
	}
	
	// function not relevant to a remote client
	public Boolean saveGame(String filename)
	{
		return false;
	}
	

	// function not relevant to a remote client
	public Boolean loadGame(String filename)
	{
		return false;
	}
}
