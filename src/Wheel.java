import java.util.Random;

/**
 * 
 * @class Wheel
 * @file Wheel.java
 * 
 * Implements the wheel feature of the game - Wheel of Fortune.
 * Method to spin the wheel randomly generates a wheel value 
 *
 */
public class Wheel {
	/**
	 * Spin the wheel, get a random value and return the dollar amount, lost turn, or lost turn with money
	 * Value -1000 represents lost a turn and lost $1000
	 * Value 0 represents lost a turn 
	 * 
	 * @return Random dollar amount value on the wheel
	 */
	public int spin() {
		int[] dollarAmounts = new int[] {100, 300, 500, 700, 900, 2000, 3000, 5000, -1000, 0};
		int value;
		
		//Create random class object
		Random random = new Random();
		
		//Randomly generate a number between 1 - 10
		value = random.nextInt(10) + 1;
		
		return dollarAmounts[value-1];
	}
}
