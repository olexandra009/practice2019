package light;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.PopupMenu;

import javax.swing.JFrame;
import javax.swing.text.LayoutQueue;

public class ApplicationTester extends JFrame{
	
	public ApplicationTester() {

//	    setLayout(new BorderLayout(2, 2));
		this.add(new BoardLightGame(), BorderLayout.CENTER);
		setTitle ("Pixi");
	    setSize(630, 660);
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ApplicationTester();
	}
}
