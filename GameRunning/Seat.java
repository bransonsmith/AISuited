package GameRunning;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameRunning.HEGame.Hands.HandStatus;
import GameRunning.HEGame.Rounds.RoundStatus;
import Players.Player;

public class Seat {

	private int chips;
	private int number;
	private List<Card> holeCards;
	protected Player player;
	private HandStatus handStatus;
	private RoundStatus roundStatus;
	
	public Seat(int _number) {
		setNumber(_number);
		setPlayer(null);
		setHoleCards(new ArrayList<Card>());
		setHandStatus(HandStatus.NeverInvolved);
	}
	
	public String toString() {
		String str = "" + number + ". ";
		if (handStatus == HandStatus.Active) {
			str += "*Active* ";
		}
		else if (handStatus == HandStatus.Folded) {
			str += "*Folded* ";
		}
		else if (handStatus == HandStatus.AllIn) {
			str += "*ALL IN* ";
		}
		else if (handStatus == HandStatus.Loser) {
			str += "*LOSER * ";
		}
		else {
			str += "*??????* ";
		}
		str += String.format("%-15s | ", getPlayerName());
		str += String.format("%-8s", String.format("[%d]", chips));
		String cardStr = "(";
		for (Card c: holeCards) cardStr += c;
		cardStr += ")";
		str += String.format("%-7s", cardStr);
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

	public boolean isActive() {
		return handStatus == HandStatus.Active || handStatus == HandStatus.AllIn;
	}

	public HandStatus getHandStatus() {
		return handStatus;
	}
	public void setHandStatus(HandStatus _handStatus) {
		handStatus = _handStatus;
	}
	public RoundStatus getRoundStatus() {
		return roundStatus;
	}
	public void setRoundStatus(RoundStatus _roundStatus) {
		roundStatus = _roundStatus;
	}
}
