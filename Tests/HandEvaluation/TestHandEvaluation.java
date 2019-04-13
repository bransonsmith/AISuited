package Tests.HandEvaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Factories.HandEvaluationFactory;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public class TestHandEvaluation {

	@Test
	void compareToGivesHoldEmHandOrder() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getRoyalFlush()
			.compareTo(	HandEvaluationFactory.getMinQuads())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinQuads()
				.compareTo(	HandEvaluationFactory.getMinFullHouse())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinFullHouse()
				.compareTo(	HandEvaluationFactory.getMinFlush())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinFlush()
				.compareTo(	HandEvaluationFactory.getMinStraight())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinStraight()
				.compareTo(	HandEvaluationFactory.getMinTrips())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinTrips()
				.compareTo(	HandEvaluationFactory.getMinTwoPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinTwoPair()
				.compareTo(	HandEvaluationFactory.getMinPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMinPair()
				.compareTo(	HandEvaluationFactory.getMinHighCard())  > 0, true);
	}
	
	@Test
	void compareToHighCards() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxHighCard()
				.compareTo(	HandEvaluationFactory.getMidHighCard())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxHighCard()
				.compareTo(	HandEvaluationFactory.getMinHighCard())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidHighCard()
				.compareTo(	HandEvaluationFactory.getMinHighCard())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinHighCard()
				.compareTo(	HandEvaluationFactory.getMaxHighCard())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinHighCard()
				.compareTo(	HandEvaluationFactory.getMidHighCard())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidHighCard()
				.compareTo(	HandEvaluationFactory.getMaxHighCard())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxHighCard()
				.compareTo(	HandEvaluationFactory.getMaxHighCard())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidHighCard()
				.compareTo(	HandEvaluationFactory.getMidHighCard())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinHighCard()
				.compareTo(	HandEvaluationFactory.getMinHighCard())  == 0, true);
	}
	
	@Test
	void compareToPairs() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxPair()
				.compareTo(	HandEvaluationFactory.getMidPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxPair()
				.compareTo(	HandEvaluationFactory.getMinPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidPair()
				.compareTo(	HandEvaluationFactory.getMinPair())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinPair()
				.compareTo(	HandEvaluationFactory.getMaxPair())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinPair()
				.compareTo(	HandEvaluationFactory.getMidPair())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidPair()
				.compareTo(	HandEvaluationFactory.getMaxPair())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxPair()
				.compareTo(	HandEvaluationFactory.getMaxPair())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidPair()
				.compareTo(	HandEvaluationFactory.getMidPair())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinPair()
				.compareTo(	HandEvaluationFactory.getMinPair())  == 0, true);
	}
	
	@Test
	void compareToTwoPairs() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxTwoPair()
				.compareTo(	HandEvaluationFactory.getMidTwoPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxTwoPair()
				.compareTo(	HandEvaluationFactory.getMinTwoPair())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidTwoPair()
				.compareTo(	HandEvaluationFactory.getMinTwoPair())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinTwoPair()
				.compareTo(	HandEvaluationFactory.getMaxTwoPair())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinTwoPair()
				.compareTo(	HandEvaluationFactory.getMidTwoPair())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidTwoPair()
				.compareTo(	HandEvaluationFactory.getMaxTwoPair())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxTwoPair()
				.compareTo(	HandEvaluationFactory.getMaxTwoPair())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidTwoPair()
				.compareTo(	HandEvaluationFactory.getMidTwoPair())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinTwoPair()
				.compareTo(	HandEvaluationFactory.getMinTwoPair())  == 0, true);
	}
	
	@Test
	void compareToTrips() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxTrips()
				.compareTo(	HandEvaluationFactory.getMidTrips())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxTrips()
				.compareTo(	HandEvaluationFactory.getMinTrips())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidTrips()
				.compareTo(	HandEvaluationFactory.getMinTrips())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinTrips()
				.compareTo(	HandEvaluationFactory.getMaxTrips())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinTrips()
				.compareTo(	HandEvaluationFactory.getMidTrips())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidTrips()
				.compareTo(	HandEvaluationFactory.getMaxTrips())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxTrips()
				.compareTo(	HandEvaluationFactory.getMaxTrips())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidTrips()
				.compareTo(	HandEvaluationFactory.getMidTrips())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinTrips()
				.compareTo(	HandEvaluationFactory.getMinTrips())  == 0, true);
	}
	
	@Test
	void compareToStraight() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxStraight()
				.compareTo(	HandEvaluationFactory.getMidStraight())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxStraight()
				.compareTo(	HandEvaluationFactory.getMinStraight())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraight()
				.compareTo(	HandEvaluationFactory.getMinStraight())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinStraight()
				.compareTo(	HandEvaluationFactory.getMaxStraight())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinStraight()
				.compareTo(	HandEvaluationFactory.getMidStraight())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraight()
				.compareTo(	HandEvaluationFactory.getMaxStraight())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxStraight()
				.compareTo(	HandEvaluationFactory.getMaxStraight())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraight()
				.compareTo(	HandEvaluationFactory.getMidStraight())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinStraight()
				.compareTo(	HandEvaluationFactory.getMinStraight())  == 0, true);
	}
	
	@Test
	void compareToFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxFlush()
				.compareTo(	HandEvaluationFactory.getMidFlush())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxFlush()
				.compareTo(	HandEvaluationFactory.getMinFlush())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidFlush()
				.compareTo(	HandEvaluationFactory.getMinFlush())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinFlush()
				.compareTo(	HandEvaluationFactory.getMaxFlush())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinFlush()
				.compareTo(	HandEvaluationFactory.getMidFlush())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidFlush()
				.compareTo(	HandEvaluationFactory.getMaxFlush())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxFlush()
				.compareTo(	HandEvaluationFactory.getMaxFlush())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidFlush()
				.compareTo(	HandEvaluationFactory.getMidFlush())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinFlush()
				.compareTo(	HandEvaluationFactory.getMinFlush())  == 0, true);
	}
	
	@Test
	void compareToFullHouse() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxFullHouse()
				.compareTo(	HandEvaluationFactory.getMidFullHouse())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxFullHouse()
				.compareTo(	HandEvaluationFactory.getMinFullHouse())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidFullHouse()
				.compareTo(	HandEvaluationFactory.getMinFullHouse())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinFullHouse()
				.compareTo(	HandEvaluationFactory.getMaxFullHouse())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinFullHouse()
				.compareTo(	HandEvaluationFactory.getMidFullHouse())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidFullHouse()
				.compareTo(	HandEvaluationFactory.getMaxFullHouse())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxFullHouse()
				.compareTo(	HandEvaluationFactory.getMaxFullHouse())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidFullHouse()
				.compareTo(	HandEvaluationFactory.getMidFullHouse())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinFullHouse()
				.compareTo(	HandEvaluationFactory.getMinFullHouse())  == 0, true);
	}
	
	@Test
	void compareToQuads() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxQuads()
				.compareTo(	HandEvaluationFactory.getMidQuads())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxQuads()
				.compareTo(	HandEvaluationFactory.getMinQuads())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidQuads()
				.compareTo(	HandEvaluationFactory.getMinQuads())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinQuads()
				.compareTo(	HandEvaluationFactory.getMaxQuads())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinQuads()
				.compareTo(	HandEvaluationFactory.getMidQuads())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidQuads()
				.compareTo(	HandEvaluationFactory.getMaxQuads())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxQuads()
				.compareTo(	HandEvaluationFactory.getMaxQuads())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidQuads()
				.compareTo(	HandEvaluationFactory.getMidQuads())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinQuads()
				.compareTo(	HandEvaluationFactory.getMinQuads())  == 0, true);
	}
	
	@Test
	void compareToStraightFlush() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		assertEquals(	HandEvaluationFactory.getMaxStraightFlush()
				.compareTo(	HandEvaluationFactory.getMidStraightFlush())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMaxStraightFlush()
				.compareTo(	HandEvaluationFactory.getMinStraightFlush())  > 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraightFlush()
				.compareTo(	HandEvaluationFactory.getMinStraightFlush())  > 0, true);
		
		assertEquals(	HandEvaluationFactory.getMinStraightFlush()
				.compareTo(	HandEvaluationFactory.getMaxStraightFlush())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMinStraightFlush()
				.compareTo(	HandEvaluationFactory.getMidStraightFlush())  < 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraightFlush()
				.compareTo(	HandEvaluationFactory.getMaxStraightFlush())  < 0, true);
		
		assertEquals(	HandEvaluationFactory.getMaxStraightFlush()
				.compareTo(	HandEvaluationFactory.getMaxStraightFlush())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMidStraightFlush()
				.compareTo(	HandEvaluationFactory.getMidStraightFlush())  == 0, true);
		assertEquals(	HandEvaluationFactory.getMinStraightFlush()
				.compareTo(	HandEvaluationFactory.getMinStraightFlush())  == 0, true);
	}
	
}
