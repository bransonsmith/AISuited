package GameRunning.Decisions;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Hands.HandStatus;
import GameRunning.HEGame.Rounds.RoundStatus;

public class PublicSeatInfo {

	private String playerName;
	private int chipCount;
	private int position;
	private boolean isDealer;
	private boolean isBB;
	private boolean isSB;
	private HandStatus handStatus;
	private RoundStatus roundStatus;
	
	public PublicSeatInfo(Seat s, HEGame game) {
		
		playerName = new String(s.getPlayerName());
		chipCount = s.getChips();
		position = s.getNumber();
		isDealer = game.getDPosition() == position;
		isBB = game.getBBPosition() == position;
		isSB = game.getSBPosition() == position;
		handStatus = s.getHandStatus();
		roundStatus = s.getRoundStatus();
		
	}

	public String     getPlayerName() 	{ return playerName; }
	public int 		  getChipCount() 	{ return chipCount; } 
	public boolean    isDealer()		{ return isDealer; } 
	public boolean    isBB()			{ return isBB; } 
	public boolean    isSB()			{ return isSB; } 
	public HandStatus getHandStatus()	{ return handStatus; } 
	public RoundStatus getRoundStatus()	{ return roundStatus; } 
	
}
