package tester;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Board extends JPanel {

	public Board() {
		setBackground(Color.blue);
		addMouseListener(new MyMouseListener());
	}
	private class MyMouseListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			try {
				showLight();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void showLight() throws InterruptedException {
	   setBackground(Color.blue);
	   Thread.sleep(1000);
	   revalidate();
	   repaint();
	   setBackground(Color.white);
	   Thread.sleep(1000);
	   revalidate();
	   repaint();
	   setBackground(Color.red);
	   Thread.sleep(1000);
	   repaint();
	}
}
