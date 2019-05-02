package GameRunning.HEGame.Hands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.IEventer;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Pot;
import GameRunning.HEGame.Rounds.Flop;
import GameRunning.HEGame.Rounds.HERound;
import GameRunning.HEGame.Rounds.PreFlop;
import GameRunning.HEGame.Rounds.River;
import GameRunning.HEGame.Rounds.Rounds;
import GameRunning.HEGame.Rounds.Turn;
import HandEvaluation.HandEvaluation;
import HandEvaluation.HandEvaluator;
import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;
import WinPercentage.WinPercent;

public class HEHand extends IEventer {

	private Deck deck;
	private HEGame game;
	private List<Seat> seats;
	private Pot pot;
	private HERound round;
	private List<Card> board;
	private List<WinPercent> winPercentages;
	
	public HEHand(HEGame _game) {
		setGame(_game);
		setDeck(new Deck());
		deck.refresh();
		setSeats(_game.getSeats());
		setPot(new Pot());
		setBoard(new ArrayList<Card>());
		isComplete = false;
		child = null;
		activateEligiblePlayers();
		winPercentages = null;
	}
	
	public HEHand(HEGame _game, boolean cheese) {

		setGame(_game);
		if (cheese) {
			setDeck(new Deck(cheese));
			deck.shuffle();
		}
		setSeats(_game.getSeats());
		setPot(new Pot());
		setBoard(new ArrayList<Card>());
		isComplete = false;
		child = null;
		activateEligiblePlayers();
		winPercentages = null;
	}
	
	private void activateEligiblePlayers() {
		for (Seat s: seats) {
			if (s.getChips() > 0) {
				s.setHandStatus(HandStatus.Active);
			}
		}
	}

	private void setBoard(List<Card> cards) {
		board = cards;
	}

	@Override
	protected void commenceMyNextEvent() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		if (game.getActiveSeats().size() == 1) {
			wrapUp();
			return;
		}
		
		if (round == null) {
			startNewRound();
			game.addMessage("Dealt new hand. Charged Blinds.");
			child = round;
			return;
		}

		String roundName = round.getName().toLowerCase();
		if (!roundName.equals("river")) {
			handleExcessBets();
		}
		
		if (roundName.equals("pre flop")) {		
			round = new Flop(this);
			game.addMessage("Starting " + round.getName() + ".");
		}
		else if (roundName.equals("flop")) {
			round = new Turn(this);
			game.addMessage("Starting " + round.getName() + ".");
		}
		else if (roundName.equals("turn")) {
			round = new River(this);
			game.addMessage("Starting " + round.getName() + ".");
		}
		else if (roundName.equals("river")) {
			round = null;
			game.addMessage("Starting SHOWDOWN!");
			commenceShowDown(false);
			wrapUp();
		}
		
	}
	
	private void startNewRound() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		activateThoseWhoCanPlay();
		dealCardsToActivePlayers();
		charge(getSeatWithNumber(game.getSBPosition()), game.getOptions().getSb());
		charge(getSeatWithNumber(game.getBBPosition()), game.getOptions().getBb());
		round = new PreFlop(this);
	}

	private void handleExcessBets() {
		Map<Seat, Integer> excessBets = pot.getExcessBets();
		if (game.getActiveSeats().size() > 1) {
			for (Map.Entry<Seat, Integer> entry : excessBets.entrySet()) {
				Seat s = entry.getKey();
				int amount = entry.getValue();
				Logger.log("Returning " + amount + " excess chips from bet to " + s.getPlayerName());
				if (amount > 0) {
					game.addMessage("Returned " + amount + " excess bet chips " + s.getPlayerName());
				}
				pot.reduceContribution(s, amount);
				s.modChips(amount);
			}
		}
		game.notifyObservers(game);
	}

	private void charge(Seat seat, int amount) {
		Logger.log("--> " + seat.getPlayerName() + " owes " + amount + ".");
		int actualAmount = Math.min(amount, seat.getChips());
		if (actualAmount != amount) {
			Logger.log("---> They only have " + actualAmount + " to give...");
		}
		seat.modChips(-1 * actualAmount);
		pot.addContribution(seat, actualAmount);
		Logger.log(seat.getPlayerName() + " put " + actualAmount + " in the pot.");
	}

	private Seat getSeatWithNumber(int num) {
		for (Seat s:seats) {
			if (s.getNumber() == num) {
				return s;
			}
		}
		return null;
	}
	
	private void wrapUp() {
		Logger.log("Wrapping up hand.");
		game.notifyObservers(game);
		List<Seat> as = game.getActiveSeats();
		if (as.size() == 1) {
			Logger.log("Well played " + as.get(0).getPlayerName());
			int winnings = pot.getTotal();
			as.get(0).modChips(winnings);
			if (winnings > 0) {
				game.addMessage(as.get(0).getPlayerName() + " won " + winnings + " chips!");
				Logger.log(as.get(0).getPlayerName() + " won " + winnings + " chips!");
			}
		}
		for (Seat s: seats) {
			s.clearHand();
		}
		isComplete = true;	
		winPercentages = null;
		game.addMessage("The round is complete!");
		round = null;
	}

	private void dealCardsToActivePlayers() {
		for (Seat s: seats) {
			if (s.isActive()) {
				s.addCardToHand(deck.getNextCard());
				s.addCardToHand(deck.getNextCard());
			}
		}
	}

	private void activateThoseWhoCanPlay() {
		for (Seat s: seats) {
			if (s.getChips() > 0) {
				s.setHandStatus(HandStatus.Active);
			} else {
				s.setHandStatus(HandStatus.Busted);
			}
		}
		
	}

	public void commenceShowDown(boolean silentMode) throws HandEvaluatorCardCountProblem, KickerFillProblem {
		Map<HandEvaluation, List<Seat>> tieredHandEvaluations = getTieredHandEvaluations();
		
		if (!silentMode) {
			int tierNum = 1;
			Logger.log("**************************************************************");
			Logger.log("|   Pot = {" + pot.getTotal() + "}");
			Logger.log("| Board = [" + boardStr() + "]");
			for (Map.Entry<HandEvaluation, List<Seat>> entry: tieredHandEvaluations.entrySet() ) {
				for (Seat s: entry.getValue()) {
					List<Card> allCards = new ArrayList<Card>();
					allCards.addAll(s.getHoleCards());
					allCards.addAll(board);
					Logger.log("| " + tierNum + ". " + String.format("%-20s", s) + " -> " + HandEvaluator.getHoldEmHandEvaluation(allCards));
				}
				tierNum++;
			}
			Logger.log("**************************************************************");
		}
		
		for (Map.Entry<HandEvaluation, List<Seat>> entry: tieredHandEvaluations.entrySet() ) {
			if (entry.getValue().size() == 1) {
				Seat soleWinner = entry.getValue().get(0);
				int winnings = pot.getWinnings(soleWinner);
				if (!silentMode) {
					Logger.log(soleWinner.getPlayerName() + " won " + winnings + " chips!");
				}
				soleWinner.modChips(winnings);
				if (winnings > 0) {
					game.addMessage(soleWinner.getPlayerName() + " won " + winnings + " chips!");
				}
			} else {
				List<Seat> sortedTier = getSortedSeatsByContribution(entry.getValue());
				
				while (sortedTier.size() > 0 && pot.getTotal() > 0) {
					int totalWinnings = pot.getWinnings(sortedTier.get(0));
					int winningShare = totalWinnings / sortedTier.size();
					
					int leftOvers = totalWinnings - (winningShare * sortedTier.size());
					
					for (Seat payMe: sortedTier) {
						int seatWinnings = winningShare;
						if (leftOvers > 0) {
							seatWinnings += 1;
							leftOvers -= 1;
						}
						if (!silentMode) {
							Logger.log(payMe.getPlayerName() + " won " + seatWinnings + " chips!");
						}
						payMe.modChips(seatWinnings);
						if (seatWinnings > 0) {
							game.addMessage(payMe.getPlayerName() + " won " + seatWinnings + " chips!");
						}
					}
					sortedTier.remove(0);
				}
			}	
		}
		
		isComplete = true;
	}
	
	public String boardStr() {
		String str = "";
		for (Card c: board) {
			str += c + " ";
		}
		int cCount = board.size();
		while (cCount < 5) {
			str += "__ ";
			cCount++;
		}
		return str.trim();
	}

	private List<Seat> getSortedSeatsByContribution(List<Seat> seatsToSort) {
		
		List<Seat> sorted = new ArrayList<Seat>();
		
		for (Seat s: seatsToSort) {
			int i = 0;
			boolean placed = false;
			while (i < sorted.size() && !placed) {
				
				int iCont = pot.getContributionsTotal(sorted.get(i));
				int sCont = pot.getContributionsTotal(s);
				if (sCont < iCont) {
					sorted.add(i, s);
				}
				i++;
			}
			if (!placed) {
				sorted.add(sorted.size(), s);
			}
		}
		
		return sorted;
	}

	private Map<HandEvaluation, List<Seat>> getTieredHandEvaluations() throws HandEvaluatorCardCountProblem, KickerFillProblem {
		Map<HandEvaluation, List<Seat>> evals = new TreeMap<HandEvaluation, List<Seat>>(Collections.reverseOrder());
		for (Seat s: game.getActiveSeats()) {
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

	public void setDeck(Deck _deck) {
		deck = _deck;
	}
	public Deck getDeck() {
		return deck;
	}

	public HEGame getGame() {
		return game;
	}
	public void setGame(HEGame _game) {
		game = _game;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}

	public Pot getPot() {
		return pot;
	}
	private void setPot(Pot _pot) {
		pot = _pot;
	}

	public String getSummaryStr() {
		String str = "| ";
		if (isComplete) {
			str += "Hand is Completed.\n";
		} else {
			str += "Hand in Progress.\n";
		}
		
		if (round == null) {
			str += "| No Current Round.\n";
		} else {
			str += "| " + round.getName() + " - current bet = " + round.getCurrentBet() + "\n";
		}
		if (pot != null) {
			str += "| Pot = " + pot.getTotal() + "\n";
		}
		str += "| Board = [";
		if (board != null) {
			for (Card c: board) {
				str += c + " ";
			}
		}
		str += "]\n";
		str += "|------------------------------------------------------------\n";
		for (Seat s: game.getActiveSeats()) {
			str += String.format("| %-8s", "("+ pot.getContributionsTotal(s) +")");
			str += String.format("| %-8s|", getPosStr(s));
			str += String.format(" %-20s|\n", s);
		}
		str += "|------------------------------------------------------------\n";
		for (Seat s: game.getFoldedSeats()) {
			str += String.format("| %-8s", "("+ pot.getContributionsTotal(s) +")");
			str += String.format("| %-8s|", getPosStr(s));
			str += String.format(" %-20s|\n", s);
		}
		for (Seat s: game.getUninvolvedAndBustedSeats()) {
			str += String.format("| %-8s", "("+ pot.getContributionsTotal(s) +")");
			str += String.format("| %-8s|", getPosStr(s));
			str += String.format(" %-20s|\n", s);
		}
		
		return str;
	}
	
	public String getPosStr(Seat s) {
		String str = "";

		if (round != null) {
			if (s.getNumber() == round.getActingPosition()) {
				str += "*";
			}
			if (s.getNumber() == game.getSBPosition()) {
				str += "SB";
			}
			if (s.getNumber() == game.getDPosition()) {
				str += "D";
			}
			if (s.getNumber() == game.getBBPosition()) {
				str += "BB";
			}
		}
		
		return str;
	}

	public void addToBoard(List<Card> deal) {
		board.addAll(deal);
	}

	public int getActingPosition() {
		if (round != null) {
			return round.getActingPosition();
		}
		return -1;
	}

	public List<Card> getBoard() {
		return board;
	}

	public void setWinPercentages(List<WinPercent> _winPercentages) {
		winPercentages = _winPercentages;		
	}

	public WinPercent getWinPercentForSeat(Seat s) {
		if (winPercentages == null) {
			return null;
		}
		for (WinPercent wp: winPercentages) {
			if (wp.getSeat().equals(s)) {
				return wp;
			}
		}
		return null;
	}
	
	public HERound getRound() {
		return round;
	}

}
