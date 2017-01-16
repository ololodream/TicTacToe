
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("serial")
public class PlayerManager implements Serializable{

	 ArrayList<Player> playerList = new ArrayList<Player>();
	 @SuppressWarnings("unchecked")
	public  boolean load()
	 {
		 try {
			 File file=new File(TicTacToe.FILENAME);
			 if(!file.exists())
				 return true;		
			ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(TicTacToe.FILENAME));
			
			try {
				playerList=(ArrayList<Player>)inputStream.readObject();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inputStream.close();
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	 }
	  public void writeToFile() {
		 
		 try {
			ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(TicTacToe.FILENAME));
			outputStream.writeObject(playerList);
			outputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public PlayerManager() {
		super();
	}

	/**
	 * add a player
	 * 
	 * @param commond
	 * @return if player already exist,return false, otherwise return true.
	 */
	public boolean addPlayer(String[] commond) {
		String userName = commond[1];
		String familyName = commond[2];
		String givenName = commond[3];
		if (findPlayer(userName) == -1) {
			Player player = new HumanPlayer(userName, familyName, givenName);
			playerList.add(player);
			return true;
		}
		return false;
	}
	public boolean addAIPlayer(String[] commond) {
		String userName = commond[1];
		String familyName = commond[2];
		String givenName = commond[3];
		if (findPlayer(userName) == -1) {
			Player player = new AIPlayer(userName, familyName, givenName);
			//Player player = new AdvancedAIPlayer(userName, familyName, givenName);
			playerList.add(player);
			return true;
		}
		return false;
	}

	/**
	 * remove a player
	 * 
	 * @param userName
	 * @return if player dose not exist,return false, otherwise return true.
	 */
	public Player removePlayer(String userName) {
		int index = findPlayer(userName);
		if (index == -1)
			return null;
		return playerList.remove(index);
	}

	/**
	 * remove all players
	 */
	public void removePlayer() {
		playerList.clear();
	}

	/**
	 * edit a player according to its name
	 * 
	 * @param commond
	 * @return if player does not exist,return false, otherwise return true.
	 */
	public boolean editPlayer(String[] commond) {
		String userName = commond[1];
		int index = findPlayer(userName);
		if (index == -1) {
			return false;
		}
		String newFamilyName = commond[2];
		String newGivenName = commond[3];
		playerList.get(index).setFamilyName(newFamilyName);
		playerList.get(index).setGivenName(newGivenName);
		return true;
	}

	/**
	 * reset states of a player
	 * @param commond
	 * @return if player not exist,return false, otherwise return true.
	 */
	public boolean resetStats(String commond) {
		int index = findPlayer(commond);
		if (index == -1) {
			return false;
		}
		playerList.get(index).initGameDrawn();
		playerList.get(index).initGameWon();
		playerList.get(index).initGamePlayed();
		return true;
	}

/**
 * reset all players' states
 */
	public void resetStats() {
		for (int i = 0; i < playerList.size(); i++) {
			resetStats(playerList.get
					
					(i).getUserName());
		}
		
	}
	

/**
 * display information of a player
 * @param userName
 * @return if player not exist,return false, otherwise return true.
 */
	public boolean displayPlayer(String userName) {
		int index = findPlayer(userName);
		if (index == -1)
			return false;
		playerList.get(index).display();
		return true;
	}
/**
 * display information for all players,order by username alphabetically
 */
	public void displayPlayer() {
		Collections.sort(playerList, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return o1.getUserName().compareTo(o2.getUserName());
			}
		});

		for (Player player : playerList) {
			player.display();
		}
	}
/**
 * display the top-10 players ranked based on win-ratio,draw-ratio and username
 */
	public void displayRankings() {
		int i;
		
		Collections.sort(playerList, new Comparator<Player>() {

			@Override
			public int compare(Player playerO, Player playerX) {

				double winRatio1 = playerO.getWonRate();
				double winRatio2 = playerX.getWonRate();
				//order by win ratio
				if (winRatio1 > winRatio2) {
					return -1;
				}
				if (winRatio1 < winRatio2) {
					return 1;
				}
				//if win ratios are the same, order by draw ratio
				double drawRatio1 = playerO.getDrawRate();
				double drawRatio2 = playerX.getDrawRate();
				if (drawRatio1 > drawRatio2) {
					return -1;
				}
				if (drawRatio1 < drawRatio2) {
					return 1;
				}
				//if both win ratio and draw ratio are equal , order by username
				return playerO.getUserName().compareTo(playerX.getUserName());
			}

		});
		System.out.println(" WIN  | DRAW | GAME | USERNAME");
		for (i = 0; i < 10 && i < playerList.size(); i++) {
			playerList.get(i).displayFomat();
		}
	}
/**
 * find a player by username
 * @param userName
 * @return the index of the player,if player doest not exist , return -1;
 */
	public int findPlayer(String userName) {
		
		//Player player = new Player(userName);
		int index=0;
		Player player = new HumanPlayer(userName);
		 
		index= playerList.indexOf(player);
		if(index>0)
			return index;
		
		player= new AIPlayer(userName);
		index=playerList.indexOf(player);
		
		return index;
	}

}
