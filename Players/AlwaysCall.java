package Players;

import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HoldEmChoice;

public class AlwaysCall extends Player {

	public AlwaysCall(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
