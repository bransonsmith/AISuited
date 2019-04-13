package HandEvaluation.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class FullHouseChecker extends HandTypeChecker {
	
	private final int PAIR_SIZE = 2;
	private final int TRIPS_SIZE = 3;
	
	@Override
	public HandStrength getStrength() { return HandStrength.FullHouse; }

	@Override
	public String getName() { return "FullHouse"; }

	@Override
	public List<Card> getHand(List<Card> cardsInHand) throws KickerFillProblem {
		
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cardsInHand);
		
		boolean tripFound = false;
		boolean pairFound = false;
		List<Card> tripsCards = new ArrayList<Card>();
		List<Card> pairCards = new ArrayList<Card>();
		
		for(Entry<Integer, List<Card>> entry : handByValue.entrySet()) {
			  List<Card> cards = entry.getValue();
			  if (isFirstTrips(tripFound, cards.size())) {
				  tripsCards.addAll(cards);
				  tripFound = true;
			  }
			  else if (isFirstPair(pairFound, cards.size())) {
				  pairCards.addAll(cards);
				  pairFound = true;
			  }
		}

		if (fullHouseWasFound(tripFound, pairFound)) return getFullHouseCards(pairCards, tripsCards);
		return null;
	}
	
	private List<Card> getFullHouseCards(List<Card> pairCards, List<Card> tripsCards) {
		
		List<Card> fh = new ArrayList<Card>();
		
		fh.add(0, pairCards.get(1));
		fh.add(0, pairCards.get(0));
		fh.add(0, tripsCards.get(2));
		fh.add(0, tripsCards.get(1));
		fh.add(0, tripsCards.get(0));
		
		return fh;
	}
	
	private boolean fullHouseWasFound(boolean tripFound, boolean pairFound) {
		return tripFound && pairFound;
	}
	
	private boolean isFirstTrips(boolean tripsAlreadyFound, int numCardsOfSize) {
		return !tripsAlreadyFound && numCardsOfSize >= TRIPS_SIZE;
	}
	
	private boolean isFirstPair(boolean pairAlreadyFound, int numCardsOfSize) {
		return !pairAlreadyFound && numCardsOfSize >= PAIR_SIZE;
	}
}
