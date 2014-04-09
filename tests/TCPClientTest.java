import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

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
	@Parameters(method = "resetTests")
	public void reset(boolean expectedResponse, String stubbedResponse) throws Exception {
		String call = "reset";

		when(network.send(call)).thenReturn(stubbedResponse);
		assertEquals(expectedResponse, client.resetServerGame());
		verify(network).send(call);
	}
	
	private Object[] resetTests() {
		String succesfulReset = "1,1";
		String gameIsIdleAndDoesNotNeedToBeReset = "1,0";
		return $(
				$(true, succesfulReset),
				$(false, gameIsIdleAndDoesNotNeedToBeReset)
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

}
