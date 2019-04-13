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
import HandEvaluation.Checkers.StraightChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestStraightChecker {

	private StraightChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new StraightChecker();
	}

	@Test
	void getStrengthReturnsStraight() {
		assertEquals(sut.getStrength(), HandStrength.Straight);
	}
	
	@Test
	void getNameReturnsStraight() {
		assertEquals(sut.getName(), "Straight");
	}

	@Test
	void getHandNullIfNoStraight() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfStraight() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinStraightByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandNotNullIfStraightAceHigh() throws KickerFillProblem {
		List<Card> hand = HandFactory.getBroadwayByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandNotNullIfStraightAceLow() throws KickerFillProblem {
		List<Card> hand = HandFactory.getWheelByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandGetsHighestStraight() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxStraightByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Clubs));
		expected.add(new Card(13, Suits.Spades));
		expected.add(new Card(12, Suits.Diamonds));
		expected.add(new Card(11, Suits.Hearts));
		expected.add(new Card(10, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

	@Test
	void getHandGetsStraightAceLowAceIsLast() throws KickerFillProblem {
		List<Card> hand = HandFactory.getWheelByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(5, Suits.Spades));
		expected.add(new Card(4, Suits.Hearts));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(14, Suits.Diamonds));
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandGetsStraightIfPairInStraight() throws KickerFillProblem {
		List<Card> hand = HandFactory.getStraightWithPairInMiddleByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(5, Suits.Spades));
		expected.add(new Card(4, Suits.Hearts));
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(14, Suits.Diamonds));
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
}
