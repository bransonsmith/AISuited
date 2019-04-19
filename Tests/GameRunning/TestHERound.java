package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.Flop;
import GameRunning.HEHand;
import GameRunning.HERound;
import GameRunning.PreFlop;
import GameRunning.RoundParticipant;

public class TestHERound {

	private HEHand getBasicHand() {
		Deck deck = new Deck();
		deck.refresh();
		HEHand hand = new HEHand(deck);
		return hand;
	}
	
	@Test
	void PreFlopDealsNoCards() {
		List<RoundParticipant> participants = new ArrayList<RoundParticipant>();
		HERound round = new PreFlop(getBasicHand(), participants);
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 0);
	}
	
	@Test
	void FlopDeals3Cards() {
		List<RoundParticipant> participants = new ArrayList<RoundParticipant>();
		HERound round = new Flop(getBasicHand(), participants);
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 3);
	}
	
	@Test
	void TurnDeals1Card() {
		List<RoundParticipant> participants = new ArrayList<RoundParticipant>();
		HERound round = new Turn(getBasicHand(), participants);
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 1);
	}
	
	@Test
	void RiverDeals1Card() {
		List<RoundParticipant> participants = new ArrayList<RoundParticipant>();
		HERound round = new River(getBasicHand(), participants);
		List<Card> dealt = round.getDeal();
		
		assertEquals(dealt.size(), 1);
	}
	
	@Test
	void RoundsGetCorrectNames() {
		List<RoundParticipant> participants = new ArrayList<RoundParticipant>();
		assertEquals(new PreFlop(getBasicHand(), participants).getName(), "Pre Flop");
		assertEquals(new Flop(getBasicHand(), participants).getName(), "Flop");
		assertEquals(new Turn(getBasicHand(), participants).getName(), "Turn");
		assertEquals(new River(getBasicHand(), participants).getName(), "River");
	}
}
