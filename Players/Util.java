package Players;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameObjects.Suits;
import GameRunning.Decisions.PublicGameInfo;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class Util {

	public static boolean isPocketPair(List<Card> holeCards) {
		return  holeCards.size() == 2 &&
				holeCards.get(0).getValue() == holeCards.get(1).getValue();
	}
	
	public static boolean isPreFlop(PublicGameInfo gameInfo) {
		return gameInfo.getBoard().size() == 0;
	}
	
	public static boolean isFlop(PublicGameInfo gameInfo) {
		return gameInfo.getBoard().size() == 3;
	}
	
	public static boolean isTurn(PublicGameInfo gameInfo) {
		return gameInfo.getBoard().size() == 4;
	}
	
	public static boolean isRiver(PublicGameInfo gameInfo) {
		return gameInfo.getBoard().size() == 5;
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

	
	public static boolean isTwoBroadWayCards(List<Card> holeCards) {
		return  holeCards.get(0).getAceVal() >= 10 &&
				holeCards.get(1).getAceVal() >= 10;
	}
	
	public static boolean isSuited(List<Card> holeCards) {
		return  holeCards.get(0).getSuit() ==
				holeCards.get(1).getSuit();
	}
	
	public static boolean isConnector(List<Card> holeCards) {
		return  Math.abs(
				holeCards.get(0).getAceVal() -
				holeCards.get(1).getAceVal()) == 1;
	}
	
	public static boolean isSuitedConnector(List<Card> holeCards) {
		return  isConnector(holeCards) && isSuited(holeCards);
	}
}
