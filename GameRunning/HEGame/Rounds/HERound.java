package GameRunning.HEGame.Rounds;

import java.util.ArrayList;
import java.util.List;

import Common.Logger;
import GameObjects.Card;
import GameRunning.IEventer;
import GameRunning.Seat;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Pot;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Hands.HandStatus;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public abstract class HERound extends IEventer {

	protected String name;
	protected HEHand hand;
	protected DealBehavior dealBehavior;
	protected List<Seat> seats;
	protected int currentBet;
	protected int actingPosition;
	protected boolean dealt;
	private HEDecision decision;
	private String state;
	private Pot pot;
	
	public HERound(HEHand _hand) throws Exception {
		setHand(_hand);
		setSeats(_hand.getSeats());
		setName();
		setDealBehavior();
		setCurrentBet();
		setFirstToAct();
		setStartingRoundStatuses();
		pot = new Pot();
		state = null;
		child = null;
		decision = null;
		isComplete = false;
		hand.addToBoard(dealBehavior.getDeal(hand.getDeck()));
	}

	protected abstract void setDealBehavior();
	protected abstract void setName();
	protected abstract void setCurrentBet();
	protected abstract void setFirstToAct();

	@Override
	public void commenceMyNextEvent() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		HEGame game = hand.getGame();
		if (noPlayersUnsettled()) {
			
			game.addMessage("No Players Unsettled... The round is complete!");
			isComplete = true;
			state = null;
			return;
		} 
		if (state == null) {

			decision = null;
			while (decision == null) {
				decision = getPlayerDecision();
				if (decision == null) {
					actingPosition = getNextPositionNumber(actingPosition);
				}
			}
			state = "have answer";
			
		} else if (state.equals("have answer")) {
			
			reactToDecision(decision);
			state = null;
			actingPosition = getNextPositionNumber(actingPosition);
			
		}
	}
	
	private HEDecision getPlayerDecision() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		HEGame game = hand.getGame();
		HEDecision decision = null;
		Seat choiceMaker = getSeatWithNumber(actingPosition);
		
		if (choiceMaker.getRoundStatus() == RoundStatus.Unsettled) {
			DecisionContext context = new DecisionContext(choiceMaker, game.getSeats(), game);
			game.addMessage("" + choiceMaker.getPlayerName() + ", What would you like to do?");
			decision = choiceMaker.getPlayer().getDecision(context);
		}
		
		return decision;
	}
	
	private void reactToDecision(HEDecision decision) {
		
		Seat actingSeat = getSeatWithNumber(actingPosition);
		actingSeat.setRoundStatus(RoundStatus.Settled);
		int amount;
		String actionString = "";
		
		if (decision == null) {
			return;
		}
		
		switch(decision.getType()) {
		case Call:
			amount = handleBet(actingSeat, decision.getAmount());
			actionString = "call " + amount + ".";
			if (actingSeat.getHandStatus() == HandStatus.AllIn) {
				actionString = "call and is ALL IN!";
			}
			break;
		case Raise:
			amount = handleBet(actingSeat, decision.getAmount());
			actionString = "raise for a total bet of " + amount + ".";
			if (actingSeat.getHandStatus() == HandStatus.AllIn) {
				actionString = "raise and is ALL IN!";
			}
			break;
		case Check:
			actionString = "check. KNK KNK";
			break;
		case Fold:
			actingSeat.setHandStatus(HandStatus.Folded);
			actionString = "fold.";
			break;
		default:
			break;
		}
		
		HEGame game = hand.getGame();
		game.addMessage(actingSeat.getPlayerName() + " decided to " + actionString);
	}
	
	private boolean noPlayersUnsettled() {
		for (Seat s: seats) {
			if (s.getRoundStatus() == RoundStatus.Unsettled) {
				return false;
			}
		}
		return true;
	}

	private int handleBet(Seat actingSeat, int amount) {
		Logger.log("------->" + actingSeat.getPlayerName() + " wants to bet " + amount + " chips.");
		
		int currentBetBefore = currentBet;
		
		int amountToCall = currentBet - pot.getContributionsTotal(actingSeat);
		int playerDeclaration = Math.max(amountToCall, amount);
		
		int actualAmount = Math.min(actingSeat.getChips(), playerDeclaration);
		if (actualAmount != amount) {
			Logger.log("-------->" + actingSeat.getPlayerName() + " Didnt have " + amount + " chips.");
		}
		if (actualAmount == actingSeat.getChips()) {
			actingSeat.setHandStatus(HandStatus.AllIn);
		}
		actingSeat.modChips(-1 * actualAmount);
		pot.addContribution(actingSeat, actualAmount);
		hand.getPot().addContribution(actingSeat, actualAmount);
		if (currentBet > currentBetBefore) {
			unsettleOthers(actingSeat);
		}
		Logger.log("------>" + actingSeat.getPlayerName() + " bet " + amount + " chips.");
		currentBet = Math.max(actualAmount, currentBet);
		return actualAmount;
	}

	protected void unsettleOthers(Seat actingSeat) {
		for (Seat s: seats) {
			if (!s.equals(actingSeat) && 
				s.getRoundStatus() == RoundStatus.Settled &&
				s.getHandStatus() == HandStatus.Active) {
				s.setRoundStatus(RoundStatus.Unsettled);
			}
		}
	}

	private int getNextPositionNumber(int position) {
		
		Seat currentSeat = getSeatWithNumber(position);
		int currentSeatIndex = seats.indexOf(currentSeat);
		if (currentSeatIndex == seats.size() - 1) {
			return seats.get(0).getNumber();
		}
		return seats.get(currentSeatIndex + 1).getNumber();
	}

	private Seat getSeatWithNumber(int seatNum) {
		for (Seat s: seats) {
			if (s.getNumber() == seatNum) {
				return s;
			}
		}
		return null;
	}

	protected List<Seat> getActiveSeats() {
		List<Seat> activeSeats = new ArrayList<Seat>();

		for (Seat s: seats) {
			if (s.isActive()) {
				activeSeats.add(s);
			}
		}
		return activeSeats;
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
				s.setRoundStatus(RoundStatus.NeverInvolved);
				break;
			default:
				s.setRoundStatus(RoundStatus.NeverInvolved);
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

	public Pot getPot() {
		return pot;
	}

}
