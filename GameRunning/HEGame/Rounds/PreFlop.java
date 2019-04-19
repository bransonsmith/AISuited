package GameRunning.HEGame.Rounds;

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

}
