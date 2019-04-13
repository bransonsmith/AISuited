package HandEvaluation.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;
import HandEvaluation.Util.KickerFiller;

public class TwoPairChecker extends HandTypeChecker {
	
	private final int PAIR_SIZE = 2;
	
	@Override
	public HandStrength getStrength() { return HandStrength.TwoPair; }

	@Override
	public String getName() { return "TwoPair"; }

	@Override
	public List<Card> getHand(List<Card> cardsInHand) throws KickerFillProblem {
		
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cardsInHand);
		
		int pairsFound = 0;
		List<Card> unusedCards = new ArrayList<Card>();
		List<Card> usedCards = new ArrayList<Card>(); 
		
		for(Entry<Integer, List<Card>> entry : handByValue.entrySet()) {
			  List<Card> cards = entry.getValue();
			  if (isFirstOrSecondPair(pairsFound, cards.size())) {
				  usedCards.addAll(cards);
				  pairsFound++;
			  }
			  else {
				  unusedCards.addAll(cards);
			  }
		}
		
		List<Card> hand = KickerFiller.Fill(Constants.FINAL_HAND_SIZE(), usedCards, unusedCards);
		if (pairsFound >= 2) return hand;
		return null;
	}
	
	private boolean isFirstOrSecondPair(int pairsFound, int numCardsOfSize) {
		return (pairsFound >= 0 && pairsFound <= 1) && numCardsOfSize >= PAIR_SIZE;
	}
}
