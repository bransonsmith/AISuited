package GameRunning.HEGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Common.Logger;
import GameRunning.Seat;
import GameRunning.HEGame.Hands.HEHand;
import GameRunning.HEGame.Hands.HandStatus;
import Players.Player;

public class HEGame {

	private HEOptions options;
	private int sbPosition;
	private int bbPosition;
	private int dPosition;
	private List<Seat> seats;
	private HEHand hand;
	
	public HEGame(List<Seat> _seats, HEOptions _options) throws Exception {
		setOptions(new HEOptions(1000, 50, 100));
		setSeats(_seats);
		if (seats.size() > 0) {
			setRandomDealerAndBlinds();
		}
	}
	
	public void startNewHand() throws Exception {
		Scanner s = new Scanner(System.in);
		Logger.log("Starting new Hand.");
		hand = new HEHand(this);
		while (hand.isNotComplete()) {
			Logger.log(toString());
			Logger.log("Press any key to continue hand...");
			s.nextLine();
			hand.commenceNextRound();
		}
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
	
}
