package main_view;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Pixi extends JFrame{

	public Pixi() {
		setSize(1000, 700);
		setTitle("Pixi");
		add(new MainMenu(this));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new Pixi();
	}
}
