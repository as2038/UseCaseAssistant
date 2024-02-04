package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_name);
		parent.add(tf_name);
		parent.add(lb_object);
		parent.add(tf_object);
		parent.add(lb_prec);
		parent.add(tf_prec);
		parent.add(lb_postc);
		parent.add(tf_postc);
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
				actionframe.toFront();
			}
		});

	}

	public void hideActorPanel() {
		actionframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Main main = Main.getInstance();
		KnowledgeBase kb = main.getKnowledgeBase();
		if (ae.getActionCommand().equals("Save")) {
			Action new_action = new Action(tf_name.getText());
			kb.getActor(kb.getLastTempName()).addAction(new_action);
			kb.addAction(new_action);
			hideActorPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideActorPanel();
		}

	}

}
