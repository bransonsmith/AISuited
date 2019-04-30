package Players;

import java.util.Random;

import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;

public class SimpleSteve extends Player {

	private double callRate;
	private double raiseRate;
	
	public SimpleSteve(String _name, String ownerName) {
		super(_name, ownerName);
		callRate = .3;
		raiseRate = .1;
	}

	@Override
	public HEDecision getDecision(DecisionContext decisionContext) {
		
		double confidenceLevel = 100.0;
		
		if (confidenceLevel > 50) {
			if (shouldRaise()){
				int raiseAmount = getRaiseAmount(decisionContext);
				return new HEDecision(Choices.Raise, raiseAmount);
			} else if (shouldCall()) {
				return new HEDecision(Choices.Call, decisionContext.getCurrentBet());
			}
		}
		if (canCheck(decisionContext)) {
			return new HEDecision(Choices.Check);
		}
		return new HEDecision(Choices.Fold);
	}

	private boolean canCheck(DecisionContext decisionContext) {
		return decisionContext.getNeededToCall() == 0;
	}

	private boolean shouldCall() {
		return rateCheck(callRate);
	}

	private boolean shouldRaise() {
		return rateCheck(raiseRate);
	}

	private boolean rateCheck(double rate) {
		Random rand = new Random();
		double randomNumber = rand.nextInt(100);
		
		return randomNumber >= (100 - (100 * rate));
	}
	
	private int getRaiseAmount(DecisionContext decisionContext) {
		return decisionContext.getCurrentBet() * 2;
	}

}
