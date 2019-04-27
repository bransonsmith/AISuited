package HandEvaluation;

import java.util.ArrayList;
import java.util.List;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Checkers.FlushChecker;
import HandEvaluation.Checkers.FullHouseChecker;
import HandEvaluation.Checkers.HandTypeChecker;
import HandEvaluation.Checkers.PairChecker;
import HandEvaluation.Checkers.QuadsChecker;
import HandEvaluation.Checkers.StraightChecker;
import HandEvaluation.Checkers.StraightFlushChecker;
import HandEvaluation.Checkers.TripsChecker;
import HandEvaluation.Checkers.TwoPairChecker;
import HandEvaluation.Util.KickerFillProblem;
import HandEvaluation.Util.KickerFiller;

public class HandEvaluator {

	private final static int EXPECTED_CARDS = Constants.CARDS_IN_HAND_EVALUATION();

	public static HandEvaluation getHoldEmHandEvaluation(List<Card> hand) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		validateHand(hand);
		
		List<HandTypeChecker> handsToCheckFor = new ArrayList<HandTypeChecker>();
		handsToCheckFor.add(new StraightFlushChecker());
		handsToCheckFor.add(new QuadsChecker());
		handsToCheckFor.add(new FullHouseChecker());
		handsToCheckFor.add(new FlushChecker());
		handsToCheckFor.add(new StraightChecker());
		handsToCheckFor.add(new TripsChecker());
		handsToCheckFor.add(new TwoPairChecker());
		handsToCheckFor.add(new PairChecker());
		
		List<Card> madeHand = null;
		for (HandTypeChecker checker : handsToCheckFor) {
			madeHand = checker.getHand(hand);
			if (madeHand != null) {
				return new HandEvaluation(checker.getStrength(), checker.getName(), madeHand);
			}
		}
		
		madeHand = KickerFiller.Fill(Constants.FINAL_HAND_SIZE(), new ArrayList<Card>(), hand);
		return new HandEvaluation(HandStrength.HighCard, "HighCard", madeHand);
	}
	
	public static void validateHand(List<Card> hand) throws HandEvaluatorCardCountProblem {
		if (hand == null) throw new HandEvaluatorCardCountProblem();
		if (hand.size() != EXPECTED_CARDS) throw new HandEvaluatorCardCountProblem();
	}

}
