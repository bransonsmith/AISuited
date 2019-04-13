package HandEvaluation.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GameObjects.Card;

public class KickerFiller {

	public static List<Card> Fill(int handSize, List<Card> usedCards, List<Card> unusedCards) throws KickerFillProblem {
		
		List<Card> hand = new ArrayList<Card>();
		List<Card> usedCopy = new ArrayList<Card>(usedCards);
		List<Card> unusedCopy = new ArrayList<Card>(unusedCards);
		
		Collections.sort(usedCopy, Collections.reverseOrder());
		Collections.sort(unusedCopy, Collections.reverseOrder());
		
		for (int i = 0; i < usedCopy.size() && hand.size() < handSize; i++) {
			hand.add(usedCopy.get(i));
		}
		for (int i = 0; i < unusedCopy.size() && hand.size() < handSize; i++) {
			hand.add(unusedCopy.get(i));
		}
		
		if (hand.size() != handSize) {
			throw new KickerFillProblem(handSize, hand.size());
		}
		return hand;
	}
	
}
