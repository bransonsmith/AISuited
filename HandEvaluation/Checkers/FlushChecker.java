package HandEvaluation.Checkers;

import java.util.List;
import java.util.TreeMap;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import GameObjects.Suits;
import HandEvaluation.Util.CardGrouper;
import HandEvaluation.Util.KickerFillProblem;

public class FlushChecker extends HandTypeChecker {

	private final int FLUSH_SIZE = 5;
	
	@Override
	public HandStrength getStrength() { return HandStrength.Flush; }

	@Override
	public String getName() { return "Flush"; }

	@Override
	public List<Card> getHand(List<Card> cards) throws KickerFillProblem {
		TreeMap<Suits, List<Card>> handBySuit = CardGrouper.getHandGroupedBySuit(cards);
		List<Card> hand = GroupSizeChecker.getHand(handBySuit, FLUSH_SIZE, Constants.FINAL_HAND_SIZE());
		return hand;
	}

}
