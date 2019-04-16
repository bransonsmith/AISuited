package GameRunning;

import java.util.ArrayList;
import java.util.List;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;

public class GameHand {

	private List<HandParticipant> participants;
	private List<RoundSettings> roundSettings;
	private Deck deck;
	private int roundIndex = 0;
	private int pot;
	private List<Card> communityCards;
	
	public GameHand(Deck _deck, List<HandParticipant> _participants) {
		setParticipants(_participants);
		setRoundSettingsToHoldEmRoundSettings();
		roundIndex = 0;
		pot = 0;
		setDeck(_deck);
		communityCards = new ArrayList<Card>();
	}
	
	private void setRoundSettingsToHoldEmRoundSettings() {
		roundSettings = new ArrayList<RoundSettings>();
		roundSettings.add(new RoundSettings("preflop", 0));
		roundSettings.add(new RoundSettings("flop", 3));
		roundSettings.add(new RoundSettings("turn", 1));
		roundSettings.add(new RoundSettings("river", 1));
		roundSettings.add(new RoundSettings("showdown", 0));
	}
	
	public void commenceNextRound() {
		if (roundIndex < roundSettings.size()) {
			List<RoundParticipant> nextRoundParticipants = getNextRoundParticipants();
			RoundSettings nextRoundSettings = getNextRoundSettings();
			
			commenceRound(nextRoundSettings, nextRoundParticipants);
			roundIndex++;
		} else {
			Logger.log("No more rounds to commence in hand.");
		}
	}
	
	public void commenceRound(RoundSettings roundSettings, List<RoundParticipant> roundParticipants) {
		HandRound round = new HandRound(roundSettings.getName(), roundSettings.getNumCardsToDeal(), roundParticipants, deck, communityCards);
		round.commenceRound();
	}
	
	private RoundSettings getNextRoundSettings() {
		return roundSettings.get(roundIndex);
	}
	
	private List<RoundParticipant> getNextRoundParticipants() {
		List<RoundParticipant> rps = new ArrayList<RoundParticipant>();
		for (HandParticipant hp: participants) {
			if (hp.getStatus() == HandStatus.Active) {
				rps.add(new RoundParticipant(hp));
			}
		}
		return rps;
	}

	public void setParticipants(List<HandParticipant> _participants ) {
		participants = _participants;
	}
	public List<HandParticipant> getParticipants() {
		return participants;
	}
	
	public Deck getDeck() {
		return deck;
	}
	public void setDeck(Deck _deck) {
		deck = _deck;
	}
}
