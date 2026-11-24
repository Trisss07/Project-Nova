package project;

public class Creatures {
    private String name;
    private char symbol;
    private int[][] positions;
    private boolean[] found;

    public Creatures() {
        this.name = "";
        this.symbol = ' ';
        this.positions = new int[0][0];
        this.found = new boolean[0];
    }

    public Creatures(String name, char symbol, int[][] positions) {
        this.name = name;
        this.symbol = symbol;
        this.positions = positions;
        this.found = new boolean[positions.length];
    }

    /**
     * Gets the name of the creature.
     * @return the creature's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the symbol representing the creature on the grid.
     * @return the creature's symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Gets the positions of the creature on the grid.
     * @return a 2D array of coordinates representing the creature's parts
     */
    public int[][] getPositions() {
        return positions;
    }

    /**
     * Checks if the given coordinates match part of the creature.
     * Marks the part as found if it was previously undiscovered.
     * @param latitude the row coordinate
     * @param longitude the column coordinate
     * @return true if the guess reveals a new part of the creature, false otherwise
     */
    public boolean guess(int latitude, int longitude) {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i][0] == latitude && positions[i][1] == longitude) {
                if (!found[i]) {
                    found[i] = true;      
                    return true;          
                }
                return false;             
            }
        }
        return false;                      
    }

    /**
     * Checks if the entire creature has been discovered.
     * @return true if all parts are found, false otherwise
     */
    public boolean isFullyFound() {
        for (boolean f : found) {
            if (!f) return false;
        }
        return true;
    }

    /**
     * Gets the array indicating which parts of the creature have been found.
     * @return a boolean array where true means the part is found
     */
    public boolean[] getFound() {
        return found;
    }
}