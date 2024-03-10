package com.baselet.assistant;

public class FlowStep {
	private final String actor;
	private final String action;

	public FlowStep(String actor, String action) {
		this.actor = actor;
		this.action = action;
	}

	public String getActor() {
		return actor;
	}

	public String getAction() {
		return action;
	}
}
