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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.baselet.assistant.Action;
import com.baselet.assistant.KnowledgeBase;
import com.baselet.assistant.SystemBoundary;
import com.baselet.control.Main;

public class SystemBoundaryPanel extends JPanel implements ActionListener {

	private static SystemBoundaryPanel systempanel;

	public static SystemBoundaryPanel getInstance() {
		if (systempanel == null) {
			systempanel = new SystemBoundaryPanel();
		}
		return systempanel;
	}

	private final JFrame systemframe;

	private final JScrollPane sp;
	private final JTable actionTable;
	private final DefaultTableModel actionModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private final JLabel lb_goals = new JLabel("Goals:");
	private final JTextField tf_goals = new JTextField();

	private final JLabel lb_actions = new JLabel("Actions:");

	private String temp_name;

	private SystemBoundaryPanel() {
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
		act_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		act_button_panel.add(button_delete);
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_goals);
		parent.add(tf_goals);
		parent.add(lb_actions);
		parent.add(sp);
		parent.add(act_button_panel);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		systemframe = new JFrame("Scenario");
		systemframe.setContentPane(parent);
		systemframe.pack();
	}

	public void showSystemBoundaryrPanel(final String system_name) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				systemframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				systemframe.setVisible(true);
				systemframe.setSize(300, 300);
				systemframe.toFront();

				temp_name = CurrentGui.getInstance().getGui().getPropertyPane().getText();
				systemframe.setTitle("System - " + system_name);
				temp_name = system_name;
				actionModel.setRowCount(0);

				Main main = Main.getInstance();
				KnowledgeBase kb = main.getKnowledgeBase();
				SystemBoundary system = kb.getSystem();

				if (system != null) {
					for (String sa : system.getActionList()) {
						actionModel.addRow(new Object[] { sa, kb.getAction(sa).getObject() });
					}
				}
				else {
					tf_goals.setText("");
				}
			}
		});
	}

	public void hideSystemBoundaryPanel() {
		systemframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();

		if (ae.getActionCommand().equals("Add")) {
			Map<String, Action> actionM = kb.getActionMap();
			String[] actionOptions = new String[actionM.size()];

			int i = 0;
			for (String actionStr : actionM.keySet()) {
				actionOptions[i] = actionStr;
				i++;
			}

			JComboBox action = new JComboBox();

			action.setModel(new DefaultComboBoxModel(actionOptions));

			Object[] addMainFields = {
					"Action:", action
			};
			int ocmain = JOptionPane.showConfirmDialog(null, addMainFields, "Add action to system", JOptionPane.CANCEL_OPTION);
			{
				if (ocmain != JOptionPane.CANCEL_OPTION) {
					if (!(action.getSelectedItem() == null)) {
						Action selectedAction = kb.getAction(action.getSelectedItem().toString());
						actionModel.addRow(new Object[] { selectedAction.getName(), selectedAction.getObject() });
					}
				}
			}
		}
		if (ae.getActionCommand().equals("Delete")) {
			int sr = actionTable.getSelectedRow();
			actionModel.removeRow(sr);
		}
		if (ae.getActionCommand().equals("Save")) {
			ArrayList<String> temp_action_list = new ArrayList<String>();
			for (int i = 0; i < actionModel.getRowCount(); i++) {
				temp_action_list.add(actionModel.getValueAt(i, 0).toString());
			}
			SystemBoundary new_system = new SystemBoundary(temp_name, temp_action_list);
			main.getKnowledgeBase().addSystem(new_system);
			hideSystemBoundaryPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideSystemBoundaryPanel();
		}
	}

}
