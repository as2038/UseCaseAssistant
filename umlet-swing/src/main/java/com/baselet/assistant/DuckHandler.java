package com.baselet.assistant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.baselet.control.Main;
import com.baselet.control.basics.geom.Rectangle;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.relation.Relation;
import com.baselet.element.sticking.PointDoubleIndexed;
import com.baselet.gui.ActorPanel;
import com.baselet.gui.CurrentGui;
import com.baselet.gui.ScenarioPanel;

public class DuckHandler {

	public DuckHandler() {
		// TODO Auto-generated constructor stub
	}

	public void checkConsistency() {
		boolean noProblems = true;
		Main main = Main.getInstance();
		KnowledgeBase knowledgeBase = main.getKnowledgeBase();

		for (Map.Entry<String, Scenario> set : knowledgeBase.getScenarioMap().entrySet()) {
			Scenario s = set.getValue();
			String s_name = s.getName();
			String pc = s.getPostcond();
			if (knowledgeBase.getActor(s.getPrac()) == null) {
				System.out.println("ERROR (" + s_name + "): Actor '" + s.getPrac() + "' does not exist in the Knowledge Base.");
				noProblems = false;
			}

			ArrayList<String> mf = s.getMainflow();
			for (String a : mf) {

				if (!a.equals("")) {
					Action act = knowledgeBase.getAction(a);
					if (act == null) {
						System.out.println("ERROR (" + s_name + "): Action '" + a + "' does not exist in the Knowledge Base.");
						noProblems = false;
					}
					else {
						if (!knowledgeBase.getActor(s.getPrac()).getActionList().contains(act)) {
							System.out.println("ERROR (" + s_name + "): Actor '" + s.getPrac() + "' does not have action '" + a + ".");
							noProblems = false;
						}
						else {
							if (!act.getPostcond().equals(pc)) {
								System.out.println("ERROR (" + s_name + "): postcondition not satisfied.");
								noProblems = false;
							}
						}
					}
				}
			}
			System.out.println("");
			if (noProblems) {
				System.out.println("No problems found!");
			}
		}

		ArrayList<GridElement> entities = new ArrayList<GridElement>();
		ArrayList<GridElement> relations = new ArrayList<GridElement>();
		ArrayList<Double> rectangleX1s = new ArrayList<Double>();
		ArrayList<Double> rectangleY1s = new ArrayList<Double>();
		ArrayList<Double> rectangleX2s = new ArrayList<Double>();
		ArrayList<Double> rectangleY2s = new ArrayList<Double>();

		for (GridElement ge : CurrentGui.getInstance().getGui().getCurrentDiagram().getGridElements()) {
			if (ge.getClass().toString().contains("Relation")) {
				relations.add(ge);
			}
			else {
				entities.add(ge);
			}
		}

		for (GridElement e : entities) {

		}

		for (GridElement ge : CurrentGui.getInstance().getGui().getCurrentDiagram().getGridElements()) {
			Rectangle stickRectangle = ((Relation) ge).getRealRectangle();
			if (ge.getClass().toString().contains("Relation")) {
				String relation_type = ge.getPanelAttributes();

				if (relation_type.contains("includes")) {
					relation_type = "includes";
				}
				else if (relation_type.contains("extends")) {
					relation_type = "extends";
				}
				else if (relation_type.contains("-")) {
					relation_type = "abstraction";
				}
				else {
					relation_type = "actor-usecase";
				}

				Double p0x = 0.0;
				Double p0y = 0.0;
				Double p1x = 0.0;
				Double p1y = 0.0;

				Iterator<PointDoubleIndexed> stickCoords = ((Relation) ge).getStickablePoints().iterator();

				int i = 0;
				while (stickCoords.hasNext()) {
					PointDoubleIndexed pdi = stickCoords.next();

					if (i == 0) {
						p0x = pdi.getX() + stickRectangle.getX();
						p0y = pdi.getY() + stickRectangle.getY();
						System.out.println("Point 0 absolute position: x= " + p0x + " y=" + p0y);

					}
					else if (i == 1) {
						p1x = pdi.getX() + stickRectangle.getX();
						p1y = pdi.getY() + stickRectangle.getY();
						System.out.println("Point 1 absolute position: x= " + p1x + " y=" + p1y);
					}
					i++;
				}

				System.out.println("Type " + relation_type + "\nReal rectangle: " + stickRectangle.toString());
			}
			else {
				System.out.println(ge.getRectangle().toString());
				actorsAndUseCases.add(ge);
			}

			for (GridElement aouc : actorsAndUseCases) {

			}
		}

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
}
