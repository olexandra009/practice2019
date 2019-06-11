package figure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

import main_view.MainMenu;

@SuppressWarnings("serial")
public class TriangleCircle extends JPanel implements ActionListener {
	private Random random;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	private static final int maxFigureWidth = 60;
	private static final int maxFigureHeight = 80;

	private Color mainColor;
	private String mainTextColor;

	/** BORDER FOR GAMING FIELD */
	private int borderStartX = WIDTH / 4;
	private int borderStartY = HEIGHT / 8;

	private int widthBorder = WIDTH - WIDTH / 6;
	private int heightBorder = HEIGHT - HEIGHT / 4;
	public static int timerWidth = 500;
	public static int timerDifference = 1;
	public static boolean shouldChangeTimerColor = false;
	private int levelDifficulty = 5;
	public static boolean timerEnded = false;

	CopyOnWriteArrayList<Figure> figures;
	CopyOnWriteArrayList<Figure> figuresToBeChoosen;

	private boolean shouldCreateNewFigures = true;// generate new Game
	private boolean shouldChoseMainColor = true;// go with shouldCreateNewFigures , for generating new Game
	private boolean shouldRepaintExisted = false;// value for continuing game
	private boolean restartGame = false;// value for continuing game
	private int generalScore = 0;
	private ThreadPanel threadPanel;
	MainMenu mainm;
	private Timer timer;

	public TriangleCircle(MainMenu mainMenu) {
		mainm = mainMenu;
		initializeAll();
		revalidate();

	}

	private void initializeAll() {
		setBackground(new Color(10, 255, 70));
		setSize(WIDTH, HEIGHT);
		setLocation(300, 200);
		random = new Random();
		figures = new CopyOnWriteArrayList<Figure>();
		figuresToBeChoosen = new CopyOnWriteArrayList<Figure>();
		mainColor = new Color(255, 255, 255);
		setVisible(true);
		addMouseListener(new MouseListenerForFigures());
		setLayout(new BorderLayout());
		threadPanel = new ThreadPanel();
		add(threadPanel, BorderLayout.NORTH);
		timer = new Timer(1000, this);
		timer.start();
	}

	/** Method that draws square or rectangle in game */
	private void drawRectangle(Graphics g, int x, int y, int height, int width, Color c) {
		g.setColor(c);
		g.fill3DRect(x, y, height, width, true);
		g.setColor(Color.black);
		g.draw3DRect(x, y, height, width, true);
	}

	/** Method that draws oval or circle in game */
	private void drawOval(Graphics g, int x, int y, int height, int width, Color c) {
		g.setColor(c);
		g.fillOval(x, y, height, width);
		g.setColor(Color.black);
		g.drawOval(x, y, height, width);
	}

	/** Method that write a task */
	private void drawMainText(Graphics g) {
		String text = "Choose " + mainTextColor + " figures above all";
		g.drawString(text, borderStartX + 150, borderStartY - 25);
	}

	/** method that update score */
	private void drawScore(Graphics g) {
		g.setColor(Color.black);
		if (generalScore < 0)
			generalScore = 0;
		String score = "Score: " + generalScore;
		g.drawString(score, borderStartX - 40, borderStartY - 25);

	}
/**method that clean sreen after gane over*/
	void deleteAll(Graphics g) {
		setBackground(Color.white);
		g.fillRect(0, 0, WIDTH, WIDTH);
		remove(threadPanel);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBorder(g);
		drawScore(g);
		if (timerEnded) {
			setVisible(false);
			mainm.setVisible(true);
			mainm.score += generalScore;
			timerEnded = false;
			timer.stop();
			return;
		}

		if (shouldCreateNewFigures) {
			shouldChoseMainColor = true;
			figures = new CopyOnWriteArrayList<Figure>();
			figuresToBeChoosen = new CopyOnWriteArrayList<Figure>();
			drawFigure(g, levelDifficulty);
			drawMainText(g);
			shouldCreateNewFigures = false;
			shouldChoseMainColor = false;
			System.out.println("shouldCreateNewFigures");
		}

		if (shouldRepaintExisted) {
			repaintExistedFigures(g, figures);
			drawMainText(g);
			shouldRepaintExisted = false;
			System.out.println("shouldRepaintExisted");
		}
		if (figuresToBeChoosen.isEmpty()) {
			shouldCreateNewFigures = true;
			repaint();
		}

	}

	/** Method that delete extra figures */
	private void repaintExistedFigures(Graphics g, CopyOnWriteArrayList<Figure> figures) {
		for (Figure f : figures) {
			if (f.getType() == "Rectangle") {
				drawRectangle(g, f.getX1(), f.getY1(), maxFigureWidth, maxFigureHeight, f.getColor());
			} else if (f.getType() == "Oval") {
				drawOval(g, f.getX1(), f.getY1(), maxFigureWidth, maxFigureHeight, f.getColor());
			} else if (f.getType() == "Circle") {
				drawOval(g, f.getX1(), f.getY1(), maxFigureWidth, maxFigureWidth, f.getColor());
			} else if (f.getType() == "Square") {
				drawRectangle(g, f.getX1(), f.getY1(), maxFigureWidth, maxFigureWidth, f.getColor());
			} else {
				System.out.println("Something went wrong");
			}
		}

	}

	/** Border , where figures exists */
	private void drawBorder(Graphics g) {
		g.setColor(Color.orange);
		g.drawRect(borderStartX - 10, borderStartY - 10, widthBorder + 20, heightBorder + 20);
		g.fillRect(borderStartX, borderStartY, widthBorder, heightBorder);

	}

	/** Random generation of figures position */
	private Figure generatePositionCircle() {
		int r1 = random.nextInt(widthBorder - maxFigureWidth);

		if (!(r1 < widthBorder + maxFigureWidth && r1 > borderStartX))
			while (!(r1 < widthBorder + maxFigureWidth && r1 > borderStartX))
				r1 = random.nextInt(widthBorder - maxFigureWidth);

		int r2 = random.nextInt(heightBorder - maxFigureHeight);
		if (!(r2 < heightBorder + maxFigureHeight && borderStartY < r2))
			while (!(r2 < heightBorder + maxFigureHeight && borderStartY < r2))
				r2 = random.nextInt(heightBorder - maxFigureHeight);
		Color c = getColor(random.nextInt(4));
		Figure f = new Figure(r1, r2, r1 + maxFigureWidth, r2 + maxFigureHeight, c);
		f.setColor(c);
		return f;
	}

	/** Method that return random color */
	private Color getColor(int x) {
		switch (x) {
		case 0:
			if (shouldChoseMainColor) {
				mainTextColor = "Yellow";

				System.out.println("Yellow");
			}
			shouldChoseMainColor = false;

			return Color.yellow;
		case 1:
			if (shouldChoseMainColor) {
				mainTextColor = "Cyan";
				System.out.println("Cyan");
			}
			shouldChoseMainColor = false;
			return Color.cyan;
		case 2:
			if (shouldChoseMainColor) {
				mainTextColor = "Green";
				System.out.println("Green");
			}
			shouldChoseMainColor = false;

			return Color.GREEN;
		case 3:
			if (shouldChoseMainColor) {
				mainTextColor = "Red";
				System.out.println("Red");
			}
			shouldChoseMainColor = false;

			return Color.RED;
		default:
			if (shouldChoseMainColor) {
				mainTextColor = "Black";
			}
			shouldChoseMainColor = false;

			return Color.BLACK;
		}

	}

	/** Method that draws random figures */
	private void drawFigure(Graphics g, int amount) {
		int res = random.nextInt(4);
		mainColor = getColor(res);
		for (int i = 0; i < amount; i++) {
			switch (res) {
			case 0:
				Figure f = generatePositionCircle();
				while (true) {
					if (f.colideWithotherFigures(figures) == false)
						break;
					f = generatePositionCircle();
					// System.out.println("AAA");
				}
				drawRectangle(g, f.getX1(), f.getY1(), f.getWidth(), f.getHeight(), f.getColor());
				f.setType("Rectangle");
				figures.add(f);
				if (f.getColor() == mainColor)
					figuresToBeChoosen.add(f);
				break;
			case 1:
				Figure f1 = generatePositionCircle();
				while (true) {
					if (f1.colideWithotherFigures(figures) == false)
						break;
					f1 = generatePositionCircle();
				}
				drawOval(g, f1.getX1(), f1.getY1(), maxFigureWidth, maxFigureHeight, f1.getColor());
				f1.setType("Oval");
				figures.add(f1);

				if (f1.getColor() == mainColor)
					figuresToBeChoosen.add(f1);

				break;
			case 2:
				Figure f2 = generatePositionCircle();
				while (true) {
					if (f2.colideWithotherFigures(figures) == false)
						break;
					f2 = generatePositionCircle();
				}

				drawRectangle(g, f2.getX1(), f2.getY1(), maxFigureWidth, maxFigureWidth, f2.getColor());
				f2.setType("Square");
				figures.add(f2);
				if (f2.getColor() == mainColor)
					figuresToBeChoosen.add(f2);

				break;
			case 3:
				Figure f3 = generatePositionCircle();
				while (true) {
					if (f3.colideWithotherFigures(figures) == false)
						break;
					f3 = generatePositionCircle();
					// System.out.println("DDD");
				}

				drawOval(g, f3.getX1(), f3.getY1(), maxFigureWidth, maxFigureWidth, f3.getColor());
				f3.setType("Circle");
				figures.add(f3);
				if (f3.getColor() == mainColor)
					figuresToBeChoosen.add(f3);
				break;

			default:
				break;
			}
			res = random.nextInt(4);
		}
	}

	class MouseListenerForFigures implements MouseListener {

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		/** Method that change game (depends on click) */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Point p = new Point(arg0.getX(), arg0.getY());
			for (Figure r : figures) {
				if (r.hadInsidePoint(p) && r.getColor() == mainColor) {
					// System.out.println("Should repaint");
					shouldRepaintExisted = true;
					repaint();
					figures.remove(r);
					figuresToBeChoosen.remove(r);
					generalScore += 1;
					break;
				} else if (r.hadInsidePoint(p)) {
					timerWidth -= 15;
					generalScore -= 2;
					shouldChangeTimerColor = true;
				}

			}

			if (figuresToBeChoosen.isEmpty()) {
				shouldChoseMainColor = true;
				shouldCreateNewFigures = true;
				shouldRepaintExisted = false;
				repaint();
			}
			if (generalScore > 120 && shouldCreateNewFigures == true/** &&timerEnded */
			) {
				System.out.println("HEEETEEEEEEE");
				if (levelDifficulty < 20)
					levelDifficulty += 1;
				generalScore = 0;
				timerEnded = true;
				repaint();
				timerEnded = false;
			}
			if (restartGame) {
				removeAll();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (timerEnded) {
			repaint();
			timer.stop();
			System.out.println("TIMER ENDED");
		} else
			System.out.println("NOT YET");

	}

}
