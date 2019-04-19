package GameRunning.Decisions;

import GameRunning.Seat;

public class DecisionContext {

	MyInfo myInfo;
	
	public DecisionContext(Seat _myInfo) {
		// DONT SET ANYTHING BY REFERENCE! PLAYERS CAN ACCESS THIS DATA!
		setMyInfo(_myInfo);
	}

	private void setMyInfo(Seat _myInfo) {
		myInfo = new MyInfo(_myInfo);
	}

	public MyInfo getMyInfo() {
		return myInfo;
	}

	
	
}
