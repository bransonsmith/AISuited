package Tests.GameRunning;

import java.util.List;

import GameRunning.DealBehavior;
import GameRunning.HEHand;
import GameRunning.HERound;
import GameRunning.RoundParticipant;

public class River extends HERound {

	public River(HEHand _hand, List<RoundParticipant> _participants) {
		super(_hand, _participants);
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
