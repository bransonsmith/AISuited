package GameRunning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Common.Logger;
import GameObjects.Card;

public class HandRound {
	private List<RoundParticipant> participants;
	private List<Card> dealtCards;
	private int currentBet;
	private String name;
	private int seatNumToAct;
	private GameRunner game;
	private int numCardsToDeal;
	
	public HandRound(String _name, int _numCardsToDeal, List<RoundParticipant> _participants, GameRunner _game) {
		setName(_name);
		setParticipants(_participants);
		setNumCardsToDeal(_numCardsToDeal);
		game = _game;
		currentBet = 0;
		setSeatNumToAct(0);
	}
	
	private void setNumCardsToDeal(int _numCardsToDeal) {
		numCardsToDeal = _numCardsToDeal;
	}

	public void commence() {
		Logger.log("Commencing " + name + ".");
		unsettleAll();
		setUpSpecificRound();
		Logger.log("Seat #" + seatNumToAct + " will act first this round.");
		playOutRound();
	}
	
	private void unsettleAll() {
		Logger.log("Unsettling all participants...");
		for (RoundParticipant p: participants) {
			p.setRoundStatus(RoundStatus.Unsettled);
		}
	}
	
	private void setUpSpecificRound() {
		if (name.equals("preflop")) {
			game.chargeBlinds();
			currentBet = game.getOptions().getBb();
			setSeatNumToAct(getUTGPosition());
			unsettleOtherActivePlayersThatArentAllIn(getParticipantWithSeatNumber(getBBPosition()));
		} else {
			currentBet = 0;
			setSeatNumToAct(getSBPosition());
			setSettleStates();
			dealCommunityCards();
			String cardStr = "";
			for (Card c: dealtCards) {
				cardStr += c + " ";
			}
			Logger.log(" | dealt cards");
			Logger.log(" | " + cardStr + "\n");
		}

	}
	
	private void playOutRound() {
		while (anyUnsettled()) {	
			RoundParticipant choiceMaker = getParticipantWithSeatNumber(seatNumToAct);
			if (choiceMaker != null) {
				if (choiceMaker.getRoundStatus() == RoundStatus.Unsettled) {
				
					DecisionContext decisionContext = getDecisionContext(choiceMaker);
					if (isLastActiveParticipant(choiceMaker)) {
						choiceMaker.setRoundStatus(RoundStatus.Settled);
					} else if (choiceMaker.getRoundStatus() == RoundStatus.Unsettled) {
							HoldEmChoice choice = getPlayerChoice(choiceMaker, decisionContext);
							reactToChoice(choice, choiceMaker);
							Logger.log(this.toString());
						}
				} else {
					choiceMaker.setRoundStatus(RoundStatus.Unsettled);
				}
			} else {
				seatNumToAct = getPositionAfter(seatNumToAct);
			}
		}
	}
	
	private boolean isLastActiveParticipant(RoundParticipant choiceMaker) {
		return game.isLastActiveSeat(choiceMaker.getSeat());
	}

	private void reactToChoice(HoldEmChoice choice, RoundParticipant choiceMaker) {
		int amountCharged = 0;
		if (choice.getType() == Choices.Fold) {
			Logger.log("" + choiceMaker.getSeat().getPlayerName() + " folded.");
			choiceMaker.getSeat().setHandStatus(HandStatus.Folded);
			choiceMaker.setRoundStatus(RoundStatus.Settled);
			seatNumToAct = getPositionAfter(seatNumToAct);
		}
		if (choice.getType() == Choices.Call) {
			Logger.log("" + choiceMaker.getSeat().getPlayerName() + " called.");
			amountCharged = game.charge(choiceMaker.getSeat(), choice.getAmount());
			if (choice.getAmount() >= choiceMaker.getSeat().getChips()) {
				Logger.log("" + choiceMaker.getSeat().getPlayerName() + " is ALL IN!");
				choiceMaker.getSeat().setHandStatus(HandStatus.AllIn);
			}
			choiceMaker.setRoundStatus(RoundStatus.Settled);
			seatNumToAct = getPositionAfter(seatNumToAct);
		}
		if (choice.getType() == Choices.Check) {
			Logger.log("" + choiceMaker.getSeat().getPlayerName() + " checked.");
			choiceMaker.setRoundStatus(RoundStatus.Settled);
			seatNumToAct = getPositionAfter(seatNumToAct);
		}
		if (choice.getType() == Choices.Raise) {
			Logger.log("" + choiceMaker.getSeat().getPlayerName() + " raised.");
			if (choice.getAmount() >= choiceMaker.getSeat().getChips()) {
				amountCharged = game.charge(choiceMaker.getSeat(), choice.getAmount());
				Logger.log("" + choiceMaker.getSeat().getPlayerName() + " is ALL IN!");
				choiceMaker.getSeat().setHandStatus(HandStatus.AllIn);
			} else if (choice.getAmount() < currentBet) {
				amountCharged = game.charge(choiceMaker.getSeat(), currentBet * 2);
			}
			choiceMaker.setRoundStatus(RoundStatus.Settled);
			unsettleOtherActivePlayersThatArentAllIn(choiceMaker);
			seatNumToAct = getPositionAfter(seatNumToAct);
		}
		currentBet = Math.max(currentBet, amountCharged);
	}
	
	private void unsettleOtherActivePlayersThatArentAllIn(RoundParticipant choiceMaker) {
		for (RoundParticipant rp: participants) {
			if (!rp.equals(choiceMaker) && 
				rp.getSeat().getHandStatus() == HandStatus.Active
				) {
				Logger.log("\tUnsettling " + rp.getSeat().getPlayerName());
				rp.setRoundStatus(RoundStatus.Unsettled);
			} else {
				rp.setRoundStatus(RoundStatus.Settled);
			}
		}
	}

	private HoldEmChoice getPlayerChoice(RoundParticipant choiceMaker, DecisionContext decisionContext) {
		HoldEmChoice decision = choiceMaker.getSeat().getPlayer().getDecision(decisionContext);
		return decision;
	}

	private RoundParticipant getParticipantWithSeatNumber(int num) {
		for (RoundParticipant rp: participants) {
			if (rp.getSeat().getNumber() == num) {
				return rp;
			}
		}
		return null;
	}

	private DecisionContext getDecisionContext(RoundParticipant decisionMaker) {
		DecisionContext dContext = new DecisionContext(decisionMaker);
		return dContext;
	}
	
	private boolean anyUnsettled() {
		for (RoundParticipant p: participants) {
			if (p.getRoundStatus() == RoundStatus.Unsettled) {
				return true;
			}
		}
		
		return false;
	}

	private void dealCommunityCards() {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < numCardsToDeal; i++) {
			cards.add(game.getNextDealtCard());
		}
		setDealtCards(cards);
		game.dealToBoard(dealtCards);
	}

	private void setSettleStates() {
		for (RoundParticipant rp: participants) {
			HandStatus rpStat = rp.getSeat().getHandStatus();
			if (rpStat == HandStatus.Active) {
				rp.setRoundStatus(RoundStatus.Unsettled);
			} else {
				rp.setRoundStatus(RoundStatus.Settled);
			}
		}
	}

	private int getBBPosition() {
		return game.getBbPosition();
	}
	
	private int getSBPosition() {
		return game.getSbPosition();
	}
	
	private int getUTGPosition() {
		int utgPos = getPositionAfter(getBBPosition());
		return utgPos;
	}
	
	private int getPositionAfter(int seatNum) {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		Collections.sort(allSeatNumbers);
		if (allSeatNumbers.indexOf(seatNum) == allSeatNumbers.size() - 1) {
			return allSeatNumbers.get(0);
		}
		return allSeatNumbers.get(allSeatNumbers.indexOf(seatNum) + 1);
	}
	
	private List<Integer> getAllSeatNumbers() {
		List<Integer> allSeatNums = new ArrayList<Integer>();
		for (RoundParticipant rp: participants) {
			allSeatNums.add(rp.getSeat().getNumber());
		}
		return allSeatNums;
	}
	
	public String toString() {
		String str = "\n--HandRound--\t";
		String cardStr = "";
		if (participants != null) {
			for (RoundParticipant p: participants) {
				if (p.getRoundStatus() == RoundStatus.Unsettled) {
					str += "-u-";
				}
				str += p.getSeat().getPlayerName() + ", ";
			}
		}
		str += "\n";
		if (dealtCards != null) {
			for (Card c: dealtCards) cardStr += c + " ";
		}
		str += String.format("%-8s- Bet: %-6d Cards: [%s]", name, currentBet, cardStr);
		return str;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	
	public List<RoundParticipant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<RoundParticipant> _participants) {
		participants = _participants;
	}
	
	public List<Card> getDealtCards() {
		return dealtCards;
	}
	public void setDealtCards(List<Card> _dealtCards) {
		dealtCards = _dealtCards;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int _currentBet) {
		currentBet = _currentBet;
	}

	public int getSeatNumToAct() {
		return seatNumToAct;
	}
	public void setSeatNumToAct(int _seatNumToAct) {
		Logger.log("Seat #" + _seatNumToAct + " will be acting next.");
		seatNumToAct = _seatNumToAct;
	}

}
