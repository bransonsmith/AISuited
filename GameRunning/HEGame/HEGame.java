package GameRunning.HEGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.Seat;
import GameRunning.Decisions.DecisionContext;
import GameRunning.Decisions.HEDecision;
import GameRunning.HEGame.Hands.HandStatus;
import GameRunning.HEGame.Rounds.RoundStatus;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import WinPercentage.WinPercent;
import WinPercentage.WinPercentageCalculator;

public class HEGame extends Observable {

	private HEOptions options;
	private int sbPosition;
	private int bbPosition;
	private int dPosition;
	private List<Seat> seats;
	//private HEHand hand;
	//private HERound round;
	private List<Card> board;
	private List<String> messages;
	protected boolean isComplete;
	private Pot pot;
	private int currentBet;
	private Pot roundPot;
	protected int actingPosition;
	private List<WinPercent> winPercentages;
	private String roundName;
	private boolean auto;
	private GameEvent currentEvent;
	Scanner kb;
	
	public HEGame(List<Seat> _seats, HEOptions _options) throws Exception {
		kb = new Scanner(System.in);
		setOptions(new HEOptions(1000, 50, 100));
		setSeats(_seats);
		board = new ArrayList<Card>();
		isComplete = false;
		if (seats.size() > 0) {
			setRandomDealerAndBlinds();
		}
		messages = new ArrayList<String>();
		roundName = "";
		pot = new Pot();
		roundPot = new Pot();
		setAuto(false);
		setCurrentEvent(GameEvent.none);
	}
	
	public void playGame() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		int handCount = 1;
		while (getSeatsWithChips().size() > 1) {
			messages.clear();
			addMessage("Press Enter to play hand #" + handCount);
			kb.nextLine();
			playHand();
			addMessage("Hand #" + handCount + " Complete!");
			handCount++;
			activateSeatsWithChipsBustThoseWithNone();
			moveButtonAndBlinds();
		}
		addMessage("Game over! ");
		kb.close();
	}
	
	public synchronized void playHand() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		pot = new Pot();
		clearAllHands();
		board = new ArrayList<Card>();
		
		currentEvent = GameEvent.none;
		roundName = null;
		
		Deck deck = new Deck();
		deck.refresh();
		deck.shuffle();
		
		dealHands(deck);

		while (getHandParticipants().size() > 1 && currentEvent != GameEvent.showdown) {
			
			advanceRound();
	
			// TODO check GameEvent and wait for kb if necessary
			addMessage("Press Enter to begin the " + roundName);
			kb.nextLine();

			unsettleActiveSeats();
			roundPot = new Pot();
			actingPosition = sbPosition;
			
			if (currentEvent == GameEvent.preflop) {
				chargeBlinds();
			} else if (currentEvent == GameEvent.flop) {
				board.add(deck.getNextCard());
				board.add(deck.getNextCard());
				board.add(deck.getNextCard());
				List<WinPercent> winPercentages = WinPercentageCalculator.getWinPercents(getActiveSeats(), board);
				setWinPercentages(winPercentages);
			} else if ( currentEvent == GameEvent.turn ||
						currentEvent == GameEvent.river) {
				board.add(deck.getNextCard());
				List<WinPercent> winPercentages = WinPercentageCalculator.getWinPercents(getActiveSeats(), board);
				setWinPercentages(winPercentages);
			}
			updateObservers();
			bettingRound();
		}
		
		commenceShowDown(false);
		setWinPercentages(null);
		return;
	}

	private void chargeBlinds() {
		charge(getSBSeat(), options.getSb());
		charge(getBBSeat(), options.getBb());
		currentBet = options.getBb();
		actingPosition = getUTGPosition();
	}

	private void unsettleActiveSeats() {
		for (Seat s: getSeatsWithChips()) {
			if (s.getHandStatus() == HandStatus.Active) {
				s.setRoundStatus(RoundStatus.Unsettled);
			} else {
				s.setRoundStatus(RoundStatus.Settled);
			}
		}
	}

	private void bettingRound() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		String state = null;
		HEDecision decision = null;
		
		while ((!noPlayersUnsettled()) && getHandParticipants().size() > 1) {
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
			// TODO base this on GameEvent check
			kb.nextLine();
			updateObservers();
		}
	}
	
	private void advanceRound() {
		if (roundName == null) {
			roundName = "Pre Flop";
			currentEvent = GameEvent.preflop;
			return;
		}
		if (roundName.equals("Pre Flop")) {
			roundName = "Flop";
			currentEvent = GameEvent.flop;
			return;
		}
		if (roundName.equals("Flop")) {
			roundName = "Turn";
			currentEvent = GameEvent.turn;
			return;
		}
		if (roundName.equals("Turn")) {
			roundName = "River";
			currentEvent = GameEvent.river;
			return;
		}
		if (roundName.equals("River")) {
			roundName = "Showdown";
			currentEvent = GameEvent.showdown;
			return;
		}
		if (roundName.equals("Showdown")) {
			roundName = null;
			currentEvent = GameEvent.none;
			return;
		}
	}

	private void dealHands(Deck deck) {
		// Deal Cards 
		for (Seat s: getSeatsWithChips()) {
			s.setHandStatus(HandStatus.Active);
			s.addCardToHand(deck.getNextCard());
			s.addCardToHand(deck.getNextCard());
		}
	}
	
	private List<Seat> getHandParticipants() {
		List<Seat> hps = new ArrayList<Seat>();
		for (Seat s: seats) {
			if (s.isActive()) {
				hps.add(s);
			}
		}
		return hps;
	}

	private void activateSeatsWithChipsBustThoseWithNone() {
		for(Seat s: seats) {
			if (s.getChips() > 0) {
				s.setHandStatus(HandStatus.Active);
			} else {
				s.setHandStatus(HandStatus.Busted);
			}
		}
	}

	public synchronized void commenceShowDown(boolean silentMode) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		if (getHandParticipants().size() <= 1) {
			int winnings = pot.getTotal();
			addMessage(getHandParticipants().get(0).getPlayerName() + " won " + winnings + " chips! Well played.");
			isComplete = true;
			getHandParticipants().get(0).modChips(winnings);
			return;
		}
		Map<HandEvaluation, List<Seat>> tieredHandEvaluations = getTieredHandEvaluations();
		
		if (!silentMode) {
			int tierNum = 1;
			Logger.log("**************************************************************");
			Logger.log("|   Pot = {" + pot.getTotal() + "}");
			Logger.log("| Board = [" + boardStr() + "]");
			for (Map.Entry<HandEvaluation, List<Seat>> entry: tieredHandEvaluations.entrySet() ) {
				for (Seat s: entry.getValue()) {
					List<Card> allCards = new ArrayList<Card>();
					allCards.addAll(s.getHoleCards());
					allCards.addAll(board);
					Logger.log("| " + tierNum + ". " + String.format("%-20s", s) + " -> " + HandEvaluator.getHoldEmHandEvaluation(allCards));
				}
				tierNum++;
			}
			Logger.log("**************************************************************");
		}
		
		for (Map.Entry<HandEvaluation, List<Seat>> entry: tieredHandEvaluations.entrySet() ) {
			if (entry.getValue().size() == 1) {
				Seat soleWinner = entry.getValue().get(0);
				int winnings = pot.getWinnings(soleWinner);
				if (!silentMode) {
					Logger.log(soleWinner.getPlayerName() + " won " + winnings + " chips!");
				}
				soleWinner.modChips(winnings);
				if (winnings > 0) {
					addMessage(soleWinner.getPlayerName() + " won " + winnings + " chips!");
				}
			} else {
				List<Seat> sortedTier = getSortedSeatsByContribution(entry.getValue());
				
				while (sortedTier.size() > 0 && pot.getTotal() > 0) {
					int totalWinnings = pot.getWinnings(sortedTier.get(0));
					int winningShare = totalWinnings / sortedTier.size();
					
					int leftOvers = totalWinnings - (winningShare * sortedTier.size());
					
					for (Seat payMe: sortedTier) {
						int seatWinnings = winningShare;
						if (leftOvers > 0) {
							seatWinnings += 1;
							leftOvers -= 1;
						}
						if (!silentMode) {
							Logger.log(payMe.getPlayerName() + " won " + seatWinnings + " chips!");
						}
						payMe.modChips(seatWinnings);
						if (seatWinnings > 0) {
							addMessage(payMe.getPlayerName() + " won " + seatWinnings + " chips!");
						}
					}
					sortedTier.remove(0);
				}
			}	
		}
		
		isComplete = true;
	}
	
	public String boardStr() {
		String str = "";
		for (Card c: board) {
			str += c + " ";
		}
		int cCount = board.size();
		while (cCount < 5) {
			str += "__ ";
			cCount++;
		}
		return str.trim();
	}

	private List<Seat> getSortedSeatsByContribution(List<Seat> seatsToSort) {
		
		List<Seat> sorted = new ArrayList<Seat>();
		
		for (Seat s: seatsToSort) {
			int i = 0;
			boolean placed = false;
			while (i < sorted.size() && !placed) {
				
				int iCont = pot.getContributionsTotal(sorted.get(i));
				int sCont = pot.getContributionsTotal(s);
				if (sCont < iCont) {
					sorted.add(i, s);
				}
				i++;
			}
			if (!placed) {
				sorted.add(sorted.size(), s);
			}
		}
		
		return sorted;
	}

	private Map<HandEvaluation, List<Seat>> getTieredHandEvaluations() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		Map<HandEvaluation, List<Seat>> evals = new TreeMap<HandEvaluation, List<Seat>>(Collections.reverseOrder());
		for (Seat s: getActiveSeats()) {
			List<Card> allCards = new ArrayList<Card>();
			allCards.addAll(s.getHoleCards());
			allCards.addAll(board);
			HandEvaluation eval = HandEvaluator.getHoldEmHandEvaluation(allCards);
		
			if (evals.containsKey(eval)) {
				evals.get(eval).add(s);
			} else {
				List<Seat> newVal = new ArrayList<Seat>();
				newVal.add(s);
				evals.put(eval, newVal);
			}
		}
		return evals;
	}
	
	private int getNextPositionNumber(int position) {
		
		Seat currentSeat = getSeatWithNumber(position);
		int currentSeatIndex = seats.indexOf(currentSeat);
		if (currentSeatIndex == seats.size() - 1) {
			return seats.get(0).getNumber();
		}
		return seats.get(currentSeatIndex + 1).getNumber();
	}

	private synchronized HEDecision getPlayerDecision() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		HEDecision decision = null;
		Seat choiceMaker = getSeatWithNumber(actingPosition);
		
		if (choiceMaker.getRoundStatus() == RoundStatus.Unsettled) {
			DecisionContext context = new DecisionContext(choiceMaker, getSeats(), this);
			int amountToCall = Math.max(0, currentBet - roundPot.getContributionsTotal(choiceMaker));
			addMessage("" + choiceMaker.getPlayerName() + ", What would you like to do? " + amountToCall + " to call.");
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
			int amountToCall = currentBet - pot.getContributionsTotal(actingSeat);
			amount = amountToCall;
			amount = handleBet(actingSeat, amount);
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
		
		addMessage(actingSeat.getPlayerName() + " decided to " + actionString);
	}
	
	private int handleBet(Seat actingSeat, int amount) {
		int currentBetBefore = currentBet;
		int amountToCall = Math.max(0, currentBet - roundPot.getContributionsTotal(actingSeat));
		int playerDeclaration = Math.max(amountToCall, amount);
		int actualAmount = Math.min(actingSeat.getChips(), playerDeclaration);
		if (actualAmount != amount) {
			Logger.log("-------->" + actingSeat.getPlayerName() + " Didnt have " + amount + " chips.");
		}
		if (actualAmount == actingSeat.getChips()) { actingSeat.setHandStatus(HandStatus.AllIn); }
		charge(actingSeat, actualAmount);
		currentBet = roundPot.getMaxContribution();
		if (currentBet > currentBetBefore) { unsettleOthers(actingSeat); }
		return actualAmount;
	}
	
	protected void unsettleOthers(Seat actingSeat) {
		for (Seat s: seats) {
			if (!s.equals(actingSeat) && 
				s.getHandStatus() == HandStatus.Active) {
				s.setRoundStatus(RoundStatus.Unsettled);
			}
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
	
	private int charge(Seat sbSeat, int amount) {
		int charged = Math.min(sbSeat.getChips(), amount);
		sbSeat.modChips(charged * -1);
		pot.addContribution(sbSeat, charged);
		roundPot.addContribution(sbSeat, charged);
		addMessage(sbSeat.getPlayerName() + " was charged " + charged);
		return charged;
	}

	private Seat getSBSeat() {
		return getSeatWithNumber(sbPosition);
	}
	private Seat getBBSeat() {
		return getSeatWithNumber(bbPosition);
	}

	private void clearAllHands() {
		for (Seat s: seats) { s.clearHand(); } 
	}

	public boolean isComplete() { return isComplete; }
	public boolean isNotComplete() { return !isComplete; }

	public void updateObservers() {
		setChanged();
        notifyObservers(this);
	}
	
	private void setHandStatuses() {
		for (Seat s: seats) {
			if (s.getChips() <= 0) {
				s.setHandStatus(HandStatus.Busted);
			} else {
				s.setHandStatus(HandStatus.Active);
			}
			notifyObservers(this);
		}
	}

	private void moveButtonAndBlinds() {
		// Dont move to busted people
		dPosition = getActivePositionAfter(dPosition);
		sbPosition = getActivePositionAfter(dPosition);
		bbPosition = getActivePositionAfter(sbPosition);
	}

	private int getActivePositionAfter(int position) {
		int next = getPositionAfter(position);
		Seat nSeat = getSeatWithNumber(next);
		if (nSeat.isActive()) return next;
		
		while (next != position) {
			next = getPositionAfter(next);
			nSeat = getSeatWithNumber(next);
			if (nSeat.isActive()) return next;
		}
		
		return -1;
	}

	private Seat getSeatWithNumber(int num) {
		for (Seat s: seats) {
			if (s.getNumber() == num) {
				return s;
			}
		}
		return null;
	}

	public void setRandomDealerAndBlinds() {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		int i;
		if (allSeatNumbers.size() == 1) {
			i = 0;
		} else {
			i = new Random().nextInt(allSeatNumbers.size() - 1);
		}
		dPosition = allSeatNumbers.get(i);
		sbPosition = getPositionAfter(dPosition);
		bbPosition = getPositionAfter(sbPosition);
		notifyObservers(this);
	}
	
	private List<Integer> getAllSeatNumbers() {
		List<Integer> allSeatNums = new ArrayList<Integer>();
		for (Seat s: seats) {
			allSeatNums.add(s.getNumber());
		}
		return allSeatNums;
	}
	
	private int getPositionAfter(int seatNum) {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		Collections.sort(allSeatNumbers);
		int xOfSeatNum = allSeatNumbers.indexOf(seatNum);
		if (xOfSeatNum >= allSeatNumbers.size() - 1) 
			return allSeatNumbers.get(0);
		return allSeatNumbers.get(xOfSeatNum + 1);
	}

	public HEOptions getOptions() {
		return options;
	}
	public void setOptions(HEOptions _options) {
		options = _options;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> participants) {
		this.seats = participants;
	}

	public void setSBPosition(int _sbPosition) throws Exception {
		
		for (Seat s: seats) {
			if (s.getNumber() == _sbPosition) {
				sbPosition = _sbPosition;
				return;
			}
		}
		throw new Exception("Cant set sb position to " + _sbPosition + " because no game participants are sitting in a seat with that number.");
	}

	public int getSBPosition() {
		return sbPosition;
	}

	public int getDPosition() {
		return dPosition;
	}
	public void setDPosition(int dPosition) {
		this.dPosition = dPosition;
	}

	public int getBBPosition() {
		return bbPosition;
	}
	public void setBBPosition(int _bbPosition) {
		bbPosition = _bbPosition;
	}

	public int getUTGPosition() {
		return getPositionAfter(bbPosition);
	}

	public String toString() {
		String str = "";
		str += getFrameBar();
		str += getFrameBar();
		return str;
	}
	
	public String getSeatSummaries() {
		String str = "";
		if (seats != null) {
			List<Seat> activeSeats = getActiveSeats();
			List<Seat> inactiveSeats = getInactiveSeats();
			for (Seat as : activeSeats) {
				str += "| " + as.toString() + "\n";
			}
			for (Seat ias : inactiveSeats) {
				str += "| " + ias.toString() + "\n";
			}
		}
		return str;
	}
	
	public List<Seat> getActiveSeats() {
		List<Seat> activeSeats = new ArrayList<Seat>();
		if (seats != null) {
			for (Seat s: seats) {
				if (s.isActive()) {
					activeSeats.add(s);
				}
			}
		}
		return activeSeats;
	}
	
	public List<Seat> getInactiveSeats() {
		List<Seat> inactiveSeats = new ArrayList<Seat>();
		if (seats != null) {
			for (Seat s: seats) {
				if (!s.isActive()) {
					inactiveSeats.add(s);
				}
			}
		}
		return inactiveSeats;
	}
	
	public String getFrameBar() {
		return "____________________________________________________________\n";
	}

	public List<Seat> getFoldedSeats() {
		List<Seat> folded = new ArrayList<Seat>();
		if (seats != null) {
			for (Seat s: seats) {
				if (s.getHandStatus() == HandStatus.Folded) {
					folded.add(s);
				}
			}
		}
		return folded;
	}

	private List<Seat> getSeatsWithChips() {
		List<Seat> withChips = new ArrayList<Seat>();
		
		for (Seat s: seats) {
			if (s.getChips() > 0) {
				withChips.add(s);
			}
		}
		
		return withChips;
	}
	
	public List<Seat> getUninvolvedAndBustedSeats() {
		List<Seat> folded = new ArrayList<Seat>();
		if (seats != null) {
			for (Seat s: seats) {
				if (s.getHandStatus() == HandStatus.Busted || 
					s.getHandStatus() == HandStatus.NeverInvolved) {
					folded.add(s);
				}
			}
		}
		return folded;
	}

	public void addMessage(String message) {
		if (messages.size() > 7) {
			messages = messages.subList(1, 7);
		}
		messages.add(message);
		updateObservers();
	}

	public List<String> getMessages() {
		return messages;
	}

	public Pot getPot() {
		return pot;
	}

	public int getActingPosition() {
		return actingPosition;
	}
	
	public void setWinPercentages(List<WinPercent> _winPercentages) {
		winPercentages = _winPercentages;		
	}

	public WinPercent getWinPercentForSeat(Seat s) {
		if (winPercentages == null) {
			return null;
		}
		for (WinPercent wp: winPercentages) {
			if (wp.getSeat().equals(s)) {
				return wp;
			}
		}
		return null;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public List<Card> getBoard() {
		return board;
	}

	public String getRoundName() {
		return roundName;
	}

	public Pot getRoundPot() {
		return roundPot;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public GameEvent getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(GameEvent currentEvent) {
		this.currentEvent = currentEvent;
	}

}
