package GameObjects;

public class Card implements Comparable<Card> {

	private Suits suit;
	private int value;
	
	public Card(int _value, Suits _suit) {
		value = _value;
		suit = _suit;
	}
	
	public String toString() {
		String str = "" + getValueChar() + getSuitChar();
		return str;
	}
	
	public Suits getSuit() {
		return suit;
	}
	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	private char getValueChar() {
		if (value == 10) return 'T';
		if (value == 11) return 'J';
		if (value == 12) return 'Q';
		if (value == 13) return 'K';
		if (value == 1 || value == 14) return 'A';
		return (char)('0' + value);
	}
	
	private char getSuitChar() {
		char suitSymbol = '?';
		switch (this.suit) {
			case Diamonds: suitSymbol = '\u2666'; break;
			case Clubs: suitSymbol = '\u2663'; break;
			case Hearts: suitSymbol = '\u2665'; break;
			case Spades: suitSymbol = '\u2660'; break;
			default: break;
		}
		
		return suitSymbol;
	}

	public int getAceVal() {
		if (value == 1) return 14;
		return value;
	}
	
	@Override
	public int compareTo(Card other) {
		return this.getAceVal() - other.getAceVal();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() == Card.class) {
			Card other = (Card)o;
			return this.value == other.value && this.suit == other.suit;
		}
		return false;
	}
	
}
