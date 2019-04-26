package GameRunning.Decisions;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameRunning.Seat;
import GameRunning.HEGame.Hands.HandStatus;

public class MyInfo {

	private int seatNumber;
	private int chips;
	private List<Card> holeCards;
	private HandStatus handStatus;
	
	public MyInfo(Seat seat) {
		setSeatNumber(seat.getNumber());
		setChips(seat.getChips());
		setHandStatus(seat.getHandStatus());
		setHoleCards(seat.getHoleCards());
	}

	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int _seatNumber) {
		seatNumber = _seatNumber;
	}

	public int getChips() {
		return chips;
	}
	public void setChips(int _chips) {
		chips = _chips;
	}

	public HandStatus getHandStatus() {
		return handStatus;
	}
	public void setHandStatus(HandStatus _handStatus) {
		handStatus = _handStatus;
	}

	public List<Card> getHoleCards() {
		return holeCards;
	}
	public void setHoleCards(List<Card> _holeCards) {
		holeCards = new ArrayList<Card>();
		for (Card c: _holeCards) {
			holeCards.add(new Card(c.getValue(), c.getSuit()));
		}
	}
	
}
