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
import HandEvaluation.Checkers.FullHouseChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestFullHouseChecker {
	
	private FullHouseChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new FullHouseChecker();
	}

	@Test
	void getStrengthReturnsFullHouse() {
		assertEquals(sut.getStrength(), HandStrength.FullHouse);
	}
	
	@Test
	void getNameReturnsFullHouse() {
		assertEquals(sut.getName(), "FullHouse");
	}
	
	@Test
	void getHandNullIfNoFullHouse() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfFullHouse() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoPairByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNullIfPair() throws KickerFillProblem {
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
	void getHandNotNullIfFullHouse() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinFullHouseByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandPutsFullHouseFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinFullHouseByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(2, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(3, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandPutsFullHouseFirstAces() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxFullHouseByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Clubs));
		expected.add(new Card(14, Suits.Spades));
		expected.add(new Card(14, Suits.Diamonds));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(13, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandPutsFullHouseFirstTwoTripsAces() throws KickerFillProblem {
		List<Card> hand = HandFactory.getTwoTripsAcesByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Clubs));
		expected.add(new Card(14, Suits.Spades));
		expected.add(new Card(14, Suits.Diamonds));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(13, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandPutsTripsFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinFullHouseByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(2, Suits.Diamonds));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(3, Suits.Diamonds));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

}
