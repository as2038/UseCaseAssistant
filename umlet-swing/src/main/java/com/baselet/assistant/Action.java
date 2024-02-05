package com.baselet.assistant;

public class Action {

	private final String name;
	private String precond;
	private String postcond;

	public Action(String name) {
		this.name = name;
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
