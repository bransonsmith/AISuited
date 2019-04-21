import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Common.Logger;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.HEOptions;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.AlwaysAllIn;
import Players.AlwaysFold;
import Players.Player;
import Players.Rock;

public class ManualInterface {

	public static void main(String[] args) throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		
		Scanner kb = new Scanner(System.in);
		
		List<Player> players = new ArrayList<Player>();
		players.add(new Rock("Branson", "Branson"));
		players.add(new Rock("Trent", "Trent"));
		players.add(new Rock("Max", "Max"));
		players.add(new Rock("Alec", "Alec"));
		players.add(new Rock("the Baz", "Tubz"));
		players.add(new Rock("Mamaux", "Mamaux"));
		players.add(new Rock("Ian", "Ian"));
		players.add(new Rock("Sheu", "Sheu"));
		players.add(new Rock("McCrae", "McCrae"));
		
		HEOptions options = new HEOptions(1000, 50, 100);
		
		List<Seat> seats = new ArrayList<Seat>();
		int num = 1;
		for (Player p : players) {
			Seat newSeat = new Seat(num++);
			newSeat.setPlayer(p);
			newSeat.setChips(options.getBuyIn());
			seats.add(newSeat);
		}
		
		
		HEGame game = new HEGame(seats, options);
		
		
		
		String cont = "y";
		while (!cont.toLowerCase().contains("n")) {
			game.startNewHand();
			Logger.log("Play another hand? y/n");
			cont = kb.nextLine();
		}
		
	}

}
