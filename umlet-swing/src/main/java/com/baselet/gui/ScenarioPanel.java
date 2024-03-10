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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.baselet.assistant.Action;
import com.baselet.assistant.Actor;
import com.baselet.assistant.FlowStep;
import com.baselet.assistant.KnowledgeBase;
import com.baselet.assistant.Scenario;
import com.baselet.assistant.StateTriple;
import com.baselet.control.Main;

public class ScenarioPanel extends JPanel implements ActionListener {

	private static ScenarioPanel scenariopanel;

	public static ScenarioPanel getInstance() {
		if (scenariopanel == null) {
			scenariopanel = new ScenarioPanel();
		}
		return scenariopanel;
	}

	private final JFrame scenarioframe;

	private final JScrollPane spMain;
	private final JScrollPane spAlt;
	private final JScrollPane spPrec;
	private final JScrollPane spPost;

	private final JTable mainFlowTable;
	private final JTable altFlowTable;
	private final JTable precTable;
	private final JTable postTable;

	private final DefaultTableModel mainFlowModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private final DefaultTableModel altFlowModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final DefaultTableModel precModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final DefaultTableModel postModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JLabel lb_prac = new JLabel("Primary actor:");
	private final JTextField tf_prac = new JTextField();

	private final JLabel lb_secac = new JLabel("Secondary actor(s):");
	private final JTextField tf_secac = new JTextField();

	private final JLabel lb_prec = new JLabel("Preconditions:");
	private final JTextField tf_prec = new JTextField();

	private final JLabel lb_postc = new JLabel("Postconditions:");
	private final JTextField tf_postc = new JTextField();

	private final JLabel lb_mainflow = new JLabel("Main flow:");

	private final JLabel lb_altflow = new JLabel("Alternative flows:");
	private final JTextArea ta_altflow = new JTextArea(5, 5);
	JScrollPane sp_altflow = new JScrollPane(ta_altflow);

	private String temp_name;
	private final ArrayList<FlowStep> mainflow_steps = new ArrayList<FlowStep>();
	private final ArrayList<StateTriple> preconditions = new ArrayList<StateTriple>();
	private final ArrayList<StateTriple> postconditions = new ArrayList<StateTriple>();

	private ScenarioPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		mainFlowModel.addColumn("Actor");
		mainFlowModel.addColumn("Action");
		mainFlowModel.setRowCount(0);
		mainFlowTable = new JTable(mainFlowModel);

		spMain = new JScrollPane();
		spMain.setViewportView(mainFlowTable);

		altFlowModel.addColumn("Actor");
		altFlowModel.addColumn("Action");
		altFlowModel.setRowCount(0);
		altFlowTable = new JTable(altFlowModel);

		spAlt = new JScrollPane();
		spAlt.setViewportView(altFlowTable);

		precModel.addColumn("Entity");
		precModel.addColumn("State");
		precModel.addColumn("Value");
		precModel.setRowCount(0);
		precTable = new JTable(precModel);

		spPrec = new JScrollPane();
		spPrec.setViewportView(precTable);

		postModel.addColumn("Entity");
		postModel.addColumn("State");
		postModel.addColumn("Value");
		postModel.setRowCount(0);
		postTable = new JTable(postModel);

		spPost = new JScrollPane();
		spPost.setViewportView(postTable);

		JButton button_addprec = new JButton("Add");
		button_addprec.setActionCommand("AddPrec");
		button_addprec.addActionListener(this);
		JButton button_editprec = new JButton("Edit");
		button_editprec.setActionCommand("EditPrec");
		button_editprec.addActionListener(this);
		JButton button_deleteprec = new JButton("Delete");
		button_deleteprec.setActionCommand("DeletePrec");

		JButton button_addpost = new JButton("Add");
		button_addpost.setActionCommand("AddPost");
		button_addpost.addActionListener(this);
		JButton button_editpost = new JButton("Edit");
		button_editpost.setActionCommand("EditPost");
		button_editpost.addActionListener(this);
		JButton button_deletepost = new JButton("Delete");
		button_deletepost.setActionCommand("DeletePost");

		JButton button_addmain = new JButton("Add");
		button_addmain.setActionCommand("AddMain");
		button_addmain.addActionListener(this);
		JButton button_editmain = new JButton("Edit");
		button_editmain.setActionCommand("EditMain");
		button_editmain.addActionListener(this);
		JButton button_deletemain = new JButton("Delete");
		button_deletemain.setActionCommand("DeleteMain");

		JButton button_addalt = new JButton("Add");
		button_addalt.setActionCommand("AddAlt");
		button_addalt.addActionListener(this);
		JButton button_editalt = new JButton("Edit");
		button_editalt.setActionCommand("EditAlt");
		button_editalt.addActionListener(this);
		JButton button_deletealt = new JButton("Delete");
		button_deletealt.setActionCommand("DeleteAlt");

		JButton button_save = new JButton("Save");
		button_save.setActionCommand("Save");
		button_save.addActionListener(this);
		JButton button_close = new JButton("Close");
		button_close.setActionCommand("Close");
		button_close.addActionListener(this);

		JPanel prec_button_panel = new JPanel();
		prec_button_panel.setLayout(new BoxLayout(prec_button_panel, BoxLayout.X_AXIS));
		prec_button_panel.add(Box.createHorizontalGlue());
		prec_button_panel.add(button_addprec);
		prec_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		prec_button_panel.add(button_editprec);
		prec_button_panel.add(Box.createHorizontalGlue());
		prec_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		prec_button_panel.add(button_deleteprec);
		prec_button_panel.add(Box.createHorizontalGlue());
		prec_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel post_button_panel = new JPanel();
		post_button_panel.setLayout(new BoxLayout(post_button_panel, BoxLayout.X_AXIS));
		post_button_panel.add(Box.createHorizontalGlue());
		post_button_panel.add(button_addpost);
		post_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		post_button_panel.add(button_editpost);
		post_button_panel.add(Box.createHorizontalGlue());
		post_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		post_button_panel.add(button_deletepost);
		post_button_panel.add(Box.createHorizontalGlue());
		post_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel main_button_panel = new JPanel();
		main_button_panel.setLayout(new BoxLayout(main_button_panel, BoxLayout.X_AXIS));
		main_button_panel.add(Box.createHorizontalGlue());
		main_button_panel.add(button_addmain);
		main_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		main_button_panel.add(button_editmain);
		main_button_panel.add(Box.createHorizontalGlue());
		main_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		main_button_panel.add(button_deletemain);
		main_button_panel.add(Box.createHorizontalGlue());
		main_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel alt_button_panel = new JPanel();
		alt_button_panel.setLayout(new BoxLayout(alt_button_panel, BoxLayout.X_AXIS));
		alt_button_panel.add(Box.createHorizontalGlue());
		alt_button_panel.add(button_addalt);
		alt_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		alt_button_panel.add(button_editalt);
		alt_button_panel.add(Box.createHorizontalGlue());
		alt_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		alt_button_panel.add(button_deletealt);
		alt_button_panel.add(Box.createHorizontalGlue());
		alt_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
		button_panel.add(Box.createHorizontalGlue());
		button_panel.add(button_save);
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(button_close);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_prac);
		parent.add(tf_prac);
		parent.add(lb_secac);
		parent.add(tf_secac);
		parent.add(lb_prec);
		parent.add(spPrec);
		parent.add(prec_button_panel);
		parent.add(lb_postc);
		parent.add(spPost);
		parent.add(post_button_panel);
		parent.add(lb_mainflow);
		parent.add(spMain);
		parent.add(main_button_panel);
		parent.add(lb_altflow);
		parent.add(spAlt);
		parent.add(alt_button_panel);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		ta_altflow.setFont(font);

		scenarioframe = new JFrame("Scenario");
		scenarioframe.setContentPane(parent);
		scenarioframe.pack();
	}

	public void showScenarioPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scenarioframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				scenarioframe.setVisible(true);
				scenarioframe.setSize(300, 600);
				scenarioframe.toFront();

				temp_name = CurrentGui.getInstance().getGui().getPropertyPane().getText();
				scenarioframe.setTitle("Scenario - " + temp_name);

				Main main = Main.getInstance();
				Scenario existing_scenario = main.getKnowledgeBase().getScenario(temp_name);
				if (existing_scenario != null) {
					tf_prac.setText(existing_scenario.getPrac());
					tf_secac.setText("");
				}
				else {
					tf_prac.setText("");
					tf_secac.setText("");
					tf_prec.setText("");
					tf_postc.setText("");
					ta_altflow.setText("");
				}
			}
		});
	}

	public void hideScenarioPanel() {
		scenarioframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();
		Map<String, Actor> actorM = kb.getActorMap();
		Map<String, Action> actionM = kb.getActionMap();

		String[] actorOptions = new String[actorM.size()];
		String[] actionOptions = new String[actionM.size()];

		ArrayList<String> stateAL = kb.getStateList();
		ArrayList<String> entityAL = kb.getObjectList();
		for (String actorStr : actorM.keySet()) {
			entityAL.add(actorStr);
		}

		String[] entityOptions = new String[entityAL.size()];
		String[] stateOptions = new String[stateAL.size()];
		String[] valueOptions = { "True", "False" };

		for (int i = 0; i < stateAL.size(); i++) {
			stateOptions[i] = stateAL.get(i);
		}
		for (int j = 0; j < entityAL.size(); j++) {
			entityOptions[j] = entityAL.get(j);
		}

		int i = 0;
		for (String actorStr : actorM.keySet()) {
			actorOptions[i] = actorStr;
			i++;
		}
		i = 0;
		for (String actionStr : actionM.keySet()) {
			actionOptions[i] = actionStr;
			i++;
		}

		if (ae.getActionCommand().equals("AddPrec")) {
			JComboBox precEntity = new JComboBox();
			JComboBox precState = new JComboBox();
			JComboBox precValue = new JComboBox();

			precValue.setModel(new DefaultComboBoxModel(valueOptions));
			precState.setModel(new DefaultComboBoxModel(stateOptions));
			precEntity.setModel(new DefaultComboBoxModel(entityOptions));

			precEntity.setEditable(true);
			precState.setEditable(true);

			Object[] addMainFields = {
					"Entity", precEntity,
					"State:", precState,
					"Value:", precValue
			};
			JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to preconditions", JOptionPane.CANCEL_OPTION);
			if (!(precEntity.getSelectedItem() == null) && !(precState.getSelectedItem() == null)) {
				String precActObjStr = precEntity.getSelectedItem().toString();
				String precStateStr = precState.getSelectedItem().toString();
				String precValueStr = precValue.getSelectedItem().toString();

				main.getKnowledgeBase().addState(precStateStr);
				DefaultTableModel precModel = (DefaultTableModel) precTable.getModel();
				precModel.addRow(new Object[] { precActObjStr, precStateStr, precValueStr });
			}
		}
		if (ae.getActionCommand().equals("AddPost")) {
			JComboBox postEntity = new JComboBox();
			JComboBox postState = new JComboBox();
			JComboBox postValue = new JComboBox();

			postValue.setModel(new DefaultComboBoxModel(valueOptions));
			postState.setModel(new DefaultComboBoxModel(stateOptions));
			postEntity.setModel(new DefaultComboBoxModel(entityOptions));

			postEntity.setEditable(true);
			postState.setEditable(true);

			Object[] addMainFields = {
					"Entity", postEntity,
					"State:", postState,
					"Value:", postValue
			};
			JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to postconditions", JOptionPane.CANCEL_OPTION);
			if (!(postEntity.getSelectedItem() == null) && !(postState.getSelectedItem() == null)) {
				String postActObjStr = postEntity.getSelectedItem().toString();
				String postStateStr = postState.getSelectedItem().toString();
				String postValueStr = postValue.getSelectedItem().toString();
				main.getKnowledgeBase().addState(postStateStr);
				DefaultTableModel postModel = (DefaultTableModel) postTable.getModel();
				postModel.addRow(new Object[] { postActObjStr, postStateStr, postValueStr });
			}
		}
		if (ae.getActionCommand().equals("AddMain")) {

			JComboBox mainActor = new JComboBox();
			JComboBox mainAction = new JComboBox();

			mainActor.setModel(new DefaultComboBoxModel(actorOptions));
			mainAction.setModel(new DefaultComboBoxModel(actionOptions));

			Object[] addMainFields = {
					"Actor:", mainActor,
					"Action:", mainAction
			};
			JOptionPane.showConfirmDialog(null, addMainFields, "Add step to main flow", JOptionPane.CANCEL_OPTION);
			if (!(mainActor.getSelectedItem() == null) && !(mainAction.getSelectedItem() == null)) {
				DefaultTableModel mainModel = (DefaultTableModel) mainFlowTable.getModel();
				mainModel.addRow(new Object[] { mainActor.getSelectedItem(), mainAction.getSelectedItem() });
			}
		}

		if (ae.getActionCommand().equals("AddAlt")) {

			JComboBox altActor = new JComboBox();
			JComboBox altAction = new JComboBox();

			altActor.setModel(new DefaultComboBoxModel(actorOptions));
			altAction.setModel(new DefaultComboBoxModel(actionOptions));

			Object[] addAltFields = {
					"Actor:", altActor,
					"Action:", altAction
			};
			JOptionPane.showConfirmDialog(null, addAltFields, "Add step to alternative flow", JOptionPane.CANCEL_OPTION);
			if (!(altActor.getSelectedItem() == null) && !(altAction.getSelectedItem() == null)) {
				DefaultTableModel altModel = (DefaultTableModel) altFlowTable.getModel();
				altModel.addRow(new Object[] { altActor.getSelectedItem(), altAction.getSelectedItem() });
			}
		}

		if (ae.getActionCommand().equals("Save")) {
			String[] temp_secacs = new String[1];
			temp_secacs[0] = tf_secac.getText();
			for (int j = 0; j < mainFlowModel.getRowCount(); j++) {
				FlowStep temp_step = new FlowStep(mainFlowModel.getValueAt(j, 0).toString(), mainFlowModel.getValueAt(j, 1).toString());
				mainflow_steps.add(temp_step);
			}
			main.getKnowledgeBase().addScenario(new Scenario(temp_name, tf_prac.getText(), temp_secacs, preconditions, postconditions, mainflow_steps));
			hideScenarioPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideScenarioPanel();
		}
	}
}
