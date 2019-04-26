package WinPercentage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.paukov.combinatorics3.Generator;

import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.Seat;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class WinPercentageCalculator {

	// Take in list of hands and board
	// Run every combination of remaining boards and tally up wins for each hand
	// return dictionary of percentages
	
	// on river "get outs" will give a list of cards that let the losing hand win, just save the combination
	
	public static List<WinPercent> getWinPercents(List<Seat> seats, List<Card> board) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		 List<WinPercent> percents = new ArrayList<WinPercent>();
		 List<WinCount> winCounts = new ArrayList<WinCount>();
		 List<Card> deck = new Deck().getRemainingCards();
		 
		 for (Card c: board) { deck.remove(c); }
		 
		 for (Seat s: seats) {
			 for (Card c: s.getHoleCards()) {
				 deck.remove(c);
			 }
			 winCounts.add(new WinCount(s, 0));
		 }
		 
		 Set<Card> deckSet = new HashSet<Card>();
		 for (Card c: deck) { deckSet.add(c); }
		 Set<List<Card>> possibleCombinations = new HashSet<List<Card>>();
		 
		 int totalWins = 0;
		 int pendingCards = 5 - board.size();
		 if (pendingCards > 0) {
			 
			 possibleCombinations = Generator.combination(deckSet)
			  .simple(pendingCards)
			  .stream()
			  .filter(p -> p.size() == pendingCards)
				.collect(Collectors.toSet());
			    
			Iterator<List<Card>> pcI = possibleCombinations.iterator();
			while (pcI.hasNext()) {
				
				List<Card> hypoCards = new ArrayList<Card>();
				for (Card c: pcI.next()) { hypoCards.add(c); }
				
				List<Card> tempBoard = new ArrayList<Card>();
				tempBoard.addAll(board);
				tempBoard.addAll(hypoCards);
			
				Map<HandEvaluation, List<Seat>> tiers = getTieredHandEvaluations(seats, tempBoard);
				Entry<HandEvaluation, List<Seat>> winnerTier = tiers.entrySet().iterator().next();
				if (winnerTier.getValue().size() == 1) {
					for (Seat winnerSeat: winnerTier.getValue()) {
						for (WinCount wc: winCounts) {
							if (wc.getSeat().equals(winnerSeat)) {
								wc.addWin();
								if (hypoCards.size() == 1) {
									wc.addCard(hypoCards.get(0));
								}
							}
						}
					}
				}
				totalWins++;
			}
		 } else {
			 Map<HandEvaluation, List<Seat>> tiers = getTieredHandEvaluations(seats, board);
				Entry<HandEvaluation, List<Seat>> winnerTier = tiers.entrySet().iterator().next();
				for (Seat winnerSeat: winnerTier.getValue()) {
					for (WinCount wc: winCounts) {
						if (wc.getSeat().equals(winnerSeat)) {
							wc.addWin();
						}
					}
				}
				totalWins++;
		 }
		 

		 for (WinCount wc: winCounts) {
			 double percentage = wc.getCount() * 100.0 / totalWins;
			 percents.add(new WinPercent(wc.getSeat(), percentage, wc.getCards()));
		 }
		 
		 return percents;
	}
	
	
	private static Map<HandEvaluation, List<Seat>> getTieredHandEvaluations(List<Seat> seats, List<Card> board) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		Map<HandEvaluation, List<Seat>> evals = new TreeMap<HandEvaluation, List<Seat>>(Collections.reverseOrder());
		for (Seat s: seats) {
			List<Card> allCards = new ArrayList<Card>();
			allCards.addAll(s.getHoleCards());
			allCards.addAll(board);
			HandEvaluation eval = HandEvaluator.getHoldEmHandEvaluation(allCards);
		
			if (evals.containsKey(eval)) {
				evals.get(eval).add(s);
			} else {
				List<Seat> newVal = new ArrayList<Seat>();
				newVal.add(s);
				evals.put(eval, newVal);
			}
		}
		return evals;
	}
	
}
