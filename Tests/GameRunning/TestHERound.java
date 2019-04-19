package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Rounds.Flop;
import GameRunning.HEGame.Rounds.HERound;
import GameRunning.HEGame.Rounds.PreFlop;
import GameRunning.HEGame.Rounds.River;
import GameRunning.HEGame.Rounds.Turn;
import Players.AlwaysFold;
import Players.Player;

public class TestHERound {

	private HEHand getBasicHand() throws Exception {
		Deck deck = new Deck();
		deck.refresh();
		
		List<Player> players = new ArrayList<Player>();
		players.add(new AlwaysFold("Branson", "bran owner"));
		HEGame game = new HEGame(players);
		
		HEHand hand = new HEHand(game, deck);
		return hand;
	}
	
	private HERound getRoundWith5() throws Exception {
		
		return new Flop(getBasicHand());
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
	void getFirstUnsettledPositionStartingWithSBBasic() throws Exception {
		HERound round = getRoundWith5();
		
		
		
	}
}
