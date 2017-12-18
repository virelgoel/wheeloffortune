/**
 * 
 * Implements the Player feature of the game - Wheel of Fortune.
 * Player is an enumerated type and has methods to start, change player,
 * get and set a players earnings. An array of 3 elements keeps track of
 * all the earnings for each player.
 * 
 *
 */
public enum Player {
    /** Player 1 */
    Player1, 
    /** Player 2 */
    Player2, 
    /** Player 3 */
    Player3;

	/**
	 * This array holds the earnings values for all the players
	 */
	int[]earnings = new int[] {0,0,0};
	
	/**
	 * This method is called at the start of the game and returns
	 * Player 1 to start.
	 * 
	 * @return Player1 
	 */
	public static Player init() {
		return Player1;
	}
	
    /**
     * Switch the player to change turns. If current player is Player1,
     * then switch to Player2. If current player is Player2, then
     * switch to Player3. If current player is Player3, switch to
     * Player1.
     * 
     * @return next player
     */
    public Player next() {
        if (this.equals(Player1)) {
            return Player2;
        } 
        else if (this.equals(Player2)){
            return Player3;
        }
        else {
        	return Player1;
        }
    }
    
    /**
     * Set the earnings for the current player.
     * 
     * @param earning
     */
    public void setEarnings(int earning) {
    	if (this.equals(Player1)) {
    		earnings[0] += earning;
    	}
    	else if (this.equals(Player2)) {
    		earnings[1] += earning;
    	}
    	else {
    		earnings[2] += earning;
    	}
    }
    
    /**
     * Reset the current player's earnings to 0. This will be invoked when
     * the player lands on -$1000 and if taking away $1000 will make their
     * balance negative. By rule, player's minimum earnings should be 0.
     */
    public void resetEarnings() {
    	if (this.equals(Player1)) {
    		earnings[0] = 0;
    	}
    	else if (this.equals(Player2)) {
    		earnings[1] = 0;
    	}
    	else {
    		earnings[2] = 0;
    	}    	
    }
    
    /**
     * Get the earnings for the current player
     * 
     * @return earnings for the current player 
     */
    public int getEarnings() {
        if (this.equals(Player1)) {
            return earnings[0];
        } 
        else if (this.equals(Player2)){
            return earnings[1];
        }
        else {
        	return earnings[2];
        }   	
    }
}