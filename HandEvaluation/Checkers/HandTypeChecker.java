package HandEvaluation.Checkers;

import java.util.List;

import GameObjects.Card;
import GameObjects.HandStrength;
import HandEvaluation.Util.KickerFillProblem;

public abstract class HandTypeChecker implements Comparable<HandTypeChecker>{
	public abstract HandStrength getStrength();
	public abstract String getName();
	public abstract List<Card> getHand(List<Card> cards) throws KickerFillProblem;
	
	@Override
	public int compareTo(HandTypeChecker other) {
		return this.getStrength().compareTo(other.getStrength());
	}
}