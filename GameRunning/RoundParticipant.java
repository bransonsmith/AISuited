package GameRunning;

public class RoundParticipant extends HandParticipant {
	
	private RoundStatus roundStatus;
	
	public RoundParticipant(Seat s) {
		roundStatus = RoundStatus.Unsettled;
		seat = s;
	}

	public void setRoundStatus(RoundStatus _roundStatus) {
		roundStatus = _roundStatus;
	}
	public RoundStatus getRoundStatus() {
		return roundStatus;
	}
}
