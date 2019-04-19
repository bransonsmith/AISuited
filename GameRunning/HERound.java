package GameRunning;

import java.util.List;

import GameObjects.Card;

public abstract class HERound {

	protected String name;
	protected HEHand hand;
	protected DealBehavior dealBehavior;
	protected List<RoundParticipant> participants;
	
	public HERound(HEHand _hand, List<RoundParticipant> _participants) {
		setHand(_hand);
		setParticipants(_participants);
		setName();
		setDealBehavior();
	}

	protected abstract void startRoundSetUp();
	protected abstract void setDealBehavior();
	protected abstract void setName();
	
	public String getName() {
		return name;
	}
	
	public List<Card> getDeal() {
		return dealBehavior.getDeal(hand.getDeck());
	}
	
	public List<RoundParticipant> getParticipants() {
		return participants;
	}
	
	private void setHand(HEHand _hand) {
		hand = _hand;
	}
	
	private void setParticipants(List<RoundParticipant> _participants) {
		participants = _participants;
	}

}
