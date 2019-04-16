package Players;

import GameRunning.HoldEmChoice;

public abstract class Player {
	
	private String name; 
	private String ownerName;
	
	public abstract HoldEmChoice getDecision();
	
	public Player(String _name, String ownerName) {
		setName(_name);
		setOwnerName(_name);
	}
	
	public String getName() { return name; }
	public void   setName(String _name) { name = _name; }
	public String getOwnerName() { return ownerName; }
	public void   setOwnerName(String _ownerName) { ownerName = _ownerName; }
}
