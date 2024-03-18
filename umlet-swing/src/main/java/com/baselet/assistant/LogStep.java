package com.baselet.assistant;

import java.util.ArrayList;

public class LogStep {
	String index;
	String action;
	private final ArrayList<StateTriple> current_state;
	private final ArrayList<StateTriple> action_precond;
	private final ArrayList<StateTriple> action_postcond;

	public LogStep(String index, String action, ArrayList<StateTriple> current_state, ArrayList<StateTriple> action_precond, ArrayList<StateTriple> action_postcond) {
		this.current_state = current_state;
		this.action_precond = action_precond;
		this.action_postcond = action_postcond;
	}
}
