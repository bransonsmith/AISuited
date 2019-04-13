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
import HandEvaluation.Checkers.TripsChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestTripsChecker {
	
	private TripsChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new TripsChecker();
	}

	@Test
	void getStrengthReturnsTrips() {
		assertEquals(sut.getStrength(), HandStrength.Trips);
	}
	
	@Test
	void getNameReturnsTrips() {
		assertEquals(sut.getName(), "Trips");
	}
	
	@Test
	void getHandNullIfNoTrips() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardByValue();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfTrips() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTripsByValue();
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
	void getHandPutsTripsFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTripsByValue();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(2, Suits.Spades));
		expected.add(new Card(2, Suits.Diamonds));
		expected.add(new Card(7, Suits.Diamonds));
		expected.add(new Card(5, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandGetsHighestTrips() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinTwoTripsByValue();
		
		List<Card> expected = new ArrayList<Card>();	
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(3, Suits.Diamonds));
		expected.add(new Card(3, Suits.Clubs));
		expected.add(new Card(4, Suits.Diamonds));
		expected.add(new Card(2, Suits.Clubs));

		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

}
