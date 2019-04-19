package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.HEOptions;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Rounds.Flop;
import GameRunning.HEGame.Rounds.HERound;
import GameRunning.HEGame.Rounds.PreFlop;
import GameRunning.HEGame.Rounds.River;
import GameRunning.HEGame.Rounds.Turn;
import Players.AlwaysFold;
import Players.Player;

public class TestHERound {

	private HEGame getBasicGame() throws Exception {
		List<Player> players = new ArrayList<Player>();
		players.add(new AlwaysFold("Branson", "Branson owner"));
		players.add(new AlwaysFold("Trent", "Trent owner"));
		players.add(new AlwaysFold("Max", "Max owner"));
		players.add(new AlwaysFold("Tubz", "Tubz owner"));
		players.add(new AlwaysFold("Mamaux", "Mamaux owner"));
		
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(new Seat(1)); 
		seats.add(new Seat(2)); 
		seats.add(new Seat(3)); 
		seats.add(new Seat(4));
		seats.add(new Seat(5));
		
		for (int i = 0; i < seats.size(); i++) {
			seats.get(i).setPlayer(players.get(i));
		}
		
		HEOptions options = new HEOptions(1000, 100, 50);
		
		return new HEGame(seats, options);
	}
	
	private HEHand getBasicHand() throws Exception {
		Deck deck = new Deck();
		deck.refresh();
		HEGame game = getBasicGame();
		
		HEHand hand = new HEHand(game);
		return hand;
	}

	private HERound getBasicPreFlop() throws Exception {
		return new PreFlop(getBasicHand());
	}
	private HERound getBasicFlop() throws Exception {
		return new Flop(getBasicHand());
	}
	private HERound getBasicTurn() throws Exception {
		return new Turn(getBasicHand());
	}
	private HERound getBasicRiver() throws Exception {
		return new River(getBasicHand());
	}
	
	@Test
	void PreFlopDealsNoCards() throws Exception {
		HERound round = new PreFlop(getBasicHand());
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 0);
	}
	
	@Test
	void FlopDeals3Cards() throws Exception {
		HERound round = new Flop(getBasicHand());
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 3);
	}
	
	@Test
	void TurnDeals1Card() throws Exception {
		HERound round = new Turn(getBasicHand());
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 1);
	}
	
	@Test
	void RiverDeals1Card() throws Exception {
		HERound round = new River(getBasicHand());
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 1);
	}
	
	@Test
	void RoundsGetCorrectNames() throws Exception {
		assertEquals(new PreFlop(getBasicHand()).getName(), "Pre Flop");
		assertEquals(new Flop(getBasicHand()).getName(), "Flop");
		assertEquals(new Turn(getBasicHand()).getName(), "Turn");
		assertEquals(new River(getBasicHand()).getName(), "River");
	}
	
	@Test
	void RoundsSetCorrectStartingBet() throws Exception {
		assertEquals(new PreFlop(getBasicHand()).getCurrentBet(), 100);
		assertEquals(new Flop(getBasicHand()).getCurrentBet(), 0);
		assertEquals(new Turn(getBasicHand()).getCurrentBet(), 0);
		assertEquals(new River(getBasicHand()).getCurrentBet(), 0);
	}
	
	@Test
	void setFirstToAct() throws Exception {
		HERound preflop = new PreFlop(getBasicHand());
		HERound flop	= new Flop(getBasicHand());
		HERound turn 	= new Turn(getBasicHand());
		HERound river 	= new River(getBasicHand());
		
		assertEquals(preflop.getActingPosition(), preflop.getHand().getGame().getUTGPosition());
		assertEquals(flop.getActingPosition(), flop.getHand().getGame().getSBPosition());
		assertEquals(turn.getActingPosition(), turn.getHand().getGame().getSBPosition());
		assertEquals(river.getActingPosition(), river.getHand().getGame().getSBPosition());
	}
}
