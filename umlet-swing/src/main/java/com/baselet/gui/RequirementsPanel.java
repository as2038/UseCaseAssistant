package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RequirementsPanel extends JPanel {

	private static final long serialVersionUID = 2L;

	private final int paddingTop = 1;
	private final int paddingBottom = 1;
	private final int outerPaddingLeft = 15;
	private final int outerPaddingRight = 15;
	private final int halfHorizontalDividerSpace = 2;
	private final int verticalDividerSpace = 10;

	private final GridBagLayout layout = new GridBagLayout();

	// private final JLabel lb_req = new JLabel("Requirement in plain text:");
	// private final JTextField tf_req = new JTextField();

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
	private final JButton bt_reset = new JButton("reset");
	private final JButton bt_close = new JButton("Close");

	// private final JTextArea ta_text = new JTextArea(5, 5);
	// JScrollPane sp_text = new JScrollPane(ta_text);

	private final Insets paddingLeftLabel = new Insets(paddingTop, outerPaddingLeft, paddingBottom, halfHorizontalDividerSpace);
	private final Insets paddingMessagebox = new Insets(paddingTop, outerPaddingLeft, paddingBottom, outerPaddingRight);
	private final Insets paddingText = new Insets(paddingTop, halfHorizontalDividerSpace, paddingBottom, outerPaddingRight);
	private final Insets paddingCheckbox = new Insets(paddingTop - 2, halfHorizontalDividerSpace, paddingBottom - 2, outerPaddingRight);
	private final Insets paddingRightLabel = new Insets(paddingTop, halfHorizontalDividerSpace, paddingBottom, halfHorizontalDividerSpace);
	private final Insets noPadding = new Insets(0, 0, 0, 0);

	private final double noWeight = 0;
	private final double fullWeight = 1;
	private final double leftWeight = 0.75;
	private final double rightWeight = 0.25;

	private final int fillWidth = GridBagConstraints.HORIZONTAL;
	private final int fillBoth = GridBagConstraints.BOTH;

	public RequirementsPanel() {
		initAndFillComponents();

		setLayout(layout);
		setSize(new Dimension(0, 250));

		int line = 0;

		// addComponent(this, layout, sp_text, 0, line, 1, 1, fillBoth, leftWeight, 1, paddingMessagebox);
		// line++;
		// addComponent(this, layout, lb_req, 0, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		// addComponent(this, layout, tf_req, 1, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		// line++;
		addComponent(this, layout, cb_ubic, 0, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		addComponent(this, layout, ubic_l1, 1, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, ubic_t1, 2, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, ubic_l2, 3, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, ubic_t2, 4, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, cb_event, 0, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		addComponent(this, layout, event_l1, 1, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, event_t1, 2, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, event_t2, 3, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, event_l2, 4, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, event_t3, 5, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, event_l3, 6, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, event_t4, 7, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, cb_unwant, 0, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		addComponent(this, layout, unwant_l1, 1, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, unwant_t1, 2, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, unwant_t2, 3, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, unwant_l2, 4, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, unwant_t3, 5, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, unwant_l3, 6, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, unwant_t4, 7, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, cb_state, 0, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		addComponent(this, layout, state_l1, 1, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, state_t1, 2, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, state_l2, 3, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, state_t2, 4, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, state_l3, 5, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, state_t3, 6, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, cb_opt, 0, line, 1, 1, fillWidth, noWeight, 0, paddingCheckbox);
		addComponent(this, layout, opt_l1, 1, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, opt_t1, 2, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, opt_l2, 3, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, opt_t2, 4, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, opt_l3, 5, line, 1, 1, fillWidth, noWeight, 0, paddingLeftLabel);
		addComponent(this, layout, opt_t3, 6, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, Box.createRigidArea(new Dimension(0, verticalDividerSpace)), 0, line, 1, 1, fillWidth, fullWeight, 0, noPadding);
		line++;
		addComponent(this, layout, bt_add, 4, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, bt_reset, 5, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, bt_close, 6, line, 1, 1, fillWidth, leftWeight, 0, paddingText);

	}

	private void initAndFillComponents() {
		// ta_text.setText("Requirement in plain text");
		ubic_t1.setText("<system name>");
		ubic_t2.setText("<system response>");
		ubic_t1.setEnabled(false);
		ubic_t2.setEnabled(false);

		unwant_t1.setText("<optional preconditions>");
		unwant_t2.setText("<trigger>");
		unwant_t3.setText("<system name>");
		unwant_t4.setText("<system response>");
		unwant_t1.setEnabled(false);
		unwant_t2.setEnabled(false);
		unwant_t3.setEnabled(false);
		unwant_t4.setEnabled(false);

		state_t1.setText("<in a specific state>");
		state_t2.setText("<system name>");
		state_t3.setText("<system response>");
		state_t1.setEnabled(false);
		state_t2.setEnabled(false);
		state_t3.setEnabled(false);

		opt_t1.setText("<feature is included>");
		opt_t2.setText("<system name>");
		opt_t3.setText("<system response>");
		opt_t1.setEnabled(false);
		opt_t2.setEnabled(false);
		opt_t3.setEnabled(false);
		setAllFonts();

	}

	private void addComponent(JPanel panel, GridBagLayout gbl, Component c, int x, int y, int width, int height, int fill, double weightx, double weighty, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		gbl.setConstraints(c, gbc);
		panel.add(c);
	}

	private void setAllFonts() {

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		Font fontBold = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		Font fontSmallItalic = new Font(Font.SANS_SERIF, Font.ITALIC, 10);

		// lb_req.setFont(fontBold);
		// tf_req.setFont(font);
		// ta_text.setFont(font);
		// sp_text.setFont(font);

	}

}
