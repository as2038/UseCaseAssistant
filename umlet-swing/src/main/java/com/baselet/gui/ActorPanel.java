package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.baselet.assistant.Action;
import com.baselet.assistant.Actor;
import com.baselet.control.Main;

public class ActorPanel extends JPanel implements ActionListener {

	private static ActorPanel actorpanel;

	public static ActorPanel getInstance() {
		if (actorpanel == null) {
			actorpanel = new ActorPanel();
		}
		return actorpanel;
	}

	private final JFrame actorframe;

	private final JScrollPane sp;
	private final JTable actionTable;
	private final DefaultTableModel actionModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JLabel lb_alias = new JLabel("Alias (optional):");
	private final JTextField tf_alias = new JTextField();

	private final JLabel lb_goals = new JLabel("Goals:");
	private final JTextField tf_goals = new JTextField();

	private final JLabel lb_actions = new JLabel("Actions:");

	private String temp_name;
	private final ArrayList<Action> temp_action_list = new ArrayList<Action>();

	private ActorPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		actionModel.addColumn("Name");
		actionModel.addColumn("Object");

		actionModel.setRowCount(0);

		actionTable = new JTable(actionModel);

		sp = new JScrollPane();
		sp.setViewportView(actionTable);

		JButton button_add = new JButton("Add");
		button_add.setActionCommand("Add");
		button_add.addActionListener(this);
		JButton button_edit = new JButton("Edit");
		button_edit.setActionCommand("Edit");
		button_edit.addActionListener(this);
		JButton button_delete = new JButton("Delete");
		button_delete.setActionCommand("Delete");
		button_delete.addActionListener(this);
		JButton button_save = new JButton("Save");
		button_save.setActionCommand("Save");
		button_save.addActionListener(this);
		JButton button_close = new JButton("Close");
		button_close.setActionCommand("Close");
		button_close.addActionListener(this);

		JPanel button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
		button_panel.add(Box.createHorizontalGlue());
		button_panel.add(button_save);
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(button_close);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel act_button_panel = new JPanel();
		act_button_panel.setLayout(new BoxLayout(act_button_panel, BoxLayout.X_AXIS));
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.add(button_add);
		act_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.add(button_edit);
		act_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		act_button_panel.add(button_delete);
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_alias);
		parent.add(tf_alias);
		parent.add(lb_goals);
		parent.add(tf_goals);
		parent.add(lb_actions);
		parent.add(sp);
		parent.add(act_button_panel);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		actorframe = new JFrame("Scenario");
		actorframe.setContentPane(parent);
		actorframe.pack();
	}

	public void showActorPanel(final String actor_name) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				actorframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				actorframe.setVisible(true);
				actorframe.setSize(300, 300);
				actorframe.toFront();

				temp_name = CurrentGui.getInstance().getGui().getPropertyPane().getText();
				actorframe.setTitle("Actor - " + actor_name);
				temp_name = actor_name;

				Main main = Main.getInstance();
				Actor existing_actor = main.getKnowledgeBase().getActor(actor_name);

				actionModel.setRowCount(0);
				if (existing_actor != null) {
					ArrayList<Action> existing_action_list = existing_actor.getActionList();
					if (existing_action_list.size() > 0) {
						for (Action a : existing_action_list) {
							actionModel.addRow(new Object[] { a.getName(), "", a.getPrecond(), a.getPostcond() });
						}

					}
					else {
						actionModel.setRowCount(0);
					}
				}
				else {
					tf_alias.setText("");
					tf_goals.setText("");
				}
			}
		});
	}

	public void hideActorPanel() {
		actorframe.setVisible(false);
	}

	public void addActionToTable() {

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Main main = Main.getInstance();
		main.getKnowledgeBase().setLastActorName(temp_name);

		if (ae.getActionCommand().equals("Add")) {
			ActionPanel.getInstance().showActionPanel();
		}
		if (ae.getActionCommand().equals("Edit")) {
			ActionPanel.getInstance().showActionPanel();
		}
		if (ae.getActionCommand().equals("Delete")) {
			int sr = actionTable.getSelectedRow();
			actionModel.removeRow(sr);
		}
		if (ae.getActionCommand().equals("Save")) {
			Actor new_actor = new Actor(temp_name, temp_action_list);
			main.getKnowledgeBase().addActor(new_actor);
			hideActorPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideActorPanel();
		}
	}

}