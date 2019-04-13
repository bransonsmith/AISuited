package HandEvaluation.Util;

public class KickerFillProblem extends Throwable {
	
	private static final long serialVersionUID = -7853733038164359890L;
	private int expectedHandSize;
	private int actualHandSize;
	
	public KickerFillProblem(int expected, int actual) {
		expectedHandSize = expected;
		actualHandSize = actual;
	}
	
	public String getMessage() {
		return "Kicker Fill created hand with " + actualHandSize + " cards instead of the expected " + expectedHandSize;
	}
}
