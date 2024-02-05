package com.baselet.assistant;

public class Action {

	private final String name;
	private final String precond;
	private final String postcond;

	public Action(String name, String precond, String postcond) {
		this.name = name;
		this.precond = precond;
		this.postcond = postcond;
	}

	public String getName() {
		return name;
	}

	public String getPrecond() {
		return precond;
	}

	public String getPostcond() {
		return postcond;
	}
}
