package main_view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import figure.TriangleCircle;
import light.LightPanel;
import math.RandomNumbers;
import words.WordPanel;
/**Клас що виконує функцію головного вікна гри*/
@SuppressWarnings("serial")
public class MainMenu extends JPanel  {
	/**Зображення для гри*/
	private Image[] pixi = new Image[4];
	private Image math;
	private Image figure;
	private Image light;
	private Image word;
	/**Визначення головного героя*/
	private int[] pixiScore = {5, 125, 625};
	public  int score = 0;
	private int pixi_level= 0;
	/**Розміри вікна*/
	private final int boardWidht = 1000;
	private final int boardHeight = 600;
	
	private Pixi pixiGame;
	
	private boolean help = false;
	
	/**Конструктор*/
	public MainMenu(Pixi pixi2) {
	   	setSize(boardWidht, boardHeight);
	   	loadImages();
	   	pixiGame = pixi2;
		this.setFocusable(true);
	  	addMouseListener(new MyMouseListner());
	   	addKeyListener(new MyKeyListner());
	}
	/**Метод що завантажує зображення*/
	private void loadImages() {
		  pixi[0] = new ImageIcon("image/Pixi-little.png").getImage();
		  pixi[1] = new ImageIcon("image/Pixi-little-crash.png").getImage();
		  pixi[2] = new ImageIcon("image/Pixi-little-birth-more crash.png").getImage();
		  pixi[3] =new ImageIcon("image/Pixi_kid.png").getImage();
		  math = new ImageIcon("image/math_game.png").getImage();
		  figure =  new ImageIcon("image/figure.png").getImage();
		  light =  new ImageIcon("image/light.png").getImage();
		  word = new ImageIcon("image/word.png").getImage();
		  
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawPixi(g);
		if(help) {
			drawHelp(g);
		}
	}
	private void drawHelp(Graphics g) {
		g.setColor(new Color(13, 13, 13, 200));
	    g.fillRect(0, 0, 1010, 650);
	    g.fillRect(90, 70, 800, 550);
	    g.setColor(Color.white);
	    g.setFont(new Font("Centry-Schoolbook", Font.PLAIN, 30));
	    g.drawString("Гра \"Pixi\"", 140, 100);
	    g.setFont(new Font("Centry-Schoolbook", Font.PLAIN, 20));
	    g.drawString("(для продовження гри натисніть F1)", 100, 160);
	    g.drawString("Головна мета гри набрати як можна більше балів та виростити Піксі", 110, 220);
	    g.drawString("Бали набираються при проходженні міні ігор", 100, 250);
	    g.drawString("Гра \"Встигни-порахуй\"", 100, 280);
	    g.drawString("Необхідно правильно порахувати приклади та ввести з клавіатури результати", 110, 310);
	    g.drawString("Гра \"Розкидане слово\"", 100, 340);
	    g.drawString("Необхідно зібрати слово, яке зображує картинка. Гра триває одну хвилину.", 110, 370);
	    g.drawString("Гра \"Трикутне коло\"", 100, 400);
	    g.drawString("Необхідно обрати всі фігури які задовольняють умові", 110, 430);
	    g.drawString("Гра \"Запам'ятай ліхтар\"", 100, 460);
	    g.drawString("Необхідно запам'ятати послідовність підсвічення клітинок і повторити її", 110, 490);
	    g.drawString("При набранні певної кількості балів персонаж змінюється", 100, 520);
	    g.drawString("Бажаємо весело та корисно провести час!", 100, 590);
	    
	}
	/**Метод, що малює головне вікно*/
	private void drawPixi(Graphics g) {
		g.setColor(Color.BLACK);
		if(pixi_level!=3&&score>pixiScore[pixi_level])
			pixi_level++;
		g.drawImage(pixi[pixi_level], 0, 0, null);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 25));
		g.drawString("Score: "+score, boardWidht-200, 40);
		g.drawImage(math, boardWidht-300, 100, null);
		g.drawImage(word, boardWidht-300, 210, null);
		g.drawImage(figure, boardWidht-300, 320, null);
		g.drawImage(light, boardWidht-300, 430, null);
		g.setColor(new Color(13,13,13,110));
		g.setFont(new Font("Centry-Shoolbook", Font.PLAIN, 10));
		g.drawString("Для допомоги натисніть F1", 0, 640);
	}
	/**Зміна ігор*/
	private void changePanel(int x, int y) {
		if(x>=boardWidht-300 && x<=boardWidht-100) {
			if(y>=100 && y <= 200) {
			pixiGame.add(new RandomNumbers(this));
		    this.setVisible(false);
			}
			else if (y>=210 && y <=310) {
			    pixiGame.add(new WordPanel(this));
			    this.setVisible(false);
			}
			
			else if(y>=320 && y<=420)
			{
				pixiGame.add(new TriangleCircle(this));
				
			    this.setVisible(false);
			}
			else if(y>=430 && y < 530) {
				pixiGame.add(new LightPanel(this));
				this.setVisible(false);
				revalidate();
				repaint();
			    
			}
		}
	}
	
class MyMouseListner extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
	   if(!help)
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
              if(e.getKeyCode() == KeyEvent.VK_F1)
              {
            	  help = !help;
            	  repaint();
              }
	
	}

}


}
