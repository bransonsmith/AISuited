package Factories;

import java.util.List;

import GameObjects.Card;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class HandEvaluationFactory {
	
	public static HandEvaluation getRoyalFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxStraightFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidStraightFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidStraightFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinStraightFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinStraightFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinHighCard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidHighCard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidHighCardByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxHighCard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxHighCardByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinTwoPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidTwoPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidTwoPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxTwoPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxTwoPairByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinTrips() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinTripsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidTrips() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidTripsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxTrips() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxTripsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinQuads() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinQuadsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidQuads() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidQuadsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxQuads() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxQuadsByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinStraight() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinStraightByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidStraight() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidStraightByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxStraight() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}	
	
	public static HandEvaluation getMidFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxFlushBySuit();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMinFullHouse() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMinFullHouseByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMidFullHouse() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMidFullHouseByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
	public static HandEvaluation getMaxFullHouse() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		List<Card> hand = HandFactory.getMaxFullHouseByValue();
		return HandEvaluator.getHoldEmHandEvaluation(hand);
	}
	
}
