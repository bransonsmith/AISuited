package HandEvaluation.Checkers;

import java.util.List;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class QuadsChecker extends HandTypeChecker {
	
	private final int QUADS_SIZE = 4;
	
	@Override
	public HandStrength getStrength() { return HandStrength.Quads; }

	@Override
	public String getName() { return "Quads"; }

	@Override
	public List<Card> getHand(List<Card> cards) throws KickerFillProblem {
		TreeMap<Integer, List<Card>> handByValue = CardGrouper.getHandGroupedByValue(cards);
		List<Card> hand = GroupSizeChecker.getHand(handByValue, QUADS_SIZE, Constants.FINAL_HAND_SIZE());
		return hand;
	}
}
