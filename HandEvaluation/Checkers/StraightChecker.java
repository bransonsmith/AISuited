package HandEvaluation.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class StraightChecker extends HandTypeChecker {

	@Override
	public HandStrength getStrength() { return HandStrength.Straight; }

	@Override
	public String getName() { return "Straight"; }

	@Override
	public List<Card> getHand(List<Card> cards) throws KickerFillProblem {
		
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cards);
		List<Card> usedCards = new ArrayList<Card>(); 
		
		for(Entry<Integer, List<Card>> entry : handByValue.entrySet()) {
			if (handByValue.containsKey(entry.getKey() - 1) &&
				handByValue.containsKey(entry.getKey() - 2) &&
				handByValue.containsKey(entry.getKey() - 3) &&
				handByValue.containsKey(entry.getKey() - 4)
				) {
				usedCards.add(entry.getValue().get(0));
				usedCards.add(handByValue.get(entry.getKey() - 1).get(0));
				usedCards.add(handByValue.get(entry.getKey() - 2).get(0));
				usedCards.add(handByValue.get(entry.getKey() - 3).get(0));
				usedCards.add(handByValue.get(entry.getKey() - 4).get(0));
				return usedCards;
			}
			// Check for Wheel
			else if (entry.getKey() == 14 &&
					handByValue.containsKey(2) &&
					handByValue.containsKey(3) &&
					handByValue.containsKey(4) &&
					handByValue.containsKey(5)
					) {
				usedCards.add(handByValue.get(5).get(0));
				usedCards.add(handByValue.get(4).get(0));
				usedCards.add(handByValue.get(3).get(0));
				usedCards.add(handByValue.get(2).get(0));
				usedCards.add(usedCards.size(), entry.getValue().get(0));
				return usedCards;
			}
		}

		return null;
	}
}
