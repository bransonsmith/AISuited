package GameRunning;

public class HoldEmChoice {

	private Choices type;
	private int amount;
	
	public HoldEmChoice(Choices _type) {
		if (_type == Choices.Raise) {
			//throw new Exception();
		} else {
			setAmount(0);
			setType(_type);
		}
	}
	
	public HoldEmChoice(Choices _type, int _amount) {
		setAmount(_amount);
		setType(_type);
	}

	public Choices getType() { return type; }
	public void setType(Choices _type) { type = _type; }

	public int getAmount() { return amount; }
	public void setAmount(int _amount) { 
		if (_amount < 0) {
			//throw new Exception();
		}
		amount = _amount;
	}
	
}
