package GameRunning;

import GameObjects.Deck;

public class HEHand {

	private Deck deck;
	
	public HEHand(Deck _deck) {
		setDeck(_deck);
	}
	
	public void setDeck(Deck _deck) {
		deck = _deck;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
}
