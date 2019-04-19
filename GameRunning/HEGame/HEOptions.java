package GameRunning.HEGame;

public class HEOptions {
	private int bb;
	private int sb;
	private int buyIn;
	// private BlindStructure blindStructure;
	
	public HEOptions(int _buyIn, int _sb, int _bb) throws Exception {
		setBuyIn(_buyIn);
		setSb(_sb);
		setBb(_bb);
	}
	
	public int getBb() {
		return bb;
	}
	public void setBb(int _bb) throws Exception {
		explodeIfNegative(_bb, "HEOptions.bb");
		bb = _bb;
	}
	
	public int getSb() {
		return sb;
	}
	public void setSb(int _sb) throws Exception {
		explodeIfNegative(_sb, "HEOptions.sb");
		sb = _sb;
	}
	
	public int getBuyIn() {
		return buyIn;
	}
	public void setBuyIn(int _buyIn) throws Exception {
		explodeIfNegative(_buyIn, "HEOptions.buyIn");
		buyIn = _buyIn;
	}
	
	private void explodeIfNegative(int num, String name) throws Exception {
		if (num < 0) throw new Exception(name + " can't be negative.");
	}
}
