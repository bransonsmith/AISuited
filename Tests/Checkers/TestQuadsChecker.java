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
import HandEvaluation.Checkers.QuadsChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestQuadsChecker {

	private QuadsChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new QuadsChecker();
	}

	@Test
	void getStrengthReturnsQuads() {
		assertEquals(sut.getStrength(), HandStrength.Quads);
	}
	
	@Test
	void getNameReturnsQuads() {
		assertEquals(sut.getName(), "Quads");
	}
	
	@Test
	void getHandNullIfNoQuads() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfQuads() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinQuadsByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandNullIfOnePair() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinPairByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfTrips() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTripsByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandPutsQuadsFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinQuadsByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(2, Suits.Diamonds));
		expected.add(new Card(2, Suits.Hearts));
		expected.add(new Card(5, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

}
