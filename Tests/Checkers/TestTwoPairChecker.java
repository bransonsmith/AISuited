package Tests.Checkers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Factories.HandFactory;
import GameObjects.Card;
import GameObjects.HandStrength;
import GameObjects.Suits;
import HandEvaluation.Checkers.TwoPairChecker;
import HandEvaluation.Util.KickerFillProblem;

class TestTwoPairChecker {

	private TwoPairChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new TwoPairChecker();
	}

	@Test
	void getStrengthReturnsTwoPair() {
		assertEquals(sut.getStrength(), HandStrength.TwoPair);
	}
	
	@Test
	void getNameReturnsTwoPair() {
		assertEquals(sut.getName(), "TwoPair");
	}
	
	@Test
	void getHandNullIfNoTwoPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfOnePair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinPairByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfTwoPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandPutsTwoPairFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(7, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandPutsHigherPairFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(7, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

	@Test
	void getHandGetsHighestTwoPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinThreePairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(4, Suits.Diamonds));
		expected.add(new Card(4, Suits.Hearts));
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(5, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandGetsHighestTwoPairAces() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxTwoPairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Clubs));
		expected.add(new Card(14, Suits.Spades));
		expected.add(new Card(13, Suits.Diamonds));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(12, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
}
