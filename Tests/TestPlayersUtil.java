package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.HandStrength;
import GameObjects.Suits;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.Util;

public class TestPlayersUtil {

	@Test
	void getHandEvaluationGetsHighCardWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(3, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Clubs));
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(11, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(11, Suits.Spades));
		expectedHand.add(new Card(9, Suits.Diamonds));
		expectedHand.add(new Card(7, Suits.Clubs));
		expectedHand.add(new Card(5, Suits.Diamonds));
		
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("HighCard", eval.getHandName());
		assertEquals(HandStrength.HighCard, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsHighCardWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(4, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(11, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(11, Suits.Spades));
		expectedHand.add(new Card(9, Suits.Diamonds));
		expectedHand.add(new Card(4, Suits.Hearts));
		expectedHand.add(new Card(2, Suits.Clubs));
		
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("HighCard", eval.getHandName());
		assertEquals(HandStrength.HighCard, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsHighCardWithEmptyBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(3, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(3, Suits.Hearts));
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(-1, Suits.FillerA));
		expectedHand.add(new Card(-3, Suits.FillerB));
		expectedHand.add(new Card(-5, Suits.FillerA));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("HighCard", eval.getHandName());
		assertEquals(HandStrength.HighCard, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsPairWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Clubs));
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(11, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(11, Suits.Spades));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Pair", eval.getHandName());
		assertEquals(HandStrength.Pair, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsPairWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(11, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(11, Suits.Spades));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Pair", eval.getHandName());
		assertEquals(HandStrength.Pair, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsPairWithEmptyBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(-1, Suits.FillerA));
		expectedHand.add(new Card(-3, Suits.FillerB));
		expectedHand.add(new Card(-5, Suits.FillerA));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Pair", eval.getHandName());
		assertEquals(HandStrength.Pair, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsTwoPairWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Clubs));
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(13, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(13, Suits.Spades));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("TwoPair", eval.getHandName());
		assertEquals(HandStrength.TwoPair, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsTwoPairWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(13, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(13, Suits.Spades));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("TwoPair", eval.getHandName());
		assertEquals(HandStrength.TwoPair, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsTripsWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Clubs));
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(2, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(2, Suits.Spades));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Trips", eval.getHandName());
		assertEquals(HandStrength.Trips, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsTripsWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(2, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(9, Suits.Diamonds));
		board.add(new Card(2, Suits.Spades));
		board.add(new Card(13, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(2, Suits.Spades));
		expectedHand.add(new Card(13, Suits.Diamonds));
		expectedHand.add(new Card(9, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Trips", eval.getHandName());
		assertEquals(HandStrength.Trips, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsStraightWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(3, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Clubs));
		board.add(new Card(6, Suits.Diamonds));
		board.add(new Card(9, Suits.Spades));
		board.add(new Card(11, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(3, Suits.Hearts));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Clubs));
		expectedHand.add(new Card(6, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Straight", eval.getHandName());
		assertEquals(HandStrength.Straight, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsStraightWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Clubs));
		holeCards.add(new Card(3, Suits.Hearts));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Spades));
		board.add(new Card(6, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(3, Suits.Hearts));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Spades));
		expectedHand.add(new Card(6, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Straight", eval.getHandName());
		assertEquals(HandStrength.Straight, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsFlushWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Diamonds));
		board.add(new Card(9, Suits.Spades));
		board.add(new Card(11, Suits.Clubs));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Diamonds));
		expectedHand.add(new Card(7, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Flush", eval.getHandName());
		assertEquals(HandStrength.Flush, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsFlushWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(7, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Diamonds));
		expectedHand.add(new Card(7, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Flush", eval.getHandName());
		assertEquals(HandStrength.Flush, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsFullHouseWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(2, Suits.Diamonds));
		board.add(new Card(2, Suits.Diamonds));
		board.add(new Card(3, Suits.Diamonds));
		board.add(new Card(9, Suits.Spades));
		board.add(new Card(11, Suits.Clubs));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("FullHouse", eval.getHandName());
		assertEquals(HandStrength.FullHouse, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsFullHouseWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(2, Suits.Diamonds));
		board.add(new Card(2, Suits.Diamonds));
		board.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("FullHouse", eval.getHandName());
		assertEquals(HandStrength.FullHouse, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsQuadsWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(2, Suits.Spades));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(2, Suits.Clubs));
		board.add(new Card(2, Suits.Hearts));
		board.add(new Card(11, Suits.Diamonds));
		board.add(new Card(9, Suits.Spades));
		board.add(new Card(11, Suits.Clubs));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Spades));
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(11, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Quads", eval.getHandName());
		assertEquals(HandStrength.Quads, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsQuadsWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(2, Suits.Spades));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(2, Suits.Clubs));
		board.add(new Card(2, Suits.Hearts));
		board.add(new Card(11, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(2, Suits.Spades));
		expectedHand.add(new Card(2, Suits.Clubs));
		expectedHand.add(new Card(2, Suits.Hearts));
		expectedHand.add(new Card(11, Suits.Diamonds));
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("Quads", eval.getHandName());
		assertEquals(HandStrength.Quads, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsStraightFlushWithFilledBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(6, Suits.Diamonds));
		board.add(new Card(9, Suits.Spades));
		board.add(new Card(11, Suits.Clubs));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Diamonds));
		expectedHand.add(new Card(6, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("StraightFlush", eval.getHandName());
		assertEquals(HandStrength.StraightFlush, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
	
	@Test
	void getHandEvaluationGetsStraightFlushWithFlopBoard() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		ArrayList<Card> holeCards = new ArrayList<Card>();
		holeCards.add(new Card(2, Suits.Diamonds));
		holeCards.add(new Card(3, Suits.Diamonds));
		
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4, Suits.Diamonds));
		board.add(new Card(5, Suits.Diamonds));
		board.add(new Card(6, Suits.Diamonds));
		
		ArrayList<Card> expectedHand = new ArrayList<Card>();
		expectedHand.add(new Card(2, Suits.Diamonds));
		expectedHand.add(new Card(3, Suits.Diamonds));
		expectedHand.add(new Card(4, Suits.Diamonds));
		expectedHand.add(new Card(5, Suits.Diamonds));
		expectedHand.add(new Card(6, Suits.Diamonds));
		Collections.reverse(expectedHand);
		
		HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
		
		assertEquals("StraightFlush", eval.getHandName());
		assertEquals(HandStrength.StraightFlush, eval.getStrength());
		assertEquals(expectedHand, eval.getCards());
	}
}
