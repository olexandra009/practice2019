package math;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import math.RandomNumbers.ButtonCancel;
import math.RandomNumbers.ButtonHelp;
import math.RandomNumbers.ButtonOk;

public class ButtonsMenu extends JPanel {
	static JPanel flow;
	private JButton properties;
	private JButton ok;
	private JButton cancel;

	ButtonsMenu() {
		initializeAll();
	}

	private void initializeAll() {

		properties = new JButton("Properties");
		properties.setSize(100, 100);
		
		JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
		setBounds(new Rectangle(10, 10, 100, 100));
		ok = new JButton("OK");
		cancel = new JButton("Exit");
		grid.add(ok);
		
		flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		flow.add(grid);
		grid.add(properties);
		grid.add(cancel);
		
		
		add(flow, BorderLayout.SOUTH);
		ok.addActionListener(RandomNumbers.ok);
		properties.addActionListener(RandomNumbers.help);
		cancel.addActionListener(RandomNumbers.cancel);
		setVisible(true);
		repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawPoints(g);
	}
	
	
	/**
	 * Method that will b printing score
	 */
	private void drawPoints(Graphics g) {
		g.drawString("Score: " + RandomNumbers.jeneralscore, 10, 30);

	}

}