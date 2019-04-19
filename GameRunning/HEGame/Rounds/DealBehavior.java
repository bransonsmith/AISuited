package GameRunning.HEGame.Rounds;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameObjects.Deck;

public class DealBehavior implements DealsCards {
	
	private int numCards;
	
	public DealBehavior(int _numCards) {
		numCards =_numCards;
	}

	@Override
	public List<Card> getDeal(Deck deck) {
		List<Card> deal = new ArrayList<Card>();
		for (int i = 0; i < numCards; i++) {
			deal.add(deck.getNextCard());
		}
		return deal;
	}
	
}


