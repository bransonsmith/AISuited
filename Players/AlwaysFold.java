package Players;

import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HoldEmChoice;

public class AlwaysFold extends Player {

	public AlwaysFold(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		return new HoldEmChoice(Choices.Fold);
	}

}
