package Players;

import GameRunning.Choices;
import GameRunning.DecisionContext;
import GameRunning.HoldEmChoice;

public class AlwaysAllIn extends Player {

	public AlwaysAllIn(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		return new HoldEmChoice(Choices.Raise, decisionContext.getMyInfo().getChips());
	}

}
