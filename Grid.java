package project;

public class Grid {
    private char[][] board;
    private int rows;
    private int cols;
    private Creatures[] creature;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Displays the current state of the game board with row and column headers.
     * @param title the title to display above the board
     */
    public void displayBoard(String title) {
        System.out.println("\n\n" + title);

        // Column headers
        System.out.print("\n    ");
        for (int j = 0; j < cols; j++) {
            System.out.print(" " + j + "  ");
        }
        System.out.println();

        // Top border
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print("+———");
        }
        System.out.println("+");

        // Rows
        for (int i = 0; i < rows; i++) {
            System.out.print(" " + i + "  "); // Row label
            for (int j = 0; j < cols; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");

            // Row separator
            System.out.print("    ");
            for (int j = 0; j < cols; j++) {
                System.out.print("+———");
            }
            System.out.println("+");
        }
    }

    /**
     * Initialises the creatures for Player 1 with predefined positions.
     */
    public void setCreaturesForPlayer1() {
        creature = new Creatures[] {
            new Creatures("Starfish", 'S', new int[][] {
                {1,0}, {1,1}, {1,2}, {0,1}, {2,1}
            }),
            new Creatures("Whale", 'W', new int[][] {
                {4,2}, {5,2}, {6,2}, {7,2}
            }),
            new Creatures("Lobster", 'L', new int[][] {
                {3,5}, {3,6}, {3,7}
            }),
            new Creatures("Crab", 'C', new int[][] {
                {9,9}
            }),
            new Creatures("Octopus", 'O', new int[][] {
                {6,4}, {6,5}, {7,4}, {7,5}
            })
        };
    }

    /**
     * Initialises the creatures for Player 2 with predefined positions.
     */
    public void setCreaturesForPlayer2() {
        creature = new Creatures[] {
            new Creatures("Starfish", 'S', new int[][] {
                {5,3}, {5,4}, {5,5}, {4,4}, {6,4}
            }),
            new Creatures("Whale", 'W', new int[][] {
                {1,8}, {2,8}, {3,8}, {4,8}
            }),
            new Creatures("Lobster", 'L', new int[][] {
                {9,0}, {9,1}, {9,2}
            }),
            new Creatures("Crab", 'C', new int[][] {
                {8,7}
            }),
            new Creatures("Octopus", 'O', new int[][] {
                {2,1}, {2,2}, {3,1}, {3,2}
            })
        };
    }

    /**
     * Processes a player's guess at given coordinates.
     * Awards points for hits, applies penalties for traps, and bonuses for power-ups.
     * @param player the player making the guess
     * @param latitude the row coordinate
     * @param longitude the column coordinate
     * @return true if the guess hit a creature part or triggered a special cell, false otherwise
     */
    public boolean makeGuess(Player player, int latitude, int longitude) {
        if (latitude < 0 || latitude >= rows || longitude < 0 || longitude >= cols) {
            System.out.println("Out of bounds.");
            return false;
        }

        // Power-up cells
        if ((latitude == 9 && longitude == 7) || (latitude == 2 && longitude == 6) || (latitude == 5 && longitude == 9)) {
            player.increaseHealth(10);
            System.out.println("Power-up! " + player.getName() + " you gained +10% health.");
            return true;
        }

        // Booby trap cells
        if ((latitude == 0 && longitude == 9) || (latitude == 8 && longitude == 2) || (latitude == 1 && longitude == 6)) {
            player.boobyTrap(10);
            System.out.println("Uh oh " + player.getName() + "! You accidentally set off a bomb and lost 10% health.");
            return true;
        }

        boolean hit = false;
        for (int i = 0; i < creature.length; i++) {
            if (creature[i].guess(latitude, longitude)) {
                board[latitude][longitude] = creature[i].getSymbol(); 
                player.addPoints(5); 
                System.out.println("Hit! Found part of " + creature[i].getName() + " (+5 points)");
                if (creature[i].isFullyFound()) {
                    player.addPoints(5); 
                    System.out.println("Fully discovered " + creature[i].getName() + " (+5 bonus)");
                }
                hit = true;
                break;
            }
        }

        if (!hit) {
            System.out.println("Miss. No creature part at [" + latitude + "," + longitude + "].");
        }
        return hit;
    }

    /**
     * Checks if all creatures on the grid have been fully discovered.
     * @return true if all creatures are found, false otherwise
     */
    public boolean allCreaturesFound() {
        for (int i = 0; i < creature.length; i++) {
            if (!creature[i].isFullyFound()) return false;
        }
        return true;
    }

    /**
     * Provides a hint to the player by revealing the coordinates of an undiscovered creature part.
     * Deducts points for using a hint.
     * @param player the player requesting the hint
     */
    public void giveHint(Player player) {
        for (Creatures c : creature) {
            int[][] pos = c.getPositions();
            boolean[] found = c.getFound();
            for (int i = 0; i < pos.length; i++) {
                if (!found[i]) {
                    System.out.println("Hint for " + player.getName() +
                        ": Try near (" + pos[i][0] + "," + pos[i][1] + ")");
                    System.out.println(player.getName() + " lost 2 points for using a hint.");
                    return;
                }
            }
        }
        System.out.println("No hints available — all creatures already found!");
    }

    /**
     * Returns the current game board.
     * @return the 2D character array representing the board
     */
    public char[][] getBoard() {
        return board;
    }
}