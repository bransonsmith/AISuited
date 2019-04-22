package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Suits;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.HEOptions;
import GameRunning.HEGame.Hands.HEHand;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.AlwaysAllIn;
import Players.Player;

public class TestHEHand {

	private boolean silentMode = false;
	
	private HEHand getHandWith2BoughtInPlayers() throws Exception {
		HEOptions options = new HEOptions(1000, 100, 50);
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(new Seat(1));
		seats.add(new Seat(2));
		seats.get(0).setPlayer(new AlwaysAllIn("A", "a"));
		seats.get(1).setPlayer(new AlwaysAllIn("B", "b"));
		seats.get(0).setChips(options.getBuyIn());
		seats.get(1).setChips(options.getBuyIn());
		
		seats.get(0).addCardToHand(new Card(7, Suits.Diamonds));
		seats.get(0).addCardToHand(new Card(8, Suits.Diamonds));
		seats.get(1).addCardToHand(new Card(9, Suits.Diamonds));
		seats.get(1).addCardToHand(new Card(10, Suits.Diamonds));

		HEGame game = new HEGame(seats, options);
		HEHand hand = new HEHand(game);
		
		List<Card> cardsInBoard = new ArrayList<Card>();
		cardsInBoard.add(new Card(1, Suits.Hearts));
		cardsInBoard.add(new Card(2, Suits.Hearts));
		cardsInBoard.add(new Card(3, Suits.Hearts));
		cardsInBoard.add(new Card(4, Suits.Hearts));
		cardsInBoard.add(new Card(6, Suits.Diamonds));
		hand.addToBoard(cardsInBoard);
		
		return hand;
	}
	
	private HEHand getHandWith3TiedPlayers() throws Exception {
		HEOptions options = new HEOptions(1000, 100, 50);
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(new Seat(1));
		seats.add(new Seat(2));
		seats.add(new Seat(3));
		
		seats.get(0).setPlayer(new AlwaysAllIn("A", "a"));
		seats.get(1).setPlayer(new AlwaysAllIn("B", "b"));
		seats.get(2).setPlayer(new AlwaysAllIn("C", "c"));
		
		seats.get(0).setChips(options.getBuyIn());
		seats.get(1).setChips(options.getBuyIn());
		seats.get(2).setChips(options.getBuyIn());
		
		seats.get(0).addCardToHand(new Card(9, Suits.Diamonds));
		seats.get(0).addCardToHand(new Card(10, Suits.Diamonds));
		seats.get(1).addCardToHand(new Card(9, Suits.Diamonds));
		seats.get(1).addCardToHand(new Card(10, Suits.Diamonds));
		seats.get(2).addCardToHand(new Card(9, Suits.Diamonds));
		seats.get(2).addCardToHand(new Card(10, Suits.Diamonds));

		HEGame game = new HEGame(seats, options);
		HEHand hand = new HEHand(game);
		
		List<Card> cardsInBoard = new ArrayList<Card>();
		cardsInBoard.add(new Card(1, Suits.Hearts));
		cardsInBoard.add(new Card(2, Suits.Hearts));
		cardsInBoard.add(new Card(3, Suits.Hearts));
		cardsInBoard.add(new Card(4, Suits.Hearts));
		cardsInBoard.add(new Card(6, Suits.Diamonds));
		hand.addToBoard(cardsInBoard);
		
		return hand;
	}
	
	@Test
	void showdownSetsHandStatusToComplete() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith2BoughtInPlayers();
		assertEquals(hand.isComplete(), false);
		hand.commenceShowDown(true);
		assertEquals(hand.isComplete(), true);
	}
	
	@Test
	void showdownSingleWinner() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith2BoughtInPlayers();
		hand.getPot().addContribution(hand.getSeats().get(0), 100);
		hand.getPot().addContribution(hand.getSeats().get(1), 100);
		
		assertEquals(hand.getPot().getTotal(), 200);
		hand.commenceShowDown(silentMode);
		assertEquals(hand.getPot().getTotal(), 0);
		assertEquals(hand.getSeats().get(0).getChips(), 1000);
		assertEquals(hand.getSeats().get(1).getChips(), 1200);
	}
	
	@Test
	void showdownFor3WayTie() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith3TiedPlayers();
		hand.getPot().addContribution(hand.getSeats().get(0), 100);
		hand.getPot().addContribution(hand.getSeats().get(1), 100);
		hand.getPot().addContribution(hand.getSeats().get(2), 100);
		
		assertEquals(hand.getPot().getTotal(), 300);
		hand.commenceShowDown(silentMode);
		assertEquals(hand.getPot().getTotal(), 0);
		assertEquals(hand.getSeats().get(0).getChips(), 1100);
		assertEquals(hand.getSeats().get(1).getChips(), 1100);
		assertEquals(hand.getSeats().get(2).getChips(), 1100);
	}
	
	@Test
	void showdownFor3WayTieLeftovers() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith3TiedPlayers();
		hand.getPot().addContribution(hand.getSeats().get(0), 100);
		hand.getPot().addContribution(hand.getSeats().get(1), 100);
		hand.getPot().addContribution(hand.getSeats().get(2), 100);
		hand.getPot().addContribution(new Seat(4), 2);
		assertEquals(hand.getPot().getTotal(), 302);
		hand.commenceShowDown(silentMode);
		assertEquals(hand.getPot().getTotal(), 0);
		assertEquals(hand.getSeats().get(0).getChips(), 1101);
		assertEquals(hand.getSeats().get(1).getChips(), 1101);
		assertEquals(hand.getSeats().get(2).getChips(), 1100);
	}
	
	@Test
	void showdownTieWithSidePot() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith3TiedPlayers();
		hand.getPot().addContribution(hand.getSeats().get(0), 50);
		hand.getPot().addContribution(hand.getSeats().get(1), 100);
		hand.getPot().addContribution(hand.getSeats().get(2), 100);
		assertEquals(hand.getPot().getTotal(), 250);
		hand.commenceShowDown(silentMode);
		assertEquals(hand.getPot().getTotal(), 0);
		assertEquals(hand.getSeats().get(0).getChips(), 1050);
		assertEquals(hand.getSeats().get(1).getChips(), 1100);
		assertEquals(hand.getSeats().get(2).getChips(), 1100);
	}
	
	@Test
	void showdownWithSidePot() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		HEHand hand = getHandWith2BoughtInPlayers();
		hand.getPot().addContribution(hand.getSeats().get(0), 100);
		hand.getPot().addContribution(hand.getSeats().get(1), 50);
		assertEquals(hand.getPot().getTotal(), 150);
		hand.commenceShowDown(silentMode);
		assertEquals(hand.getPot().getTotal(), 0);
		assertEquals(hand.getSeats().get(0).getChips(), 1050);
		assertEquals(hand.getSeats().get(1).getChips(), 1100);
	}
	
}
