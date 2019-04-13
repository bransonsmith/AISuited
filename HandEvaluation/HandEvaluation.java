package HandEvaluation;

import java.util.List;

import Common.Constants;
import GameObjects.Card;
import GameObjects.HandStrength;

public class HandEvaluation implements Comparable<HandEvaluation> {

	private HandStrength strength;
	private String handName;
	private List<Card> cards;
	
	public HandEvaluation(HandStrength _strength, String _handName, List<Card> _cards) {
		setStrength(_strength);
		setCards(_cards);
		setHandName(_handName);
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public HandStrength getStrength() {
		return strength;
	}

	public void setStrength(HandStrength strength) {
		this.strength = strength;
	}

	public String getHandName() {
		return handName;
	}

	public void setHandName(String handName) {
		this.handName = handName;
	}
	
	public String toString() {
		String str = "";
		str += handName;
		for (Card c : cards) {
			str += " " + c.toString();
		}
		return str;
	}

	@Override
	public int compareTo(HandEvaluation other) {
		if (getStrength() != other.getStrength()) {
			return getStrength().compareTo(other.getStrength());
		}
		for (int i = 0; i < Constants.FINAL_HAND_SIZE(); i++) {
			if (cards.get(i).compareTo(other.getCards().get(i)) != 0) {
				return cards.get(i).compareTo(other.getCards().get(i));
			}
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != HandEvaluation.class) return false;
		
		HandEvaluation other = (HandEvaluation)o;
		if (this.getStrength()     != other.getStrength()) return false; 
		if (this.getCards().size() != other.getCards().size()) return false;

		for (int i = 0; i < this.getCards().size(); i++) {
			if (!this.getCards().get(i).equals(other.getCards().get(i))) return false;
		}

		return true;
	}
	
}
