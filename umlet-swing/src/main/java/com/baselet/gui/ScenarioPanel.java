package com.baselet.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.baselet.assistant.Scenario;
import com.baselet.control.Main;

public class ScenarioPanel extends JPanel implements ActionListener {

	private static ScenarioPanel scenariopanel;

	public static ScenarioPanel getInstance() {
		if (scenariopanel == null) {
			scenariopanel = new ScenarioPanel();
		}
		return scenariopanel;
	}

	private final JFrame scenarioframe;

	private final JLabel lb_prac = new JLabel("Primary actor:");
	private final JTextField tf_prac = new JTextField();

	private final JLabel lb_secac = new JLabel("Secondary actor(s):");
	private final JTextField tf_secac = new JTextField();

	private final JLabel lb_prec = new JLabel("Preconditions:");
	private final JTextField tf_prec = new JTextField();

	private final JLabel lb_postc = new JLabel("Postconditions:");
	private final JTextField tf_postc = new JTextField();

	private final JLabel lb_mainflow = new JLabel("Main flow:");
	private final JTextArea ta_mainflow = new JTextArea(5, 5);
	JScrollPane sp_mainflow = new JScrollPane(ta_mainflow);

	private final JLabel lb_altflow = new JLabel("Alternative flows:");
	private final JTextArea ta_altflow = new JTextArea(5, 5);
	JScrollPane sp_altflow = new JScrollPane(ta_altflow);

	private String temp_name;
	private final ArrayList<String> temp_mainflow = new ArrayList<String>();

	private ScenarioPanel() {
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
		parent.add(lb_prac);
		parent.add(tf_prac);
		parent.add(lb_secac);
		parent.add(tf_secac);
		parent.add(lb_prec);
		parent.add(tf_prec);
		parent.add(lb_postc);
		parent.add(tf_postc);
		parent.add(lb_mainflow);
		parent.add(sp_mainflow);
		parent.add(lb_altflow);
		parent.add(sp_altflow);
		parent.add(button_panel);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		ta_mainflow.setFont(font);
		ta_altflow.setFont(font);

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

				temp_name = CurrentGui.getInstance().getGui().getPropertyPane().getText();
				scenarioframe.setTitle("Scenario - " + temp_name);

				Main main = Main.getInstance();
				Scenario existing_scenario = main.getKnowledgeBase().getScenario(temp_name);
				if (existing_scenario != null) {
					tf_prac.setText(existing_scenario.getPrac());
					tf_secac.setText("");
					tf_prec.setText(existing_scenario.getPrecond());
					tf_postc.setText(existing_scenario.getPostcond());
					ta_mainflow.setText(existing_scenario.getMainflow().get(0));
				}
				else {
					tf_prac.setText("");
					tf_secac.setText("");
					tf_prec.setText("");
					tf_postc.setText("");
					ta_mainflow.setText("");
					ta_altflow.setText("");
				}
			}
		});

	}

	public void hideScenarioPanel() {
		scenarioframe.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Save")) {
			temp_mainflow.add(ta_mainflow.getText());
			Main main = Main.getInstance();
			main.getKnowledgeBase().addScenario(new Scenario(temp_name, tf_prac.getText(), tf_prec.getText(), tf_postc.getText(), temp_mainflow));
			hideScenarioPanel();
		}
		if (ae.getActionCommand().equals("Close")) {
			hideScenarioPanel();
		}
	}

}
