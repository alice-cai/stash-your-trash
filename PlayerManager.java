/**
* Player.java
*
* This class is responsible for saving, loading, and sorting the players on the
* leaderboard.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerManager {
	private static final String FILE_NAME = "leaderboard.txt";
	private static final int MAX_PLAYERS = 10;
	private ArrayList<Player> playerList;

	public PlayerManager () {
		playerList = new ArrayList<>();
		loadFromFile();
		Collections.sort(playerList);
		removeExcessPlayers();
	}

	public ArrayList<Player> getPlayerList () {
		return playerList;
	}

	/**
	* This method takes in a Player object and checks if the argument Player's score is higher
	* than the worst leaderboard score. If so, the Player gets added to the leaderboard and the
	* worst Player gets removed. If not, this method does nothing.
	*
	* @param newPlayer - object representing the Player that is to be added to the leaderboard
	* @return boolean indicating whether the argument Player was successfully added to the leaderboard
	*/
	public boolean addPlayer (Player newPlayer) {
		if (playerList.size() == MAX_PLAYERS) {
			int lowestScore = playerList.get(MAX_PLAYERS-1).getScore();
			if (newPlayer.getScore() < lowestScore) {
				return false;
			}
		}

		playerList.add(newPlayer);
		Collections.sort(playerList);
		removeExcessPlayers();
		saveToFile();
		return true;
	}

	private void loadFromFile () {
		try{
			BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
			String input;
			while ((input = in.readLine()) != null) {
				String name = input;
				int score = Integer.parseInt(in.readLine());
				String code = in.readLine();
				Player player = new Player(name, score, code);
				playerList.add(player);
				in.readLine();
			}
		} catch (IOException e){
			System.out.println("PlayerManager: Error loading player file.");
		}
	}

	private void saveToFile () {
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(FILE_NAME));
			for (Player player : playerList) {
				out.write(player.getName());
				out.newLine();

				out.write(player.getScore() + "");
				out.newLine();

				out.write(player.getCode());
				out.newLine();

				out.newLine();
			}
			out.close();
		} catch (IOException e){
			System.out.println("PlayerManager: Error writing to player file.");
		}
	}

	public void removeExcessPlayers () {
		if (playerList.size() <= MAX_PLAYERS) {
			return;
		}
		while (playerList.size() > MAX_PLAYERS) {
			playerList.remove(playerList.size() - 1);
		}
	}
}