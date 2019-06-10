package math;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main_view.MainMenu;

public class RandomNumbers extends JPanel {
	protected static RandomNumbers randomNumbers;
	private int score = 0;
	static int jeneralscore = 0;

	private JPanel examplePanel;
	private Random random;
	static ButtonCancel cancel;
	static ButtonHelp help;
	static ButtonOk ok;
	static ButtonSet buttonSet;
	static ButtonBackFromSet buttomBack;
	private ButtonsMenu buttonsMenu;
	private ProperiesMenu propertiesMenu;

	private JLabel rools;
	static boolean propertiesSet = false;

	private HashMap<String, Integer> examples;// HashMap of new examples
	private ArrayList<JTextArea> answerFields;
	private ArrayList<JLabel> questionFields;

	private int amountOfExamples = 10;
	private int maxNumbersValue = 10;
	private int maxOperator = 1;
	private int amountOfSolvedExamples = 0;
	private MainMenu mainm;
	public RandomNumbers(MainMenu mainMenu) {
		mainm=mainMenu;
		initializeAll();

	}

	/** Method that initialize initial properties */
	private void initializeAll() {
		setSize(600, 600);
		//setLocation(300, 200);
		random = new Random();
		setLayout(new BorderLayout());

		rools = new JLabel("Your main goal is to solve all examples");
		rools.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		rools.setFont(new Font("Arial", 20, 20));

		cancel = new ButtonCancel();
		ok = new ButtonOk();
		help = new ButtonHelp();
		buttonSet = new ButtonSet();
		buttomBack = new ButtonBackFromSet();

		// 2
		setPropertiesPanel = new SetPropertiesPanel();
		buttonsMenu = new ButtonsMenu();
		propertiesMenu = new ProperiesMenu();
		add(setPropertiesPanel, BorderLayout.CENTER);
		add(propertiesMenu, BorderLayout.SOUTH);
		setPropertiesPanel.setVisible(false);
		propertiesMenu.setVisible(false);

		// 1
		examplePanel = new JPanel(new GridLayout(5, 1, 10, 10));
		examplePanel = addExamplesToPanel();
		buttonsMenu = new ButtonsMenu();

		add(rools, BorderLayout.NORTH);
		add(examplePanel, BorderLayout.CENTER);
		add(buttonsMenu, BorderLayout.SOUTH);

		examplePanel.setVisible(true);
		buttonsMenu.setVisible(true);
		buttonsMenu.repaint();

	}

	/** Method that generate and draw different examples in panel */
	private JPanel addExamplesToPanel() {
		
		generate(amountOfExamples, maxOperator, maxNumbersValue);
		examplePanel.removeAll();
		answerFields = new ArrayList<JTextArea>();
		questionFields = new ArrayList<JLabel>();

		for (Map.Entry<String, Integer> x : examples.entrySet()) {
			JLabel question = new JLabel(x.getKey());
			JTextArea answer = new JTextArea();
			answerFields.add(answer);
			questionFields.add(question);
			question.setFont(new Font("Robo", 20, 20));

			examplePanel.add(question);
			examplePanel.add(answer);
		}
		examplePanel.setVisible(true);
		return examplePanel;

	}

	/**
	 * Method that generate examples to solve maxOperator - how many operators to
	 * use maxNumber - max number to get
	 */
	private void generate(int amountOfExamples, int maxOperator, int maxNumber) {
		examples = new HashMap<String, Integer>();
		int number1 = 0;
		int number2 = 0;
		String example = null;
		for (int i = 0; i < amountOfExamples; i++) {
			int opp = random.nextInt(maxOperator);
			if (opp == 0) {
				number1 = random.nextInt(maxNumber);
				number2 = random.nextInt(maxNumber);
				example = number1 + " + " + number2 + " = ";
				int res = number1 + number2;
				examples.put(example, res);
			}
			// check
			else if (opp == 1) {
				number1 = random.nextInt(maxNumber);
				number2 = random.nextInt(maxNumber);
				example = (number1 > number2) ? (number1 + " - " + number2 + " = ")
						: (number2 + " - " + number1 + " = ");
				int res = Math.abs(number2 - number1);
				examples.put(example, (res));
			} else if (opp == 2) {
				number1 = random.nextInt(maxNumber);
				number2 = random.nextInt(maxNumber);
				example = number1 + " * " + number2 + " = ";
				int res = number1 * number2;
				examples.put(example, res);
			} else if (opp == 3) {
				number1 = random.nextInt(maxNumber);
				number2 = random.nextInt(maxNumber);
				if (number1 == 0)
					while (number1 == 0)
						number1 = random.nextInt(maxNumber);
				if (number2 == 0)
					while (number2 == 0)
						number2 = random.nextInt(maxNumber);
				int res = number1 * number2;
				example = res + " / " + number2 + " = ";
				examples.put(example, number1);
			}
		}

	}

	class ButtonCancel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removeAll();
			mainm.score+=jeneralscore;
			setVisible(false);
			mainm.setVisible(true);
			
		}

	}

	SetPropertiesPanel setPropertiesPanel;

	class ButtonHelp implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// 2
			removeAll();
			setPropertiesPanel = new SetPropertiesPanel();
			buttonsMenu = new ButtonsMenu();
			propertiesMenu = new ProperiesMenu();
			add(setPropertiesPanel, BorderLayout.CENTER);
			add(propertiesMenu, BorderLayout.SOUTH);
			setPropertiesPanel.setVisible(true);
			propertiesMenu.setVisible(true);
			revalidate();

		}

	}

	class ButtonOk implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (correctAllFiles())
				checkAnswers();
		}

		private void checkAnswers() {
			jeneralscore = score;
			score = 0;
			for (int i = 0; i < answerFields.size(); i++) {
				if (Integer.parseInt(answerFields.get(i).getText()) == examples.get(questionFields.get(i).getText())) {
					answerFields.get(i).setBackground(Color.GREEN);
					score += 1;
					amountOfSolvedExamples++;
				} else {
					answerFields.get(i).setBackground(Color.red);
					score -= 2;
					return;
				}
			}

			jeneralscore += score;

			// TODO:Р”РћР”РђРўР� Р Р†Р’РќР† Р’ Р—РђР›Р•Р–РќРћРЎРўР† Р’Р†Р” РЎРљРћР РЈ
			if (!propertiesSet) {
				if (amountOfSolvedExamples >= 65)
					maxNumbersValue = 1000;
				else if (amountOfExamples >= 50)
					maxOperator = 4;
				else if (amountOfSolvedExamples > 35)
					maxNumbersValue = 300;
				else if (amountOfSolvedExamples >= 30)
					maxOperator = 3;
				else if (amountOfSolvedExamples >= 20)
					maxNumbersValue = 100;
				if (amountOfSolvedExamples >= 15)
					maxOperator = 2;
			}

			
			addExamplesToPanel();
			examplePanel.revalidate();  
			examplePanel.setVisible(true);

			add(examplePanel);
			buttonsMenu = new ButtonsMenu();
			add(buttonsMenu, BorderLayout.SOUTH);
			buttonsMenu.repaint();

		}

		private boolean correctAllFiles() {
			for (JTextArea area : answerFields) {
				if (!isInteger(area.getText()) || (area.getText().isEmpty())) {
					area.setBackground(Color.YELLOW);
					repaint();
					return false;
				}

			}
			repaint();
			return true;
		}

		private boolean isInteger(String str) {
			for (int i = 0; i < str.length(); i++)
				if (Character.isLetter(str.charAt(i)) || Character.isWhitespace(str.charAt(i)))
					return false;
			return true;
		}

	}

	private JSpinner maxValueSpinner;
	private JComboBox editComboBox;

	class SetPropertiesPanel extends JPanel {

		SetPropertiesPanel() {
			init();
		}

		private void init() {
			setLayout(new GridLayout(5, 5));
			JLabel label = new JLabel("Set maximum value to be present int examples:");
			SpinnerModel value = new SpinnerNumberModel(maxNumbersValue, // initial value
					0, // minimum value
					Integer.MAX_VALUE, // maximum value
					1); // step
			maxValueSpinner = new JSpinner(value);
			add(label);
			add(maxValueSpinner);

			JLabel label1 = new JLabel("Choose the difficulty of the game: ");
			String[] items = { "1", "2", "3", "4" };
			editComboBox = new JComboBox(items);
			editComboBox.setEditable(true);
			add(label1);
			add(editComboBox);
			editComboBox.setVisible(true);
			maxValueSpinner.setVisible(true);
			setVisible(true);

		}

	}

	class ButtonSet implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			maxOperator = editComboBox.getSelectedIndex() + 1;
			maxNumbersValue = (Integer) maxValueSpinner.getValue();
			System.out.println("maxValue " + maxNumbersValue + maxOperator + "-maxOperator");
		}
	}

	class ButtonBackFromSet implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("maxValue " + maxNumbersValue + maxOperator + "-maxOperator");
			removeAll();
			addExamplesToPanel();
		//	buttonsMenu=new ButtonsMenu();
			add(rools,BorderLayout.NORTH);
			add(examplePanel,BorderLayout.CENTER);
			add(buttonsMenu,BorderLayout.SOUTH);
			revalidate();
			

		}
	}

	// /**
	// * Method that generate and draw 10 examples
	// */
	// private void drawExamples(Graphics g) {
	// generate(10, 4, 100);
	//
	// g.setFont(new Font("Robo", 20, 20));
	// int i = 0;
	//
	// for (Map.Entry<String, Integer> x : examples.entrySet()) {
	// g.drawRect(yPosotion - 20, xPosition - delayForTipAnswer, delayForTipAnswer,
	// delayForTipAnswer);
	// g.drawString(x.getKey(), yPosotion, xPosition);
	//
	// // here will be checking for tips in order to know what put
	// // drawdrawCheckMark();
	//
	// xPosition += 40;
	//
	// }
	// }

}
