package GameRunning;

import java.util.List;

public class PreFlop extends HERound {

	public PreFlop(HEHand _hand, List<RoundParticipant> _participants) {
		super(_hand, _participants);
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
	protected void startRoundSetUp() {
		
		
	}

}
