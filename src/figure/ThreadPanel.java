package figure;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThreadPanel extends JPanel {

	/** BORDER FOR GAMING FIELD */
	private int borderStartX = 600 / 4;
	private int borderStartY = 5;
	volatile boolean running = true;
	Graphics g;
	static Thread timerThread;

	public ThreadPanel() {
		initializeAll();
	}

	public void initializeAll() {
		setBackground(new Color(10, 255, 70));
		setSize(300, 600);
		setLayout(new BorderLayout());
		JLabel lBEL = new JLabel("Time left: ");
		add(lBEL , BorderLayout.WEST);
		timerThread = new Thread(new MyThread(g));
		timerThread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawTimer(g);
		if (TriangleCircle.timerWidth < borderStartX) {
			TriangleCircle.timerWidth = 500;
			TriangleCircle.timerEnded=true;
			running=false;
		}
	}

	/** Method that shows times */
	private void drawTimer(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(borderStartX, borderStartY, 500, 10);
		if (TriangleCircle.shouldChangeTimerColor)
			g.setColor(Color.red);
		else
			g.setColor(Color.ORANGE);
		g.fillRect(borderStartX, borderStartY, TriangleCircle.timerWidth, 10);

		TriangleCircle.shouldChangeTimerColor = false;

	}

	/**Timer count in seperate thread*/
	class MyThread extends Thread {
		Graphics g;

		MyThread(Graphics g) {
			this.g = g;
		}

		public void run() {
			while (running) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (running)
					TriangleCircle.timerWidth -= TriangleCircle.timerDifference;
				repaint();

			}
		}
	}
}
