package GameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> cards;
	private int nextCardIndex;
	
	public Deck() {
		setCardsToPokerDeck();
		nextCardIndex = 0;
	}
	
	public void refresh() {
		setCardsToPokerDeck();
		nextCardIndex = 0;
		shuffle();
	}
	
	public int size() {
		return cards.size();
	}
	
	public void setToOneFullSuit() {
		this.cards = new ArrayList<Card>();
		for (int value = 2; value <= 14; value++) {
			this.cards.add(new Card(value, Suits.Spades));
		}
	}
	
	public int getNextCardIndex() { 
		return nextCardIndex;
	}
	
	public Card getNextCard() throws IndexOutOfBoundsException {
		return cards.get(nextCardIndex++);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		nextCardIndex = 0;
	}
	
	private void setCardsToPokerDeck() {
		this.cards = new ArrayList<Card>();
		for (int value = 2; value <= 14; value++) {
			this.cards.add(new Card(value, Suits.Clubs));
			this.cards.add(new Card(value, Suits.Diamonds));
			this.cards.add(new Card(value, Suits.Hearts));
			this.cards.add(new Card(value, Suits.Spades));
		}
	}
}
