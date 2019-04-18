package GameRunning;

import java.util.List;

import GameObjects.Card;

public class HandParticipant {

	protected Seat seat;
	protected List<Card> holeCards;
	
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat _seat) {
		seat = _seat;
	}
	
	public List<Card> getHoleCards() {
		return holeCards;
	}
	public void setHoleCards(List<Card> _holeCards) {
		holeCards = _holeCards;
	}
	
}
