package GameRunning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;
import Players.Player;

public class GameRunner {

	private List<Seat> seats;
	private GameOptions options;
	private int bbPosition;
	private int sbPosition;
	private int dealerPosition;
	private int pot;
	private Deck deck;
	private List<Card> board;
	private HandRound currentRound;
	
	public GameRunner(int numSeats) throws Exception {
		pot = 0;
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
		str += "| POT = " + pot + "\n";
		str += "| Board = [";
		for (Card c: board) {
			str += c.toString() + " ";
		}
		str += "]\n";
		str += "| ";
		for (Seat s: seats) {
			str += getSeatStrInfo(s);
			str += s.toString() + " | ";
		}
		str += "\n---------------------------------------------------------------------------------------------";
		return str;
	}
	
	public void startNewHand() {
		deck.refresh();
		//chargeBlinds();
		dealHands();
		playPreFlop();
		playFlop();
		playTurn();
		playRiver();
		playShowdown();
		
		//advancePositions();
	}

	private void playPreFlop() {
		currentRound = new HandRound("preflop", 0, getRoundParticipants("preflop"), this);
		currentRound.commence();
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

	private void playFlop() {
		// TODO Auto-generated method stub
		
	}

	private void playTurn() {
		// TODO Auto-generated method stub
		
	}

	private void playRiver() {
		// TODO Auto-generated method stub
		
	}

	private void playShowdown() {
		// TODO Auto-generated method stub
		
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
	
	private void advancePositions() {
		dealerPosition = getPositionAfter(dealerPosition);
		sbPosition = getPositionAfter(dealerPosition);
		bbPosition = getPositionAfter(sbPosition);
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
	
	public void charge(Seat s, int amount) {
		int charged = s.chargeChips(amount);
		String message = "Seat #" + s.getNumber() + " charged " + charged;
		if (charged != amount) {
			message += " / " + amount;
		}
		Logger.log(message + ".");
		pot += charged;
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
}
