package com.baselet.assistant;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeBase {

	private String last_temp_name;
	private final Map<String, Scenario> scenario_map;
	private final Map<String, Actor> actor_map;
	private final Map<String, Action> action_map;

	public KnowledgeBase() {
		scenario_map = new HashMap<String, Scenario>();
		actor_map = new HashMap<String, Actor>();
		action_map = new HashMap<String, Action>();
	}

	public void addScenario(Scenario new_scenario) {
		scenario_map.put(new_scenario.getName(), new_scenario);
	}

	public void addActor(Actor new_actor) {
		actor_map.put(new_actor.getName(), new_actor);
	}

	public void addAction(Action new_action) {
		action_map.put(new_action.getName(), new_action);
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

	public void setLastTempName(String last_temp_name) {
		this.last_temp_name = last_temp_name;
	}

	public String getLastTempName() {
		return last_temp_name;
	}

}
