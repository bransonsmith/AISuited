package GameRunning;

import Players.Player;

public class Seat {

	private int chips;
	private int number;
	private Player player;
	
	public Seat(int _number) {
		setNumber(_number);
	}
	
	public boolean isEmpty() {
		return player != null;
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
}
