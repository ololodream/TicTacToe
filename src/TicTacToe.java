
/* Class Name : TicTacToe
 * Author : Mengjia Chen (Student Number: 747254)
 * Date : 27th May 2016
 * Description : Write a program named TicTacToe.
 * version: 1.0
 * */
import java.util.Scanner;

public class TicTacToe {
	private final int argOfRvm = 2;
	private final int argOfReset = 2;
	private final int argOfDisp = 2;
	private final int argOfPlayGame = 3;
	private final int argOfAdd = 4;
	private final int argOfAddAi = 4;
	private final int argOfEdit = 4;

	private PlayerManager playerManager = new PlayerManager();
	private GameManager gameManager = new GameManager();
	private final String errorInf = "The player does not exist.";
	private final int NOTEXIST = -1;
	public static Scanner keyboard = new Scanner(System.in);
	public static final String FILENAME = "player.dat";

	// list of command
	enum commandType {
		addplayer, addaiplayer, removeplayer, editplayer, resetstats, displayplayer, rankings, playgame, exit
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TicTacToe gameSystem = new TicTacToe();
		gameSystem.run();

	}

	private void printErrorInf() {

		System.out.println(errorInf);
	}

	private void removeplayerCmd(String[] commond) throws incorrectArgumetException {
		// decide remove all players or a specific player
		if (commond.length == 1) {
			// if there is no argument,remove all players
			System.out.println("Are you sure you want to remove all players? (y/n)");
			if (keyboard.next().equals("y")) {
				playerManager.removePlayer();
			}
		} else {
			// remove one player
			if (commond.length != argOfRvm)
				throw new incorrectArgumetException();
			if (playerManager.removePlayer(commond[1]) == null) {
				System.out.println(errorInf);
			}
		}
	}

	private void resetstatsCmd(String[] commond) throws incorrectArgumetException {
		// decide reset all players' or a specific player's statistic
		if (commond.length == 1) {
			System.out.println("Are you sure you want to reset all player statistics? (y/n)");
			if (keyboard.next().equals("y")) {
				playerManager.resetStats();
			}
		} else {
			if (commond.length != argOfReset)
				throw new incorrectArgumetException();
			if (playerManager.resetStats(commond[1])) {
				printErrorInf();
			}
		}
	}

	private void displayplayerCmd(String[] commond) throws incorrectArgumetException {
		// decide display all players' or a specific player's information
		if (commond.length == 1) {
			playerManager.displayPlayer();
		} else {
			if (commond.length != argOfDisp)
				throw new incorrectArgumetException();

			if (!playerManager.displayPlayer(commond[1])) {
				printErrorInf();
			}
		}
	}

	private void playgameCmd(String[] commond) throws incorrectArgumetException {
		// find two players to play a single game
		if (commond.length != argOfPlayGame)
			throw new incorrectArgumetException();

		int playerOId, playerXId;
		playerOId = playerManager.findPlayer(commond[1]);
		playerXId = playerManager.findPlayer(commond[2]);
		if (playerOId == NOTEXIST || playerXId == NOTEXIST)
			// if player does not exist, show error massage
			System.out.println("Player does not exist.");
		else {
			Player playerO = playerManager.playerList.get(playerOId);
			Player playerX = playerManager.playerList.get(playerXId);

			gameManager.playGame(playerO, playerX);
		}
	}

	public void addplayerCmd(String[] commond) throws incorrectArgumetException {
		if (commond.length != argOfAdd)
			throw new incorrectArgumetException();
		if (!playerManager.addPlayer(commond)) {
			System.out.println("The username has been used already.");
		}
	}

	public void addaiplayerCmd(String[] commond) throws incorrectArgumetException {
		if (commond.length != argOfAddAi)
			throw new incorrectArgumetException();
		if (!playerManager.addAIPlayer(commond)) {
			System.out.println("The username has been used already.");
		}
	}

	public void editPlayer(String[] commond) throws incorrectArgumetException {
		if (commond.length != argOfEdit)
			throw new incorrectArgumetException();
		if (!playerManager.editPlayer(commond)) {
			printErrorInf();
		}
	}

	public void exitCmd(String[] commond) throws incorrectArgumetException {
		if (commond.length != 1)
			throw new incorrectArgumetException();

		System.out.println("");
		playerManager.writeToFile();
		// System.out.println("fileUpdate");
		System.exit(0);

	}

	/**
	 * run TicTacToe
	 * 
	 * @throws Exception
	 * 
	 */
	public void run() {
		String line;
		
		
		commandType operator;
		System.out.println("Welcome to Tic Tac Toe!");
		playerManager.load();
		System.out.println("");
		while (true) {
			// running a system, until exit command is executed
			System.out.print(">");
			line = keyboard.nextLine();
			while (line.equals("")) {
				line = keyboard.nextLine();
			}
			String[] commond = line.split(" +|,");
			try {
				operator = commandType.valueOf(commond[0]);

				switch (operator) {
				// judge which operation should be called
				case addplayer: {
					addplayerCmd(commond);

				}
					;
					break;
				case addaiplayer: {
					addaiplayerCmd(commond);

				}
					;
					break;

				case removeplayer: {
					removeplayerCmd(commond);
				}
					;
					break;
				case editplayer: {
					editPlayer(commond);
				}
					;
					break;
				case resetstats: {
					resetstatsCmd(commond);
				}
					;
					break;
				case displayplayer: {
					displayplayerCmd(commond);
				}
					;
					break;
				case rankings: {
					playerManager.displayRankings();
				}
					;
					break;
				case playgame: {
					playgameCmd(commond);
				}
					;
					break;
				case exit: {
					exitCmd(commond);
				}
					;
					break;

				default: {

				}
				}
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("'" + commond[0] + "' " + "is not a valid command.");
			} catch (incorrectArgumetException e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			System.out.println();
		}

	}
}
