package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.baselet.assistant.Action;
import com.baselet.assistant.Actor;
import com.baselet.assistant.KnowledgeBase;
import com.baselet.assistant.StateTriple;
import com.baselet.control.Main;

public class ActionPanel extends JPanel implements ActionListener {

	private static ActionPanel actionpanel;

	public static ActionPanel getInstance() {
		if (actionpanel == null) {
			actionpanel = new ActionPanel();
		}
		return actionpanel;
	}

	private final JFrame actionframe;

	private final JScrollPane spPrec;
	private final JScrollPane spPost;

	private final JTable precTable;
	private final DefaultTableModel precModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JTable postTable;
	private final DefaultTableModel postModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final JLabel lb_name = new JLabel("Name:");
	private final JTextField tf_name = new JTextField();

	private final JLabel lb_object = new JLabel("Object (optional):");
	private final JTextField tf_object = new JTextField();

	private final JLabel lb_prec = new JLabel("Preconditions:");
	private final JTextField tf_prec = new JTextField();

	private final JLabel lb_postc = new JLabel("Postconditions:");
	private final JTextField tf_postc = new JTextField();

	public ActionPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

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
		JButton button_deleteprec = new JButton("Delete");
		button_deleteprec.setActionCommand("DeletePrec");
		button_deleteprec.addActionListener(this);

		JButton button_addpost = new JButton("Add");
		button_addpost.setActionCommand("AddPost");
		button_addpost.addActionListener(this);
		JButton button_deletepost = new JButton("Delete");
		button_deletepost.setActionCommand("DeletePost");
		button_deletepost.addActionListener(this);

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
		post_button_panel.add(Box.createHorizontalGlue());
		post_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		post_button_panel.add(button_deletepost);
		post_button_panel.add(Box.createHorizontalGlue());
		post_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

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
		parent.add(lb_name);
		parent.add(tf_name);
		// parent.add(lb_object);
		// parent.add(tf_object);
		parent.add(lb_prec);
		parent.add(spPrec);
		parent.add(prec_button_panel);
		parent.add(lb_postc);
		parent.add(spPost);
		parent.add(post_button_panel);
		parent.add(button_panel);

		actionframe = new JFrame("Action");
		actionframe.setContentPane(parent);
		actionframe.pack();
	}

	public void showActionPanel(final String action_name) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				actionframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				actionframe.setVisible(true);
				actionframe.setSize(300, 400);
				actionframe.toFront();

				precModel.setRowCount(0);
				postModel.setRowCount(0);

				actionframe.setTitle("Action - " + action_name);
				tf_name.setText(action_name);

				Main main = Main.getInstance();
				KnowledgeBase kb = main.getKnowledgeBase();
				Action existing_action = kb.getAction(action_name);

				if (existing_action != null) {

					ArrayList<StateTriple> precs = existing_action.getPrecond();
					ArrayList<StateTriple> posts = existing_action.getPostcond();
					if (precs.size() > 0) {
						for (StateTriple pr : precs) {
							precModel.addRow(new Object[] { pr.getEntity(), pr.getState(), pr.getValue() });
						}
					}
					if (posts.size() > 0) {
						for (StateTriple po : posts) {
							postModel.addRow(new Object[] { po.getEntity(), po.getState(), po.getValue() });
						}
					}
				}
			}
		});
	}

	public void hideActionPanel() {
		actionframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();
		ArrayList<String> stateAL = kb.getStateList();
		ArrayList<String> objectAL = kb.getObjectList();
		Map<String, Actor> actorM = kb.getActorMap();
		String[] entityOptions = new String[objectAL.size() + actorM.size()];
		String[] stateOptions = new String[stateAL.size()];
		String[] valueOptions = { "True", "False" };

		int g = 0;
		for (String ostr : objectAL) {
			entityOptions[g] = ostr;
			g++;
		}
		for (String actorStr : actorM.keySet()) {
			entityOptions[g] = actorStr;
			g++;
		}
		g = 0;

		for (int i = 0; i < stateAL.size(); i++) {
			stateOptions[i] = stateAL.get(i);
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
			int ocprec = JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to preconditions", JOptionPane.CANCEL_OPTION);
			{
				if (ocprec != JOptionPane.CANCEL_OPTION) {
					if (!(precEntity.getSelectedItem() == null) && !(precState.getSelectedItem() == null)) {
						String precActObjStr = precEntity.getSelectedItem().toString();
						String precStateStr = precState.getSelectedItem().toString();
						String precValueStr = precValue.getSelectedItem().toString();

						main.getKnowledgeBase().addState(precStateStr);
						DefaultTableModel precModel = (DefaultTableModel) precTable.getModel();
						precModel.addRow(new Object[] { precActObjStr, precStateStr, precValueStr });
					}
				}
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
			int ocpost = JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to postconditions", JOptionPane.CANCEL_OPTION);
			{
				if (ocpost != JOptionPane.CANCEL_OPTION) {
					if (!(postEntity.getSelectedItem() == null) && !(postState.getSelectedItem() == null)) {
						String postActObjStr = postEntity.getSelectedItem().toString();
						String postStateStr = postState.getSelectedItem().toString();
						String postValueStr = postValue.getSelectedItem().toString();
						main.getKnowledgeBase().addState(postStateStr);
						DefaultTableModel postModel = (DefaultTableModel) postTable.getModel();
						postModel.addRow(new Object[] { postActObjStr, postStateStr, postValueStr });
					}
				}
			}
		}
		if (ae.getActionCommand().equals("DeletePrec")) {
			int sr = precTable.getSelectedRow();
			precModel.removeRow(sr);
		}
		if (ae.getActionCommand().equals("DeletePost")) {
			int sr = postTable.getSelectedRow();
			postModel.removeRow(sr);
		}
		if (ae.getActionCommand().equals("Save")) {
			ArrayList<StateTriple> preconditions = new ArrayList<StateTriple>();
			ArrayList<StateTriple> postconditions = new ArrayList<StateTriple>();

			for (int k = 0; k < precModel.getRowCount(); k++) {
				StateTriple new_prec = new StateTriple(precModel.getValueAt(k, 0).toString(), precModel.getValueAt(k, 1).toString(), precModel.getValueAt(k, 2).toString());
				preconditions.add(new_prec);
			}
			for (int l = 0; l < postModel.getRowCount(); l++) {
				StateTriple new_post = new StateTriple(postModel.getValueAt(l, 0).toString(), postModel.getValueAt(l, 1).toString(), postModel.getValueAt(l, 2).toString());
				postconditions.add(new_post);
			}
			Action new_action = new Action(tf_name.getText(), preconditions, postconditions);
			kb.addAction(new_action);
			hideActionPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideActionPanel();
		}
	}

}
