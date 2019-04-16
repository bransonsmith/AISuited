package GameRunning;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import Players.Player;

public class Seat {

	private int chips;
	private int number;
	private List<Card> holeCards;
	private Player player;
	private HandStatus handStatus;
	
	public Seat(int _number, Player _player) {
		setNumber(_number);
		setPlayer(_player);
		setHoleCards(new ArrayList<Card>());
		setHandStatus(HandStatus.NeverInvolved);
	}
	
	public String toString() {
		String str = "" + number + ". ";
		str += getPlayerName();
		str += " [" + chips + "] ";
		str += " (";
		for (Card c: holeCards) {
			str += c;
		}
		str += ")";
		return str;
	}
	
	public boolean isEmpty() {
		return player == null;
	}
	
	public boolean isReadyForNextHand() {
		return !isEmpty() && chips > 0;
	}
	
	public void clearHand() {
		holeCards = new ArrayList<Card>();
	}
	
	public void addCardToHand(Card c) {
		holeCards.add(c);
	}
	
	public void emptySeat() {
		setPlayer(null);
	}
	
	public int getChips() {
		return chips;
	}
	public void setChips(int _chips) {
		chips = _chips;
	}
	public void modChips(int amount) {
		chips += amount;
	}
	public int chargeChips(int amount) {
		if (amount < 0) {
			amount *= -1;
		}
		int amountCharged = amount;
		if (chips < amount) {
			amountCharged = chips;
		}
		modChips(amountCharged * -1);
		return amountCharged;
	}
	
	public String getPlayerName() {
		if (player == null) {
			return "<EMPTY>";
		}
		return player.getName();
	}
	
	public void setPlayer(Player _player) {
		player = _player;
	}
	public Player getPlayer() {
		return player;
	}
	
	public void setNumber(int _number) {
		number = _number;
	}
	public int getNumber() {
		return number;
	}

	public List<Card> getHoleCards() {
		return holeCards;
	}

	public void setHoleCards(List<Card> _holeCards) {
		holeCards = _holeCards;
	}

	public HandStatus getHandStatus() {
		return handStatus;
	}

	public void setHandStatus(HandStatus _handStatus) {
		handStatus = _handStatus;
	}
}
