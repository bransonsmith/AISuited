package HandEvaluation;

public class HandEvaluatorCardCountProblem extends Throwable {
	
	private static final long serialVersionUID = -7853733038164359890L;
	
	public String getMessage() {
		return "HandEvaluator expects a list of exactly 7 cards.";
	}
}
