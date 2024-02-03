package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;

import com.baselet.control.constants.Constants;

public class ScenarioPanel extends JPanel implements ActionListener {

	private static ScenarioPanel scenariopanel;

	public static ScenarioPanel getInstance() {
		if (scenariopanel == null) {
			scenariopanel = new ScenarioPanel();
		}
		return scenariopanel;
	}

	private final JFrame scenarioframe;
	private final Vector<String> uis_technicalNames = new Vector<String>();

	private ScenarioPanel() {
		setLayout(new GridLayout(0, 2, 4, 4));
		setAlignmentX(Component.LEFT_ALIGNMENT);

		Vector<String> uis_humanReadableNameVector = new Vector<String>();
		LookAndFeelInfo[] lookAndFeelInfoArray = Constants.lookAndFeels.toArray(new LookAndFeelInfo[Constants.lookAndFeels.size()]);
		for (LookAndFeelInfo info : lookAndFeelInfoArray) {
			uis_technicalNames.add(info.getClassName());
			uis_humanReadableNameVector.add(info.getName());
		}

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
		parent.add(button_panel);

		scenarioframe = new JFrame("Scenario");
		scenarioframe.setContentPane(parent);
		scenarioframe.pack();
	}

	public void showScenarioPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scenarioframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				scenarioframe.setVisible(true);
				scenarioframe.toFront();
			}
		});

	}

	public void hideScenarioPanel() {
		scenarioframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
