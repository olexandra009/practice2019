package math;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ProperiesMenu extends JPanel {
	static JPanel flow;
	private JButton back;
	private JButton set;
	
	public ProperiesMenu() {
		initializeAll();
	}

	private void initializeAll() {

		back = new JButton("Back");
		back.setBackground(new Color(100,205,200));
		back.setSize(100, 100);
		back.setToolTipText("If you dont want to set new properties push this buttomn");

		
		JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
		setBounds(new Rectangle(10, 10, 100, 100));
		set = new JButton("Set");
		set.setBackground(new Color(100,205,200));
		grid.add(set);
		flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		flow.add(grid);
		flow.add(back);
		add(flow, BorderLayout.SOUTH);
		set.addActionListener(RandomNumbers.buttonSet);
		back.addActionListener(RandomNumbers.buttomBack);
		back.setVisible(true);
		set.setVisible(true);
		setVisible(true);
	}

}
