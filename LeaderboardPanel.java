/**
* LeaderboardPanel.java
*
* This class is responsible for displaying the current leaderboard for the
* "Stash Your Trash" game.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LeaderboardPanel extends JPanel {
	private final String FILE_NAME = "leaderboard.txt";
	private final String TITLE = "LEADERBOARD";
	private final int NUM_DISPLAY = 10;

	// dimensions of this panel
	private final int PANEL_WIDTH = 700;
	private final int PANEL_HEIGHT = 700;

	// dimensions of title label
	private final int TITLE_LENGTH = PANEL_WIDTH / 5 * 4;
	private final int TITLE_HEIGHT = PANEL_HEIGHT / 7;

	// size of empty space below player list
	private final int EMPTY_HEIGHT = 200;

	private JPanel playerListPanel;
	private JLabel titleLabel;

	private PlayerManager playerManager;

	private CardLayout cardLayout;
	private JPanel cards;
	private Color backgroundColour = new Color(232,232,232);

	public LeaderboardPanel (CardLayout cardLayout, JPanel cards, PlayerManager playerManager) {
		this.playerManager = playerManager;
		this.cardLayout = cardLayout;
		this.cards = cards;
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(backgroundColour);
		createTitleLabel();
		createPlayerListPanel();
		createBackButton();
	}

	private void createTitleLabel () {      
		titleLabel = new JLabel(TITLE);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setPreferredSize(new Dimension(TITLE_LENGTH / 2, TITLE_HEIGHT));
		titleLabel.setForeground(Color.DARK_GRAY);
		titleLabel.setFont(new Font("Optima", Font.BOLD, 55));
		add(titleLabel);
	}

	private void createPlayerListPanel () {
		playerListPanel = new JPanel();
		playerListPanel.setPreferredSize(new Dimension(TITLE_LENGTH, PANEL_HEIGHT - TITLE_HEIGHT - EMPTY_HEIGHT));
		playerListPanel.setLayout(new GridLayout(NUM_DISPLAY, 1));
		constructPlayerList();
		add(playerListPanel);
	}

	public void constructPlayerList () {
		playerListPanel.removeAll();
		int eachHeight = (PANEL_HEIGHT - TITLE_HEIGHT - EMPTY_HEIGHT) / NUM_DISPLAY;

		ArrayList<Player> players = playerManager.getPlayerList();
		for(int i = 0; i < NUM_DISPLAY; i++){
			if(i == players.size()) break;

			Player player = players.get(i);

			JLabel temp = new JLabel();
			temp.setPreferredSize(new Dimension(TITLE_LENGTH, eachHeight));
			temp.setLayout(new BorderLayout());

			temp.setOpaque(true);

			JLabel nameLabel = new JLabel("     " + player.getName());
			nameLabel.setPreferredSize(new Dimension(TITLE_LENGTH / 3 * 2, eachHeight));
			nameLabel.setFont(new Font("Optima", Font.BOLD, 25));
			nameLabel.setForeground(Color.DARK_GRAY);
			temp.add(BorderLayout.WEST, nameLabel);

			JLabel scoreLabel = new JLabel(player.getScore() + "");
			scoreLabel.setPreferredSize(new Dimension(TITLE_LENGTH / 6, eachHeight));
			scoreLabel.setFont(new Font("Optima", Font.BOLD, 25));
			scoreLabel.setForeground(Color.DARK_GRAY);
			scoreLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			temp.add(BorderLayout.EAST, scoreLabel);

			if(i == 0){
				temp.setBackground(new Color (98, 168, 234));
				nameLabel.setForeground(Color.WHITE);
				scoreLabel.setForeground(Color.WHITE);
			} else if (i % 2 == 1){
				temp.setBackground(Color.WHITE);
			} else {
				temp.setBackground(new Color(200, 200, 200));
			}

			playerListPanel.add(temp);
		}
	}

	private void createBackButton () {
		JButton backButton = new JButton("BACK");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ActionListener backButtonHandler = __ -> {
			cardLayout.show(cards, StashYourTrash.START_PANEL);
		};
		backButton.setFont(new Font("Optima", Font.BOLD, 30));
		backButton.setForeground(Color.DARK_GRAY);
		backButton.setBackground(new Color (200, 200, 200));
		backButton.setBorderPainted(false);
		backButton.setOpaque(true);
		backButton.addActionListener(backButtonHandler);
		add(backButton);
	}
}