/**
* Player.java
*
* This class is responsible for storing information about a single player.
*/

public class Player implements Comparable<Player> {
	private String name;
	private int score;
	private String code;

	public Player (String name) {
		this.name = name;
		score = 0;
		code = "";
	}

	public Player (String name, int score, String code) {
		this.name = name;
		this.score = score;
		this.code = code;
	}

	public String getName () {
		return name;
	}

	public int getScore () {
		return score;
	}

	public String getCode () {
		return code;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setScore (int score) {
		this.score = score;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public int compareTo (Player other) {
		return other.score - score;
	}
}