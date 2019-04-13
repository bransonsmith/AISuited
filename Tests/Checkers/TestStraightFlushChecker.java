package Tests.Checkers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Factories.HandFactory;
import GameObjects.Card;
import GameObjects.HandStrength;
import GameObjects.Suits;
import HandEvaluation.Checkers.StraightFlushChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestStraightFlushChecker {
	private StraightFlushChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new StraightFlushChecker();
	}

	@Test
	void getStrengthReturnsStraightFlush() {
		assertEquals(sut.getStrength(), HandStrength.StraightFlush);
	}
	
	@Test
	void getNameReturnsStraightFlush() {
		assertEquals(sut.getName(), "StraightFlush");
	}

	@Test
	void getHandNullIfNoStraightFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfStraightButNotFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinStraightBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfFlushButNotStraight() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMidFlushBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfStraightFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightFlushBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandGetsHighestStraightFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightFlushBySuit();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Hearts));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(12, Suits.Hearts));
		expected.add(new Card(11, Suits.Hearts));
		expected.add(new Card(10, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

	@Test
	void getHandGetsStraightFlushAceLowAceIsLast() throws KickerFillProblem {
		List<Card> hand = HandFactory.getSuitedWheelBySuit();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(5, Suits.Hearts));
		expected.add(new Card(4, Suits.Hearts));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(2, Suits.Hearts));
		expected.add(new Card(14, Suits.Hearts));
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
}
