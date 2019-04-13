package HandEvaluation.Checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import HandEvaluation.Util.KickerFillProblem;
import HandEvaluation.Util.KickerFiller;

public class GroupSizeChecker {
	
	public static <GroupByType> List<Card> getHand(TreeMap<GroupByType, List<Card>> handGroupedByTypeAndSorted, int sizeOfGroupNeeded, int handSize) throws KickerFillProblem {
	
		boolean groupFound = false;
		List<Card> unusedCards = new ArrayList<Card>();
		List<Card> usedCards = new ArrayList<Card>();

		for(Entry<GroupByType, List<Card>> entry : handGroupedByTypeAndSorted.entrySet()) {
			  List<Card> cards = entry.getValue();
			  if (!groupFound && cards.size() >= sizeOfGroupNeeded) {
				  Collections.sort(cards, Collections.reverseOrder());
				  for (int i = 0; i < cards.size(); i++)
					  if (i < sizeOfGroupNeeded)
						  usedCards.add(cards.get(i));
					  else 
						  unusedCards.add(cards.get(i));
				  groupFound = true;
			  }
			  else {
				  unusedCards.addAll(cards);
			  }
		}
		
		if (groupFound) {
			List<Card> hand = KickerFiller.Fill(Constants.FINAL_HAND_SIZE(), usedCards, unusedCards);
			return hand;
		}
		return null;
	}
}
