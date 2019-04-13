package HandEvaluation.Checkers;

import java.util.List;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class PairChecker extends HandTypeChecker {

	private final int PAIR_SIZE = 2;
	
	@Override
	public HandStrength getStrength() { return HandStrength.Pair; }

	@Override
	public String getName() { return "Pair"; }

	@Override
	public List<Card> getHand(List<Card> cards) throws KickerFillProblem {
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cards);
		List<Card> hand = GroupSizeChecker.getHand(handByValue, PAIR_SIZE, Constants.FINAL_HAND_SIZE());
		return hand;
	}
}
