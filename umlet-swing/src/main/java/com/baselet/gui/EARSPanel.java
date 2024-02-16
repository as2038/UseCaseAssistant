package com.baselet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EARSPanel extends JPanel implements ActionListener {

	// Ubiquitous
	private final JCheckBox cb_ubic = new JCheckBox();
	private final JLabel ubic_l1 = new JLabel("The");
	private final JTextField ubic_t1 = new JTextField();
	private final JLabel ubic_l2 = new JLabel("shall");
	private final JTextField ubic_t2 = new JTextField();

	// Event-driven
	private final JCheckBox cb_event = new JCheckBox();
	private final JLabel event_l1 = new JLabel("WHEN");
	private final JTextField event_t1 = new JTextField();
	private final JTextField event_t2 = new JTextField();
	private final JLabel event_l2 = new JLabel("the");
	private final JTextField event_t3 = new JTextField();
	private final JLabel event_l3 = new JLabel("shall");
	private final JTextField event_t4 = new JTextField();

	// Unwanted behaviour
	private final JCheckBox cb_unwant = new JCheckBox();
	private final JLabel unwant_l1 = new JLabel("IF");
	private final JTextField unwant_t1 = new JTextField();
	private final JTextField unwant_t2 = new JTextField();
	private final JLabel unwant_l2 = new JLabel("THEN the");
	private final JTextField unwant_t3 = new JTextField();
	private final JLabel unwant_l3 = new JLabel("shall");
	private final JTextField unwant_t4 = new JTextField();

	// State-driven
	private final JCheckBox cb_state = new JCheckBox();
	private final JLabel state_l1 = new JLabel("WHILE");
	private final JTextField state_t1 = new JTextField();
	private final JLabel state_l2 = new JLabel("the");
	private final JTextField state_t2 = new JTextField();
	private final JLabel state_l3 = new JLabel("shall");
	private final JTextField state_t3 = new JTextField();

	// Optional features
	private final JCheckBox cb_opt = new JCheckBox();
	private final JLabel opt_l1 = new JLabel("WHILE");
	private final JTextField opt_t1 = new JTextField();
	private final JLabel opt_l2 = new JLabel("the");
	private final JTextField opt_t2 = new JTextField();
	private final JLabel opt_l3 = new JLabel("shall");
	private final JTextField opt_t3 = new JTextField();

	private final JButton bt_add = new JButton("Add");
	private final JButton bt_reset = new JButton("Reset");
	private final JButton bt_close = new JButton("Close");

	public EARSPanel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
