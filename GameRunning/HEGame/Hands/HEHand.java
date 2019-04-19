package GameRunning.HEGame.Hands;

import java.util.List;

import Common.Logger;
import GameObjects.Deck;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Pot;
import GameRunning.HEGame.Rounds.Flop;
import GameRunning.HEGame.Rounds.HERound;
import GameRunning.HEGame.Rounds.PreFlop;
import GameRunning.HEGame.Rounds.River;
import GameRunning.HEGame.Rounds.Turn;

public class HEHand {

	private Deck deck;
	private HEGame game;
	private List<Seat> seats;
	private Pot pot;
	private HERound round;
	private boolean complete;
	
	public HEHand(HEGame _game) {
		setGame(_game);
		setDeck(new Deck());
		setSeats(_game.getSeats());
		setPot(new Pot());
		complete = false;
	}
	
	public void commenceNextRound() throws Exception {
		if (round == null) {
			round = new PreFlop(this);
			while (round.isNotComplete()) {
				round.commenceNextAction();
			}
		}
		String roundName = round.getName().toLowerCase();
		if (roundName.equals("preflop")) {
			round = new Flop(this);
			while (round.isNotComplete()) {
				round.commenceNextAction();
			}
		}
		if (roundName.equals("flop")) {
			round = new Turn(this);
			while (round.isNotComplete()) {
				round.commenceNextAction();
			}
		}
		if (roundName.equals("turn")) {
			round = new River(this);
			while (round.isNotComplete()) {
				round.commenceNextAction();
			}
		}
		if (roundName.equals("river")) {
			round = null;
			commenceShowDown();
		}
	}

	private void commenceShowDown() {
		// TODO Auto-generated method stub
		Logger.log("No showdown logic yet :(. But it will happen here.");
		complete = true;
	}
	
	public boolean isComplete() {
		return complete;
	}
	public boolean isNotComplete() {
		return !complete;
	}

	public void setDeck(Deck _deck) {
		deck = _deck;
	}
	public Deck getDeck() {
		return deck;
	}

	public HEGame getGame() {
		return game;
	}
	public void setGame(HEGame _game) {
		game = _game;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}

	public Pot getPot() {
		return pot;
	}
	private void setPot(Pot _pot) {
		pot = _pot;
	}
}
