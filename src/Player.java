import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Player implements Serializable {

	private String userName;
	private String familyName;
	private String givenName;
	private int gamePlayed;
	private int gameWon;
	private int gameDrawn;
	
	//abstract method
	public abstract Move makeMove(char[][] grid);

	public Player() {
		super();
	}

	public Player(String userName, String familyName, String givenName) {
		super();
		this.userName = userName;
		this.familyName = familyName;
		this.givenName = givenName;
		this.gamePlayed = 0;
		this.gameWon = 0;
		this.gameDrawn = 0;
	}

	public Player(Player player) {
		super();
		this.userName = player.getUserName();
		this.familyName = player.getFamilyName();
		this.givenName = player.getGivenName();
		this.gamePlayed = player.getGamePlayed();
		this.gameWon = player.getGameOwn();
		this.gameDrawn = player.getGameDrawn();
	}

	public Player(String username) {
		this.userName = username;
	}

	public String getUserName() {
		return userName;
	}

	/*
	 * public void setUserName(String userName) { this.userName = userName; }
	 */

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public int getGamePlayed() {
		return gamePlayed;
	}

	public void incriseGamePlayed() {
		this.gamePlayed++;
	}

	public int getGameWon() {
		return gameWon;
	}

	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	public void setGamePlayed(int gamePlayed) {
		this.gamePlayed = gamePlayed;
	}

	public void setGameDrawn(int gameDrawn) {
		this.gameDrawn = gameDrawn;
	}

	public int getGameOwn() {
		return gameWon;
	}

	public void incriseGameWon() {
		this.gameWon++;
	}

	public int getGameDrawn() {
		return gameDrawn;
	}

	public double getWonRate() {
		// TODO Auto-generated method stub

		if (gamePlayed == 0)
			return 0;

		return (double) getGameOwn() / getGamePlayed();
	}

	public double getDrawRate() {
		// TODO Auto-generated method stub

		if (gamePlayed == 0)
			return 0;

		return (double) getGameDrawn() / getGamePlayed();
	}

	public void incriseGameDrawn() {
		this.gameDrawn++;
	}

	public void initGamePlayed() {
		this.gamePlayed = 0;
	}

	public void initGameWon() {
		this.gameWon = 0;
	}

	public void initGameDrawn() {
		this.gameDrawn = 0;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Player) obj).getUserName().equals(this.getUserName());
	}

	public void display() {
		// show information for a player
		System.out.print(this.getUserName() + ',' + this.getFamilyName() + ',' + this.getGivenName() + ',');
		System.out.print(this.getGamePlayed() + " games,");
		System.out.print(this.getGameOwn() + " wins,");
		System.out.print(this.getGameDrawn() + " draws");
		System.out.println("");

	}

	public void displayFomat() {
		// show information for a player
		System.out.printf(" %3d%% | %3d%% | %2d   | %s\n", (int) Math.round(getWonRate() * 100),
				(int) Math.round(getDrawRate() * 100), gamePlayed, userName);
	}

}
