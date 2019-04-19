package GameRunning.HEGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Common.Logger;
import GameRunning.Seat;
import Players.Player;

public class HEGame {

	private HEOptions options;
	private int sbPosition;
	private int bbPosition;
	private int dPosition;
	private List<Seat> seats;
	
	public HEGame(List<Player> players) throws Exception {
		setOptions(new HEOptions(1000, 50, 100));
		if (seats.size() > 0) {
			setRandomDealerAndBlinds();
		}
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
		Logger.log("First Dealer Position was randomly selected as seat #" + dPosition + ".");
		Logger.log("SB seat #" + sbPosition + ".");
		Logger.log("BB seat #" + bbPosition + ".");
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

	
	
}
