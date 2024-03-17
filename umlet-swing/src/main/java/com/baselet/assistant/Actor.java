package com.baselet.assistant;

import java.util.ArrayList;

public class Actor {

	private final String name;
	private final ArrayList<String> action_list;

	public Actor(String name, ArrayList<String> action_list) {
		this.name = name;
		this.action_list = action_list;

		// System.out.println("Saved actor " + name + " with " + action_list.size() + " actions");
	}

	public void addAction(String action) {
		action_list.add(action);
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getActionList() {
		return action_list;
	}

}
