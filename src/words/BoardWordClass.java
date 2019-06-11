package words;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import main_view.MainMenu;
import tools.Queue;

 /**Клас що відповідає за логіку та виконання гри
 * @author Alexandra
 *
 */
@SuppressWarnings("serial")
public class BoardWordClass extends JPanel implements ActionListener {
    /**Розміри ігрового поля*/
	private final int SCREEN_WIDTH = 600;
	private final int SCREEN_HEIGHT = 600;
	private int gameplay = 8;
	
	 /**Масив зображеннь та імен*/
	private final int images = 8;
	private String[] arrayofNames;
	private Image[] arrayOfImage;
	
	 /**Змінні що відповідають за наповнення ігрового поля літерами та кольорами*/
	private Queue<Character> wordLetter;
	private char[][] letters;
	private byte[][] colorLetter;
	private int game_play_width = 450;
	private int gp_X = 10;
	private int gp_Y = 30;
	
	 /**Змінні для визначення часу*/
	private long timeEnd=0;
	private long timerStart=0;
	private long currentTime=60;
	private long time = 60;
	 /**Бали*/
	private long score = 0;
	
	 /**Змінні що відповідають за стан гри*/
	private boolean nextLevelInit = false; 
	private boolean inGame = false;
	private boolean nextLevel = false;
	private boolean gameOver = false;
	private int life = 3;
	
	private Image image;
	private String word;
	private Timer timer;
	private Random rand ;
	private MainMenu mainm;
	private WordPanel wp;
	
	
	/**Конструктор гри
	 * @param m
	 * @param w
	 */
	public BoardWordClass(MainMenu m, WordPanel w) {
		timer = new Timer(30, this);
		timer.start();
		mainm = m;
		wp= w;
		loadImages();
		 rand = new Random();
		initGame();
		addMouseListener(new MyMouseListner());
		
	}
	

	/**Метод, що завантажує зображення*/
private void loadImages(){
	arrayOfImage = new Image[images];
	arrayofNames = new String[images];
	arrayofNames[0] = "огірок";
	arrayofNames[1] = "яблуко";
	arrayofNames[2] = "банан";
	arrayofNames[3] = "полуниця";
	arrayofNames[4] = "помідор";
	arrayofNames[5] = "ананас";
	arrayofNames[6] = "груша";
	arrayofNames[7] = "вишня";
	arrayOfImage[0] = new ImageIcon("image/cucumber.png").getImage();
	arrayOfImage[1] = new ImageIcon("image/apple.png").getImage();
	arrayOfImage[2] = new ImageIcon("image/banana.png").getImage();
	arrayOfImage[3] = new ImageIcon("image/strawberry.png").getImage();
	arrayOfImage[4] = new ImageIcon("image/tomato.png").getImage();
	arrayOfImage[5] = new ImageIcon("image/pineapple.png").getImage();
	arrayOfImage[6] = new ImageIcon("image/pear.png").getImage();
	arrayOfImage[7] = new ImageIcon("image/cherry.png").getImage();

}
/**Метод, що ініціалізує гру*/
private void initGame() {
	letters = new char [gameplay][gameplay];
	colorLetter = new byte [gameplay][gameplay];
    int im = rand.nextInt(arrayOfImage.length);
    image = arrayOfImage[im];
    word = arrayofNames[im];
    wordLetter =  new Queue<>();
    ArrayList<Integer> repits = new ArrayList<>();
    int x;
    int y;
    for (char letter : word.toUpperCase().toCharArray()) {
		while(true) {
    	x = rand.nextInt(letters.length);
		y = rand.nextInt(letters.length);
		if(!repits.contains(x+y*letters.length))
		{
			repits.add((x+y*letters.length));
			break;
		}
		}
		letters[x][y] = letter;
		wordLetter.enqueue(letter);
	}
	for (int i = 0; i < letters.length; i++) 
		for (int j = 0; j < letters[i].length; j++) {
			if(letters[i][j]!=0)
				continue;
			char a = (char) (rand.nextInt('Я'-'А') + 'А');
		    if(a == 1069) a = 1031;
		    else if(a ==1067) a = 1030;
		    else if(a ==1066) a= 1028;
			letters[i][j] =	a;
		}
	
}
@Override
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	drawMaze(g);
	
}

/**Метод що малює поле гри*/
	private void drawMaze(Graphics g) {
	
	g.setColor(Color.white);
	g.fillRect(6, 6, SCREEN_WIDTH-12, SCREEN_HEIGHT-12);
	g.setColor(Color.BLACK);
	g.drawRect(5, 5, SCREEN_WIDTH-10, SCREEN_HEIGHT-10);
	g.drawRect(SCREEN_WIDTH-130, 30, 101, 101);
	g.drawRect(SCREEN_WIDTH-131, 29, 103, 103);
	g.drawRect(SCREEN_WIDTH-130, 135, 101, 30);
	g.drawRect(SCREEN_WIDTH-131, 136, 103, 30);
	g.setFont(new Font("Helvetica", Font.BOLD, 20));

	g.drawString("score: "+score, SCREEN_WIDTH-129, 160);
	
	
	  g.setFont(new Font("Helvetica", Font.ITALIC, 70));
	g.drawRect(SCREEN_WIDTH-130, 172, 101, 101);
	
	g.drawString(getTime(), SCREEN_WIDTH-120, 250);
	
	g.drawRect(SCREEN_WIDTH-131, 173, 103, 101);
	
	g.drawOval(SCREEN_WIDTH-120, 280, 20, 30);
	g.drawOval(SCREEN_WIDTH-90, 280, 20, 30);
	g.drawOval(SCREEN_WIDTH-60, 280, 20, 30);
	 
	g.setColor(Color.GREEN);
	if(life == 0)
		g.setColor(new Color(37,37,37));
	g.fillOval(SCREEN_WIDTH-120, 280, 20, 30);
	if(life == 1)
		g.setColor(new Color(37,37,37));
	g.fillOval(SCREEN_WIDTH-90, 280, 20, 30);
	if(life == 2)
		g.setColor(new Color(37,37,37));
	g.fillOval(SCREEN_WIDTH-60, 280, 20, 30);
	
		
	g.setColor(Color.BLACK);
	
	g.drawImage(image, SCREEN_WIDTH-132, 31, null);
   
	g.drawRect(gp_X, gp_Y-1, game_play_width+2, game_play_width+2);
    g.drawRect(gp_X+1, gp_Y, game_play_width, game_play_width);
    
    int dx = game_play_width/letters.length;

    for(int i = 0; i<colorLetter.length; i++)
    	for(int j = 0; j<colorLetter.length; j++)
    		if(colorLetter[i][j]==1) {
    		    g.setColor(new Color(12, 120, 120, 34));
    			g.fillRect(dx*i+gp_X, gp_Y+dx*j, dx, dx);
    		}	
    		else if (colorLetter[i][j]==2) {
    			g.setColor(new Color(120, 12, 120, 34));
    			g.fillRect(dx*i+gp_X, gp_Y+dx*j, dx, dx);
    		}
    g.setColor(Color.BLACK);		
    g.setFont(new Font("Helvetica", Font.BOLD, 30));
    for(int i = 0; i <= letters.length; i++)
    {
    	g.drawLine(dx*i+gp_X, gp_Y, dx*i+gp_X, gp_Y+game_play_width);
    	g.drawLine(gp_X, gp_Y+dx*i, gp_X+game_play_width, gp_Y+dx*i);
    	if(i == letters.length) continue;
        for(int j = 0; j < letters[i].length; j++)
        {
        	g.drawChars(letters[i], j, 1, dx*j+gp_X+5, gp_Y+dx*i+30);
        }
    }
   
    for (int i = 0; i< word.length(); i++)
    {
    	g.drawLine(gp_X+dx*i+5, gp_Y+game_play_width+dx+10, gp_X+dx+dx*i, gp_Y+game_play_width+dx+10);
    }
    g.setFont(new Font("Helvetica", Font.BOLD, 40));
    for (int i = 0; i<word.length()-wordLetter.length(); i++)
    {
    	g.drawString(""+word.toUpperCase().charAt(i), gp_X+dx*i+5, gp_Y+game_play_width+dx+10);
    }
    if(gameOver) {
    	g.setColor(new Color(12, 255, 70, 34));
    	g.fillRect(0, 0, 1000, 600);
    	mainm.score += score;
    	g.setColor(Color.WHITE);
		g.fillRect(150, 150, 300, 300);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.BOLD, 18));
		g.drawString("GameOver", 175, 175);
		g.drawString("Your score: "+score, 175, 190);
		g.drawRect(270, 400, 60, 30);
		g.drawString("Back", 278, 421);
    	
    }

}

/**Метод що перевіряє чи вибрана лутера правильна*/
	private void lightLetter(int x, int y) {
		if(x<gp_X||x>gp_X+game_play_width) return;
		if(y<gp_Y||y>gp_Y+game_play_width) return;
	    int xa = (x-gp_X)*letters.length/game_play_width;
	    int ya = (y-gp_Y)*letters.length/game_play_width;
	    char a = wordLetter.peek();
	    		   
        if (a == letters[ya][xa])
        {
	    colorLetter[xa][ya] = (byte) 1;
	    wordLetter.dequeue();
        }
        else {
        colorLetter[xa][ya] = (byte) 2;	
        life --;
        if(life<=0) {
        timer.stop();	
        gameOver = true;
        repaint();
          }
        }
	}
	/**Метод що обраховує час*/
	private String getTime() {
		if(timerStart==0)
			return  "60";
		timeEnd = System.currentTimeMillis();
		
		long time = timeEnd - timerStart;
		long sec = time / 1000;
		long minutes = sec / 60;
		long secunds = sec - minutes * 60;
		currentTime = this.time - secunds;
		System.out.println(time);
		System.out.println(currentTime);
		//String t = minutes + ":" + (secunds >= 10 ? secunds : "0" + secunds) + ":" + (last >= 10 ? last : "0" + last);
		String t = ""+((currentTime>=10)?currentTime:"0"+currentTime);
		
		if(time>60000)
		{
			gameOver=true;
			timer.stop();
			repaint();
		   System.out.println("Now stop");
		   return "00";
		}
		return t;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		 repaint();
		 if(nextLevelInit)
		 {
			 initGame();
			 nextLevelInit = false;
		 }
		if(nextLevel) {
			nextLevel = false;
			nextLevelInit= true;
        	score++;
        }
	   
	}


	/**
	 * @author Alexandra
	 *  Клас що опрацьовує натискання миші
	 */
	private class MyMouseListner extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
            if(gameOver) {
            	if(e.getX() >= 270 && e.getX() <= (270+60) )
					if(e.getY()>=400 && e.getY()<=430)
					{
						wp.setVisible(false);
						
						mainm.setVisible(true);
					}
            }
			if (!gameOver && inGame) {
				lightLetter(e.getX(), e.getY());

				if (wordLetter.isEmpty())
					nextLevel = true;
			}
			if (!inGame&&!gameOver) {
				inGame = true;
				timerStart = System.currentTimeMillis();
			}
		}
	}

}
