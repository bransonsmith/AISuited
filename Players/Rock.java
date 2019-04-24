package Players;

import java.util.Collections;
import java.util.List;

import GameObjects.Card;
import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;

public class Rock extends Player {

	public Rock(String _name, String ownerName) {
		super(_name, ownerName);
	}

	@Override
	public HEDecision getDecision(DecisionContext decisionContext) {
		List<Card> myHoleCards = decisionContext.getMyInfo().getHoleCards();
		if (isEliteHand(myHoleCards)) {
			return new HEDecision(Choices.Raise, decisionContext.getMyInfo().getChips());
		}
		else {
			return new HEDecision(Choices.Fold);
		}
	}

	private boolean isEliteHand(List<Card> myHoleCards) {
		if (isPocketPair(myHoleCards)) {
			return true;
		}
		if (isPlayableAce(myHoleCards)) {
			return true;
		}
		if (isTwoBroadWayCards(myHoleCards)) {
			return true;
		}
		return false;
	}
	
	private boolean isPlayableAce(List<Card> myHoleCards) {
		Collections.sort(myHoleCards, Collections.reverseOrder());
		return 	myHoleCards.get(0).getAceVal() == 14 && (  
				myHoleCards.get(1).getAceVal() >= 9 ||
				myHoleCards.get(1).getAceVal() <= 5 	) ;
	}
	
	private boolean isTwoBroadWayCards(List<Card> myHoleCards) {
		Collections.sort(myHoleCards, Collections.reverseOrder());
		return 	myHoleCards.get(0).getAceVal() >= 14 &&  
				myHoleCards.get(1).getAceVal() >= 10;
	}
	
	private boolean isPocketPair(List<Card> myHoleCards) {
		return 	myHoleCards.get(0).getAceVal() == myHoleCards.get(1).getAceVal();
	}

}
