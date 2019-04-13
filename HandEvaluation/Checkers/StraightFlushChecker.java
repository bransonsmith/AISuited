package HandEvaluation.Checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import GameObjects.Suits;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class StraightFlushChecker extends HandTypeChecker {
	@Override
	public HandStrength getStrength() { return HandStrength.StraightFlush; }

	@Override
	public String getName() { return "StraightFlush"; }

	@Override
	public List<Card> getHand(List<Card> cardsInHand) throws KickerFillProblem {
		
		TreeMap<Suits, List<Card>> handBySuit = CardGrouper.getHandGroupedBySuit(cardsInHand);
		List<Card> usedCards = new ArrayList<Card>(); 
		
		for(Entry<Suits, List<Card>> entry : handBySuit.entrySet()) {
			if (entry.getValue().size() >= Constants.FLUSH_SIZE()) {
				Collections.sort(entry.getValue(), Collections.reverseOrder());
				TreeMap<Integer, List<Card>> flushByValue = CardGrouper.getHandGroupedByValue(entry.getValue());		
				for(Entry<Integer, List<Card>> valEntry : flushByValue.entrySet()) {
					if (flushByValue.containsKey(valEntry.getKey() - 1) &&
							flushByValue.containsKey(valEntry.getKey() - 2) &&
							flushByValue.containsKey(valEntry.getKey() - 3) &&
							flushByValue.containsKey(valEntry.getKey() - 4)
						) {
						usedCards.add(entry.getValue().get(0));
						usedCards.add(flushByValue.get(valEntry.getKey() - 1).get(0));
						usedCards.add(flushByValue.get(valEntry.getKey() - 2).get(0));
						usedCards.add(flushByValue.get(valEntry.getKey() - 3).get(0));
						usedCards.add(flushByValue.get(valEntry.getKey() - 4).get(0));
						return usedCards;
					}
					// Check for Wheel
					else if (valEntry.getKey() == 14 &&
							flushByValue.containsKey(2) &&
							flushByValue.containsKey(3) &&
							flushByValue.containsKey(4) &&
							flushByValue.containsKey(5)
							) {
						usedCards.add(flushByValue.get(5).get(0));
						usedCards.add(flushByValue.get(4).get(0));
						usedCards.add(flushByValue.get(3).get(0));
						usedCards.add(flushByValue.get(2).get(0));
						usedCards.add(usedCards.size(), entry.getValue().get(0));
						return usedCards;
					}
				}	
			}
		}
		return null;
	}
}
