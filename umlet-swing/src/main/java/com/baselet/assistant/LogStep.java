package com.baselet.assistant;

import java.util.ArrayList;

public class LogStep {
	String index;
	String actor;
	String action;
	private final ArrayList<StateTriple> current_state;
	private final ArrayList<StateTriple> action_precond;
	private final ArrayList<StateTriple> action_postcond;

	public LogStep(String index, String actor, String action, ArrayList<StateTriple> current_state, ArrayList<StateTriple> action_precond, ArrayList<StateTriple> action_postcond) {
		this.index = index;
		this.action = action;
		this.actor = actor;
		this.current_state = current_state;
		this.action_precond = action_precond;
		this.action_postcond = action_postcond;
	}

	public String getIndex() {
		return index;
	}

	public String getActor() {
		return actor;
	}

	public String getAction() {
		return action;
	}

	public ArrayList<StateTriple> getCurrentState() {
		return current_state;
	}

	public ArrayList<StateTriple> getActionPrecond() {
		return action_precond;
	}

	public ArrayList<StateTriple> getActionPostcond() {
		return action_postcond;
	}
}
