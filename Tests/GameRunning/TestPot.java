package Tests.GameRunning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import GameRunning.Pot;
import GameRunning.Seat;
import Players.AlwaysFold;

public class TestPot {

	@Test
	void ctorMakesEmptyContributionList() {
		
		Pot sut = new Pot();
		assertNotNull(sut);
		assertEquals(sut.getContributions().keySet().size(), 0);
	}
	
	@Test
	void addContributionCreatesNewEntry() {
		
		Seat seat = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		int amount = 100;
		Pot sut = new Pot();
		sut.addContribution(seat, amount);
		
		assertEquals((int)(sut.getContributions().get(seat)), amount);
	}
	
	@Test 
	void getExcessBetsWhenZero() {
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		int amountA = 100;
		Pot sut = new Pot();
		sut.addContribution(seatB, amountA);
		sut.addContribution(seatM, amountA);
		sut.addContribution(seatT, amountA);
		
		int expectedExcess = 0;
		int actualExcessT = sut.getExcessBets().getOrDefault(seatT, 0);
		int actualExcessB = sut.getExcessBets().getOrDefault(seatB, 0);
		int actualExcessM = sut.getExcessBets().getOrDefault(seatM, 0);
		assertEquals((int)(actualExcessT), expectedExcess);
		assertEquals((int)(actualExcessB), expectedExcess);
		assertEquals((int)(actualExcessM), expectedExcess);
	}
	
	@Test 
	void getExcessBetsWhenExists() {
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		int amountMore = 1000;
		int amountLess = 100;
		Pot sut = new Pot();
		sut.addContribution(seatB, amountMore);
		sut.addContribution(seatM, amountLess);
		sut.addContribution(seatT, amountLess);
		
		int expectedExcess = 900;
		int actualExcessB = sut.getExcessBets().getOrDefault(seatB, 0);
		int actualExcessM = sut.getExcessBets().getOrDefault(seatM, 0);
		int actualExcessT = sut.getExcessBets().getOrDefault(seatT, 0);
		assertEquals((int)(actualExcessB), expectedExcess);
		assertEquals((int)(actualExcessM), 0);
		assertEquals((int)(actualExcessT), 0);
	}
	
	@Test 
	void getExcessBetsWhenTwoAtTop() {
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		int amountMore = 1000;
		int amountLess = 100;
		Pot sut = new Pot();
		sut.addContribution(seatB, amountMore);
		sut.addContribution(seatM, amountMore);
		sut.addContribution(seatT, amountLess);
		
		int actualExcessB = sut.getExcessBets().getOrDefault(seatB, 0);
		int actualExcessM = sut.getExcessBets().getOrDefault(seatM, 0);
		int actualExcessT = sut.getExcessBets().getOrDefault(seatT, 0);
		assertEquals((int)(actualExcessB), 0);
		assertEquals((int)(actualExcessM), 0);
		assertEquals((int)(actualExcessT), 0);
	}
	
	@Test
	void getTotalSumsAllContributions() {
		
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		int amountA = 100;
		int amountB = 50;
		int amountC = 5;
		Pot sut = new Pot();
		sut.addContribution(seatB, amountA);
		sut.addContribution(seatB, amountB);
		sut.addContribution(seatM, amountA);
		sut.addContribution(seatT, amountC);
		sut.addContribution(seatM, amountB);
		
		int expectedTotal = amountA + amountB + amountA + amountC + amountB;
		assertEquals((int)(sut.getTotal()), expectedTotal);
	}
	
	@Test
	void resetEmptiesContributions() {
		
		Seat seat = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		int amount = 100;
		Pot sut = new Pot();
		sut.addContribution(seat, amount);
		assertEquals((int)(sut.getContributions().size()), 1);
		
		sut.reset();
		assertEquals((int)(sut.getContributions().size()), 0);
	}
	
	@Test
	void addContributionAppendsExistingEntry() {
		
		Seat seat = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		int originalAmount = 100;
		int additionalAmount = 300;
		Pot sut = new Pot();
		sut.addContribution(seat, originalAmount);
		sut.addContribution(seat, additionalAmount);
		
		assertEquals((int)(sut.getContributions().get(seat)), originalAmount + additionalAmount);
	}
	
	@Test
	void addContributionReducesExistingEntry() {
		
		Seat seat = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		int originalAmount = 100;
		int reduceBy = 40;
		Pot sut = new Pot();
		sut.addContribution(seat, originalAmount);
		int reducedBy = sut.reduceContribution(seat, reduceBy);
		
		assertEquals(reducedBy, reduceBy);
		assertEquals((int)(sut.getContributions().get(seat)), originalAmount - reduceBy);
	}
	
	@Test
	void addContributionReducesExistingEntryTooMuch() {
		
		Seat seat = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		int originalAmount = 100;
		int reduceBy = 150;
		Pot sut = new Pot();
		sut.addContribution(seat, originalAmount);
		int reducedBy = sut.reduceContribution(seat, reduceBy);
		
		assertEquals(reducedBy, originalAmount);
		assertEquals((int)(sut.getContributions().get(seat)), 0);
	}
	
	@Test
	void getWinningsHighestContributor() {
		
		Seat most = new Seat(1, new AlwaysFold("most", "most Owner"));
		Seat middle = new Seat(2, new AlwaysFold("middle", "middle Owner"));
		Seat least = new Seat(3, new AlwaysFold("least", "least Owner"));
		
		int leastContribution = 100;
		int middleContribution = 200;
		int mostContribution = 300;
		
		Pot sut = new Pot();
		sut.addContribution(most, mostContribution);
		sut.addContribution(middle, middleContribution);
		sut.addContribution(least, leastContribution);
		
		int winnings = sut.getWinnings(most);
		int total = leastContribution + middleContribution + mostContribution;
		assertEquals((int)winnings, total);
		assertEquals((int)(sut.getContributions().get(most)), 0);
		assertEquals((int)(sut.getContributions().get(middle)), 0);
		assertEquals((int)(sut.getContributions().get(least)), 0);
	}
	
	@Test
	void getWinningsMiddleContributor() {
		
		Seat most = new Seat(1, new AlwaysFold("most", "most Owner"));
		Seat middle = new Seat(2, new AlwaysFold("middle", "middle Owner"));
		Seat least = new Seat(3, new AlwaysFold("least", "least Owner"));
		
		int leastContribution = 100;
		int middleContribution = 200;
		int mostContribution = 300;
		
		Pot sut = new Pot();
		sut.addContribution(most, mostContribution);
		sut.addContribution(middle, middleContribution);
		sut.addContribution(least, leastContribution);
		
		int winnings = sut.getWinnings(middle);
		int expectedWinnings = leastContribution + middleContribution + middleContribution;
		int mostLeftovers = mostContribution - middleContribution;
		
		assertEquals((int)winnings, expectedWinnings);
		assertEquals((int)(sut.getContributions().get(most)), mostLeftovers);
		assertEquals((int)(sut.getContributions().get(middle)), 0);
		assertEquals((int)(sut.getContributions().get(least)), 0);
		
		int leftOverWinnings = sut.getWinnings(most);
		assertEquals((int)(leftOverWinnings), mostLeftovers);
		assertEquals((int)(sut.getContributions().get(most)), 0);
	}
	
	@Test
	void sortByContributionSortsAsc() {
		
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		Pot pot = new Pot();
		
		int amountB = 200;
		int amountM = 100;
		int amountT = 300;
		
		pot.addContribution(seatB, amountB);
		pot.addContribution(seatM, amountM);
		pot.addContribution(seatT, amountT);
		
		
		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seatB); seats.add(seatM); seats.add(seatT);
		
		List<Seat> sorted = pot.getSortedByContribution(seats);
		
		assertEquals(sorted.size(), 3);
		assertEquals(sorted.get(0), seatM);
		assertEquals(sorted.get(1), seatB);
		assertEquals(sorted.get(2), seatT);
	}
}
