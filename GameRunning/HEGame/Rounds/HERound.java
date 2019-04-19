package GameRunning.HEGame.Rounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Common.Logger;
import GameObjects.Card;
import GameRunning.Seat;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Hands.HandStatus;

public abstract class HERound {

	protected boolean complete;
	protected String name;
	protected HEHand hand;
	protected DealBehavior dealBehavior;
	protected List<Seat> seats;
	protected int currentBet;
	protected int actingPosition;
	
	public HERound(HEHand _hand) throws Exception {
		setHand(_hand);
		setSeats(_hand.getSeats());
		setName();
		setDealBehavior();
		setCurrentBet();
		setFirstToAct();
		setStartingRoundStatuses();
		complete = false;
	}

	protected abstract void setDealBehavior();
	protected abstract void setName();
	protected abstract void setCurrentBet();
	protected abstract void setFirstToAct();
	
	public void commenceNextAction() {
		if (noPlayersUnsettled()) {
			complete = true;
		} else {
			Seat choiceMaker = getSeatWithNumber(actingPosition);
			DecisionContext context = new DecisionContext(choiceMaker);
			Logger.log("------> " + choiceMaker.getPlayerName() + ", What would you like to do?");
			HEDecision decision = choiceMaker.getPlayer().getDecision(context);
			Logger.log("-------> " + choiceMaker.getPlayerName() + " decided to " + decision + ".");
			
			reactToDecision(decision);
			actingPosition = getNextActiveSeat(actingPosition);
		}
	}
	
	private boolean noPlayersUnsettled() {
		for (Seat s: seats) {
			if (s.getRoundStatus() == RoundStatus.Unsettled) {
				return false;
			}
		}
		return true;
	}

	private void reactToDecision(HEDecision decision) {
		
		Seat actingSeat = getSeatWithNumber(actingPosition);
		actingSeat.setRoundStatus(RoundStatus.Settled);
		
		switch(decision.getType()) {
		case Call:
			handleBet(actingSeat, decision.getAmount());
			break;
		case Raise:
			handleBet(actingSeat, decision.getAmount());
			break;
		case Check:
			break;
		case Fold:
			actingSeat.setHandStatus(HandStatus.Folded);
			break;
		default:
			break;
		
		}
		
	}

	private void handleBet(Seat actingSeat, int amount) {
		Logger.log("------->" + actingSeat.getPlayerName() + " wants to bet " + amount + " chips.");
		int actualAmount = Math.min(amount, actingSeat.getChips());
		if (actualAmount != amount) {
			Logger.log("-------->" + actingSeat.getPlayerName() + " Didnt have " + amount + " chips.");
		}
		hand.getPot().addContribution(actingSeat, actualAmount);
		Logger.log("------>" + actingSeat.getPlayerName() + " bet " + amount + " chips.");
		currentBet = Math.max(actualAmount, currentBet);
	}

	private int getNextActiveSeat(int position) {
		
		return 0;
	}

	private Seat getSeatWithNumber(int seatNum) {
		for (Seat s: seats) {
			if (s.getNumber() == seatNum) {
				return s;
			}
		}
		return null;
	}

	protected void setStartingRoundStatuses() throws Exception {
		for (Seat s: seats) {
			switch (s.getHandStatus()) {
			case Active:
				s.setRoundStatus(RoundStatus.Unsettled);
				break;
			case AllIn:
				s.setRoundStatus(RoundStatus.Settled);
				break;
			case Folded:
				s.setRoundStatus(RoundStatus.Settled);
				break;
			case Loser:
				s.setRoundStatus(RoundStatus.Settled);
				break;
			case NeverInvolved:
				s.setRoundStatus(RoundStatus.Settled);
				break;
			default:
				s.setRoundStatus(RoundStatus.Settled);
				break;
			}
		}
	}

	public HEHand getHand() {
		return hand;
	}
	
	public String getName() {
		return name;
	}
	
	public void setActingPosition(int _position) {
		actingPosition = _position;
	}
	public int getActingPosition() {
		return actingPosition;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	
	public List<Card> getDeal() {
		return dealBehavior.getDeal(hand.getDeck());
	}
	
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	
	private void setHand(HEHand _hand) {
		hand = _hand;
	}

	public boolean isComplete() {
		return complete;
	}
	public boolean isNotComplete() {
		return !complete;
	}
}
