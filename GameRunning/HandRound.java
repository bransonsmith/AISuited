package GameRunning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Common.Logger;
import GameObjects.Card;

public class HandRound {
	private List<RoundParticipant> participants;
	private List<Card> dealtCards;
	private int currentBet;
	private String name;
	private int seatNumToAct;
	private GameRunner game;
	private int numCardsToDeal;
	
	public HandRound(String _name, int _numCardsToDeal, List<RoundParticipant> _participants, GameRunner _game) {
		setName(_name);
		setParticipants(_participants);
		setNumCardsToDeal(_numCardsToDeal);
		game = _game;
		currentBet = 0;
		setSeatNumToAct(0);
	}
	
	private void setNumCardsToDeal(int _numCardsToDeal) {
		numCardsToDeal = _numCardsToDeal;
	}

	public void commence() {
		Logger.log("Commencing " + name + ".");
		unsettleAll();
		setUpSpecificRound();
		// Find first person to act
		// until all players settled,
		//	present actingPlayer with context
		//	get playerResponse
		// 	react to playerResponse
	}
	
	private void unsettleAll() {
		for (RoundParticipant p: participants) {
			p.setRoundStatus(RoundStatus.Unsettled);
		}
	}
	
	private void setUpSpecificRound() {
		if (name.equals("preflop")) {
			game.chargeBlinds();
			setSeatNumToAct(getUTGPosition());
		} else {
			setSeatNumToAct(getSBPosition());
		}

	}
	
	private int getBBPosition() {
		return game.getBbPosition();
	}
	
	private int getSBPosition() {
		return game.getSbPosition();
	}
	
	private int getUTGPosition() {
		int utgPos = getPositionAfter(getBBPosition());
		return utgPos;
	}
	
	private int getPositionAfter(int seatNum) {
		List<Integer> allSeatNumbers = getAllSeatNumbers();
		Collections.sort(allSeatNumbers);
		if (allSeatNumbers.indexOf(seatNum) == allSeatNumbers.size() - 1) {
			return allSeatNumbers.get(0);
		}
		return allSeatNumbers.get(allSeatNumbers.indexOf(seatNum) + 1);
	}
	
	private List<Integer> getAllSeatNumbers() {
		List<Integer> allSeatNums = new ArrayList<Integer>();
		for (RoundParticipant rp: participants) {
			allSeatNums.add(rp.getSeat().getNumber());
		}
		return allSeatNums;
	}
	
	public String toString() {
		String str = "";
		String cardStr = "";
		for (Card c: dealtCards) cardStr += c + " ";
		str += String.format("%s-8: Bet: %d-6 Cards: [%s]", name, currentBet, cardStr);
		return str;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	
	public List<RoundParticipant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<RoundParticipant> _participants) {
		participants = _participants;
	}
	
	public List<Card> getDealtCards() {
		return dealtCards;
	}
	public void setDealtCards(List<Card> _dealtCards) {
		dealtCards = _dealtCards;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int _currentBet) {
		currentBet = _currentBet;
	}

	public int getSeatNumToAct() {
		return seatNumToAct;
	}

	public void setSeatNumToAct(int seatNumToAct) {
		Logger.log("Seat #" + seatNumToAct + " will be acting next.");
		this.seatNumToAct = seatNumToAct;
	}

}
