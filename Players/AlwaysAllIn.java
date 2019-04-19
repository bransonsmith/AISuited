package Players;

import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;

public class AlwaysAllIn extends Player {

	public AlwaysAllIn(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HEDecision getDecision(DecisionContext decisionContext) {
		return new HEDecision(Choices.Raise, decisionContext.getMyInfo().getChips());
	}

}
