package com.baselet.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.baselet.control.Main;
import com.baselet.control.config.Config;

public class RequirementsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2L;

	private final JTable requirementsTable;
	private final JScrollPane sp;

	DefaultTableModel reqModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final int paddingTop = 1;
	private final int paddingBottom = 1;
	private final int outerPaddingLeft = 15;
	private final int outerPaddingRight = 15;
	private final int halfHorizontalDividerSpace = 2;
	private final int verticalDividerSpace = 10;

	private final GridBagLayout layout = new GridBagLayout();

	private final JButton bt_newreq = new JButton("New Requirement");
	private final JButton bt_edit = new JButton("Edit");
	private final JButton bt_delete = new JButton("Delete");
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

		reqModel.addColumn("Name");
		reqModel.addColumn("Type");
		reqModel.addColumn("Satisfied?");

		requirementsTable = new JTable(reqModel);

		sp = new JScrollPane();
		sp.setViewportView(requirementsTable);
		setSize(new Dimension(0, 250));

		this.add(sp, BorderLayout.NORTH);
		this.add(bt_newreq, BorderLayout.NORTH);
		bt_newreq.setActionCommand("Add");
		bt_newreq.addActionListener(this);
		this.add(bt_edit, BorderLayout.NORTH);
		this.add(bt_delete, BorderLayout.NORTH);
		this.add(bt_close, BorderLayout.NORTH);
		bt_close.setActionCommand("Close");
		bt_close.addActionListener(this);

	}

	private void initAndFillComponents() {
		// bt_newreq.addActionListener(new AddActionListener());
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

	public void closePanel() {
		Config.getInstance().setMail_split_position((int) this.getSize().getHeight());
		CurrentGui.getInstance().getGui().setRequirementsPanelEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Add")) {
			Main main = Main.getInstance();
			main.getKnowledgeBase().getDuckHandler().showEARS();
		}
		if (ae.getActionCommand().equals("Close")) {
			closePanel();
		}

	}

}
