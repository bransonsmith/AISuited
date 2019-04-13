package HandEvaluation.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import GameObjects.Card;
import GameObjects.Suits;

public class CardGrouper {
	
	public static TreeMap<Integer, List<Card>> getHandGroupedByValue(List<Card> hand) {
		TreeMap<Integer,List<Card>> byVal = new TreeMap<Integer,List<Card>>(Collections.reverseOrder());
		for (Card card: hand) {
			if (! byVal.keySet().contains(card.getValue())) {
				byVal.put(card.getValue(), new ArrayList<Card>());
			}
			byVal.get(card.getValue()).add(card);
		}
		return byVal;
	}
	
	public static TreeMap<Suits, List<Card>> getHandGroupedBySuit(List<Card> hand) {
		TreeMap<Suits,List<Card>> byVal = new TreeMap<Suits,List<Card>>(Collections.reverseOrder());
		for (Card card: hand) {
			if (! byVal.keySet().contains(card.getSuit())) {
				byVal.put(card.getSuit(), new ArrayList<Card>());
			}
			byVal.get(card.getSuit()).add(card);
		}
		return byVal;
	}
}