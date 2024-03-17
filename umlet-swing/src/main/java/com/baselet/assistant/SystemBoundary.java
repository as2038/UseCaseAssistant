package com.baselet.assistant;

import java.util.ArrayList;

public class SystemBoundary {
	private final String name;
	private final ArrayList<String> action_list;

	public SystemBoundary(String name, ArrayList<String> action_list) {
		this.name = name;
		this.action_list = action_list;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getActionList() {
		return action_list;
	}
}
