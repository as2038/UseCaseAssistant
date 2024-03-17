package com.baselet.assistant;

import java.util.ArrayList;

public class Action {

	private final String name;
	private final String object;
	private final ArrayList<StateTriple> precond;
	private final ArrayList<StateTriple> postcond;

	public Action(String name, String object, ArrayList<StateTriple> precond, ArrayList<StateTriple> postcond) {
		this.name = name;
		this.object = object;
		this.precond = precond;
		this.postcond = postcond;
	}

	public String getName() {
		return name;
	}

	public String getObject() {
		return object;
	}

	public ArrayList<StateTriple> getPrecond() {
		return precond;
	}

	public ArrayList<StateTriple> getPostcond() {
		return postcond;
	}
}
