package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScenarioLogPanel extends JPanel implements ActionListener {

	private static ScenarioLogPanel scenariologpanel;

	public static ScenarioLogPanel getInstance() {
		if (scenariologpanel == null) {
			scenariologpanel = new ScenarioLogPanel();
		}
		return scenariologpanel;
	}

	private final JFrame scenariologframe;

	private final JScrollPane spCurrSt;

	private final JTable currStTable;
	private final DefaultTableModel currStModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// UI

	public ScenarioLogPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		currStModel.addColumn("Entity");
		currStModel.addColumn("State");
		currStModel.addColumn("Value");

		currStModel.setRowCount(0);

		currStTable = new JTable(currStModel);

		spCurrSt = new JScrollPane();
		spCurrSt.setViewportView(currStTable);

		JButton button_close = new JButton("Close");
		button_close.setActionCommand("Close");
		button_close.addActionListener(this);

		JPanel button_panel = new JPanel();
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
		button_panel.add(Box.createHorizontalGlue());
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(button_close);
		button_panel.add(Box.createHorizontalGlue());
		button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel act_button_panel = new JPanel();
		act_button_panel.setLayout(new BoxLayout(act_button_panel, BoxLayout.X_AXIS));
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		act_button_panel.add(Box.createHorizontalGlue());
		act_button_panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(spCurrSt);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		scenariologframe = new JFrame("Scenario");
		scenariologframe.setContentPane(parent);
		scenariologframe.pack();
	}

	public void showScenarioLogPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scenariologframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				scenariologframe.setVisible(true);
				scenariologframe.setSize(600, 400);
				scenariologframe.toFront();

				scenariologframe.setTitle("Scenario Logs");

			}
		});
	}

	public void hideScenarioLogPanel() {
		scenariologframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Close")) {
			hideScenarioLogPanel();
		}
	}

}
