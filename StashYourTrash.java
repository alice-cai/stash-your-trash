/**
* StashYourTrash.java
*
* This class represents a "Stash Your Trash" game, which prompts users to sort
* various types of Tim Hortons trash into the correct bins by clicking on the
* provided buttons.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.function.Consumer;

public class StashYourTrash {
	// Identfiers used for switching between JPanels (or "cards") with CardLayout.''
	public static final String START_PANEL = "card with title, start button, leaderboard button";
	public static final String INSTRUCTION_PANEL = "card with instructions and name prompt";
	public static final String GAME_PANEL = "card with sorting game";
	public static final String LEADERBOARD_PANEL = "card that displays the top 10 players' scores";

	private JFrame mainFrame;
	private CardLayout cardLayout;
	private JPanel cards;
	private StartPanel startPanel;
	private InstructionPanel instructionPanel;
	private GamePanel gamePanel;
	private LeaderboardPanel leaderboardPanel;

	// callbacks
	private Consumer<Player> onGameInitiation;
	private Consumer<Player> onGameCompletion;

	private PlayerManager playerManager;

	public StashYourTrash () {
		playerManager = new PlayerManager();

		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);

		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(700, 700);

		createStartPanel();
		createInstructionPanel();
		createGamePanel();
		createLeaderboardPanel();

		mainFrame.add(cards);
		cardLayout.show(cards, START_PANEL);
		mainFrame.setVisible(true);
	}

	private void createStartPanel () {
		startPanel = new StartPanel(cardLayout, cards);
		cards.add(startPanel, START_PANEL);
	}

	private void createInstructionPanel () {
		onGameInitiation = player -> {
			cardLayout.show(cards, GAME_PANEL);
			gamePanel.startGame(player);
		};

		instructionPanel = new InstructionPanel(mainFrame, onGameInitiation);
		cards.add(instructionPanel, INSTRUCTION_PANEL);
	}

	private void createGamePanel () {
		onGameCompletion = player -> {
			boolean leaderboardUpdated = playerManager.addPlayer(player);
			if (leaderboardUpdated) {
				leaderboardPanel.constructPlayerList();
			}
			cardLayout.show(cards, LEADERBOARD_PANEL);
		};

		gamePanel = new GamePanel(mainFrame, onGameCompletion);
		cards.add(gamePanel, GAME_PANEL);
	}

	private void createLeaderboardPanel () {
		leaderboardPanel = new LeaderboardPanel(cardLayout, cards, playerManager);
		cards.add(leaderboardPanel, LEADERBOARD_PANEL);
	}
}