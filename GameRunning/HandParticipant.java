package GameRunning;

import java.util.List;

import GameObjects.Card;

public class HandParticipant {

	protected Seat seat;
	protected List<Card> holeCards;
	protected int chipsPutInPot; // how many chips can they win (if a side-pot is needed)

	public int getChipsPutInPot() {
		return chipsPutInPot;
	}
	public void setChipsPutInPot(int _chipsPutInPot) {
		chipsPutInPot = _chipsPutInPot;
	}
	public void modChipsPutInPot(int _chipsPutInPot) {
		chipsPutInPot += _chipsPutInPot;
	}
	
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
