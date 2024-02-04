package com.baselet.assistant;

public class Scenario {
	private final String name;
	private final String prac;
	private final String precond;
	private final String postcond;
	private final String[] mainflow;

	public Scenario(String name, String prac, String precond, String postcond, String[] mainflow) {
		this.name = name;
		this.prac = prac;
		this.precond = precond;
		this.postcond = postcond;
		this.mainflow = mainflow;
	}

	public String getName() {
		return name;
	}
}
