package GameRunning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.Player;

public class GameRunner {

	private List<Seat> seats;
	private GameOptions options;
	private int bbPosition;
	private int sbPosition;
	private int dealerPosition;
	private Pot pot;
	private Deck deck;
	private List<Card> board;
	private HandRound currentRound;
	
	public GameRunner(int numSeats) throws Exception {
		pot = new Pot();
		dealerPosition = 0;
		sbPosition = 0;
		bbPosition = 0;
		options = new GameOptions();
		options.setBb(100);
		options.setSb(50);
		options.setBuyIn(1000);
		deck = new Deck();
		board = new ArrayList<Card>();
		seats = new ArrayList<Seat>();
		for (int i = 0; i < numSeats; i++) seats.add(new Seat(i + 1, null));
	}
	
	public void sitPlayerDown(Player p) {
		for (Seat s: seats) {
			if (s.isEmpty()) {
				s.setPlayer(p);
				Logger.log("" + p.getName() + " was sat down at seat #" + s.getNumber());
				s.setChips(options.getBuyIn());
				return;
			}
		}
		Logger.log("No empty seats for " + p.getName() + " to sit down at.");
	}
	
	public String toString() {
		String str = "";
		str += "---------------------------------------------------------------------------------------------\n";
		str += "|   POT = " + pot.getTotal() + "\n";
		str += "| BOARD = [";
		for (Card c: board) {
			str += c.toString() + " ";
		}
		str += "]\n";
		
		List<Seat> activeSeats = getActiveSeats();
		List<Seat> inactiveSeats = getInactiveSeats();
		str += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		for (Seat s: activeSeats) {
			str += "| " + getSeatStrInfo(s);
			str += "| " + s.toString() + " | \n";
		}
		str += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		for (Seat s: inactiveSeats) {
			str += "| " + getSeatStrInfo(s);
			str += "| " + s.toString() + " | \n";
		}
		str += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		str += "\n---------------------------------------------------------------------------------------------";
		return str;
	}
	
	private List<Seat> getInactiveSeats() {
		List<Seat> inactiveSeats = new ArrayList<Seat>();
		for (Seat s: seats) {
			if (!s.isActive()) {
				inactiveSeats.add(s);
			}
		}
		return inactiveSeats;
	}

	public void startNewHand() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		
		Scanner s = new Scanner(System.in);
		
		deck.refresh();
		pot.reset();
		board = new ArrayList<Card>();
		
		activatePlayers();
		dealHands();
		
		Logger.log(this.toString());
		Logger.log("\n == PREFLOP ==");
		// s.nextLine();
		playPreFlop();
		handleExcessBets();
		Logger.log(this.toString() + "\n");
		
		if (areMoreThanOneActiveSeats()) {
			s.nextLine();
			Logger.log("\n == FLOP ==");
			playFlop();
			Logger.log(this.toString() + "\n");
		}
		
		if (areMoreThanOneActiveSeats()) {
			s.nextLine();
			Logger.log("\n == TURN ==");
			playTurn();
			Logger.log(this.toString() + "\n");
		}
		
		if (areMoreThanOneActiveSeats()) {
			s.nextLine();
			Logger.log("\n == RIVER ==");
			playRiver();
			Logger.log(this.toString() + "\n");
		}
		
		if (areMoreThanOneActiveSeats()) {
			s.nextLine();
			Logger.log("\n == SHOWDOWN ==");
			playShowdown();
			Logger.log(this.toString() + "\n");
			s.nextLine();
		}
		wrapUpHand();
		advancePositions();
	}

	private void handleExcessBets() {
		Map<Seat, Integer> excessBets = pot.getExcessBets();
		for (Map.Entry<Seat, Integer> excessBet : excessBets.entrySet()) {
			int excessToAdd = pot.reduceContribution(excessBet.getKey(), excessBet.getValue());
			if (excessToAdd > 0) {
				Logger.log("Giving excess from bet back to " + excessBet.getKey().getPlayerName() + " -> " + excessToAdd + " chips.");
				excessBet.getKey().modChips(excessToAdd);
			}
		}
	}

	private void activatePlayers() {
		for (Seat s: seats) {
			if (s.isReadyForNextHand()) {
				s.setHandStatus(HandStatus.Active);
			}
		}
	}
	
	private List<Seat> getSeatsReadyForNextHand() {
		List<Seat> readySeats = new ArrayList<Seat>();
		for (Seat s: seats) {
			if (s.isReadyForNextHand()) {
				readySeats.add(s);
			}
		}
		return readySeats;
	}

	private void wrapUpHand() {
		//payOutWinnings();
		//splitPotBetweenRemainingActiveSeats();
	}


	private List<Seat> getActiveSeats() {
		List<Seat> activeSeats = new ArrayList<Seat>();
		for (Seat s: seats) {
			if (s.isActive()) {
				activeSeats.add(s);
			}
		}
		return activeSeats;
	}

	private boolean areMoreThanOneActiveSeats() {
		int count = 0;
		for (Seat s: seats) {
			if (s.isActive()) {
				count++;
			}
		}
		return count >= 2;
	}

	private List<RoundParticipant> getRoundParticipants(String roundName) {
		if (roundName.equals("preflop")) {
			for (Seat s: seats) {
				if (s.isReadyForNextHand()) {
					s.setHandStatus(HandStatus.Active);
				}
			}
		}
		
		List<RoundParticipant> rps = new ArrayList<RoundParticipant>();
		for (Seat s: seats) {
			if (s.getHandStatus() == HandStatus.Active) {
				rps.add(new RoundParticipant(s));
			}
		}
		return rps;
	}

	private void playPreFlop() {
		currentRound = new HandRound("preflop", 0, getRoundParticipants("preflop"), this);
		currentRound.commence();
	}
	
	private void playFlop() {
		currentRound = new HandRound("flop", 3, getRoundParticipants("flop"), this);
		currentRound.commence();
	}

	private void playTurn() {
		currentRound = new HandRound("turn", 1, getRoundParticipants("turn"), this);
		currentRound.commence();
	}

	private void playRiver() {
		currentRound = new HandRound("river", 1, getRoundParticipants("river"), this);
		currentRound.commence();
	}

	private void playShowdown() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		// Sort all hands that made it to showdown (desc)
		// Start at top,
		// While pot.getTotal has money, pay all hands of next evaluation value
		String boardStr = "";
		for(Card c: board) boardStr += c + " ";
		Logger.log("Board = [" + boardStr + "]");
		
		Map<HandEvaluation, List<Seat>> tieredEvals = new TreeMap<HandEvaluation, List<Seat>>();
		for (Seat seat: getActiveSeats()) {
			
			List<Card> cards = new ArrayList<Card>();
			cards.addAll(seat.getHoleCards());
			cards.addAll(board);
			HandEvaluation eval = HandEvaluator.getHoldEmHandEvaluation(cards);
			
			List<Seat> newVal = new ArrayList<Seat>();
			if (tieredEvals.containsKey(eval)) {
				newVal = tieredEvals.get(eval);
			}
			newVal.add(seat);
			tieredEvals.put(eval, newVal);
		}
		
		int tierNum = 1;
		for (Map.Entry<HandEvaluation, List<Seat>> tier: tieredEvals.entrySet()) {
			
			// Sort tier by contribution (asc)
			List<Seat> tierSortedByContribution = getSortedByContribution(tier.getValue());
			
			// While still seats to collect
				// Gather for lowest
				// Split amongst remaining in tier
			
			
			
			for (Seat seat: tier.getValue()) {
				List<Card> cards = new ArrayList<Card>();
				cards.addAll(seat.getHoleCards());
				cards.addAll(board);
				HandEvaluation freshEval = HandEvaluator.getHoldEmHandEvaluation(cards);
				Logger.log(tierNum + ". " + seat + " -> " + freshEval);
			}
			tierNum++;
		}

		
		Logger.log("\n-----HAND WINNERS-----");
		for (int i = 0; i < winningEvals.size(); i++) {
			Logger.log(String.format(" | %-15s = %s", winningSeats.get(i).getPlayerName(), winningEvals.get(i)));
		}
		Logger.log("");
		deactivateAllExcept(winningSeats);
		
	}

	public List<Seat> getSortedByContribution(List<Seat> tier) {
		List<Seat> sorted = new ArrayList<Seat>();
		for (Seat s : tier) {
			boolean inserted = false;
			for (int i = 0; i < sorted.size(); i++) {
				if (pot.getContributions().containsKey(sorted.get(i)) && pot.getContributions().containsKey(s)) {
					int sortedAtICont = pot.getContributions().get(sorted.get(i));
					int sInTierCont = pot.getContributions().get(s);
					if (sortedAtICont >= sInTierCont) {
						sorted.add(i, s);
						inserted = true;
					}
				}
			}
			if (!inserted) {
				sorted.add(sorted.size(), s);
			}
		}
		return sorted;
	}

	private void deactivateAllExcept(List<Seat> winningSeats) {
		for (Seat s: seats) {
			if (!winningSeats.contains(s)) {
				s.setHandStatus(HandStatus.Loser);
			}
		}
	}

	public void dealToBoard(List<Card> cards) {
		board.addAll(cards);
	}
	
	private void dealHands() {
		for (Seat s: seats) {
			s.clearHand();
			if (s.isReadyForNextHand()) {
				dealHand(s);
			}
		}
	}
	
	private void dealHand(Seat s) {
		s.addCardToHand(deck.getNextCard());
		s.addCardToHand(deck.getNextCard());
	}
	
	private int getReadyPositionAfter(int seatNum) {
		List<Integer> allReadySeatNumbers = getAllReadySeatNumbers();
		Collections.sort(allReadySeatNumbers);
		int xOfSeatNum = allReadySeatNumbers.indexOf(seatNum);
		if (xOfSeatNum >= allReadySeatNumbers.size() - 1) 
			return allReadySeatNumbers.get(0);
		return allReadySeatNumbers.get(xOfSeatNum + 1);
	}
	
	private List<Integer> getAllReadySeatNumbers() {
		List<Integer> readySeatNums = new ArrayList<Integer>();
		for (Seat s: getSeatsReadyForNextHand()) {
			readySeatNums.add(s.getNumber());
		}
		return readySeatNums;
	}
	
	private void advancePositions() {
		dealerPosition = getReadyPositionAfter(dealerPosition);
		sbPosition = getReadyPositionAfter(dealerPosition);
		bbPosition = getReadyPositionAfter(sbPosition);
	}
	
	public String getSeatStrInfo(Seat s) {
		String info = "";
		
		int toPlay = -1;
		if (currentRound != null) toPlay = currentRound.getSeatNumToAct();
		
		if (s.getNumber() == toPlay) {
			info += "*";
		}
		if (s.getNumber() == dealerPosition) {
			info += "(D)";
		}
		if (s.getNumber() == sbPosition) {
			info += "(SB)";
		}
		if (s.getNumber() == bbPosition) {
			info += "(BB)";
		}
		
		while (info.length() < 6) {
			info += " ";
		}
		
		return info;
	}
	
	public void setRandomDealerAndBlinds() {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		int i = new Random().nextInt(allSeatNumbers.size() - 1);
		dealerPosition = allSeatNumbers.get(i);
		sbPosition = getPositionAfter(dealerPosition);
		bbPosition = getPositionAfter(sbPosition);
		Logger.log("First Dealer Position was randomly selected as seat #" + dealerPosition + ".");
	}

	private int getPositionAfter(int seatNum) {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		Collections.sort(allSeatNumbers);
		int xOfSeatNum = allSeatNumbers.indexOf(seatNum);
		if (xOfSeatNum >= allSeatNumbers.size() - 1) 
			return allSeatNumbers.get(0);
		return allSeatNumbers.get(xOfSeatNum + 1);
	}
	
	private List<Integer> getAllSeatNumbers() {
		List<Integer> allSeatNums = new ArrayList<Integer>();
		for (Seat s: seats) {
			allSeatNums.add(s.getNumber());
		}
		return allSeatNums;
	}
	
	public void chargeBlinds() {
		Logger.log("Charging Blinds (" + options.getBb() + "/" + options.getSb() + ").");
		chargeSb();
		chargeBb();
	}
	
	public void chargeSb() {
		chargeSeatWithNumber(sbPosition, options.getSb());
	}	
	public void chargeBb() {
		chargeSeatWithNumber(bbPosition, options.getBb());
	}
	
	public void chargeSeatWithNumber(int num, int amount) {
		Seat s = getSeatWithNumber(num);
		charge(s, amount);
	}
	
	public int charge(Seat s, int amount) {
		int charged = s.chargeChips(amount);
		String message = "Seat #" + s.getNumber() + " charged " + charged;
		if (charged != amount) {
			message += " / " + amount;
		}
		Logger.log(message + ".");
		pot.addContribution(s, charged);
		return charged;
	}
	
	private Seat getSeatWithNumber(int n) {
		for (Seat s: seats) {
			if (s.getNumber() == n) {
				return s;
			}
		}
		return null;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}
 
	public GameOptions getOptions() {
		return options;
	}
	public void setOptions(GameOptions _options) {
		options = _options;
	}

	public int getBbPosition() {
		return bbPosition;
	}
	public void setBbPosition(int _bbPosition) {
		bbPosition = _bbPosition;
	}
	
	public int getSbPosition() {
		return sbPosition;
	}
	public void setSbPosition(int _sbPosition) {
		sbPosition = _sbPosition;
	}

	public boolean isLastActiveSeat(Seat seat) {
		for (Seat s: seats) {
			if (s.isActive() && !s.equals(seat)) {
				return false;
			}
		}
		return true;
	}

	public Card getNextDealtCard() {
		return deck.getNextCard();
	}

	public int getSeatsWithChipsCount() {
		int count = 0;
		for (Seat s: seats) {
			if (s.getChips() > 0) {
				count++;
			}
		}
		return count;
	}
}
