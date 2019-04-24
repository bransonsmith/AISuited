package GameRunning.HEGame;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import Common.Logger;
import GameRunning.IEventer;
import GameRunning.Seat;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Hands.HandStatus;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import Players.Player;

public class HEGame extends Observable {

	private HEOptions options;
	private int sbPosition;
	private int bbPosition;
	private int dPosition;
	private List<Seat> seats;
	private HEHand hand;
	private List<String> messages;
	protected boolean isComplete;
	protected IEventer child;
	
	public HEGame(List<Seat> _seats, HEOptions _options) throws Exception {
		setOptions(new HEOptions(1000, 50, 100));
		setSeats(_seats);
		child = null;
		isComplete = false;
		if (seats.size() > 0) {
			setRandomDealerAndBlinds();
		}
		messages = new ArrayList<String>();
	}
	
	public boolean isComplete() { return isComplete; }
	public boolean isNotComplete() { return !isComplete; }
	public IEventer getChild() { return child; }
	public void setChild(IEventer _child) { child = _child; }
	public void commenceNextEvent() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		if (child != null && child.isNotComplete()) {
			child.commenceNextEvent();
		} else {
			commenceMyNextEvent();
		}
	}
	
	protected void commenceMyNextEvent() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		
		List<Seat> withChips = getSeatsWithChips();
		if (withChips.size() == 1 ) {
			isComplete = true;
			addMessage(withChips.get(0).getPlayerName() + " is the winner!");
			return;
		}
		
		if (hand == null) {
			startNewHand();
			child = hand;
			return;
		}
		
		if (hand.isComplete()) {
			addMessage("Hand complete.");
			hand = null;
			child = null;
			return;
		}
	}

	public void updateObservers() {
		setChanged();
        notifyObservers(this);
	}
	
	public void startNewHand() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		messages.clear();
		hand = new HEHand(this);
		setHandStatuses();
		moveButtonAndBlinds();
	}
	
	public void startNewHandWithCheeseDeck() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		boolean cheese = true;
		hand = new HEHand(this, cheese);
		setHandStatuses();
		moveButtonAndBlinds();
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
		str += getHandSummaryStr();
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

	public String getHandSummaryStr() {
		if (hand == null) {
			return "No Current Hand";
		}
		return hand.getSummaryStr();
		
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

	public HEHand getHand() {
		return hand;
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

}
