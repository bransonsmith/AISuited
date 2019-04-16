package GameRunning;

import java.util.List;

public class GameRunner {

	private List<Seat> seats;
	private GameOptions options;
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> _seats) {
		seats = _seats;
	}
 
	public GameOptions getOptions() {
		return options;
	}

	public void setOptions(GameOptions _options) {
		options = _options;
	}
	
}
