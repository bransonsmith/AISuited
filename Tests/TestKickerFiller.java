package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Suits;
import HandEvaluation.Util.KickerFillProblem;
import HandEvaluation.Util.KickerFiller;

class TestKickerFiller {

	@Test
	void KickerFillNoUsedCards() throws KickerFillProblem {
		
		int handSize = 5;
		List<Card> usedCards = new ArrayList<Card>();
		
		List<Card> unusedCards = new ArrayList<Card>();
		unusedCards.add(new Card(2, Suits.Clubs));
		unusedCards.add(new Card(11, Suits.Spades));
		unusedCards.add(new Card(7, Suits.Clubs));
		unusedCards.add(new Card(13, Suits.Spades));
		unusedCards.add(new Card(4, Suits.Clubs));
		unusedCards.add(new Card(9, Suits.Clubs));
		unusedCards.add(new Card(10, Suits.Hearts));
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(13, Suits.Spades));
		expected.add(new Card(11, Suits.Spades));
		expected.add(new Card(10, Suits.Hearts));
		expected.add(new Card(9, Suits.Clubs));
		expected.add(new Card(7, Suits.Clubs));
		
		List<Card> actual = KickerFiller.Fill(handSize, usedCards, unusedCards);
		
		assertEquals(expected, actual);
	}

	@Test
	void KickerFillTooManyUsedCards() throws KickerFillProblem {
		
		int handSize = 5;
		List<Card> usedCards = new ArrayList<Card>();
		usedCards.add(new Card(2, Suits.Clubs));
		usedCards.add(new Card(11, Suits.Spades));
		usedCards.add(new Card(7, Suits.Clubs));
		usedCards.add(new Card(13, Suits.Spades));
		usedCards.add(new Card(4, Suits.Clubs));
		usedCards.add(new Card(9, Suits.Clubs));
		usedCards.add(new Card(10, Suits.Hearts));
		
		List<Card> unusedCards = new ArrayList<Card>();
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(13, Suits.Spades));
		expected.add(new Card(11, Suits.Spades));
		expected.add(new Card(10, Suits.Hearts));
		expected.add(new Card(9, Suits.Clubs));
		expected.add(new Card(7, Suits.Clubs));
		
		List<Card> actual = KickerFiller.Fill(handSize, usedCards, unusedCards);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void KickerFillNotEnoughCardsThrowsKickerFillProblem() throws KickerFillProblem {
		
		int handSize = 5;
		List<Card> usedCards = new ArrayList<Card>();
		usedCards.add(new Card(2, Suits.Clubs));
		usedCards.add(new Card(11, Suits.Spades));
		
		List<Card> unusedCards = new ArrayList<Card>();
		unusedCards.add(new Card(7, Suits.Clubs));
		unusedCards.add(new Card(13, Suits.Spades));
		
		Throwable exception = assertThrows(KickerFillProblem.class, () -> KickerFiller.Fill(handSize, usedCards, unusedCards));
	    
		String expectedMessage = "Kicker Fill created hand with " + (usedCards.size() + unusedCards.size()) + " cards instead of the expected " + handSize;
		assertEquals(expectedMessage, exception.getMessage());
	}
	
	@Test
	void KickerFillUsedCardsSortedFirst() throws KickerFillProblem {
		
		int handSize = 5;
		List<Card> usedCards = new ArrayList<Card>();
		usedCards.add(new Card(2, Suits.Clubs));
		usedCards.add(new Card(11, Suits.Spades));
		usedCards.add(new Card(7, Suits.Clubs));
		
		List<Card> unusedCards = new ArrayList<Card>();
		unusedCards.add(new Card(13, Suits.Spades));
		unusedCards.add(new Card(4, Suits.Clubs));
		unusedCards.add(new Card(9, Suits.Clubs));
		unusedCards.add(new Card(10, Suits.Hearts));
		
		
		List<Card> expected = new ArrayList<Card>();
		expected.add(new Card(11, Suits.Spades));
		expected.add(new Card(7, Suits.Clubs));
		expected.add(new Card(2, Suits.Clubs));
		expected.add(new Card(13, Suits.Spades));
		expected.add(new Card(10, Suits.Hearts));
		
		List<Card> actual = KickerFiller.Fill(handSize, usedCards, unusedCards);
		
		assertEquals(expected, actual);
	}
	
}
