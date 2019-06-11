package light;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

import main_view.MainMenu;
import tools.Queue;


/**Клас що відповідає за логіку і виконання гри Запам'ятай ліхтар
 * @author Alexandra
 *
 */
@SuppressWarnings("serial")
public class BoardLightGame extends JPanel{
	
	/**Розміри ігрового поля*/
	private final int SCREEN_WIDTH = 600;
	private final int SCREEN_HEIGHT = 600;
	/**Розміри ігрової сітки*/
	private int block_count = 0;
	private final int[] BLOCK_COUNT_LEVEL = {3, 4, 5, 6, 8};
	private final int[] MAX_COLOR_BLOCK_ON_LEVEL = {4, 8, 9, 10, 13};
	/**Змінні що визнаючають хід гри*/
	private int score=0;
	private boolean [][] gameArray;
	private int showLights = 3;
	
	/**Індикатори ігрового процесу*/
	private boolean inGame = false;
	private boolean fail = false;
	private boolean nextLevel = true;
	
	/**Черги для перевірки відповідей*/
	private Queue<Integer> positionCheck;
	private Queue<Integer> position;
	
	private Random random; 

	private LightWorker worker;
	private MainMenu mainm;
	private LightPanel lp;
	
	
	/**Конструктор гри
	 * @param m - головне меню
	 * @param panel - ігрова панель
	 */
	public BoardLightGame(MainMenu m, LightPanel panel) {
		
		setVisible(true);
        setSize(new Dimension( 600, 600));
        mainm = m;
        lp = panel;
	}

	/** Метод що запускає гру
	 * @return
	 */
	public int runGame() {
        addMouseListener(new MyMouseListeners());
        random = new Random();
        positionCheck = new Queue<>();
		gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
	     
		return score;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!inGame&&fail) {
			drawLast(g);
		}else {
		drawMaze(g);
		gameDraw(g);
		}
		System.out.println("Repaint");
		
	}
	/**Метод що промальовує кінцевий результат*/
	private void drawLast(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(150, 150, 300, 300);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.BOLD, 18));
		g.drawString("GameOver", 175, 175);
		g.drawString("Your score: "+score, 175, 190);
		g.drawRect(270, 400, 60, 30);
		g.drawString("Back", 278, 421);
	}
	/**Метод що промальвує ліхтарі*/
	private void gameDraw(Graphics g) {
		int d_wid = SCREEN_WIDTH/gameArray.length;
		int d_hei = SCREEN_HEIGHT/gameArray.length;

		for (int i = 0; i < gameArray.length; i++) 
			for (int j = 0; j < gameArray[i].length; j++)
				if(gameArray[i][j])
				{
					System.out.println("paint:::"+i+" "+j);
					g.setColor(Color.yellow);
					g.fillRect(i*d_wid, j*d_hei, d_wid, d_hei);
				}
	
	}
	/**Метод що обраховує позицію кліка людини і перевіряє чи правильно вибрана клітинка*/
	private void drawLight(int x, int y){
		if(!positionCheck.isEmpty())
	    {
	     //ОБРАХОВУємо позицію в двомірному масиві
		 int xa = x*gameArray.length/SCREEN_WIDTH;
		 int ya = y*gameArray.length/SCREEN_HEIGHT;
		 gameArray[xa][ya] = !gameArray[xa][ya];
		 int check = positionCheck.dequeue();
			if(check != ya*gameArray.length+xa)
			{
				this.setBackground(new Color(12, 255, 70, 34));
				inGame = false;
				fail = true;
			}
	    }
	}
	
	private void drawMaze(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 600);
		 g.setColor(new Color(13, 123, 12));
		  int d_wid = SCREEN_WIDTH/gameArray.length;
		  int d_hei = SCREEN_HEIGHT/gameArray.length;
		  for(int i = 0; i <= gameArray.length; i++)
		  {
			  g.setColor(new Color(13, 123, 12));
			  g.drawLine(0, i*d_wid, 600, i*d_wid);
			  g.setColor(new Color(123, 12, 12));
			  g.drawLine(i*d_hei, 0,  i*d_hei, 600);
		  }
         
	}
	
	
	
	/***створюємо пусту чергу подій
	на кількість ліхтарів вираховуємо їх місце розташування*/
	
	private void gameStartShow() {
		gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
		
		System.out.println("gameStartShow");
		System.out.println("inGame && nextLevel"+inGame+" "+nextLevel);
		if (inGame && nextLevel) {
			System.out.println("inGame && nextLevel");
			position = new Queue<>();
			ArrayList<Integer> array = new ArrayList<>();
			int x = 0;
			int y = 0;
			int light = 0;

			for (int i = 0; i < showLights; i++) {
				while (true) {
					light = random.nextInt(gameArray.length * gameArray.length);
					
					if (!array.contains(light))
					{
						array.add(light);
						break;
					}
				}
				position.enqueue(light);
				x = light % BLOCK_COUNT_LEVEL[block_count];
				y = (light - x) / BLOCK_COUNT_LEVEL[block_count];
				System.out.println(light + " :: x::" + x + " y::" + y + " " + BLOCK_COUNT_LEVEL[block_count]);
				
			}
			
		}

	}

	/** Клас для використання потоку, який послідовно підсвічує клітинки
	 * @author Alexandra
	 *
	 */
	class LightWorker extends Thread{
		
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(700);
				gameStartShow();
				while(!position.isEmpty())
				{
					int sq  = position.dequeue();
					positionCheck.enqueue(sq);
					int x = sq%BLOCK_COUNT_LEVEL[block_count];
					int y = (sq-x)/BLOCK_COUNT_LEVEL[block_count];
					gameArray[x][y] = true;
			     	repaint();
					Thread.sleep(500);
				}
				gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
				repaint();
				nextLevel = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	
	}
	

	/** Клас що опрацьовує натискання миші
	 * @author Alexandra
	 *
	 */
	private class MyMouseListeners extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if(!inGame&&fail) 
				if(e.getX() >= 270 && e.getX() <= (270+60) )
					if(e.getY()>=400 && e.getY()<=430)
					{
						lp.setVisible(false);
						mainm.score+=score;
						mainm.setVisible(true);
					}
			
			if (!inGame && !fail) {
				inGame = true;
				worker=  new LightWorker();
				worker.start();
			} 
			if(inGame) 
				if (!nextLevel) {
					int x = e.getX();
					int y = e.getY();
					System.out.println(x + " " + y);
					drawLight(x, y);
					System.out.println("HERE");
				
					for (int i = 0; i < BLOCK_COUNT_LEVEL[block_count]; i++) 
						for (int j = 0; j < BLOCK_COUNT_LEVEL[block_count]; j++)
							if(gameArray[i][j])
								System.out.println("yellow:::"+i+" "+j);
					if(positionCheck.isEmpty())
					{
						
						nextLevel();
					
					}
					repaint();
					
				}
			
		}
	}
	/**Метод що змінює рівень*/
	private void nextLevel() {
		
		nextLevel = true;
		score+=1;
		if(showLights<MAX_COLOR_BLOCK_ON_LEVEL[block_count])
			showLights++;
		else {
			block_count++;
			if(block_count>=BLOCK_COUNT_LEVEL.length) {
				inGame = false;
			    fail = true;
			    return;
			}
			
		  showLights = 3;
		}
		System.out.println(showLights+" "+block_count);
		worker=  new LightWorker();
		worker.start();
		
	}
}


