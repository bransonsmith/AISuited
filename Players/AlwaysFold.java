package Players;

import GameRunning.Choices;
import GameRunning.DecisionContext;
import GameRunning.HoldEmChoice;

public class AlwaysFold extends Player {

	public AlwaysFold(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		return new HoldEmChoice(Choices.Fold);
	}

}
