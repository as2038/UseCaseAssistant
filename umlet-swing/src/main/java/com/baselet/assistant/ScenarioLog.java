package com.baselet.assistant;

import java.util.ArrayList;

public class ScenarioLog {
	private final String name;
	private final ArrayList<LogStep> log_steps;

	public ScenarioLog(String name, ArrayList<LogStep> log_steps) {
		this.name = name;
		this.log_steps = log_steps;
	}

	public String getName() {
		return name;
	}

	public ArrayList<LogStep> getLogSteps() {
		return log_steps;
	}

}
