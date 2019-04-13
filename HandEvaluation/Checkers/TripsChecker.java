package HandEvaluation.Checkers;

import java.util.List;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class TripsChecker extends HandTypeChecker {
	
	private final int TRIPS_SIZE = 3;
	
	@Override
	public HandStrength getStrength() { return HandStrength.Trips; }

	@Override
	public String getName() { return "Trips"; }

	@Override
	public List<Card> getHand(List<Card> cards) throws KickerFillProblem {
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cards);
		List<Card> hand = GroupSizeChecker.getHand(handByValue, TRIPS_SIZE, Constants.FINAL_HAND_SIZE());
		return hand;
	}
}
