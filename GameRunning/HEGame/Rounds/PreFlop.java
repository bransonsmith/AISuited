package GameRunning.HEGame.Rounds;

import GameRunning.Seat;
import GameRunning.HEGame.Hands.HEHand;

public class PreFlop extends HERound {

	public PreFlop(HEHand _hand) throws Exception {
		super(_hand);
	}

	@Override
	protected void setDealBehavior() {
		dealBehavior = new DealBehavior(0);
	}

	@Override
	protected void setName() {
		name = "Pre Flop";
	}
	
	@Override
	protected void setCurrentBet() {
		currentBet = hand.getGame().getOptions().getBb();
	}
	
	@Override
	protected void setFirstToAct() {
		actingPosition = hand.getGame().getUTGPosition();
	}

	@Override
	protected void setStartingRoundStatuses() throws Exception {
		for (Seat s: seats) {
			if (s.isActive()) {
				if (s.getNumber() == hand.getGame().getBBPosition()) {
					s.setRoundStatus(RoundStatus.Settled);
				} else {
					s.setRoundStatus(RoundStatus.Unsettled);
				}
			}
		}
	}
	
}
