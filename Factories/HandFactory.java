package Factories;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import GameObjects.Card;
import GameObjects.Suits;
import HandEvaluation.Util.CardGrouper;

public class HandFactory {
	
	public final static int EXPECTED_CARDS = 7;
	
	public TreeMap<Integer, List<Card>> getHandByValue(List<Card> hand) {
		return CardGrouper.getHandGroupedByValue(hand);
	}
	
	public TreeMap<Suits, List<Card>> getHandBySuit(List<Card> hand) {
		return CardGrouper.getHandGroupedBySuit(hand);
	}
	
	public static List<Card> getMaxStraightFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(10, Suits.Hearts));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(14, Suits.Hearts));

		return hand;
	}
	
	public static List<Card> getMidStraightFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(6, Suits.Hearts));
		hand.add(new Card(7, Suits.Hearts));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(14, Suits.Hearts));

		return hand;
	}
	
	public static List<Card> getMinStraightFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Hearts));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(4, Suits.Hearts));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(14, Suits.Hearts));

		return hand;
	}
	
	public static List<Card> getMaxFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(7, Suits.Hearts));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(14, Suits.Hearts));

		return hand;
	}
	
	public static List<Card> getMidFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(6, Suits.Hearts));
		hand.add(new Card(8, Suits.Hearts));

		return hand;
	}
	
	public static List<Card> getMinFlushBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Hearts));
		hand.add(new Card(4, Suits.Hearts));
		hand.add(new Card(7, Suits.Hearts));
		hand.add(new Card(10, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(11, Suits.Diamonds));

		return hand;
	}
	
	public static List<Card> getStraightByValue(int start) {
		List<Card> hand = getStraightDifferentSuits(start);
		return hand;
	}
	
	public static List<Card> getMaxStraightByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(12, Suits.Diamonds));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(10, Suits.Diamonds));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(8, Suits.Diamonds));
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(13, Suits.Spades));	
		return hand;
	}
	
	public static List<Card> getMinStraightBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(10, Suits.Diamonds));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(5, Suits.Diamonds));
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(4, Suits.Spades));

		return hand;
	}
	
	public static List<Card> getMidStraightByValue() {
		List<Card> hand = getStraightDifferentSuits(5);
		return hand;
	}
	
	public static List<Card> getMinStraightByValue() {
		List<Card> hand = getStraightDifferentSuits(1);
		return hand;
	}
	
	public static List<Card> getWheelByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(8, Suits.Diamonds));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(5, Suits.Spades));
		hand.add(new Card(4, Suits.Hearts));
		
		return hand;
	}
	
	public static List<Card> getSuitedWheelBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Hearts));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(14, Suits.Hearts));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(4, Suits.Hearts));
		
		return hand;
	}
	
	public static List<Card> getStraightWithPairInMiddleByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(5, Suits.Spades));
		hand.add(new Card(4, Suits.Hearts));
		
		return hand;
	}
	
	public static List<Card> getBroadwayByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(12, Suits.Clubs));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(5, Suits.Diamonds));
		hand.add(new Card(10, Suits.Spades));
		hand.add(new Card(11, Suits.Hearts));
		
		return hand;
	}
	
	public static List<Card> getMinThreePairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(5, Suits.Diamonds));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(4, Suits.Hearts));

		return hand;
	}

	public static List<Card> getTwoTripsAcesByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(13, Suits.Diamonds));
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(14, Suits.Spades));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(13, Suits.Clubs));
		hand.add(new Card(12, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMaxFullHouseByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(13, Suits.Diamonds));
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(14, Suits.Spades));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(11, Suits.Clubs));
		hand.add(new Card(12, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMidFullHouseByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(5, Suits.Diamonds));
		hand.add(new Card(5, Suits.Clubs));
		hand.add(new Card(9, Suits.Spades));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(9, Suits.Clubs));
		hand.add(new Card(6, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinFullHouseByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(4, Suits.Clubs));
		hand.add(new Card(5, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinQuadsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(2, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMidQuadsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(8, Suits.Clubs));
		hand.add(new Card(8, Suits.Spades));
		hand.add(new Card(10, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(8, Suits.Diamonds));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(7, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMaxQuadsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(11, Suits.Diamonds));
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(14, Suits.Hearts));
		hand.add(new Card(13, Suits.Diamonds));
		hand.add(new Card(14, Suits.Spades));
		hand.add(new Card(14, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinTripsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(7, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMidTripsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(8, Suits.Clubs));
		hand.add(new Card(8, Suits.Spades));
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(12, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMaxTripsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(14, Suits.Diamonds));
		hand.add(new Card(12, Suits.Clubs));
		hand.add(new Card(14, Suits.Spades));
		hand.add(new Card(11, Suits.Diamonds));
		hand.add(new Card(14, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinTwoTripsByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(2, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(3, Suits.Clubs));
		hand.add(new Card(4, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMaxTwoPairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(13, Suits.Diamonds));
		hand.add(new Card(12, Suits.Diamonds));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(14, Suits.Spades));
		
		return hand;
	}
	
	public static List<Card> getMidTwoPairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(7, Suits.Clubs));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(7, Suits.Diamonds));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(2, Suits.Spades));
		
		return hand;
	}
	
	public static List<Card> getMinTwoPairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(7, Suits.Diamonds));
		hand.add(new Card(3, Suits.Hearts));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(2, Suits.Spades));
		
		return hand;
	}
	
	public static List<Card> getPairByValue(int value) {
		List<Card> hand = getSinglePair(value);
		return hand;
	}
	
	public static List<Card> getMaxPairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(14, Suits.Clubs));
		hand.add(new Card(14, Suits.Spades));
		hand.add(new Card(13, Suits.Diamonds));
		hand.add(new Card(12, Suits.Hearts));
		hand.add(new Card(11, Suits.Diamonds));
		hand.add(new Card(9, Suits.Hearts));
		hand.add(new Card(8, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMidPairByValue() {
		List<Card> hand = getSinglePair(8);
		return hand;
	}
	
	public static List<Card> getMinPairByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(2, Suits.Spades));
		hand.add(new Card(3, Suits.Diamonds));
		hand.add(new Card(4, Suits.Hearts));
		hand.add(new Card(5, Suits.Diamonds));
		hand.add(new Card(7, Suits.Hearts));
		hand.add(new Card(8, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinHighCardByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(3, Suits.Spades));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(7, Suits.Diamonds));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMinHighCardBySuit() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(2, Suits.Clubs));
		hand.add(new Card(3, Suits.Spades));
		hand.add(new Card(4, Suits.Diamonds));
		hand.add(new Card(5, Suits.Hearts));
		hand.add(new Card(7, Suits.Diamonds));
		hand.add(new Card(8, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMidHighCardByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(4, Suits.Clubs));
		hand.add(new Card(5, Suits.Spades));
		hand.add(new Card(6, Suits.Diamonds));
		hand.add(new Card(7, Suits.Hearts));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(10, Suits.Hearts));
		hand.add(new Card(11, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getMaxHighCardByValue() {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(7, Suits.Clubs));
		hand.add(new Card(8, Suits.Spades));
		hand.add(new Card(9, Suits.Diamonds));
		hand.add(new Card(11, Suits.Hearts));
		hand.add(new Card(12, Suits.Diamonds));
		hand.add(new Card(13, Suits.Hearts));
		hand.add(new Card(14, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getSinglePair(int pairValue) {
		List<Card> hand = new ArrayList<Card>();
		
		hand.add(new Card(pairValue, Suits.Clubs));
		hand.add(new Card(pairValue, Suits.Spades));
		
		hand.add(new Card((pairValue + 2) % 13 + 14, Suits.Diamonds));
		hand.add(new Card((pairValue + 4) % 13 + 14, Suits.Hearts));
		hand.add(new Card((pairValue + 6) % 13 + 14, Suits.Diamonds));
		hand.add(new Card((pairValue + 8) % 13 + 14, Suits.Hearts));
		hand.add(new Card((pairValue + 10) % 13 + 14, Suits.Diamonds));
		
		return hand;
	}
	
	public static List<Card> getStraightDifferentSuits(int start) {
		List<Card> hand = new ArrayList<Card>();
		for (int i = start; i < start + EXPECTED_CARDS; i++) {
			hand.add(new Card(i, moddedSuit(i)));
		}
		return hand;
	}
	
	public static List<Card> getHandOf(int count, int value, Suits suit) {
		List<Card> hand = new ArrayList<Card>();	
		for (int i = 0; i < count; i++) {
			hand.add(new Card(value, suit));
		}
		return hand;
	}
	
	public static List<Card> getHandOf(int count, int value) {
		Suits suit = Suits.Spades;
		return getHandOf(count, value, suit);
	}
	
	public static List<Card> getHandOf(int count, Suits suit) {
		int value = 5;
		return getHandOf(count, value, suit);
	}
	
	public static List<Card> getHandOf(int count) {
		Suits suit = Suits.Spades;
		int value = 5;
		return getHandOf(count, value, suit);
	}
	
	public static Suits moddedSuit(int i) {
		return Suits.values()[i % Suits.values().length];
	}
}
