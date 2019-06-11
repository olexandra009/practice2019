package math;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
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
		properties.setBackground(new Color(100,205,100));
		
		JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
		setBounds(new Rectangle(10, 10, 100, 100));
		ok = new JButton("OK");
		ok.setBackground(new Color(100,205,100));
		cancel = new JButton("Exit");
		cancel.setBackground(new Color(100,205,100));
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
