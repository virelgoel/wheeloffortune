import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @class PuzzleState
 * @file PuzzleState.java
 * 
 * Implements the puzzle feature of the game - Wheel of Fortune.
 * Has methods to handle creation of new puzzle, keeping track of the
 * solved puzzle, player earnings, handling player guesses and switching
 * turns
 *
 */
public class PuzzleState {
	/** stores all available letters */
	private String letters;
	/** stores all the categories and their puzzles as a map, making it easy to read as key-value pairs */
	private Map<Category, List<String>> puzzles;
	/** stores the current puzzle */
	private static String currentPuzzle;
	/** stores the current state of the solved puzzle including _ for unsolved letters */
	private List<Character> solvedPuzzle;
	/** stores the current guess letter */
	private static String currentGuess = "";
	/** stores the number of occurrences of the current guess in the puzzle */
	private int letterOccurrencesInPuzzle;
	/** stores the puzzle categry */
	private String category;
	/** for the current player */
	private Player player;

	 
	/**
	 * Categories for the Wheel of Fortune game
	 *
	 */
	public enum Category {
		/** Category - Songs */
		Songs, 
		/** Category - Places */
		Places, 
		/** Category - Food */
		Food, 
		/** Category - Phrases */
		Phrases, 
		/** Category - Landmarks */
		Landmarks;
		
		/**
		 * Get a random category. For a new puzzle, get a random category
		 * 
		 * @method: getRandom
		 * @return Random category from the above list
		*/
		public static Category getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
	
	/**
	 * Default constructor. Initialize and create a map of puzzle categories and words.
	 * Get a new puzzle and initialize the solved puzzle to start with all letters as _
	 * Start the game with player 1.
	 */
	public PuzzleState() {
		puzzles = new EnumMap<Category, List<String>>(Category.class);
		letters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
		creatPuzzleList();
		
		getNewPuzzle();
		
		solvedPuzzle = new ArrayList<> ();
		
		updateSolvedPuzzle();
		
		this.player = Player.init();
		
		
		letterOccurrencesInPuzzle = 0;
	}
	
	/**
	 * Create a map of all the puzzles along with their categories
	 * Puzzles are stored as a list and 1 puzzle string will be generated
	 * randomly when a new puzzle is requested.
	 */
	private void creatPuzzleList() {		
		puzzles.put(Category.Songs, Arrays.asList("STAIRWAY TO HEAVEN", "WE BELONG TOGETHER", "ROLLING IN THE DEEP", "PLEASE DON'T STOP THE MUSIC"));
		puzzles.put(Category.Places, Arrays.asList("SKI RESORT IN THE MOUNTAINS", "AIR FORCE BASE", "AMUSEMENT PARK", "ADORABLE BOUTIQUE HOTEL"));
		puzzles.put(Category.Food, Arrays.asList("HOMEMADE BISCUITS AND GRAVY", "BOWL OF RAMEN NOODLES", "GIANT SLICE OF APPLE PIE", "ALASKAN KING SALMON FILET"));
		puzzles.put(Category.Phrases, Arrays.asList("A BITTER PILL TO SWALLOW", "PIECE OF CAKE", "BITTER PILL TO SWALLOW", "HARD DAY'S WORK"));
		puzzles.put(Category.Landmarks, Arrays.asList("SHANGHAI WORLD FINANCIAL CENTER", "BROADWAY THEATRE", "GRAND CANYON NATIONAL PARK", "EGYPTIAN PYRAMIDS"));
	}
	
	/**
	 * Generate a random puzzle for the new game
	 */
	public void getNewPuzzle() {		
		Category cat = Category.getRandom();
		List<String> words = puzzles.get(cat);
		
		category = cat.toString();
		currentPuzzle = words.get((int)(Math.random()*(words.size()-1)));
	}
	
	/**
	 * Update the solved puzzle list with guessed characters
	 * Create a new list with the newly guessed characters and merge
	 * with the original solved list
     */
	public void updateSolvedPuzzle() {
		char[] chars = currentPuzzle.toCharArray();
		List<Character> converted = new ArrayList<> ();
		
		char guessed = !currentGuess.isEmpty() ? currentGuess.charAt(0) : Character.MIN_VALUE;
		
		for (char c : chars) {
			if (c == guessed) {
				converted.add(guessed);
			}
			else if (c == '\'') {
				converted.add(c);
			}
			else if (c != ' ') {
				converted.add('_');
			}
			else {
				converted.add(' ');
			}
			
			converted.add(' ');
		}
		
		addLettersToSolvedPuzzle(converted);
	}
	
	/**
	 * Add characters to the solved puzzle
	 * 
	 * @param List  A list newly guessed character to update in the solved puzzle
	 */
	private void addLettersToSolvedPuzzle(List<Character> newList) {
		char c;

		if (solvedPuzzle.isEmpty()) {
			solvedPuzzle = newList;
		}
		else {
			for (int i = 0; i < newList.size(); i++) {
				c = newList.get(i);
				
				if (Character.isLetter(c)) {
					solvedPuzzle.set(i, c);
				}
			}
		}
	}
	
	/**
	 * Get the current state of the solved puzzle
	 * 
	 * @return solved puzzle with _ as a string 
	 */
	public String getSolvedPuzzleAsString() {
		StringBuilder output;
		
		output = new StringBuilder(solvedPuzzle.size());
		 for(Character ch: solvedPuzzle)
		    {
		    	output.append(ch);
		    }
		
		return output.toString();
	}
	
	/**
	 * Set the current guess
	 * 
	 * @param guess  Guessed letter
	 */
	public void setGuess(String guess) {
		currentGuess = guess;
		
		this.updateAvailableLetters();
	}
	
	/**
	 * Get the guessed letter
	 * 
	 * @return guess  Guessed letter 
	 */
	public String getGuess() {
		return currentGuess;
	}
	
	/**
	 * Set the category
	 * 
	 * @param cat  Category
	 */
	public void setCategory(String cat) {
		category = cat;
	}
	
	/**
	 * Get the category
	 * 
	 * @return category as string
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Get the letters available to the player
	 * 
	 * @return Available letters string 
	 */
	public String getAvailableLetters() {		
		return letters;
	}
	
	/**
	 * Find the number of times a substring appears in a given string.
	 * Used to count how many times a guessed letter appears in the puzzle.
	 * 
	 * @return number of times a substring appears in a given string 
	 * @param string  Given string
	 * @param substring  Substring to search for
	 */
	public int count(String string, String substring)
	  {
	     int count = 0;
	     int idx = 0;

	     while ((idx = string.indexOf(substring, idx)) != -1)
	     {
	        idx++;
	        count++;
	     }

	     return count;
	  }
	
	/**
	 * Find if a guessed letter exists in the puzzle. If so,
	 * also update the number of times the letter is found
	 * 
	 * @return  true if the letter exists in the puzzle, false otherwise
	 */
	public boolean letterExists() {
		boolean match = false;

		if (currentPuzzle.contains(currentGuess)) {
			letterOccurrencesInPuzzle = count(currentPuzzle, currentGuess);
			
			match = true;
		}
		
		return match;
	}
	
	/**
	 * Get the number of times a guessed letter appears in a puzzle
	 * 
	 * @return number of occurrences 
	 */
	public int getLetterOccurrences() {
		return letterOccurrencesInPuzzle;
	}
	
	/**
	 * Update the letters available to the player by removing the
	 * current guess from the letters.
	 */
	private void updateAvailableLetters () {
		letters = letters.replace(currentGuess, " ");
	}
	
	/**
	 * Check if the guessed letter is available to the player
	 * 
	 * @return true if the letter is available, false otherwise 
	 * @param guess  Guessed letter
	 */
	public boolean isLetterAvailable(String guess) {
		if (letters.contains(guess.toUpperCase())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check if the puzzle is solved. Puzzle is considered solved when the solve guess matches
	 * the puzzle string or if the current state of the solved puzzle matches the complete puzzle.
	 * The current state will only match if all the letters have been guessed correctly.
	 * 
	 * @return true if the puzzle is solved, false otherwise 
	 */
	public boolean isSolved() {
		boolean result = false;
		
		if (currentPuzzle.equals(currentGuess) || (currentPuzzle.replaceAll("\\s+","").equalsIgnoreCase(getSolvedPuzzleAsString().replaceAll("\\s+","")))) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Get the current player
	 * 
	 * @return Player  Current player
	 */
	 public Player getCurrentPlayer() {
		 return player;
	 }
	 
	 /**
	  * Toggle current player. If current player is player 1, change to player 2
	  * If current player is player 2, change to player 3. If current player is
	  * player 3, change to player 1
	  */
	 public void toggleCurrentPlayer() {
		 this.player = player.next();
	 }
	 
	 /**
	  * Get the earnings for the current player
	  * 
	  * @return earnings
	  */
	 public int getPlayerEarnings() {
		 return player.getEarnings();
	 }
	 
	 /**
	  * Update earnings for the current player after making a correct guess. Earnings are 
	  * calculated by the following formula:
	  * 
	  * base earnings * number of times the guess appears in the puzzle
	  * Example: For $300, if letter H appears 3 times in the puzzle, then the earnings will be 300 * 3 = 900
	  * 
	  * If a player's earnings will become negative after landing on -$1000, reset their earnings back to 0
	  * If a player's earnings will remain positive or 0 after landing on -$1000, take away $1000 from their earnings
	  * 
	  * @param baseEarning  base earnings determined by the wheel value
	  */
	 public void updatePlayerAmt(int baseEarning) {
		 int newEarning;
		 int currentPlayerBalance = getPlayerEarnings();

		 //If wheel value was -1000 and balance will remain positive or 0, take away $1000 from their earnings
		 if (baseEarning < 0 && (currentPlayerBalance + baseEarning >= 0)) {
			 newEarning = baseEarning;
			 player.setEarnings(newEarning);
		 }
		 //If wheel value was -1000 and balance will become negative, reset player earnings to 0
		 else if (baseEarning < 0 && (currentPlayerBalance + baseEarning < 0)){
			 player.resetEarnings(); 
		 }
		 //For all other cases, new earnings should be base earnings * occurrences of guess
		 else {
			 newEarning = baseEarning * letterOccurrencesInPuzzle;
			 player.setEarnings(newEarning);
		 }
	 }
	 
	 /**
	  * Show the winner and their earnings. The player who solves the puzzle is the winner
	  */
	 public void showWinner() {
		 System.out.println("********** Solved puzzle: " + currentPuzzle + " **********");
		 System.out.println("********** " + player + ", YOU WON! Your total earnings are: $" + getPlayerEarnings() + " **********");
	 }
}
