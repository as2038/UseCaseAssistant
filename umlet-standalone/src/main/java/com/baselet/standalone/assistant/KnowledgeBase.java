package com.baselet.standalone.assistant;

import java.util.Map;

public class KnowledgeBase {

	private Map<String, Scenario> scenario_map;
	private Map<String, Actor> actor_map;
	private Map<String, Action> action_map;

	public KnowledgeBase() {
		// TODO Auto-generated constructor stub
	}

	public void addScenario(String new_scenario_name, Scenario new_scenario) {
		scenario_map.put(new_scenario_name, new_scenario);
	}

	public void addActor() {

	}

	public void addAction() {

	}

	public Scenario getScenario(String scenario_name) {
		return scenario_map.get(scenario_name);
	}

	public Actor getActor(String actor_name) {
		return actor_map.get(actor_name);
	}

	public Action getAction(String action_name) {
		return action_map.get(action_name);
	}

}
