package Players;

import java.util.List;
import java.util.Random;

import GameObjects.Card;
import GameObjects.HandStrength;
import GameRunning.Decisions.Choices;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class SimpleSteve extends Player {

	private double callRate;
	private double raiseRate;
	private double confidenceLevel;
	
	public SimpleSteve(String _name, String ownerName) {
		super(_name, ownerName);
		callRate = .5;
		raiseRate = .05;
		confidenceLevel = 100.0;
	}

	@Override
	public HEDecision getDecision(DecisionContext decisionContext) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		confidenceLevel = 100.0;

		if (Util.isPreFlop(decisionContext.getGameInfo())) {
			if (inOpeningRange(decisionContext.getMyInfo().getHoleCards())) {
				confidenceLevel = 75;
			} else {
				int randomNum = new Random().nextInt(100);
				if (randomNum >= 96) {
					confidenceLevel = 95;
				} else {
					confidenceLevel = 0;
				}
			}
			
		} else if (Util.isFlop(decisionContext.getGameInfo())) {
			List<Card> holeCards = decisionContext.getMyInfo().getHoleCards();
			List<Card> board = decisionContext.getGameInfo().getBoard();
			HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
			
			if (eval.getStrength().ordinal() >= HandStrength.Pair.ordinal()) {
				confidenceLevel = 50 + 10 * eval.getStrength().ordinal();
			}
			
		} else if (Util.isTurn(decisionContext.getGameInfo())) {
			List<Card> holeCards = decisionContext.getMyInfo().getHoleCards();
			List<Card> board = decisionContext.getGameInfo().getBoard();
			HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
			
			if (eval.getStrength().ordinal() >= HandStrength.Pair.ordinal()) {
				confidenceLevel = 45 + 10 * eval.getStrength().ordinal();
			}
		} else if (Util.isRiver(decisionContext.getGameInfo())) {
			List<Card> holeCards = decisionContext.getMyInfo().getHoleCards();
			List<Card> board = decisionContext.getGameInfo().getBoard();
			HandEvaluation eval = Util.getHandEvaluation(holeCards, board);
			
			if (eval.getStrength().ordinal() >= HandStrength.Pair.ordinal()) {
				confidenceLevel = 35 + 10 * eval.getStrength().ordinal();
			}
		}
		
		if (confidenceLevel > 35) {
			if (shouldRaise(decisionContext)){
				int raiseAmount = getRaiseAmount(decisionContext);
				return new HEDecision(Choices.Raise, raiseAmount);
			} else if (shouldCall(decisionContext)) {
				return new HEDecision(Choices.Call, decisionContext.getCurrentBet());
			}
		}
		if (canCheck(decisionContext)) {
			return new HEDecision(Choices.Check);
		}
		return new HEDecision(Choices.Fold);
	}

	private boolean inOpeningRange(List<Card> holeCards) {
		if (Util.isPocketPair(holeCards)) { return true; }
		if (isHighAce(holeCards)) { return true; }
		if (Util.isTwoBroadWayCards(holeCards)) { return true; }
		if (Util.isSuitedConnector(holeCards)) {
			int randomNum = new Random().nextInt(100);
			if (randomNum >= 40) {
				return true;
			}
		}
		return false;
	}

	private boolean isHighAce(List<Card> holeCards) {
		if ((holeCards.get(0).getAceVal() == 14 &&
			 holeCards.get(1).getAceVal() >= 8  )||
			(holeCards.get(0).getAceVal() >= 8  &&
			 holeCards.get(1).getAceVal() == 14  )) {
			return true;
		}
		return false;
	}

	private boolean canCheck(DecisionContext decisionContext) {
		return decisionContext.getNeededToCall() == 0;
	}

	private boolean shouldCall(DecisionContext decisionContext) {
		double betRatio = (double)decisionContext.getNeededToCall() / decisionContext.getGameInfo().getPot(); 
		double inverseBetRatio = 1 - betRatio;
		
		
		if (confidenceLevel >= 90) return true;
		if (inverseBetRatio >= .85) return true;
		
		
		if (inverseBetRatio <= 0) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 60) {
				if (randomNum > 10) return true;
			} 
		}
		if (inverseBetRatio <= .75) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 55) {
				if (randomNum > 10) return true;
			} 
		}
		if (inverseBetRatio <= .33) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 40) {
				if (randomNum > 10) return true;
			} 
		}
		
		return rateCheck(callRate);
	}

	private boolean shouldRaise(DecisionContext decisionContext) {
		
		double betRatio = (double)decisionContext.getNeededToCall() / decisionContext.getGameInfo().getPot(); 
		double inverseBetRatio = 1 - betRatio;
		if (true) return false;
		if 	(confidenceLevel >= 100) return true;
		
		if (inverseBetRatio <= 0) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 60) {
				if (randomNum > 10) return true;
			} 
		}
		if (inverseBetRatio <= .75) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 55) {
				if (randomNum > 10) return true;
			} 
		}
		if (inverseBetRatio <= .33) {
			int randomNum = new Random().nextInt(100);
			if (confidenceLevel > 40) {
				if (randomNum > 10) return true;
			} 
		}
		
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
