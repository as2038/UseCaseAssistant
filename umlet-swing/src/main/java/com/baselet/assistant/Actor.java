package com.baselet.assistant;

import java.util.ArrayList;

public class Actor {

	private final String name;
	private final ArrayList<Action> action_list;

	public Actor(String name, ArrayList<Action> action_list) {
		this.name = name;
		this.action_list = action_list;
	}

	public void addAction(Action action) {
		action_list.add(action);
	}

	public String getName() {
		return name;
	}

	public ArrayList<Action> getActionList() {
		return action_list;
	}

}
