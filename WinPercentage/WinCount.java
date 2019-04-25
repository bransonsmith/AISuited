package WinPercentage;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameRunning.Seat;

public class WinCount implements Comparable<WinCount> {
	private int count;
	private Seat seat;
	private List<Card> cards;
	
	public WinCount(Seat _seat, int _count) {
		count = _count;
		seat = _seat;
		setCards(new ArrayList<Card>());
	}
	
	@Override
	public int compareTo(WinCount other) {
		return count - other.count;
	}

	public Seat getSeat() {
		return seat;
	}

	public void addWin() {
		count++;
	}

	public int getCount() {
		return count;
	}
	
	public String toString() {
		String str = "";
		
		str += seat.getNumber() + ". " + seat.getPlayerName();
		str += " | " + count; 
		str += " | ";
		for (Card c: cards) {
			str += c + " ";
		}
		
		return str;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> _cards) {
		cards = _cards;
	}
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public void clearCards() {
		cards = new ArrayList<Card>();
	}
}
