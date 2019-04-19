package GameRunning.Decisions;

public class HEDecision {

	private Choices type;
	private int amount;
	
	public HEDecision(Choices _type) {
		if (_type == Choices.Raise) {
			//throw new Exception();
		} else {
			setAmount(0);
			setType(_type);
		}
	}
	
	public HEDecision(Choices _type, int _amount) {
		setAmount(_amount);
		setType(_type);
	}

	public String toString() {
		String str = "";
		switch(type) {
		case Call:
			str += "call " + amount;
			break;
		case Check:
			str += "check";
			break;
		case Fold:
			str += "fold";
			break;
		case Raise:
			str += "raise " + amount;
			break;
		default:
			break;
			
		}
		return str;
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
