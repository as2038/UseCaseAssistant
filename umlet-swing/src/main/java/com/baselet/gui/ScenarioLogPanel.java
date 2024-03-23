package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.baselet.assistant.KnowledgeBase;
import com.baselet.assistant.LogStep;
import com.baselet.assistant.Scenario;
import com.baselet.assistant.ScenarioLog;
import com.baselet.assistant.StateTriple;
import com.baselet.control.Main;

public class ScenarioLogPanel extends JPanel implements ActionListener {

	private static ScenarioLogPanel scenariologpanel;

	public static ScenarioLogPanel getInstance() {
		if (scenariologpanel == null) {
			scenariologpanel = new ScenarioLogPanel();
		}
		return scenariologpanel;
	}

	private final JFrame scenariologframe;

	private final JScrollPane spCurrSt;

	private final JTable currStTable;
	private final DefaultTableModel currStModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JScrollPane spActPrec;

	private final JTable actPrecTable;
	private final DefaultTableModel actPrecModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JScrollPane spActPost;

	private final JTable actPostTable;
	private final DefaultTableModel actPostModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private ArrayList<LogStep> logSteps;
	private int stepNo;
	private int maxStep;

	// UI
	private final JLabel lb_scenario = new JLabel("Scenario:");
	JComboBox scenario = new JComboBox();
	private final JLabel lb_step = new JLabel("Step:");
	private final JTextField tf_stepIndex = new JTextField();
	private final JLabel lb_currSt = new JLabel("Current state:");
	private final JLabel lb_stepAct = new JLabel("Step action:");
	private final JTextField tf_actName = new JTextField();
	private final JLabel lb_stepActPrec = new JLabel("Step action preconditions:");
	private final JLabel lb_stepActPost = new JLabel("Step action preconditions:");

	public ScenarioLogPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		currStModel.addColumn("Entity");
		currStModel.addColumn("State");
		currStModel.addColumn("Value");

		currStModel.setRowCount(0);

		currStTable = new JTable(currStModel);

		spCurrSt = new JScrollPane();
		spCurrSt.setViewportView(currStTable);

		actPrecModel.addColumn("Entity");
		actPrecModel.addColumn("State");
		actPrecModel.addColumn("Value");

		actPrecModel.setRowCount(0);

		actPrecTable = new JTable(actPrecModel);

		spActPrec = new JScrollPane();
		spActPrec.setViewportView(actPrecTable);

		actPostModel.addColumn("Entity");
		actPostModel.addColumn("State");
		actPostModel.addColumn("Value");

		actPrecModel.setRowCount(0);

		actPostTable = new JTable(actPostModel);

		spActPost = new JScrollPane();
		spActPost.setViewportView(actPostTable);

		JButton button_load = new JButton("Load");
		button_load.setActionCommand("Load");
		button_load.addActionListener(this);
		JButton button_previous = new JButton("< Previous Step");
		button_previous.setActionCommand("Previous");
		button_previous.addActionListener(this);
		JButton button_next = new JButton("Next Step >");
		button_next.setActionCommand("Next");
		button_next.addActionListener(this);
		JButton button_close = new JButton("Close");
		button_close.setActionCommand("Close");
		button_close.addActionListener(this);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BoxLayout(top_panel, BoxLayout.X_AXIS));
		top_panel.add(scenario);
		top_panel.add(Box.createHorizontalGlue());
		top_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		top_panel.add(button_load);
		top_panel.add(Box.createHorizontalGlue());
		top_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
		button_panel.add(button_previous);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(button_next);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(button_close);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_scenario);
		parent.add(top_panel);
		parent.add(lb_step);
		parent.add(tf_stepIndex);
		parent.add(lb_currSt);
		parent.add(spCurrSt);
		parent.add(lb_stepAct);
		parent.add(tf_actName);
		parent.add(lb_stepActPrec);
		parent.add(spActPrec);
		parent.add(lb_stepActPost);
		parent.add(spActPost);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		scenariologframe = new JFrame("Scenario");
		scenariologframe.setContentPane(parent);
		scenariologframe.pack();
	}

	public void showScenarioLogPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scenariologframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				scenariologframe.setVisible(true);
				scenariologframe.setSize(500, 400);
				scenariologframe.toFront();

				scenariologframe.setTitle("Scenario Logs");

				Main main = Main.getInstance();
				KnowledgeBase kb = main.getKnowledgeBase();
				Map<String, Scenario> scenarioM = kb.getScenarioMap();
				if (scenarioM.size() > 0) {
					String[] scenarioOptions = new String[scenarioM.size()];
					int i = 0;
					for (String scenarioStr : scenarioM.keySet()) {
						scenarioOptions[i] = scenarioStr;
						i++;
					}
					scenario.setModel(new DefaultComboBoxModel(scenarioOptions));
				}
			}
		});
	}

	public void hideScenarioLogPanel() {
		scenariologframe.setVisible(false);
	}

	private void loadStep() {
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();
		ScenarioLog sl = kb.getLogMap().get(scenario.getSelectedItem().toString());
		logSteps = sl.getLogSteps();
		maxStep = logSteps.size();
		LogStep ls = logSteps.get(stepNo);
		tf_stepIndex.setText(ls.getIndex());
		tf_actName.setText(ls.getActor() + " - " + ls.getAction());

		currStModel.setRowCount(0);
		actPrecModel.setRowCount(0);
		actPostModel.setRowCount(0);

		for (StateTriple crst : ls.getCurrentState()) {
			currStModel.addRow(new Object[] { crst.getEntity(), crst.getState(), crst.getValue() });
		}
		if (ls.getActionPrecond() != null) {
			for (StateTriple prst : ls.getActionPrecond()) {
				actPrecModel.addRow(new Object[] { prst.getEntity(), prst.getState(), prst.getValue() });
			}
			for (StateTriple post : ls.getActionPostcond()) {
				actPostModel.addRow(new Object[] { post.getEntity(), post.getState(), post.getValue() });
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Load")) {
			stepNo = 0;
			loadStep();
		}
		if (ae.getActionCommand().equals("Previous")) {
			if (stepNo > 0) {
				stepNo--;
				loadStep();
			}
		}
		if (ae.getActionCommand().equals("Next")) {
			if (stepNo < maxStep - 1) {
				stepNo++;
				loadStep();
			}
		}
		if (ae.getActionCommand().equals("Close")) {
			hideScenarioLogPanel();
		}
	}

}
