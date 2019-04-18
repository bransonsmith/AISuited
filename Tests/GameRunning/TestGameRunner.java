package Tests.GameRunning;

import org.junit.jupiter.api.Test;

import GameRunning.Pot;
import GameRunning.Seat;
import Players.AlwaysFold;

public class TestGameRunner {

	@Test
	void sortByContribution() {
		
		Seat seatB = new Seat(1, new AlwaysFold("Branson", "Branson Owner"));
		Seat seatM = new Seat(2, new AlwaysFold("Max", "Max Owner"));
		Seat seatT = new Seat(3, new AlwaysFold("Trent", "Trent Owner"));
		
		Pot pot = new Pot();
		
		int amountB = 100;
		int amountM = 100;
		int amountT = 100;
		
		pot.addContribution(seatB, amountB);
		pot.addContribution(seatM, amountM);
		pot.addContribution(seatT, amountT);
		
		
	}
	
	
	
}
