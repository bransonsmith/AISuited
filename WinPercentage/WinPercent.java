package WinPercentage;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameRunning.Seat;

public class WinPercent implements Comparable<WinPercent> {

	private double percentage;
	private Seat seat;
	private List<Card> outs;
	
	public WinPercent(Seat _seat, double _percentage, List<Card> _outs) {
		percentage = _percentage;
		setSeat(_seat);
		setOuts(_outs);
	}
	
	@Override
	public int compareTo(WinPercent other) {
		double dif = percentage - other.percentage;
		if (dif > 0) {
			return 1;
		}
		if (dif < 0) {
			return -1;
		}
		return 0;
	}
	
	public String toString() {
		String str = "";
		
		str += seat.getNumber() + ". " + seat.getPlayerName();
		str += " | " + percentage; 
		str += " | ";
		for (Card c: outs) {
			str += c + " ";
		}
		
		return str;
	}
	
	public double getPercent() {
		return percentage;
	}

	public List<Card> getOuts() {
		return outs;
	}

	public void setOuts(List<Card> _outs) {
		outs = _outs;
	}
	
	public void addOut(Card c) {
		outs.add(c);
	}
	
	public void clearOuts() {
		outs = new ArrayList<Card>();
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat _seat) {
		seat = _seat;
	}
}
