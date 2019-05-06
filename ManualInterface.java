import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.HEOptions;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.Player;
import Players.Rock;
import Players.SimpleSteve;

public class ManualInterface {

	public static void main(String[] args) throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		
		
		Scanner kb = new Scanner(System.in);
		
		List<Player> players = new ArrayList<Player>();
		players.add(new SimpleSteve("Branson", "Branson"));
		players.add(new SimpleSteve("Trent", "Trent"));
		players.add(new SimpleSteve("Max", "Max"));
		players.add(new SimpleSteve("Alec", "Alec"));
		players.add(new SimpleSteve("the Baz", "Tubz"));
		players.add(new SimpleSteve("Mamaux", "Mamaux"));
		players.add(new SimpleSteve("Ian", "Ian"));
		players.add(new SimpleSteve("Sheu", "Sheu"));
		players.add(new SimpleSteve("McCrae", "McCrae"));
		
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
		Display display = new Display(game); 
		
		game.addObserver(display);
		
		game.playGame();
		
		/*
		while (game.isNotComplete()) {
			
			kb.nextLine();
			game.commenceNextEvent();
			display.repaint();
			
		}*/
		
		kb.close();
	}
}
