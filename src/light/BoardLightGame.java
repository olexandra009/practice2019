package light;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

import main_view.MainMenu;
import tools.Queue;

@SuppressWarnings("serial")
public class BoardLightGame extends JPanel{
	private final int SCREEN_WIDTH = 600;
	private final int SCREEN_HEIGHT = 600;
	//визначаЇ розм≥р с≥тки 
	private int block_count = 0;
	private final int[] BLOCK_COUNT_LEVEL = {3, 4, 5, 6, 8};
	private final int[] MAX_COLOR_BLOCK_ON_LEVEL = {4, 8, 9, 10, 13};
	private int level = 0;
	private boolean inGame = false;
	private boolean fail = false;
	private boolean gameColorBlockPaint= true;
	private boolean [][] gameArray;
	private int score=0;
	private Random random; 
	Queue<Integer> positionCheck;
	private int showLights = 3;
	Queue<Integer> position;
	LightWorker worker ;
	private boolean nextLevel = true;
	MainMenu mainm;
	public BoardLightGame(MainMenu m) {
		setBackground(Color.white);
		setVisible(true);
        setSize(new Dimension( 600, 600));
        mainm = m;
	}

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

	private void drawLight(int x, int y){
		if(!positionCheck.isEmpty())
	    {
	     //ќЅ–ј’ќ¬”Їмо позиц≥ю в двом≥рному масив≥
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
			else {
				
			}
			
	    }
		
	
	}
	
	private void drawMaze(Graphics g) {
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
	
	
	
	//створюЇмо пусту чергу под≥й
	//на к≥льк≥сть л≥хтар≥в вираховуЇмо њх м≥сце розташуванн€
	
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

	class LightWorker extends Thread{
		
		@Override
		public void run() {
			super.run();
			System.out.println("Worker run");
			try {
				Thread.sleep(700);
				gameStartShow();
				System.out.println("Worker run::after show game");
				while(!position.isEmpty())
				{
					System.out.println("Worker run::after show game:: show light");
					int sq  = position.dequeue();
					positionCheck.enqueue(sq);
					int x = sq%BLOCK_COUNT_LEVEL[block_count];
					int y = (sq-x)/BLOCK_COUNT_LEVEL[block_count];
					gameArray[x][y] = true;
					gameColorBlockPaint= true;
					repaint();
					System.out.println("Worker run::after show game:: repaint");
					Thread.sleep(500);
					System.out.println("Worker run::after show game:: sleep");
				}
				System.out.println("Worker run::after show game:: clean");
				gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
				System.out.println("Worker run::after show game:: repaint");
				repaint();
				nextLevel = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	}
	

	private class MyMouseListeners extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if(!inGame&&fail) {
				if(e.getX() >= 270 && e.getX() <= (270+60) )
					if(e.getY()>=400 && e.getY()<=430)
					{
						setVisible(false);
						mainm.score+=score;
						mainm.setVisible(true);
					}
			}
			System.out.println("MouseClicked");
			if (!inGame && !fail) {
				System.out.println("MouseClicked::!inGameFail");
				inGame = true;
				worker=  new LightWorker();
				worker.start();
			} 
			if(inGame) {
				System.out.println("MouseClicked::inGameFail");
				if (!nextLevel) {
					System.out.println("MouseClicked::inGameFail::!nextLevel");
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
	}

	public void nextLevel() {
		
		nextLevel = true;
		score+=1;
		level++;
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


