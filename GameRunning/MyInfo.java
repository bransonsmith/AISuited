package GameRunning;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;

public class MyInfo {

	private int seatNumber;
	private int chips;
	private List<Card> holeCards;
	private HandStatus handStatus;
	
	public MyInfo(RoundParticipant rp) {
		setSeatNumber(rp.getSeat().getNumber());
		setChips(rp.getSeat().getChips());
		setHandStatus(rp.getSeat().getHandStatus());
		setHoleCards(rp.getSeat().getHoleCards());
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
