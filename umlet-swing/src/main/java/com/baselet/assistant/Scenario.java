package com.baselet.assistant;

import java.util.ArrayList;

public class Scenario {
	private final String name;
	private final String prac;
	private final String[] secac;
	private final String precond;
	private final String postcond;
	private final ArrayList<String> mainflow;

	public Scenario(String name, String prac, String[] secac, String precond, String postcond, ArrayList<String> mainflow) {
		this.name = name;
		this.prac = prac;
		this.precond = precond;
		this.secac = secac;
		this.postcond = postcond;
		this.mainflow = mainflow;
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
