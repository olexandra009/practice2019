package tester;

import javax.swing.JFrame;

public class App extends JFrame{
 public App() {
	
	add(new Board());
	pack();
	setSize(1000, 1000);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
}
 public static void main(String[] args) {
	new App();
}
}
