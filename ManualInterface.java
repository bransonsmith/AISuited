import java.util.ArrayList;
import java.util.List;

import GameRunning.GameRunner;
import GameRunning.Seat;
import Players.AlwaysFold;
import Players.Player;

public class ManualInterface {

	public static void main(String[] args) throws Exception {
		
		List<Player> players = new ArrayList<Player>();
		players.add(new AlwaysFold("lil b", "Branson"));
		players.add(new AlwaysFold("Lindley", "Trent"));
		players.add(new AlwaysFold("Zappy", "Max"));
		players.add(new AlwaysFold("BGJR", "Alec"));
		players.add(new AlwaysFold("the Baz", "Tubz"));
		
		GameRunner game = new GameRunner(5);

		System.out.println(game.toString());
		
		for (Player p: players) {
			game.sitPlayerDown(p);
		}
		game.setRandomDealerAndBlinds();
		
		System.out.println(game.toString());
		
		game.startNewHand();
		System.out.println(game.toString());
	}

}
