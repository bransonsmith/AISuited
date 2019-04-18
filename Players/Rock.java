package Players;

import java.util.Collections;
import java.util.List;

import GameObjects.Card;
import GameRunning.Choices;
import GameRunning.DecisionContext;
import GameRunning.HoldEmChoice;

public class Rock extends Player {

	public Rock(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HoldEmChoice getDecision(DecisionContext decisionContext) {
		List<Card> myHoleCards = decisionContext.getMyInfo().getHoleCards();
		if (isEliteHand(myHoleCards)) {
			return new HoldEmChoice(Choices.Raise, decisionContext.getMyInfo().getChips());
		}
		else {
			return new HoldEmChoice(Choices.Fold);
		}
	}

	private boolean isEliteHand(List<Card> myHoleCards) {
		if (isHighPocketPair(myHoleCards)) {
			return true;
		}
		if (isHighAce(myHoleCards)) {
			return true;
		}
		return false;
	}
	
	private boolean isHighAce(List<Card> myHoleCards) {
		Collections.sort(myHoleCards, Collections.reverseOrder());
		return 	myHoleCards.get(0).getAceVal() == 14 &&  
				myHoleCards.get(1).getAceVal() >= 12;
	}
	
	private boolean isHighPocketPair(List<Card> myHoleCards) {
		return 	myHoleCards.get(0).getAceVal() >= 9 &&
				myHoleCards.get(0).getAceVal() == myHoleCards.get(1).getAceVal();
	}

}
