package GameRunning.HEGame.Rounds;

import GameRunning.HEGame.Hands.HEHand;

public class River extends HERound {

	public River(HEHand _hand) throws Exception {
		super(_hand);
	}

	@Override
	protected void setDealBehavior() {
		dealBehavior = new DealBehavior(1);
	}
	
	@Override
	protected void setName() {
		name = "River";
	}

}
