package Players;

import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;

public class AlwaysFold extends Player {

	public AlwaysFold(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HEDecision getDecision(DecisionContext decisionContext) {
		return new HEDecision(Choices.Fold);
	}

}
