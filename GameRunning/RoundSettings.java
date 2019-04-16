package GameRunning;

public class RoundSettings {

	private int numCardsToDeal;
	private String name;
	
	public RoundSettings(String _name, int _numCardsToDeal) {
		setName(_name);
		setNumCardsToDeal(_numCardsToDeal);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	
	public int getNumCardsToDeal() {
		return numCardsToDeal;
	}
	public void setNumCardsToDeal(int _numCardsToDeal) {
		numCardsToDeal = _numCardsToDeal;
	}
	
}
