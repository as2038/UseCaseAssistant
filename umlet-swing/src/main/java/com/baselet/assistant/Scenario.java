package com.baselet.assistant;

import java.util.ArrayList;

public class Scenario {
	private final String name;
	private final String prac;
	private final String[] secac;
	private final ArrayList<StateTriple> precond;
	private final ArrayList<StateTriple> postcond;
	private final ArrayList<FlowStep> mainflow_steps;

	public Scenario(String name, String prac, String[] secac, ArrayList<StateTriple> precond, ArrayList<StateTriple> postcond, ArrayList<FlowStep> mainflow_steps) {
		this.name = name;
		this.prac = prac;
		this.precond = precond;
		this.secac = secac;
		this.postcond = postcond;
		this.mainflow_steps = mainflow_steps;
	}

	public String getName() {
		return name;
	}

	public String getPrac() {
		return prac;
	}

	public String[] getSecac() {
		return secac;
	}

	public ArrayList<StateTriple> getPrecond() {
		return precond;
	}

	public ArrayList<StateTriple> getPostcond() {
		return postcond;
	}

	public ArrayList<FlowStep> getMainflowSteps() {
		return mainflow_steps;
	}
}
