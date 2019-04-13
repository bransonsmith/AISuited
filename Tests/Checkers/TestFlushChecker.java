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
import HandEvaluation.Checkers.FlushChecker;
import HandEvaluation.Util.KickerFillProblem;

public class TestFlushChecker {

	private FlushChecker sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new FlushChecker();
	}

	@Test
	void getStrengthReturnsFlush() {
		assertEquals(sut.getStrength(), HandStrength.Flush);
	}
	
	@Test
	void getNameReturnsFlush() {
		assertEquals(sut.getName(), "Flush");
	}

	@Test
	void getHandNullIfNoFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinHighCardBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNull(result);
	}
	
	@Test
	void getHandNotNullIfFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinFlushBySuit();
		List<Card> result = sut.getHand(hand);
		
		assertNotNull(result);
	}
	
	@Test
	void getHandPutsFlushFirst() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMinFlushBySuit();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(7, Suits.Hearts));
		expected.add(new Card(5, Suits.Hearts));
		expected.add(new Card(4, Suits.Hearts));
		expected.add(new Card(3, Suits.Hearts));
		expected.add(new Card(2, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}

	@Test
	void getHandPutsFlushFirstAce() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxFlushBySuit();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Hearts));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(12, Suits.Hearts));
		expected.add(new Card(11, Suits.Hearts));
		expected.add(new Card(9, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getHandGetsHighestFlush() throws KickerFillProblem {
		List<Card> hand = HandFactory.getMaxFlushBySuit();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(14, Suits.Hearts));
		expected.add(new Card(13, Suits.Hearts));
		expected.add(new Card(12, Suits.Hearts));
		expected.add(new Card(11, Suits.Hearts));
		expected.add(new Card(9, Suits.Hearts));
		
		List<Card> actual = sut.getHand(hand);
		
		assertEquals(expected, actual);
	}
}
