package com.baselet.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DUCKHelpPanel extends JPanel implements ActionListener {

	private static DUCKHelpPanel duckhelppanel;

	public static DUCKHelpPanel getInstance() {
		if (duckhelppanel == null) {
			duckhelppanel = new DUCKHelpPanel();
		}
		return duckhelppanel;
	}

	private final JFrame duckhelpframe;

	private final JLabel lb_requirements = new JLabel("1. Requirements");
	private final JLabel lb_knowledgebase = new JLabel("2. Knowledge Base");
	private final JLabel lb_scenarios = new JLabel("3. Scenarios");
	private final JLabel lb_consistencychecks = new JLabel("4. Consistency Checks");

	public DUCKHelpPanel() {
		JPanel parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		parent.add(lb_requirements);
		parent.add(lb_knowledgebase);
		parent.add(lb_scenarios);
		parent.add(lb_consistencychecks);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		duckhelpframe = new JFrame("DUCK Help");
		duckhelpframe.setContentPane(parent);
		duckhelpframe.pack();
	}

	public void showDuckHelpPanel() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				duckhelpframe.setLocationRelativeTo(CurrentGui.getInstance().getGui().getMainFrame());
				duckhelpframe.setVisible(true);
				duckhelpframe.toFront();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
