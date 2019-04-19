package GameRunning.HEGame.Rounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GameObjects.Card;
import GameRunning.Seat;
import GameRunning.HEGame.Hands.HEHand;

public abstract class HERound {

	protected String name;
	protected HEHand hand;
	protected DealBehavior dealBehavior;
	protected List<Seat> seats;
	protected int currentBet;
	protected int actingPosition;
	
	public HERound(HEHand _hand) throws Exception {
		setHand(_hand);
		setSeats(_hand.getSeats());
		setName();
		setDealBehavior();
		setUp();
	}
	
	protected abstract void setDealBehavior();
	protected abstract void setName();
	
	protected void setUp() throws Exception {
		currentBet = 0;
		//actingPosition = getFirstUnsettledPositionStartingWithSB();
	}

	public String getName() {
		return name;
	}
	
	public List<Card> getDeal() {
		return dealBehavior.getDeal(hand.getDeck());
	}
	
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	
	private void setHand(HEHand _hand) {
		hand = _hand;
	}
}
