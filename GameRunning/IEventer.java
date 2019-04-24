package GameRunning;

import HandEvaluation.HandEvaluatorCardCountProblem;
import HandEvaluation.Util.KickerFillProblem;

public abstract class IEventer {

	protected boolean isComplete;
	protected IEventer child;
	
	public boolean isComplete() { return isComplete; }
	public boolean isNotComplete() { return !isComplete; }
	public IEventer getChild() { return child; }
	public void setChild(IEventer _child) { child = _child; }
	public void commenceNextEvent() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem {
		if (child != null && child.isNotComplete()) {
			child.commenceNextEvent();
		} else {
			commenceMyNextEvent();
		}
	}
	
	protected abstract void commenceMyNextEvent() throws Exception, HandEvaluatorCardCountProblem, KickerFillProblem;
	
}
