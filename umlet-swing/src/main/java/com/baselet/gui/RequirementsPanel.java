package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.baselet.control.Main;

public class RequirementsPanel extends JPanel {

	private static final long serialVersionUID = 2L;

	private final int paddingTop = 1;
	private final int paddingBottom = 1;
	private final int outerPaddingLeft = 15;
	private final int outerPaddingRight = 15;
	private final int halfHorizontalDividerSpace = 2;
	private final int verticalDividerSpace = 10;

	private final GridBagLayout layout = new GridBagLayout();

	private final JButton bt_add = new JButton("Add");
	private final JButton bt_reset = new JButton("Reset");
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

		addComponent(this, layout, Box.createRigidArea(new Dimension(0, verticalDividerSpace)), 0, line, 1, 1, fillWidth, fullWeight, 0, noPadding);
		line++;
		addComponent(this, layout, bt_add, 4, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, bt_reset, 5, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		addComponent(this, layout, bt_close, 6, line, 1, 1, fillWidth, leftWeight, 0, paddingText);

	}

	private void initAndFillComponents() {
		bt_add.addActionListener(new AddActionListener());
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

	private class AddActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main main = Main.getInstance();
			main.getKnowledgeBase().getDuckHandler().showEARS();
		}
	}

}
