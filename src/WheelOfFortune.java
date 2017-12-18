import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * @class WheelOfFortune
 * @file WheelOfFortune.java
 * 
 * This is the main class for the game - Wheel of Fortune.
 * It has methods to draw the puzzle board, get input from the players
 * and run the game.
 *
 */
public class WheelOfFortune {
	/** For user input */
	private static Scanner input = new Scanner(System.in);
	/** For a new puzzle */
	private static PuzzleState puzzleState = null;
	/** To check if the game is over */
	private static boolean isGameOver = false;
	/** To ask the player to spin or guess the puzzle */
	private static final String ASKFORCHOICE = "Would you like to Spin (1) or Guess (2) the puzzle? ";
	
	/**
	 * Get a choice from the player, whether to spin the wheel or solve the puzzle.
	 * Only allow a player to enter 1 or 2 as valid inputs.
	 * 
	 * @return player's choice as 1 - spin or 2 - guess 
	 * @param player  current player
	 */
	private int getChoice(Player player) {
		int out = 0;
	    System.out.print(player + " – " + ASKFORCHOICE);

	    while(true){
	        try {
	        	
	            out = Integer.parseInt(input.nextLine());
	            
	            if (out != 1 && out != 2)
	            	throw new Exception("Error - You did not enter a valid choice!");
	            
	            return out;
	            
	        } catch(NumberFormatException e) {
	            System.out.print("Error: You did not enter an integer. Try again!\n" + ASKFORCHOICE);
	        } catch(Exception e) {
	        	System.out.print(e.getMessage() + "\nValid choices are - Spin(1) or Guess(2)\n" + ASKFORCHOICE);
	        }
	    }
	}
	
	/**
	 * Get a guess from the player when they decide to spin the wheel and land on a valid
	 * dollar value, or guess to solve the puzzle
	 * 
	 * @return String  Either a single character string or the puzzle phrase as the guess.
	 * @param message  Message to display to the player
	 */
	private String getPlayerGuess(String message) {
		String guess = "";
		String regx = "^[\\p{L} .'-]+$";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		
		System.out.print(message);
		
		while(true) {
			try {
				guess = input.nextLine();
				if (!pattern.matcher(guess).find())
					throw new Exception("Error - Your guess did not have all letters or a phrase. Please try again!");
				if (!puzzleState.isLetterAvailable(guess) && (guess.length() == 1))
					throw new Exception(guess.toUpperCase() + " is not an available letter. Please try again!");
				return guess.toUpperCase();
			} catch(Exception e) {
				System.out.print(e.getMessage() + "\n" + message);
			}
		}
	}
	
	/**
	 * Draw the current puzzle board. This will keep getting updated after every guess
	 */
	private void drawBoard() {
		System.out.println("Available letters - " + puzzleState.getAvailableLetters());
		System.out.println("Here is the puzzle (" + puzzleState.getCategory() + "):");
		System.out.println(puzzleState.getSolvedPuzzleAsString() + "\n");
	}
	
	/**
	 * Main method to run the wheel of fortune game. Keep playing the game until the puzzle is
	 * solved.
	 */
	public static void main(String[] args) {
		int choice;              //Represents the player's choice to spin or guess the puzzleState
		int wheelValue;		     //Represents the value on the wheel after spinning
		
		WheelOfFortune wof = new WheelOfFortune();
		Wheel wheel = new Wheel();
		
		puzzleState = new PuzzleState();
		
		System.out.println("Welcome to the Wheel of Fortune");
		
		while(!isGameOver) {
			
			wof.drawBoard();
			
			choice = wof.getChoice(puzzleState.getCurrentPlayer());
			
			if (choice == 1) {
				wheelValue = wheel.spin();
				
				if (wheelValue == 0) {
					
					System.out.println("\nYou landed on 'Lose Your Turn'!\n");
					
					puzzleState.toggleCurrentPlayer();
				}
				else if (wheelValue == -1000) {					
					System.out.println("\nYou landed on 'Lose Your Turn and $1000'!");
					
					System.out.println("You lose your turn and $1000\n");

					puzzleState.updatePlayerAmt(wheelValue);
					
					puzzleState.toggleCurrentPlayer();
				}
				else {
					System.out.println("You landed on $" + wheelValue + ".");
										
					puzzleState.setGuess(wof.getPlayerGuess("Select your letter from the available letters from above: "));
					
					if (puzzleState.letterExists()) {
						System.out.println("\n" + puzzleState.getGuess() + " occurs " + puzzleState.getLetterOccurrences() + " time(s)!\n");
						
						puzzleState.updateSolvedPuzzle();
						
						puzzleState.updatePlayerAmt(wheelValue);
												
						if (puzzleState.isSolved()) {
							System.out.println(puzzleState.getSolvedPuzzleAsString() + "\n");
							System.out.println("You solved the puzzle!");
							isGameOver = true;
						}
					}
					
					else {
						System.out.println("\n" + puzzleState.getGuess() + " is incorrect!\n");
						
						puzzleState.toggleCurrentPlayer();
					}
				}
			}
			else {
				puzzleState.setGuess(wof.getPlayerGuess("Guess the answer: "));
				
				if (puzzleState.isSolved()) {
					System.out.println("\n" + puzzleState.getGuess() + " is correct! You solved the puzzle!\n");
					isGameOver = true;
				}
				else {
					System.out.println("\n" + puzzleState.getGuess() + " is incorrect!\n");
					
					puzzleState.toggleCurrentPlayer();
				}
			}
		}
		
		puzzleState.showWinner();
	}
}