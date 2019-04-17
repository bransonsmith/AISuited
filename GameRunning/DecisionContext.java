package GameRunning;

public class DecisionContext {

	MyInfo myInfo;
	
	public DecisionContext(RoundParticipant _myInfo) {
		// DONT SET ANYTHING BY REFERENCE! PLAYERS CAN ACCESS THIS DATA!
		setMyInfo(_myInfo);
	}

	private void setMyInfo(RoundParticipant _myInfo) {
		myInfo = new MyInfo(_myInfo);
	}

	public MyInfo getMyInfo() {
		return myInfo;
	}

	
	
}
