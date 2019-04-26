package GameRunning.HEGame.Rounds;

import java.util.List;

import Common.Logger;
import GameObjects.Card;
import GameRunning.Seat;
import GameRunning.HEGame.Hands.HEHand;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import WinPercentage.WinPercent;
import WinPercentage.WinPercentageCalculator;

public class River extends HERound {

	public River(HEHand _hand) throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		super(_hand);
		
		try {
			List<Seat> activeSeats = getActiveSeats();
			List<Card> board = hand.getBoard();
			
			List<WinPercent> winPercentages = WinPercentageCalculator.getWinPercents(activeSeats, board);
			hand.setWinPercentages(winPercentages);
		} catch (Exception e) {
			Logger.log("Failed to get WinPercentages: " + e);
		} finally {
			
		}
	}

	@Override
	protected void setDealBehavior() {
		dealBehavior = new DealBehavior(1);
	}
	
	@Override
	protected void setName() {
		name = "River";
	}
	
	@Override
	protected void setCurrentBet() {
		currentBet = 0;
	}
	
	@Override
	protected void setFirstToAct() {
		actingPosition = hand.getGame().getSBPosition();
	}
}
