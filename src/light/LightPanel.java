package light;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main_view.MainMenu;


/**Клас що відповідає за поєднання ігрового поля з дизайном*/
@SuppressWarnings("serial")
public class LightPanel extends JPanel {

	public LightPanel(MainMenu m) {
		setLayout(new BorderLayout(0, 0));
		BoardLightGame bl = new BoardLightGame(m, this);
		add(new ColorPanel(), BorderLayout.EAST);
		add(bl, BorderLayout.CENTER);
		add(new ColorPanel(), BorderLayout.WEST);
		setVisible(true);
		setSize(1000, 600);
		bl.runGame();
	}
	
	private class ColorPanel extends JPanel{
		public ColorPanel() {
			setSize(200, 600);
            setVisible(true);
            add(new JLabel(new ImageIcon("image/bg_panel1.png")));
		}
		
	}
}