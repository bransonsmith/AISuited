package GameRunning.HEGame.Hands;

import java.util.List;
import GameObjects.Deck;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;

public class HEHand {

	private Deck deck;
	private HEGame game;
	private List<Seat> seats;
	
	public HEHand(HEGame _game, Deck _deck) {
		setGame(_game);
		setDeck(_deck);
		setSeats(_game.getSeats());
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
}
