package Tests.HandEvaluation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Common.Constants;
import Factories.HandFactory;
import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

class TestHandEvaluator {
	
	private String HEValExcMessage = "HandEvaluator expects a list of exactly " + Constants.CARDS_IN_HAND_EVALUATION() + " cards.";
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void holdEmEvalSetsHighCard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> eHand = new ArrayList<Card>();
		eHand.addAll(hand);
		Collections.sort(eHand, Collections.reverseOrder());
		eHand = eHand.subList(0, 5);
		HandEvaluation expected = new HandEvaluation(HandStrength.HighCard, "HighCard", eHand);

		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void holdEmEvalSetsPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinPairByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.Pair, "Pair", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsTwoPair() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.TwoPair, "TwoPair", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsTrips() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinTripsByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.Trips, "Trips", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsStraight() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinStraightByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.Straight, "Straight", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinFlushBySuit();
		HandEvaluation expected = new HandEvaluation(HandStrength.Flush, "Flush", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsFullHouse() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinFullHouseByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.FullHouse, "FullHouse", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	
	@Test
	void holdEmEvalSetsQuads() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinQuadsByValue();
		HandEvaluation expected = new HandEvaluation(HandStrength.Quads, "Quads", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void holdEmEvalSetsStraightFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> hand = HandFactory.getMinStraightFlushBySuit();
		HandEvaluation expected = new HandEvaluation(HandStrength.StraightFlush, "StraightFlush", hand.subList(0, Constants.FINAL_HAND_SIZE()));
		HandEvaluation actual = HandEvaluator.getHoldEmHandEvaluation(hand);
		
		assertEquals(expected.getStrength(), actual.getStrength());
		assertEquals(expected.getHandName(), actual.getHandName());
	}
	
	@Test
	void validateHandThrowsCardCountProblemIfTooFewCards() {
		List<Card> hand = HandFactory.getHandOf(Constants.CARDS_IN_HAND_EVALUATION() - 1);
		Throwable exception = assertThrows(HandEvaluatorCardCountProblem.class, () -> HandEvaluator.validateHand(hand));
	    
		String expectedMessage = HEValExcMessage;
		assertEquals(expectedMessage, exception.getMessage());
	}
	
	@Test
	void validateHandThrowsCardCountProblemIfTooManyCards() {
		List<Card> hand = HandFactory.getHandOf(Constants.CARDS_IN_HAND_EVALUATION() + 1);
		Throwable exception = assertThrows(HandEvaluatorCardCountProblem.class, () -> HandEvaluator.validateHand(hand));
	    
		String expectedMessage = HEValExcMessage;
		assertEquals(expectedMessage, exception.getMessage());
	}
	
	@Test
	void validateHandThrowsCardCountProblemIfNullHand() {
		List<Card> hand = null;
		Throwable exception = assertThrows(HandEvaluatorCardCountProblem.class, () -> HandEvaluator.validateHand(hand));
	    
		String expectedMessage = HEValExcMessage;
		assertEquals(expectedMessage, exception.getMessage());
	}
	
}
