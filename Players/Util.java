package Players;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameObjects.Suits;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class Util {

	public static boolean isPocketPair(List<Card> holeCards) {
		return  holeCards.size() == 2 &&
				holeCards.get(0).getValue() == holeCards.get(1).getValue();
	}
	
	public static HandEvaluation getHandEvaluation(List<Card> holeCards, List<Card> board) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> allCards = new ArrayList<Card>();
		allCards.addAll(holeCards);
		
		// Creating fake filler cards because the evaluator is stingy
		// about getting 7 cards for now...
		int fillerValue = -1;
		Suits fillerSuit = Suits.FillerA;
		while (board.size() < 5) {
			board.add(new Card (fillerValue, fillerSuit));
			fillerValue -= 2;
			if (fillerSuit == Suits.FillerA) {
				fillerSuit = Suits.FillerB;
			} else {
				fillerSuit = Suits.FillerA;
			}
		}
		
		allCards.addAll(board);
		return HandEvaluator.getHoldEmHandEvaluation(allCards);
	}
}
