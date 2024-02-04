package com.baselet.assistant;

import java.util.ArrayList;

public class Scenario {
	private final String name;
	private final String prac;
	private final String precond;
	private final String postcond;
	private final ArrayList<String> mainflow;

	public Scenario(String name, String prac, String precond, String postcond, ArrayList<String> mainflow) {
		this.name = name;
		this.prac = prac;
		this.precond = precond;
		this.postcond = postcond;
		this.mainflow = mainflow;
	}

	public String getName() {
		return name;
	}

	public String getPrac() {
		return prac;
	}

	public String getPrecond() {
		return precond;
	}

	public String getPostcond() {
		return postcond;
	}

	public ArrayList<String> getMainflow() {
		return mainflow;
	}
}
