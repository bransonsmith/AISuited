package GameRunning.HEGame;

public class GameOptions {

	private int bb;
	private int sb;
	private int buyIn;
	// private BlindStructure blindStructure;
	
	public int getBb() {
		return bb;
	}
	public void setBb(int _bb) throws Exception {
		explodeIfNegative(_bb, "GameOptions.bb");
		bb = _bb;
	}
	
	public int getSb() {
		return sb;
	}
	public void setSb(int _sb) throws Exception {
		explodeIfNegative(_sb, "GameOptions.sb");
		sb = _sb;
	}
	
	public int getBuyIn() {
		return buyIn;
	}
	public void setBuyIn(int _buyIn) throws Exception {
		explodeIfNegative(_buyIn, "GameOptions.buyIn");
		buyIn = _buyIn;
	}
	
	private void explodeIfNegative(int num, String name) throws Exception {
		if (num < 0) throw new Exception(name + " can't be negative.");
	}
}
