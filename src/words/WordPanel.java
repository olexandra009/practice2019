package words;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main_view.MainMenu;

/**Клас що відповідає за поєднання ігрового поля з дизайном*/
@SuppressWarnings("serial")
public class WordPanel extends JPanel{

	public WordPanel(MainMenu m) {
		setLayout(new BorderLayout(0, 0));
		add(new ColorPanel(), BorderLayout.EAST);
		add(new BoardWordClass(m, this), BorderLayout.CENTER);
		add(new ColorPanel(), BorderLayout.WEST);
		setVisible(true);
		setSize(1000, 600);
	}
private class ColorPanel extends JPanel{
		public ColorPanel() {
			setSize(200, 600);
            setVisible(true);
            add(new JLabel(new ImageIcon("image/bg_panel.png")));
		}
	}
}
