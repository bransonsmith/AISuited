package GameRunning;

import java.util.List;

public class Flop extends HERound {

	public Flop(HEHand _hand, List<RoundParticipant> _participants) {
		super(_hand, _participants);
	}

	@Override
	protected void setDealBehavior() {
		dealBehavior = new DealBehavior(3);
	}

	@Override
	protected void setName() {
		name = "Flop";
	}
}
