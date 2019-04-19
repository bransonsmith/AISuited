package GameRunning;

import java.util.List;

import GameObjects.Card;
import GameObjects.Deck;

public interface DealsCards {
	List<Card> getDeal(Deck deck);
}
