package Players;

import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HoldEmChoice;

public class AlwaysAllIn extends Player {

	public AlwaysAllIn(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		return new HoldEmChoice(Choices.Raise, decisionContext.getMyInfo().getChips());
	}

}
