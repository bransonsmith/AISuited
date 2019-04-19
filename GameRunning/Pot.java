package GameRunning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pot {

	private Map<Seat, Integer> contributions;
	
	public Pot() { 
		contributions = new HashMap<Seat, Integer>();
	}
	
	public void reset() {
		contributions = new HashMap<Seat, Integer>();
	}
	
	public int getTotal() {
		int total = 0;
		for (Map.Entry<Seat, Integer> cont : contributions.entrySet()) {
			total += cont.getValue();
		}
		return total;
	}
	
	public void addContribution(Seat s, int amount) {
		if (contributions.containsKey(s)) {
			int existingAmount = contributions.get(s);
			int newAmount = existingAmount + amount;
			contributions.put(s, newAmount);
		} else {
			contributions.put(s, amount);
		}
	}
	
	public Map<Seat, Integer> getContributions() {
		return contributions;
	}
	
	public int getWinnings(Seat s) {
		int winnings = 0;
		if (contributions.containsKey(s)) {
			int amount = contributions.get(s);
			for (Map.Entry<Seat, Integer> cont : contributions.entrySet()) {
				winnings += reduceContribution(cont.getKey(), amount);
			}
		}
		return winnings;
	}
	
	public int reduceContribution(Seat s, int desiredAmountToReduce) {
		int actualAmountReduced = 0;
		if (contributions.containsKey(s)) {
			int amountContributionHasLeft = contributions.get(s);
			actualAmountReduced = Math.min(desiredAmountToReduce, amountContributionHasLeft);
			contributions.put(s, amountContributionHasLeft - actualAmountReduced);
		}
		return actualAmountReduced;
	}
	
	public Map<Seat, Integer> getExcessBets() {
		Map<Seat, Integer> excessBets = new HashMap<Seat, Integer>();
		Map.Entry<Seat, Integer> highestContribution = null;
		Map.Entry<Seat, Integer> secondContribution = null;
		for (Map.Entry<Seat, Integer> contribution : contributions.entrySet()) {
			if (highestContribution == null) {
				highestContribution = contribution;
			} else if (highestContribution.getValue() <= contribution.getValue()) {
				secondContribution = highestContribution;
				highestContribution = contribution;
			} else {
				if (secondContribution == null) {
					secondContribution = contribution;
				} else if (secondContribution.getValue() < contribution.getValue()) {
					secondContribution = contribution;
				}
			}
		}
		
		excessBets.put(highestContribution.getKey(), highestContribution.getValue() - secondContribution.getValue());
		
		return excessBets;
	}
	
	public List<Seat> getSortedByContribution(List<Seat> tier) {
		List<Seat> sorted = new ArrayList<Seat>();
		for (Seat s : tier) {
			boolean inserted = false;
			for (int i = 0; i < sorted.size() && !inserted; i++) {
				if (contributions.containsKey(sorted.get(i)) && contributions.containsKey(s)) {
					int sortedAtICont = contributions.get(sorted.get(i));
					int sInTierCont = contributions.get(s);
					if (sortedAtICont >= sInTierCont) {
						sorted.add(i, s);
						inserted = true;
					}
				}
			}
			if (!inserted) {
				sorted.add(sorted.size(), s);
			}
		}
		return sorted;
	}
	
	public String toString() {
		String str = "";
		
		str += "Pot Summary\n";
		for (Map.Entry<Seat, Integer> contribution : contributions.entrySet()) {
			str += String.format("| %-17s | %-8d |", ("" + contribution.getKey().getNumber() + ". " + contribution.getKey().getPlayerName()), contribution.getValue());
		}
		
		return str;
	}

	public int getContributionsTotal(Seat s) {
		if (contributions.containsKey(s)) {
			return contributions.get(s);
		}
		return 0;
	}
	
}
