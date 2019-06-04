package light;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.Query;
import javax.swing.JPanel;
import javax.swing.text.Position;

@SuppressWarnings("serial")
public class BoardLightGame extends JPanel{
	private final int SCREEN_WIDTH = 600;
	private final int SCREEN_HEIGHT = 600;
	private int block_count = 0;
	private final int[] BLOCK_COUNT_LEVEL = {3, 4, 5, 6, 8};
	private final int[] MAX_COLOR_BLOCK_ON_LEVEL = {8, 8, 9, 10, 13};
	private int level = 0;
	private boolean inGame = true;
	private boolean gamerClickBlock = true;
	private boolean [][] gameArray;
	private Random random; 
	LinkedBlockingQueue<Integer> positionCheck;
	private int showLights = 3;
	LinkedBlockingQueue<Integer> position;
	
	
	public BoardLightGame() {
		setBackground(Color.white);
		setVisible(true);
        setSize(new Dimension( 600, 600));
        random = new Random();
        positionCheck = new LinkedBlockingQueue<>();
		gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
        this.addMouseListener(new MyMouseListeners());
		LightWorker w = new LightWorker();
        w.start();
        
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMaze(g);
		gameDraw(g);
		
//		for (int i = 0; i < BLOCK_COUNT_LEVEL[block_count]; i++) {
//			for (int j = 0; j < BLOCK_COUNT_LEVEL[block_count]; j++)
//			{
//			 System.out.print(gameArray[i][j]+" ");
//			}
//			System.out.println();
//		}
//	
	}

	private void gameDraw(Graphics g) {
		int d_wid = SCREEN_WIDTH/BLOCK_COUNT_LEVEL[block_count];
		int d_hei = SCREEN_HEIGHT/BLOCK_COUNT_LEVEL[block_count];
		for (int i = 0; i < BLOCK_COUNT_LEVEL[block_count]; i++) {
			for (int j = 0; j < BLOCK_COUNT_LEVEL[block_count]; j++)
			{
				if(gameArray[i][j])
				{
					System.out.println("paint:::"+i+" "+j);
					g.setColor(Color.yellow);
					g.fillRect(i*d_wid, j*d_hei, d_wid, d_hei);
					//gameArray[i][j] = false;
				}
					
			}
		}
	}

	private void drawLight(int x, int y) throws InterruptedException {
		  if(!positionCheck.isEmpty())
		    {
		int xa = x*BLOCK_COUNT_LEVEL[block_count]/SCREEN_WIDTH;
		int ya = y*BLOCK_COUNT_LEVEL[block_count]/SCREEN_HEIGHT;
		gameArray[xa][ya] = !gameArray[xa][ya];
	  
		int check = positionCheck.take();
		if(check != ya*BLOCK_COUNT_LEVEL[block_count]+xa)
		{
			this.setBackground(Color.MAGENTA);
		}
	    }
	}
	
	private void drawMaze(Graphics g) {
		 g.setColor(new Color(13, 123, 12));
		 
		   // n blocks:: n-1
		  // n*n blocks:: (n-1)(n-1)
		  // d_wid = screen_w / n
		  // d_hei = screen_h / n
		  //    |
		  ////
		  int d_wid = SCREEN_WIDTH/BLOCK_COUNT_LEVEL[block_count];
		  int d_hei = SCREEN_HEIGHT/BLOCK_COUNT_LEVEL[block_count];
		  for(int i = 0; i <= BLOCK_COUNT_LEVEL[block_count]; i++)
		  {
			  g.setColor(new Color(13, 123, 12));
			  g.drawLine(0, i*d_wid, 600, i*d_wid);
			  g.setColor(new Color(123, 12, 12));
			  g.drawLine(i*d_hei, 0,  i*d_hei, 600);
		  }
         
	}
	
	
	
	
	private void gameStartShow() {
		int d_wid = SCREEN_WIDTH/BLOCK_COUNT_LEVEL[block_count];
		int d_hei = SCREEN_HEIGHT/BLOCK_COUNT_LEVEL[block_count];
	    position = new LinkedBlockingQueue<>();
        for(int i = 0; i < showLights; i++)
        {
         int x ;
   		 int y;
        	do {
		 int light = random.nextInt(gameArray.length*gameArray.length);
		 position.add(light);
		  x = light%BLOCK_COUNT_LEVEL[block_count];
		  y = (light-x)/BLOCK_COUNT_LEVEL[block_count];
		  System.out.println(light+" :: "+x+" "+y+" "+BLOCK_COUNT_LEVEL[block_count]);
        	}while(gameArray[x][y]);
        //	gameArray[x][y] = true;
	//	 g.setColor(Color.yellow);
	//	 g.fillRect(x*d_wid, y*d_wid, d_wid, d_hei);
      
        }
        
        
		
	}

	class LightWorker extends Thread{
		
		@Override
		public void run() {
			super.run();
			try {
				gameStartShow();
				while(!position.isEmpty())
				{
				int sq  = position.take();
				positionCheck.add(sq);
				int x = sq%BLOCK_COUNT_LEVEL[block_count];
				int y = (sq-x)/BLOCK_COUNT_LEVEL[block_count];
				System.out.println("sq "+sq+" "+x+" "+y);
				gameArray[x][y] = true;
				repaint();
				Thread.sleep(500);
				}
//				for (int i = 0; i < gameArray.length; i++) 
//					for (int j = 0; j < gameArray.length; j++) {
//						gameArray[i][j] = false;
//					}
//				
				gameArray = new boolean[BLOCK_COUNT_LEVEL[block_count]] [BLOCK_COUNT_LEVEL[block_count]];
			    repaint();
				gamerClickBlock = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class MyMouseListeners extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			System.out.println(x+" "+y);
			try {
				drawLight(x, y);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
			super.mouseClicked(e);
		}
	}
}


