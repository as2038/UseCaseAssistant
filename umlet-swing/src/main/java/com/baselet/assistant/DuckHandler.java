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
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> checkConsistency() {
		ArrayList<String> warnings = new ArrayList<String>();
		boolean noProblems = true;
		Main main = Main.getInstance();
		KnowledgeBase knowledgeBase = main.getKnowledgeBase();
		HashMap<String, Scenario> scenario_map = (HashMap<String, Scenario>) knowledgeBase.getScenarioMap();
		HashMap<String, Actor> actor_map = (HashMap<String, Actor>) knowledgeBase.getActorMap();
		ArrayList<RelationTriple> relation_list = new ArrayList<RelationTriple>();

		for (Map.Entry<String, Scenario> set : scenario_map.entrySet()) {

			Scenario s = set.getValue();
			String s_name = s.getName();
			String pc = s.getPostcond();
			String pa = s.getPrac();
			String sa = s.getSecac()[0];
			// entity_map.put(s_name, "scenario");
			relation_list.add(new RelationTriple(s_name, pa, "actor-usecase"));
			// relation_list.add(new RelationTriple(s_name, sa, "actor-usecase"));

			if (knowledgeBase.getActor(pa) == null) {
				// System.out.println("Warning: (" + s_name + "): Actor '" + s.getPrac() + "' does not exist in the Knowledge Base.");
				warnings.add("Warning (" + s_name + "): Actor '" + s.getPrac() + "' does not exist in the Knowledge Base.\n");
				noProblems = false;
			}

			ArrayList<String> mf = s.getMainflow();
			for (String a : mf) {

				if (!a.equals("")) {
					Action act = knowledgeBase.getAction(a);
					if (act == null) {
						// System.out.println("Warning: (" + s_name + "): Action '" + a + "' does not exist in the Knowledge Base.");
						warnings.add("Warning (" + s_name + "): Action '" + a + "' does not exist in the Knowledge Base.\n");
						noProblems = false;
					}
					else {
						if (!knowledgeBase.getActor(s.getPrac()).getActionList().contains(act)) {
							// System.out.println("Warning: (" + s_name + "): Actor '" + s.getPrac() + "' does not have action '" + a + ".");
							warnings.add("Warning (" + s_name + "): Actor '" + s.getPrac() + "' does not have action '" + a + ".\n");
							noProblems = false;
						}
						else {
							if (!act.getPostcond().equals(pc)) {
								// System.out.println("Warning: (" + s_name + "): postcondition not satisfied.");
								warnings.add("Warning (" + s_name + "): postcondition not satisfied.\n");
								noProblems = false;
							}
						}
					}
				}
			}

		}
		/* for (Map.Entry<String, Actor> set : knowledgeBase.getActorMap().entrySet()) { Actor a = set.getValue(); entity_map.put(a.getName(), "actor"); } */

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
						// scenario_map.remove(entity_name);
					}
					else {
						warnings.add("Warning (Diagram): No scenario exists for the use case '" + entity_name + "'.\n");
						noProblems = false;
					}
				}
				else if (entity_class.contains("Actor")) {
					if (actor_map.containsKey(entity_name)) {
						// actor_map.remove(entity_name);
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
					// System.out.println("Point 0 absolute position: x= " + p0x + " y=" + p0y);
				}
				else if (i == 1) {
					p1x = pdi.getX() + stickRectangle.getX();
					p1y = pdi.getY() + stickRectangle.getY();
					// System.out.println("Point 1 absolute position: x= " + p1x + " y=" + p1y);
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
					// System.out.println("Warning: Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'includes' relation!");
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'includes' relation.\n");
					noProblems = false;
				}
			}
			else if (relation_type.contains("extends")) {
				relation_type = "extends";
				if (entity1type.equals("actor") || entity2type.equals("actor")) {
					// System.out.println("Warning: Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'extends' relation!");
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'extends' relation.\n");
					noProblems = false;
				}
			}
			else if (relation_type.contains("-")) {
				relation_type = "abstraction";
				if (entity1type.equals("use case") || entity2type.equals("use case")) {
					// System.out.println("Warning: Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'abstraction' relation!");
					warnings.add("Warning (Diagram): Cannot connect " + entity1 + " (" + entity1type + ") and " + entity2 + " (" + entity2type + ") with an 'abstraction' relation.\n");
					noProblems = false;
				}
			}
			else {
				relation_type = "actor-usecase";

				if (!(entity1type.equals("use case") && entity2type.equals("actor") || entity2type.equals("use case") && entity1type.equals("actor"))) {

					// if (entity1type.equals(entity2type)) {
					// System.out.println("Warning: Cannot connect two " + entity1type + " entities (" + entity1 + " and " + entity2 + ") with an 'actor-usecase' relation!");
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
								// System.out.println(entity1 + " and " + entity2 + " matched on the diagram!");
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

			// System.out.println("An '" + relation_type + "' relation connects " + entity1 + " and " + entity2);
		}
		for (RelationTriple rt : relation_list) {
			warnings.add("Warning (Diagram): Use case '" + rt.getFirstEntity() + "' has a reference to actor '" + rt.getSecondEntity() + "', but no connection was drawn.\n");
			noProblems = false;
		}
		if (noProblems) {
			// System.out.println("No problems found!");
			warnings.add("No problems found!");
		}
		return warnings;
	}

	public void showProperties() {
		// System.out.println("Element name: " + CurrentGui.getInstance().getGui().getPropertyPane().getText());
		String selected_elements_string = CurrentGui.getInstance().getGui().getSelectedElements().toString();
		// System.out.println(selected_elements_string);
		String element_id = selected_elements_string.substring(selected_elements_string.lastIndexOf("@") + 1);
		element_id = element_id.replace("]", "");
		// System.out.println("Element ID: " + element_id);
		String element_type = selected_elements_string.substring(selected_elements_string.lastIndexOf(".") + 1);
		element_type = element_type.substring(0, element_type.lastIndexOf("@"));
		// System.out.println("Element type: " + element_type);
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
