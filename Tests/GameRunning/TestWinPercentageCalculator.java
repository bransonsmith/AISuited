package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Suits;
import GameRunning.Seat;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.Rock;
import WinPercentage.WinPercent;
import WinPercentage.WinPercentageCalculator;

public class TestWinPercentageCalculator {

	@Test
	void calcForHeadsUpTurnDealt() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> board = new ArrayList<Card>();
		board.add(new Card(3, Suits.Hearts));
		board.add(new Card(14, Suits.Clubs));
		board.add(new Card(4, Suits.Hearts));
		board.add(new Card(14, Suits.Diamonds));
		
		Seat seatA = new Seat(1);
		Seat seatB = new Seat(2);
		
		seatA.setPlayer(new Rock("BRAN", ""));
		seatB.setPlayer(new Rock("SON ", ""));
		
		List<Card> handA = new ArrayList<Card>();
		handA.add(new Card(14, Suits.Hearts));
		handA.add(new Card(14, Suits.Spades));
		seatA.setHoleCards(handA);
		
		List<Card> handB = new ArrayList<Card>();
		handB.add(new Card(5, Suits.Hearts));
		handB.add(new Card(6, Suits.Hearts));
		seatB.setHoleCards(handB);
		
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seatA);
		seats.add(seatB);

		List<WinPercent> results = WinPercentageCalculator.getWinPercents(seats, board);
		
		assertEquals(95.45454545454545, results.get(0).getPercent());
		assertEquals(4.545454545454546, results.get(1).getPercent());
	}
	
	@Test
	void calcFor3TurnDealt() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Card> board = new ArrayList<Card>();
		board.add(new Card(3, Suits.Hearts));
		board.add(new Card(14, Suits.Clubs));
		board.add(new Card(4, Suits.Hearts));
		board.add(new Card(14, Suits.Diamonds));
		
		Seat seatA = new Seat(1);
		Seat seatB = new Seat(2);
		Seat seatC = new Seat(3);
		
		seatA.setPlayer(new Rock("BRAN", ""));
		seatB.setPlayer(new Rock("SON ", ""));
		seatC.setPlayer(new Rock("SMITH", ""));
		
		List<Card> handA = new ArrayList<Card>();
		handA.add(new Card(14, Suits.Hearts));
		handA.add(new Card(14, Suits.Spades));
		seatA.setHoleCards(handA);
		
		List<Card> handB = new ArrayList<Card>();
		handB.add(new Card(5, Suits.Hearts));
		handB.add(new Card(6, Suits.Hearts));
		seatB.setHoleCards(handB);
		
		List<Card> handC = new ArrayList<Card>();
		handC.add(new Card(9, Suits.Diamonds));
		handC.add(new Card(10, Suits.Clubs));
		seatC.setHoleCards(handC);
		
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seatA);
		seats.add(seatB);
		seats.add(seatC);

		List<WinPercent> results = WinPercentageCalculator.getWinPercents(seats, board);
		
		assertEquals(95.23809523809524, results.get(0).getPercent());
		assertEquals(4.761904761904762, results.get(1).getPercent());
		assertEquals(0 , results.get(2).getPercent());
	}
	
}
