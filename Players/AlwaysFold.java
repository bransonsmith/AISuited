package Players;

import GameRunning.Choices;
import GameRunning.HoldEmChoice;

public class AlwaysFold extends Player {

	public AlwaysFold(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision() {
		return new HoldEmChoice(Choices.Fold);
	}

}
