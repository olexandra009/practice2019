package main_view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import figure.TriangleCircle;
import light.BoardLightGame;
import math.RandomNumbers;
import words.BoardWordClass;

public class MainMenu extends JPanel  {
	
	private Image[] pixi = new Image[4];
	private Image[] background = new Image[5];
	private int[] pixiScore = {5, 125, 625};
	public  int score = 0;
	private int pixi_level= 0;
	private final int boardWidht = 1000;
	private final int boardHeight = 600;
	private  int bg = 0;
	private Pixi pixiGame;
	public MainMenu(Pixi pixi2) {
	   	setSize(1000, 600);
	   	loadImages();
	   	pixiGame = pixi2;
		this.setFocusable(true);
	  	addMouseListener(new MyMouseListner());
	   	addKeyListener(new MyKeyListner());
//	   	Timer timer = new Timer(200, this);
//	   	timer.start();
	}
	
	private void loadImages() {
		  pixi[0] = new ImageIcon("image/Pixi-little.png").getImage();
		  pixi[1] = new ImageIcon("image/Pixi-little-crash.png").getImage();
		  pixi[2] = new ImageIcon("image/Pixi-little-birth-more crash.png").getImage();
		  pixi[3] =new ImageIcon("image/Pixi_kid.png").getImage();
		  math = new ImageIcon("image/math_game.png").getImage();
		  figure =  new ImageIcon("image/figure.png").getImage();
		  light =  new ImageIcon("image/light.png").getImage();
		  word = new ImageIcon("image/word.png").getImage();
//		  background[0] = new ImageIcon("image/bg0.png").getImage();
//		  background[1] = new ImageIcon("image/bg1.png").getImage();
//		  background[2] = new ImageIcon("image/bg2.png").getImage();
//		  background[3] = new ImageIcon("image/bg3.png").getImage();
//		  background[4] = new ImageIcon("image/bg4.png").getImage();
//		  
	}
	Image math;
	Image figure;
	Image light;
	Image word;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawPixi(g);
	}

	private void drawPixi(Graphics g) {
		if(pixi_level!=3&&score>pixiScore[pixi_level])
			pixi_level++;
	//	g.drawImage(background[bg], 0, 20, null);
		g.drawImage(pixi[pixi_level], 0, 0, null);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 25));
		g.drawString("Score: "+score, boardWidht-200, 40);
		g.drawImage(math, boardWidht-300, 100, null);
		g.drawImage(word, boardWidht-300, 210, null);
		g.drawImage(figure, boardWidht-300, 320, null);
		g.drawImage(light, boardWidht-300, 430, null);
	}

	private void changePanel(int x, int y) {
		if(x>=boardWidht-300 && x<=boardWidht-100) {
			if(y>=100 && y <= 200) {
			pixiGame.add(new RandomNumbers(this));
		    this.setVisible(false);
			}
			else if (y>=210 && y <=310) {
			    pixiGame.add(new BoardWordClass(this));
			    this.setVisible(false);
			}
			
			else if(y>=320 && y<=420)
			{
				pixiGame.add(new TriangleCircle(this));
				
			    this.setVisible(false);
			}
			else if(y>=430 && y < 530) {
				BoardLightGame lg = new BoardLightGame(this);
				pixiGame.add(lg);
				lg.runGame();
				this.setVisible(false);
				revalidate();
				repaint();
			    
			}
		}
	}
	
class MyMouseListner extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		changePanel(e.getX(), e.getY());
		
	}

}
class MyKeyListner extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
              if(e.isShiftDown()&&e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_F5) {
            	  score+=100;
            	  repaint();
              }
	
	}

}


}
