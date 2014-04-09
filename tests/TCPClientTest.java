import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.font.NumericShaper;
import java.util.Arrays;
import java.util.List;

import javax.swing.tree.ExpandVetoException;

import static junitparams.JUnitParamsRunner.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class TCPClientTest {
	private NetworkWrapper network;
	private TCPClient client;

	@Before
	public void setUp() {
		network = mock(NetworkWrapper.class);
		client = new TCPClient(network);
	}

	@Test
	@Parameters(method = "serverGameTests")
	public void joinServerGame(List<Integer> expectedResponse, String stubbedResponse) {
		String call = "join";

		when(network.send(call)).thenReturn(stubbedResponse);
		List<Integer> players = client.joinServerGame();
		verify(network).send(call);
		assertEquals(expectedResponse, players);
	}

	@SuppressWarnings("unused")
	private Object[] serverGameTests() {
		String gameIsAlreadyRunningResponse = "1,-1";
		String gameIsUninitializedResponse = "1,0";
		String players = "3:4:5";
		String gameHasBeenJoined = "1,1";
		String validResponse = gameHasBeenJoined + "," + players;

		return $(
				$(Arrays.asList(3,4,5), validResponse),
				$(null, gameIsUninitializedResponse),
				$(null, gameIsAlreadyRunningResponse)
				);
	}

	@Test
	@Parameters(method = "moveTests")
	public void move(boolean expectedResponse, String stubbedResponse) throws Exception {
		int playerId = 1;
		int targetLocation = 2;
		Initialisable.TicketType ticket = Initialisable.TicketType.Taxi;
		String call = "move," + playerId + "," + targetLocation + "," + ticket;

		when(network.send(call)).thenReturn(stubbedResponse);
		boolean response = client.makeServerMove(playerId, targetLocation, ticket);
		verify(network).send(call);
		assertEquals(expectedResponse, response);
	}

	@SuppressWarnings("unused")
	private Object[] moveTests() {
		String succesfulMove = "1,1";
		String invalidMove = "1,0";
		String gameIsNotRunning = "1,-1";
		return $(
				$(true, succesfulMove),
				$(false, invalidMove),
				$(false, gameIsNotRunning)
				);
	}

	@Test
	@Parameters(method = "nextPlayerTests")
	public void nextPlayer(int expectedResponse, String stubbedResponse) throws Exception {
		String call = "next_player";
		when(network.send(call)).thenReturn(stubbedResponse);
		assertEquals(expectedResponse, client.getServerNextPlayer());
		verify(network).send(call);

	}

	@SuppressWarnings("unused")
	private Object[] nextPlayerTests() {
		int no_player = 0;
		String gameIsNotInProgressResponse = "1,-1";
		String gameHasBeenWonResponse = "1,0";
		String gameWithNextPlayerResponse = "1,1,2";
		int nextPlayer = 2;

		return $(
				$(no_player, gameIsNotInProgressResponse),
				$(no_player, gameHasBeenWonResponse),
				$(nextPlayer, gameWithNextPlayerResponse)
				);
	}

	@Test
	@Parameters(method = "winningPlayerTests")
	public void winningPlayer(int expectedWinningPlayer, String stubbedResponse) throws Exception {
		String call = "winning_player";
		when(network.send(call)).thenReturn(stubbedResponse);
		int actualWinningPlayer = client.getServerWinningPlayer();
		verify(network).send(call);
		assertEquals(expectedWinningPlayer, actualWinningPlayer);
	}
	
	@SuppressWarnings("unused")
	private Object[] winningPlayerTests() {
		int no_player = 0;
		int playerId = 1;
		String gameOverResponse = "1,1";
		String gameIsNotOverResponse = "1,0";
		return $(
				$(playerId, gameOverResponse + "," + playerId),
				$(no_player, gameIsNotOverResponse)
				);
	}

	@Test
	@Parameters(method = "resetTests")
	public void reset(boolean expectedResponse, String stubbedResponse) throws Exception {
		String call = "reset";

		when(network.send(call)).thenReturn(stubbedResponse);
		assertEquals(expectedResponse, client.resetServerGame());
		verify(network).send(call);
	}

	@SuppressWarnings("unused")
	private Object[] resetTests() {
		String succesfulReset = "1,1";
		String gameIsIdleAndDoesNotNeedToBeReset = "1,0";
		return $(
				$(true, succesfulReset),
				$(false, gameIsIdleAndDoesNotNeedToBeReset)
				);
	}
	
	
	@Test
	@Parameters(method = "initialiseGameTests")
	public void initialiseGame(int numberOfDetectives, String session, int filesId, boolean expectedResponse, String stubbedResponse) throws Exception {
		String call = "initialise," + numberOfDetectives + "," + session + "," + filesId;
		when(network.send(call)).thenReturn(stubbedResponse);
		boolean actualResponse = client.initialiseServerGame(session, numberOfDetectives, filesId);
		assertEquals(expectedResponse, actualResponse);
	}
	
	@SuppressWarnings("unused")
	private Object[] initialiseGameTests() {
		String testSession = "test_session";
		int exampleFilesId = 1;
		String gameInitialisedOK = "1,1";
		String gameIsAlreadyInitialised = "1,0";
		String gameIsInProgressAndCantBeInitialised = "1,-2";
		String gameIsInProcessOfInitialisation = "1,-1";

		return $(
				$(5, testSession, exampleFilesId, true, gameInitialisedOK),
				$(5, testSession, exampleFilesId, true, gameIsInProcessOfInitialisation),
				$(5, testSession, exampleFilesId, false, gameIsAlreadyInitialised),
				$(5, testSession, exampleFilesId, true, gameIsInProgressAndCantBeInitialised)
				);
	}


}
