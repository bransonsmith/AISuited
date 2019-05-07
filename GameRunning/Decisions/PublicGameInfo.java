package GameRunning.Decisions;

import java.util.ArrayList;
import java.util.List;

import GameObjects.Card;
import GameRunning.HEGame.HEGame;

public class PublicGameInfo {

	private int bbPosition;
	private int sbPosition;
	private int dPosition;
	private int bb;
	private int sb;
	private int buyIn;
	private int pot;
	private List<Card> board;
	
	public PublicGameInfo(HEGame game) {
		
		bbPosition = game.getBBPosition();
		sbPosition = game.getSBPosition();
		dPosition = game.getDPosition();
		bb = game.getOptions().getBb();
		sb = game.getOptions().getSb();
		buyIn = game.getOptions().getBuyIn();
		
		board = new ArrayList<Card>();
		if (game != null) {
			if (game.getBoard() != null) {
				for (Card c: game.getBoard()) {
					board.add(new Card(c.getValue(), c.getSuit()));
				}
			}
			pot = game.getPot().getTotal();
		} else {
			pot = 0;
		}

	}
	
	public int   getBBPosition() { return bbPosition; }
	public int   getSBPosition() { return sbPosition; }
	public int    getDPosition() { return dPosition; }
	public int 		     getBB() { return bb; }
	public int 		     getSB() { return sb; }
	public int 	      getBuyIn() { return buyIn; }
    public int          getPot() { return pot; }
	public List<Card> getBoard() { return board; }
	
}
