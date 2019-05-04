package GameRunning.Decisions;

import java.util.ArrayList;
import java.util.List;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame;

public class DecisionContext {

	private MyInfo myInfo;
	private List<PublicSeatInfo> allPlayersInfo;
	private PublicGameInfo gameInfo;
	private int currentBet;
	
	public DecisionContext(Seat _myInfo, List<Seat> _allPlayers, HEGame _game) {
		// DONT SET ANYTHING BY REFERENCE! PLAYERS CAN ACCESS THIS DATA!
		// Make deep copies in the Info object constructors
		setMyInfo(_myInfo);
		setOtherPlayersInfo(_allPlayers, _game);
		setGameInfo(_game);
	}

	public MyInfo 				getMyInfo() 		{ return myInfo; }
	public List<PublicSeatInfo> getAllPlayersInfo() { return allPlayersInfo; }
	public PublicGameInfo 		getGameInfo() 		{ return gameInfo; }
	
	
	private void setGameInfo(HEGame _game) {
		gameInfo = new PublicGameInfo(_game);
		if (_game != null) {
			currentBet = _game.getCurrentBet();
		} else {
			currentBet = 0;
		}
	}
	private void setOtherPlayersInfo(List<Seat> _allPlayers, HEGame _game) {
		allPlayersInfo = new ArrayList<PublicSeatInfo>();
		for (Seat s: _allPlayers) {
			allPlayersInfo.add(new PublicSeatInfo(s, _game));
		}
	}
	private void setMyInfo(Seat _myInfo) {
		myInfo = new MyInfo(_myInfo);
	}

	public int getNeededToCall() {
		return currentBet;
	}

	public int getCurrentBet() {
		return currentBet;
	}



	
	
}
