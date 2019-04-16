package GameRunning;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameObjects.Deck;

public class HandRound {
	private List<RoundParticipant> participants;
	private List<Card> dealtCards;
	private List<Card> preexistingCards;
	private int currentBet;
	private String name;
	private Deck deck;
	
	public HandRound(String _name, int _numCardsToDeal, List<RoundParticipant> _participants, Deck _deck, List<Card> _preexistingCards) {
		setName(_name);
		setDealtCards(_numCardsToDeal);
		setParticipants(_participants);
		setDeck(_deck);
		setPreexistingCards(_preexistingCards);
		currentBet = 0;
	}
	
	public void commenceRound() {
		// Unsettle Participants
		// Find first person to act
		// until all players settled,
		//	present actingPlayer with context
		//	get playerResponse
		// 	react to playerResponse
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
	public void setDealtCards(int numCardsToDeal) {
		dealtCards = new ArrayList<Card>();
		for (int i = 0; i < numCardsToDeal; i++) {
			dealtCards.add(deck.getNextCard());
		}
	}
	
	public List<Card> getAllCards() {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(preexistingCards);
		cards.addAll(dealtCards);
		return cards;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int _currentBet) {
		currentBet = _currentBet;
	}

	public Deck getDeck() {
		return deck;
	}
	public void setDeck(Deck _deck) {
		deck = _deck;
	}

	public List<Card> getPreexistingCards() {
		return preexistingCards;
	}
	public void setPreexistingCards(List<Card> _preexistingCards) {
		preexistingCards = _preexistingCards;
	}
}
