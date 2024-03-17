package com.baselet.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baselet.gui.BaseGUI;
import com.baselet.gui.CurrentGui;

public class KnowledgeBase {

	private String last_scenario_name;
	private String last_actor_name;
	private String last_action_name;
	private SystemBoundary system_boundary;
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
		if (!scenario_map.containsKey(new_scenario.getName())) {
			gui.getScenarioListPanel().addScenarioToTable(new_scenario.getName(), new_scenario.getPrac());
		}
		scenario_map.put(new_scenario.getName(), new_scenario);
	}

	public void addActor(Actor new_actor) {
		if (!actor_map.containsKey(new_actor.getName())) {
			gui.getKnowledgeBasePanel().addEntityToTable(new_actor.getName(), "actor");
		}
		actor_map.put(new_actor.getName(), new_actor);
	}

	public void addAction(Action new_action) {
		if (!action_map.containsKey(new_action.getName())) {
			gui.getKnowledgeBasePanel().addEntityToTable(new_action.getName(), "action");
		}
		action_map.put(new_action.getName(), new_action);
	}

	public void addObject(String object_name) {
		boolean object_exists = false;
		for (String on : object_list) {
			if (on.equals(object_name)) {
				object_exists = true;
			}
		}
		if (!object_exists) {
			object_list.add(object_name);
			gui.getKnowledgeBasePanel().addEntityToTable(object_name, "object");
		}
	}

	public void addState(String state_name) {
		boolean state_exists = false;
		for (String sn : state_list) {
			if (sn.equals(state_name)) {
				state_exists = true;
			}
		}
		if (!state_exists) {
			state_list.add(state_name);
			gui.getKnowledgeBasePanel().addEntityToTable(state_name, "state");
		}
	}

	public void addSystem(SystemBoundary new_system) {
		if (system_boundary == null) {
			gui.getKnowledgeBasePanel().addEntityToTable(new_system.getName(), "system");
		}
		system_boundary = new_system;
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

	public SystemBoundary getSystem(String system_name) {
		return system_boundary;
	}

	public void setLastActorName(String last_actor_name) {
		this.last_actor_name = last_actor_name;
	}

	public String getLastActorName() {
		return last_actor_name;
	}

	public Map<String, Scenario> getScenarioMap() {
		return scenario_map;
	}

	public Map<String, Actor> getActorMap() {
		return actor_map;
	}

	public Map<String, Action> getActionMap() {
		return action_map;
	}

	public ArrayList<String> getStateList() {
		return state_list;
	}

	public ArrayList<String> getObjectList() {
		return object_list;
	}

	public DuckHandler getDuckHandler() {
		return duck_handler;
	}

	public void deleteScenario(String scenario_name) {
		scenario_map.remove(scenario_name);
	}

	public void deleteActor(String actor_name) {
		actor_map.remove(actor_name);
	}

	public void deleteAction(String action_name) {
		action_map.remove(action_name);
	}

	public void deleteObject(String object_name) {
		object_list.remove(object_list.indexOf(object_name));
	}

	public void deleteState(String state_name) {
		state_list.remove(state_list.indexOf(state_name));

	}
}
