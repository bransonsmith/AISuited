package GameRunning.HEGame.Rounds;

import GameRunning.HEGame.Hands.HEHand;

public class Flop extends HERound {

	public Flop(HEHand _hand) throws Exception {
		super(_hand);
	}

	@Override
	protected void setDealBehavior() {
		dealBehavior = new DealBehavior(3);
	}

	@Override
	protected void setName() {
		name = "Flop";
	}

	@Override
	protected void setCurrentBet() {
		currentBet = 0;
	}

	@Override
	protected void setFirstToAct() {
		actingPosition = hand.getGame().getSBPosition();
	}

}
