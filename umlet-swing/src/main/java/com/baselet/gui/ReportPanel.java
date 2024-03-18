package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.baselet.control.Main;
import com.baselet.control.config.Config;

public class ReportPanel extends JPanel implements ActionListener {

	private final int paddingTop = 1;
	private final int paddingBottom = 1;
	private final int outerPaddingLeft = 15;
	private final int outerPaddingRight = 15;
	private final int halfHorizontalDividerSpace = 2;
	private final int verticalDividerSpace = 10;

	private final GridBagLayout layout = new GridBagLayout();

	// UI
	private final JLabel lb_warnings = new JLabel("Warnings:");
	private final JTextArea ta_warnings = new JTextArea(5, 5);
	JScrollPane sp_warnings = new JScrollPane(ta_warnings);

	private final JButton bt_check = new JButton("Check Consistency");
	private final JButton bt_logs = new JButton("View Scenario Logs");
	private final JButton bt_close = new JButton("Close");

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

	public ReportPanel() {
		initAndFillComponents();

		setLayout(layout);
		setSize(new Dimension(0, 250));

		int line = 0;

		line++;
		addComponent(this, layout, lb_warnings, 0, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, sp_warnings, 0, line, 5, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, bt_check, 0, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, bt_logs, 0, line, 1, 1, fillWidth, leftWeight, 0, paddingText);
		line++;
		addComponent(this, layout, bt_close, 0, line, 1, 1, fillWidth, leftWeight, 0, paddingText);

		bt_check.setActionCommand("Check");
		bt_check.addActionListener(this);
		bt_logs.setActionCommand("Logs");
		bt_logs.addActionListener(this);
		bt_close.setActionCommand("Close");
		bt_close.addActionListener(this);
	}

	private void initAndFillComponents() {
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

		ta_warnings.setFont(font);

	}

	public void closePanel() {
		Config.getInstance().setMail_split_position((int) this.getSize().getHeight());
		CurrentGui.getInstance().getGui().setReportPanelEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Check")) {
			ta_warnings.setText("");
			Main main = Main.getInstance();
			ArrayList<String> warnings = main.getKnowledgeBase().getDuckHandler().checkConsistency();
			for (String s : warnings) {
				ta_warnings.append(s);
			}
		}
		if (ae.getActionCommand().equals("Logs")) {
			ScenarioLogPanel.getInstance().showScenarioLogPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			closePanel();
		}

	}

}
