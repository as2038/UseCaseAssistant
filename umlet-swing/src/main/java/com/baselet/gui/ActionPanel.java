package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private final JLabel lb_object = new JLabel("Object:");
	private final JTextField tf_object = new JTextField();

	private final JLabel lb_prec = new JLabel("Preconditions:");
	private final JTextField tf_prec = new JTextField();

	private final JLabel lb_postc = new JLabel("Postconditions:");
	private final JTextField tf_postc = new JTextField();

	public ActionPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		precModel.addColumn("Actor/Object");
		precModel.addColumn("State");
		precModel.addColumn("Value");

		precModel.setRowCount(0);

		precTable = new JTable(precModel);

		spPrec = new JScrollPane();
		spPrec.setViewportView(precTable);

		postModel.addColumn("Actor/Object");
		postModel.addColumn("State");
		postModel.addColumn("Value");

		postModel.setRowCount(0);

		postTable = new JTable(precModel);

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
		parent.add(lb_object);
		parent.add(tf_object);
		parent.add(lb_prec);
		parent.add(spPrec);
		parent.add(prec_button_panel);
		parent.add(lb_postc);
		parent.add(spPost);
		parent.add(post_button_panel);
		parent.add(button_panel);

		Main main = Main.getInstance();

		actionframe = new JFrame(main.getKnowledgeBase().getLastTempName() + " - New Action");
		actionframe.setContentPane(parent);
		actionframe.pack();
	}

	public void showActionPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				actionframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				actionframe.setVisible(true);
				actionframe.setSize(300, 300);
				actionframe.toFront();
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
		String[] valueOptions = { "True", "False" };

		if (ae.getActionCommand().equals("AddPrec")) {
			JTextField precState = new JTextField();
			JComboBox precValue = new JComboBox();
			precValue.setModel(new DefaultComboBoxModel(valueOptions));

			Object[] addMainFields = {
					"State:", precState,
					"Value:", precValue
			};
			JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to preconditions", JOptionPane.CANCEL_OPTION);
			main.getKnowledgeBase().addState(precState.getText());
		}
		if (ae.getActionCommand().equals("AddPost")) {
			JTextField postState = new JTextField();
			JComboBox postValue = new JComboBox();
			postValue.setModel(new DefaultComboBoxModel(valueOptions));

			Object[] addMainFields = {
					"State:", postState,
					"Value:", postValue
			};
			JOptionPane.showConfirmDialog(null, addMainFields, "Add a state to postconditions", JOptionPane.CANCEL_OPTION);
			main.getKnowledgeBase().addState(postState.getText());
		}
		if (ae.getActionCommand().equals("Save")) {
			Action new_action = new Action(tf_name.getText(), tf_prec.getText(), tf_postc.getText());
			kb.getActor(kb.getLastTempName()).addAction(new_action);
			kb.addAction(new_action);
			hideActionPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideActionPanel();
		}

	}

}
