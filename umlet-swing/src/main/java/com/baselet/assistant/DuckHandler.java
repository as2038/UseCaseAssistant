package com.baselet.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.baselet.control.Main;
import com.baselet.control.basics.geom.Rectangle;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.relation.Relation;
import com.baselet.element.sticking.PointDoubleIndexed;
import com.baselet.gui.ActorPanel;
import com.baselet.gui.CurrentGui;
import com.baselet.gui.DUCKHelpPanel;
import com.baselet.gui.EARSPanel;
import com.baselet.gui.ScenarioPanel;

public class DuckHandler {

	public DuckHandler() {

	}

	public ArrayList<String> checkConsistency() {
		ArrayList<String> warnings = new ArrayList<String>();
		boolean noProblems = true;
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();
		HashMap<String, Scenario> scenario_map = (HashMap<String, Scenario>) kb.getScenarioMap();
		HashMap<String, Actor> actor_map = (HashMap<String, Actor>) kb.getActorMap();
		ArrayList<RelationTriple> relation_list = new ArrayList<RelationTriple>();

		for (Map.Entry<String, Scenario> set : scenario_map.entrySet()) {

			Scenario s = set.getValue();
			String s_name = s.getName();
			String pa = s.getPrac();
			String sa = s.getSecac()[0];
			relation_list.add(new RelationTriple(s_name, pa, "actor-usecase"));

			if (kb.getActor(pa) == null) {
				warnings.add("Warning (" + s_name + "): Actor '" + s.getPrac() + "' does not exist in the Knowledge Base.\n");
				noProblems = false;
			}
			ArrayList<FlowStep> mainflow_steps = s.getMainflowSteps();
			ArrayList<StateTriple> flow_states = s.getPrecond();

			// Running the proof step-by-step
			for (FlowStep fs : mainflow_steps) {
				Actor fs_actor = kb.getActor(fs.getActor());
				Action fs_action = kb.getAction(fs.getAction());
				if (fs_actor == null) {
					warnings.add("Warning (" + s_name + "): Actor '" + fs.getActor() + "' does not exist in the Knowledge Base.\n");
					noProblems = false;
				}
				else if (!fs_actor.getActionList().contains(fs_action)) {
					warnings.add("Warning (" + s_name + "): Actor '" + fs.getActor() + "' does not have action '" + fs.getAction() + "'.\n");
					noProblems = false;
				}

				if (fs_action == null) {
					warnings.add("Warning (" + s_name + "): Action '" + fs.getAction() + "' does not exist in the Knowledge Base.\n");
					noProblems = false;
				}
				else {
					for (StateTriple st : fs_action.getPrecond()) {
						boolean satisfied = false;
						for (StateTriple cst : flow_states) {
							if (st.getEntity().equals(cst.getEntity()) && st.getState().equals(cst.getState()) && st.getValue().equals(cst.getValue())) {
								for (StateTriple pst : fs_action.getPostcond()) {
									boolean changed = false;
									for (StateTriple nst : flow_states) {
										if (pst.getEntity().equals(cst.getEntity()) && pst.getState().equals(cst.getState())) {
											int edit_index = flow_states.indexOf(nst);
											nst.setValue(pst.getValue());
											flow_states.add(edit_index, st);
											changed = true;
											break;
										}
									}
									if (!changed) {
										flow_states.add(pst);
									}
								}
								satisfied = true;
								break;
							}
						}
						if (!satisfied) {
							warnings.add("Warning (" + s_name + "): Precondition not satisfied for action '" + fs.getAction() + "'.\n");
							noProblems = false;
						}
					}
				}
			}
			// Checking that all postconditions are satisfied
			for (StateTriple pst : s.getPostcond()) {
				boolean satisfied = false;
				for (StateTriple cst : flow_states) {
					if (pst.getEntity().equals(cst.getEntity()) && pst.getState().equals(cst.getState()) && pst.getValue().equals(cst.getValue())) {
						satisfied = true;
						break;
					}
				}
				if (!satisfied) {
					warnings.add("Warning (" + s_name + "): Postcondition (" + pst.getEntity() + ", " + pst.getState() + ", " + pst.getValue() + ") not satisfied.\n");
					noProblems = false;
				}
			}
		}

		System.out.println();
		ArrayList<GridElement> entities = new ArrayList<GridElement>();
		ArrayList<GridElement> relations = new ArrayList<GridElement>();
		ArrayList<Double> rectX1s = new ArrayList<Double>();
		ArrayList<Double> rectY1s = new ArrayList<Double>();
		ArrayList<Double> rectX2s = new ArrayList<Double>();
		ArrayList<Double> rectY2s = new ArrayList<Double>();

		for (GridElement ge : CurrentGui.getInstance().getGui().getCurrentDiagram().getGridElements()) {
			String entity_name = ge.getPanelAttributes();
			String entity_class = ge.getClass().toString();

			if (entity_class.contains("Relation")) {
				relations.add(ge);
			}
			else {
				entities.add(ge);
				Rectangle rect = ge.getRectangle();
				rectX1s.add((double) rect.getX());
				rectY1s.add((double) rect.getY());
				rectX2s.add((double) rect.getX2());
				rectY2s.add((double) rect.getY2());

				if (entity_class.contains("UseCase")) {
					if (scenario_map.containsKey(entity_name)) {
					}
					else {
						warnings.add("Warning (Diagram): No scenario exists for the use case '" + entity_name + "'.\n");
						noProblems = false;
					}
				}
				else if (entity_class.contains("Actor")) {
					if (actor_map.containsKey(entity_name)) {
					}
					else {
						warnings.add("Warning (Diagram): Actor '" + entity_name + "' does not exist in the Knowledge Base.\n");
						noProblems = false;
					}

				}
			}
		}

		for (GridElement r : relations) {
			Rectangle stickRectangle = ((Relation) r).getRealRectangle();

			Double p0x = 0.0;
			Double p0y = 0.0;
			Double p1x = 0.0;
			Double p1y = 0.0;

			Iterator<PointDoubleIndexed> stickCoords = ((Relation) r).getStickablePoints().iterator();
			String entity1 = "nothing";
			String entity2 = "nothing";
			String entity1type = "";
			String entity2type = "";

			int i = 0;
			while (stickCoords.hasNext()) {
				PointDoubleIndexed pdi = stickCoords.next();

				if (i == 0) {
					p0x = pdi.getX() + stickRectangle.getX();
					p0y = pdi.getY() + stickRectangle.getY();
				}
				else if (i == 1) {
					p1x = pdi.getX() + stickRectangle.getX();
					p1y = pdi.getY() + stickRectangle.getY();
				}
				for (int j = 0; j < entities.size(); j++) {
					if (p0x >= rectX1s.get(j) && p0y >= rectY1s.get(j) && p0x <= rectX2s.get(j) && p0y <= rectY2s.get(j)) {
						entity1 = entities.get(j).getPanelAttributes();
						entity1type = entities.get(j).getClass().toString();
						if (entity1type.contains("Actor")) {
							entity1type = "actor";
						}
						else if (entity1type.contains("UseCase")) {
							entity1type = "use case";
						}
					}
					else if (p1x >= rectX1s.get(j) && p1y >= rectY1s.get(j) && p1x <= rectX2s.get(j) && p1y <= rectY2s.get(j)) {
						entity2 = entities.get(j).getPanelAttributes();
						entity2type = entities.get(j).getClass().toString();
						if (entity2type.contains("Actor")) {
							entity2type = "actor";
						}
						else if (entity2type.contains("UseCase")) {
							entity2type = "use case";
						}
					}
				}

				i++;
			}

			String relation_type = r.getPanelAttributes();

			if (relation_type.contains("includes")) {
				relation_type = "includes";
				if (entity1type.equals("actor") || entity2type.equals("actor")) {
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'includes' relation.\n");
					noProblems = false;
				}
			}
			else if (relation_type.contains("extends")) {
				relation_type = "extends";
				if (entity1type.equals("actor") || entity2type.equals("actor")) {
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'extends' relation.\n");
					noProblems = false;
				}
			}
			else if (relation_type.contains("-")) {
				relation_type = "abstraction";
				if (entity1type.equals("use case") || entity2type.equals("use case")) {
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'abstraction' relation.\n");
					noProblems = false;
				}
			}
			else {
				relation_type = "actor-usecase";
				if (!(entity1type.equals("use case") && entity2type.equals("actor") || entity2type.equals("use case") && entity1type.equals("actor"))) {
					warnings.add("Warning (Diagram): Cannot connect an " + entity1type + "(" + entity1 + ") and " + entity2type + "(" + entity2 + ") with an 'actor-usecase' relation.\n");
					noProblems = false;
				}

				else {
					if (entity2type.equals("use case")) {
						String temp_a = entity1;
						entity1 = entity2;
						entity2 = temp_a;
					}
					boolean matched = false;
					for (int k = 0; k < relation_list.size(); k++) {
						RelationTriple temp_r = relation_list.get(k);
						if (temp_r.getType().equals("actor-usecase")) {
							if (entity1.contains(temp_r.getFirstEntity()) && entity2.contains(temp_r.getSecondEntity())) {
								matched = true;
								relation_list.remove(k);
								break;
							}
						}
					}
					if (!matched) {
						warnings.add("Warning (Diagram): Actor '" + entity2 + "' is not mentioned in use case '" + entity1 + "', but a connection was drawn.\n");
						noProblems = false;
					}
				}
			}
		}
		for (RelationTriple rt : relation_list) {
			warnings.add("Warning (Diagram): Use case '" + rt.getFirstEntity() + "' has a reference to actor '" + rt.getSecondEntity() + "', but no connection was drawn.\n");
			noProblems = false;
		}
		if (noProblems) {
			warnings.add("No problems found!");
		}
		return warnings;
	}

	public void showProperties() {
		String selected_elements_string = CurrentGui.getInstance().getGui().getSelectedElements().toString();
		String element_id = selected_elements_string.substring(selected_elements_string.lastIndexOf("@") + 1);
		element_id = element_id.replace("]", "");
		String element_type = selected_elements_string.substring(selected_elements_string.lastIndexOf(".") + 1);
		element_type = element_type.substring(0, element_type.lastIndexOf("@"));
		if (element_type.equals("Actor")) {
			ActorPanel.getInstance().showActorPanel();
		}
		else if (element_type.equals("UseCase")) {
			ScenarioPanel.getInstance().showScenarioPanel();
		}
		else if (element_type.equals("Relation")) {

		}
	}

	public void showHelp() {
		DUCKHelpPanel.getInstance().showDuckHelpPanel();
	}

	public void showEARS() {
		EARSPanel.getInstance().showEARSPanel();
	}
}
