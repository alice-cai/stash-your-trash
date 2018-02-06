/**
* GamePanel.java
*
* This class is responsible for providing the GUI and handling the logic for the
* "Stash Your Trash" game.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.Timer;
import java.util.TimerTask;

import java.util.function.Consumer;

public class GamePanel extends JPanel {
	private static final String CONFIG_FILE = "trash.txt";
	private static final String PAPER = "Paper";
	private static final String RECYCLABLES = "Recyclables";
	private static final String WASTE = "Waste";
	private static final int SECRET_CODE_LENGTH = 6;
	private static final int TIME_LIMIT = 30;

	// points system
	private static final int POINTS = 10;
	private static final int COMBO_POINTS = 5;
	private static final int INCORRECT_PENALTY = 5;

	private Player currentPlayer;

	private SpringLayout layout;
	private Font buttonFont = new Font("Optima", Font.BOLD, 35);
	private Font labelFont = new Font("Optima", Font.BOLD, 35);
	private Color backgroundColour = new Color(232,232,232);

	// fields related to image display
	private Trash[] trashList;
	private Trash currentTrash;
	private JLabel imageLabel;

	// fields related to buttons
	private JButton paperButton;
	private JButton recyclablesButton;
	private JButton wasteButton;
	private ButtonHandler buttonHandler;

	private int score = 0;
	private int comboCounter = 0;
	private JLabel scoreLabel;

	private JLabel timerLabel;
	private Stopwatch stopwatch;

	private JLabel messageLabel1;
	private JLabel messageLabel2;

	private JFrame mainFrame;
	private Consumer<Player> onGameCompletion;

	public GamePanel (JFrame mainFrame, Consumer<Player> onGameCompletion) {
		this.mainFrame = mainFrame;
		this.onGameCompletion = onGameCompletion;
		layout = new SpringLayout();
		setLayout(layout);
		setBackground(backgroundColour);

		createButtons();
		createScoreLabel();
		createTimerLabel();
		loadImages();
		createImageLabel();
		createMessageLabel();
	}

	public void setPlayer (Player player) {
		currentPlayer = player;
	}

	public void startGame (Player player) {
		currentPlayer = player;
		score = 0;
		comboCounter = 0;
		scoreLabel.setText("Score: " + score);
		messageLabel1.setText("Where does this go?");
		messageLabel2.setText("");
		setRandomImage();
		stopwatch = new Stopwatch(TIME_LIMIT);
		stopwatch.setCountdownInterval(TIME_LIMIT);
		stopwatch.startTimer();
	}

	private void loadImages () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(CONFIG_FILE));

			int numImages = Integer.parseInt(in.readLine());
			trashList = new Trash[numImages];

			for (int i = 0; i < numImages; i++) {
				in.readLine();
				trashList[i] = new Trash(in.readLine(), in.readLine());
			}
		} catch (IOException iox) {
			System.out.println("GamePanel: Error loading images from file.");
		}
	}

	private void createImageLabel () {
		imageLabel = new JLabel();
		add(imageLabel);
		layout.putConstraint(SpringLayout.WEST, imageLabel, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, imageLabel, 100, SpringLayout.NORTH, this);
	}

	private void setRandomImage () {
		Trash temp = currentTrash;
		while (currentTrash == temp) {
			int randInt = (int)(Math.random() * trashList.length);
			currentTrash = trashList[randInt];
		}
		imageLabel.setIcon(new ImageIcon(currentTrash.getImageFile()));
	}

	private void createButtons () {
		buttonHandler = new ButtonHandler();

		paperButton = new JButton(PAPER);
		paperButton.addActionListener(buttonHandler);
		paperButton.setFont(buttonFont);
		paperButton.setBackground(new Color (35, 137, 238));
		paperButton.setForeground(Color.WHITE);
		paperButton.setBorderPainted(false);
		paperButton.setOpaque(true);
		paperButton.setPreferredSize(new Dimension(250, 55));
		layout.putConstraint(SpringLayout.WEST, paperButton, 410, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, paperButton, 170, SpringLayout.NORTH, this);
		add(paperButton);

		recyclablesButton = new JButton(RECYCLABLES);
		recyclablesButton.addActionListener(buttonHandler);
		recyclablesButton.setFont(buttonFont);
		recyclablesButton.setBackground(new Color (35, 137, 238));
		recyclablesButton.setForeground(Color.WHITE);
		recyclablesButton.setBorderPainted(false);
		recyclablesButton.setOpaque(true);
		recyclablesButton.setPreferredSize(new Dimension(250, 55));
		layout.putConstraint(SpringLayout.WEST, recyclablesButton, 410, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, recyclablesButton, 270, SpringLayout.NORTH, this);
		add(recyclablesButton);

		wasteButton = new JButton(WASTE);
		wasteButton.addActionListener(buttonHandler);
		wasteButton.setFont(buttonFont);
		wasteButton.setBackground(new Color (103, 53, 54));
		wasteButton.setForeground(Color.WHITE);
		wasteButton.setBorderPainted(false);
		wasteButton.setOpaque(true);
		wasteButton.setPreferredSize(new Dimension(250, 55));
		layout.putConstraint(SpringLayout.WEST, wasteButton, 410, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, wasteButton, 370, SpringLayout.NORTH, this);
		add(wasteButton);
	}

	private void createScoreLabel () {
		score = 0;
		scoreLabel = new JLabel("Score: " + score);
		scoreLabel.setFont(labelFont);
		scoreLabel.setForeground(Color.DARK_GRAY);
		add(scoreLabel);
		layout.putConstraint(SpringLayout.WEST, scoreLabel, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scoreLabel, 20, SpringLayout.NORTH, this);
	}

	private void createTimerLabel () {
		timerLabel = new JLabel();
		timerLabel.setFont(labelFont);
		timerLabel.setForeground(Color.DARK_GRAY);
		add(timerLabel);
		layout.putConstraint(SpringLayout.WEST, timerLabel, 596, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, timerLabel, 20, SpringLayout.NORTH, this);
	}

	private void createMessageLabel () {
		messageLabel1 = new JLabel("Where does this go?");
		messageLabel1.setFont(labelFont);
		messageLabel1.setForeground(Color.DARK_GRAY);
		add(messageLabel1);
		layout.putConstraint(SpringLayout.WEST, messageLabel1, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, messageLabel1, 560, SpringLayout.NORTH, this);

		messageLabel2 = new JLabel("");
		messageLabel2.setFont(labelFont);
		messageLabel2.setForeground(Color.DARK_GRAY);
		add(messageLabel2);
		layout.putConstraint(SpringLayout.WEST, messageLabel2, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, messageLabel2, 610, SpringLayout.NORTH, this);
	}

	private void endGame () {
		String code = generateCode();
		String message = String.format("Time's up, %s!%nYou ended the game with %d points.%n", currentPlayer.getName(), score) + 
			String.format("Your secret code is %s. If you're%nfirst on the leaderboard, show the%ncashier your code to redeem a prize!", code);
		JOptionPane.showMessageDialog(mainFrame, message, "Game over!", JOptionPane.INFORMATION_MESSAGE);
		currentPlayer.setScore(score);
		currentPlayer.setCode(code);
		onGameCompletion.accept(currentPlayer);
	}

	private String generateCode(){
		String secretCode = "";
		for(int i = 0; i < SECRET_CODE_LENGTH; i++){
			secretCode += (char)(48 + Math.random()*43);
		}
		return secretCode;
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			String choice = ((JButton)event.getSource()).getText();
			if (choice.equals(currentTrash.getCategory())) {
				comboCounter++;
				if (comboCounter > 1) {
					int comboPoints = POINTS + COMBO_POINTS * (comboCounter - 1);
					messageLabel1.setText(comboCounter + " correct answers in a row!");
					messageLabel2.setText("You earned " + comboPoints + " points.");
					score += comboPoints;
				} else {
					messageLabel1.setText("Correct! You earned " + POINTS + " points.");
					messageLabel2.setText("");
					score += POINTS;
				}
			} else {
				messageLabel1.setText("Incorrect! That item goes in " + currentTrash.getCategory().toLowerCase() + ".");
				messageLabel2.setText("You lost " + INCORRECT_PENALTY + " points.");
				comboCounter = 0;
				score -= INCORRECT_PENALTY;
			}
			scoreLabel.setText("Score: " + score);
			setRandomImage();
		}
	}

	private class Stopwatch {
	    private Timer timer;
	    private int countdownInterval;
	    private int delay = 1000;
	    private int period = 1000;

	    public Stopwatch (int seconds) {
	        countdownInterval = seconds;
	        timer = new Timer();
	    }

	    public void setCountdownInterval (int seconds) {
	    	countdownInterval = seconds;
	    }

	    public void startTimer () {
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					int currentTime = decrementTime();
					timerLabel.setText(formatTime(currentTime));
				}
	        }, delay, period);
	    }

		private int decrementTime () {
			if (countdownInterval == 1) {
				timerLabel.setText("0:00");
				endGame();
				timer.cancel();
			}
			return countdownInterval--;
		}

		private String formatTime (int timeInSeconds) {
			int minutes = timeInSeconds / 60;
			int seconds = timeInSeconds % 60;

			return String.format("%01d:%02d", minutes, seconds);
		}
	}
}