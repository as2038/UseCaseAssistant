package com.baselet.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baselet.gui.BaseGUI;
import com.baselet.gui.CurrentGui;

public class KnowledgeBase {

	private String last_temp_name;
	private final Map<String, Scenario> scenario_map;
	private final Map<String, Actor> actor_map;
	private final Map<String, Action> action_map;
	private final ArrayList<String> object_list;
	private final ArrayList<String> state_list;
	private final DuckHandler duck_handler;

	BaseGUI gui = CurrentGui.getInstance().getGui();

	public KnowledgeBase() {
		scenario_map = new HashMap<String, Scenario>();
		actor_map = new HashMap<String, Actor>();
		action_map = new HashMap<String, Action>();
		object_list = new ArrayList<String>();
		state_list = new ArrayList<String>();
		duck_handler = new DuckHandler();
	}

	public void addScenario(Scenario new_scenario) {
		scenario_map.put(new_scenario.getName(), new_scenario);
		gui.getScenarioListPanel().addScenarioToTable(new_scenario.getName(), new_scenario.getPrac());
	}

	public void addActor(Actor new_actor) {
		actor_map.put(new_actor.getName(), new_actor);
		gui.getKnowledgeBasePanel().addEntityToTable(new_actor.getName(), "actor");
	}

	public void addAction(Action new_action) {
		action_map.put(new_action.getName(), new_action);
		gui.getKnowledgeBasePanel().addEntityToTable(new_action.getName(), "action");
	}

	public void addObject(String object_name) {
		if (!object_list.contains(object_name)) {
			object_list.add(object_name);
			gui.getKnowledgeBasePanel().addEntityToTable(object_name, "object");
		}
	}

	public void addState(String state_name) {
		if (!object_list.contains(state_name)) {
			state_list.add(state_name);
			gui.getKnowledgeBasePanel().addEntityToTable(state_name, "state");
		}
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

	public Map<String, Scenario> getScenarioMap() {
		return scenario_map;
	}

	public Map<String, Actor> getActorMap() {
		return actor_map;
	}

	public DuckHandler getDuckHandler() {
		return duck_handler;
	}
}
