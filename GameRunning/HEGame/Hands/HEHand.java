package GameRunning.HEGame.Hands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Common.Logger;
import GameObjects.Card;
import GameObjects.Deck;
import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Pot;
import GameRunning.HEGame.Rounds.Flop;
import GameRunning.HEGame.Rounds.HERound;
import GameRunning.HEGame.Rounds.PreFlop;
import GameRunning.HEGame.Rounds.River;
import GameRunning.HEGame.Rounds.Turn;

public class HEHand {

	private Deck deck;
	private HEGame game;
	private List<Seat> seats;
	private Pot pot;
	private HERound round;
	private boolean complete;
	private List<Card> board;
	
	public HEHand(HEGame _game) {
		setGame(_game);
		setDeck(new Deck());
		deck.refresh();
		setSeats(_game.getSeats());
		setPot(new Pot());
		setBoard(new ArrayList<Card>());
		complete = false;
		activateEligiblePlayers();
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

	public void commenceNextRound() throws Exception {
		
		if (game.getActiveSeats().size() == 1) {
			wrapUp();
		} else {
			if (round == null) {
				Logger.log("--> Activating players.");
				activateThoseWhoCanPlay();
				Logger.log("--> Dealing cards to active players.");
				dealCardsToActivePlayers();
				Logger.log("Charging Blinds.");
				charge(getSeatWithNumber(game.getSBPosition()), game.getOptions().getSb());
				charge(getSeatWithNumber(game.getBBPosition()), game.getOptions().getBb());
				
				Logger.log(game.toString());
				Logger.log("\nPress any key to continue hand...");
				new Scanner(System.in).nextLine();
				
				round = new PreFlop(this);
				Logger.log("Set Up Preflop...");
				Logger.log(game.toString());
				Logger.log("\nPress any key to continue hand...");
				new Scanner(System.in).nextLine();
				Logger.log("Commencing " + round.getName());
				while (round.isNotComplete()) {
					round.commenceNextAction();
				}
			}
			else {
				String roundName = round.getName().toLowerCase();
			
				if (roundName.equals("pre flop")) {
				
					round = new Flop(this);
					Logger.log("Commencing " + round.getName());
					while (round.isNotComplete()) {
						round.commenceNextAction();
					}
				}
				else if (roundName.equals("flop")) {
					round = new Turn(this);
					Logger.log("Commencing " + round.getName());
					while (round.isNotComplete()) {
						round.commenceNextAction();
					}
				}
				else if (roundName.equals("turn")) {
					round = new River(this);
					Logger.log("Commencing " + round.getName());
					while (round.isNotComplete()) {
						round.commenceNextAction();
					}
				}
				else if (roundName.equals("river")) {
					round = null;
					Logger.log("Commencing Showdown");
					commenceShowDown();
					wrapUp();
				}
			}
		}
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
		List<Seat> as = game.getActiveSeats();
		if (as.size() == 1) {
			Logger.log("Well played " + as.get(0).getPlayerName());
			int winnings = pot.getTotal();
			as.get(0).modChips(winnings);
			Logger.log(as.get(0).getPlayerName() + " won " + winnings + " chips!");
		}
		for (Seat s: seats) {
			s.clearHand();
		}
		complete = true;	
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

	private void commenceShowDown() {
		// TODO Auto-generated method stub
		Logger.log("No showdown logic yet :(. But it will happen here.");
		complete = true;
	}
	
	public boolean isComplete() {
		return complete;
	}
	public boolean isNotComplete() {
		return !complete;
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
		if (complete) {
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
}
