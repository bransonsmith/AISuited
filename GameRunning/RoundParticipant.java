package GameRunning;

public class RoundParticipant extends HandParticipant {
	
	private RoundStatus roundStatus;
	
	public RoundParticipant(HandParticipant hp) {
		roundStatus = RoundStatus.Unsettled;
		handStatus = hp.getStatus();
		seat = hp.getSeat();
		holeCards = hp.getHoleCards();
	}
	
}
