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
import HandEvaluation.Checkers.PairChecker;
import HandEvaluation.Util.KickerFillProblem;

class TestPairChecker {

	private PairChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new PairChecker();
	}

	@Test
	void getStrengthReturnsPair() {
		assertEquals(sut.getStrength(), HandStrength.Pair);
	}
	
	@Test
	void getNameReturnsPair() {
		assertEquals(sut.getName(), "Pair");
	}

	@Test
	void getHandNullIfNoPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinPairByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandPutsPairFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinPairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(8, Suits.Diamonds));
		expected.add(new Card(7, Suits.Hearts));
		expected.add(new Card(5, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

	@Test
	void getHandGetsHighestPair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(7, Suits.Diamonds));
		expected.add(new Card(5, Suits.Hearts));
		expected.add(new Card(4, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
}
