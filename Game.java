package project;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
    private Grid grid1;   // Player 1 grid
    private Grid grid2;   // Player 2 grid
    private Player p1;
    private Player p2;
    private boolean running = true;
    Scanner s = new Scanner(System.in);
    int choice;
    int latitude;
    int longitude;
    int currentTurn = 1;

    /**
     * Starts the game loop and keeps showing the menu until the game ends.
     */
    public void start() {
        while (running) {
            showMenu();
        }
    }

    /**
     * Displays the main menu and handles user input for menu options.
     */
    public void showMenu() {
        System.out.println("\n----------MENU----------");
        System.out.println("1. Start a new game");
        System.out.println("2. Load a previously saved game");
        System.out.println("3. View Help");
        System.out.println("4. Exit");
        System.out.print("\nPlease enter your choice (1-4): ");
        choice = s.nextInt();

        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                loadGame();
                break;
            case 3:
                showHelp();
                break;
            case 4:
                exitGame();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    /**
     * Starts a new game by initialising players, grids, and running the main game loop.
     * Handles player turns, guesses, hints, saving, and quitting.
     */
    public void startNewGame() {
        s.nextLine(); 
        System.out.println("Starting a new game...");

        System.out.print("\nEnter name for Player 1: ");
        String name1 = s.nextLine();
        p1 = new Player(name1);

        System.out.print("\nEnter name for Player 2: ");
        String name2 = s.nextLine();
        p2 = new Player(name2);

        System.out.println("\n" + p1.getName() + " - Points: " + p1.getPoints() + ", Health: " + p1.getHealth() + "%");
        System.out.println(p2.getName() + " - Points: " + p2.getPoints() + ", Health: " + p2.getHealth() + "%");

        // Build two separate 10x10 grids
        grid1 = new Grid(10, 10);
        grid2 = new Grid(10, 10);

        grid1.setCreaturesForPlayer1();
        grid2.setCreaturesForPlayer2();

        int current = 1; // Player 1 starts

        while (true) {
            if (current == 1) {
                grid1.displayBoard("Player 1: " + p1.getName());
                p1.showStatus();

                System.out.print("\nEnter latitude (y-axis) or type hint, save or -1 to quit: ");
                String input1 = s.next();

                if (input1.equalsIgnoreCase("hint")) {
                    grid1.giveHint(p1);       
                    p1.reducePoints(2);         // penalty
                    System.out.print("Now enter latitude (y-axis): ");
                    latitude = s.nextInt();
                } 
                else if (input1.equalsIgnoreCase("save")) {
                    saveGame();
                    continue;
                }
                else {
                    latitude = Integer.parseInt(input1);
                }

                if (latitude == -1) break;

                System.out.print("Enter longitude (x-axis): ");
                longitude = s.nextInt();

                grid1.makeGuess(p1, latitude, longitude);
                p1.reduceHealth(1);

                if (p1.getHealth() <= 0) {
                    System.out.println(p1.getName() + " you have ran out of health!");
                    break;
                }
                if (grid1.allCreaturesFound()) {
                    System.out.println("\nAll creatures found on " + p1.getName() + "'s grid!");
                    grid1.displayBoard("Player 1: " + p1.getName());
                    break;
                }

                current = 2; // switch to Player 2
            } else {
                grid2.displayBoard("Player 2: " + p2.getName());
                p2.showStatus();

                System.out.print("\nEnter latitude (y-axis) or type hint, save or -1 to quit: ");
                String input2 = s.next();

                if (input2.equalsIgnoreCase("hint")) {
                    grid2.giveHint(p2);       
                    p2.reducePoints(2);         // penalty
                    System.out.print("Now enter latitude (y-axis): ");
                    latitude = s.nextInt();
                }  
                else if (input2.equalsIgnoreCase("save")) {
                    saveGame();
                    continue;
                } 
                else {
                    latitude = Integer.parseInt(input2);
                }

                if (latitude == -1) break;

                System.out.print("Enter longitude (x-axis): ");
                longitude = s.nextInt();

                grid2.makeGuess(p2, latitude, longitude);
                p2.reduceHealth(1);

                if (p2.getHealth() <= 0) {
                    System.out.println(p2.getName() + " you have ran out of health!");
                    break;
                }
                if (grid2.allCreaturesFound()) {
                    System.out.println("\nAll creatures found on " + p2.getName() + "'s grid!");
                    grid2.displayBoard("Player 2: " + p2.getName());
                    break;
                }
                current = 1; // switch back to Player 1
            }
        }

        System.out.println("\n----- Game Over -----");
        System.out.println(p1.getName() + "'s Score: " + p1.getPoints());
        System.out.println(p2.getName() + "'s Score: " + p2.getPoints());

        if (p1.getPoints() > p2.getPoints()) {
            System.out.println("Congratulations " + p1.getName() + ", you have won!");
        } else if (p2.getPoints() > p1.getPoints()) {
            System.out.println("Congratulations " + p2.getName() + ", you have won!");
        } else {
            System.out.println("It's a draw!");
        }
    }

        /**
         * Loads a previously saved game from a file.
         * Restores players, grids, and turn order, then resumes the game loop.
         */
        public void loadGame() {
            try {
                System.out.print("Enter filename to load game: ");
                String filename = s.next();

                FileReader fr = new FileReader(filename + ".txt");
                BufferedReader br = new BufferedReader(fr);

                // Load Player 1
                String line = br.readLine();
                String[] p1Data = line.split(":")[1].split(",");
                p1 = new Player(p1Data[0]);
                p1.addPoints(Integer.parseInt(p1Data[1]));
                p1.reduceHealth(100 - Integer.parseInt(p1Data[2]));

                // Load Player 2
                line = br.readLine();
                String[] p2Data = line.split(":")[1].split(",");
                p2 = new Player(p2Data[0]);
                p2.addPoints(Integer.parseInt(p2Data[1]));
                p2.reduceHealth(100 - Integer.parseInt(p2Data[2]));

                // Load turn
                line = br.readLine();
                currentTurn = Integer.parseInt(line.split(":")[1]);

                // Load Grid1
                grid1 = new Grid(10, 10);
                grid1.setCreaturesForPlayer1();
                br.readLine(); // skip "Grid1:"
                for (int i = 0; i < 10; i++) {
                    String row = br.readLine();
                    for (int j = 0; j < 10; j++) {
                        grid1.getBoard()[i][j] = row.charAt(j);
                    }
                }

                // Load Grid2
                grid2 = new Grid(10, 10);
                grid2.setCreaturesForPlayer2();
                br.readLine(); // skip "Grid2:"
                for (int i = 0; i < 10; i++) {
                    String row = br.readLine();
                    for (int j = 0; j < 10; j++) {
                        grid2.getBoard()[i][j] = row.charAt(j);
                    }
                }

                br.close();
                fr.close();
                System.out.println("Game loaded successfully.");

                // Resume game loop
                while (true) {
                    if (currentTurn == 1) {
                        playTurn(p1, grid1);
                        if (p1.getHealth() <= 0 || grid1.allCreaturesFound()) break;
                        currentTurn = 2;
                    } else {
                        playTurn(p2, grid2);
                        if (p2.getHealth() <= 0 || grid2.allCreaturesFound()) break;
                        currentTurn = 1;
                    }
                }

            } catch (Exception e) {
                System.out.println("Error loading game: " + e.getMessage());
            }
        }

        /**
         * Displays instructions on how to play the game.
         */
        public void showHelp() {
            System.out.println("\n----------INSTRUCTIONS TO PLAY----------");
            System.out.println("Two players take turns guessing coordinates (lat, lon) to reveal hidden creatures.");
            System.out.println("Each player has their own 10x10 grid hidden with the same number of creatures.");
            System.out.println("There are five creatures to be found: Starfish (s), Whale (w) , Lobster (l) , Crab (c) and Octopus (o).");
            System.out.println("Earn 5 points for each correct part. Earn +5 bonus when you complete a creature.");
            System.out.println("Health percentage decreases by 1% on each turn.");
            System.out.println("If you use a hint, you lose 2 points but still complete your turn.");
            System.out.println("Chance to win power-ups or mega reveals during the game!");
            System.out.println("Beware of certain traps within the grid!");
            System.out.println("The game ends when a player has found all creatures.");
            System.out.println("The player with the most points wins!!!");
        }

        /**
         * Exits the game and stops the main loop.
         */
        public void exitGame() {
            System.out.println("\nThank you for playing 😊");
            running = false;
        }

        /**
         * Saves the current game state to a file.
         * Stores players, grids, and turn order.
         */
        public void saveGame() {
            try {
                System.out.print("Enter filename to save game: ");
                String filename = s.next();

                FileOutputStream fos = new FileOutputStream(filename + ".txt");
                PrintWriter pw = new PrintWriter(fos);

                // Save Player 1
                pw.println("Player1:" + p1.getName() + "," + p1.getPoints() + "," + p1.getHealth());

                // Save Player 2
                pw.println("Player2:" + p2.getName() + "," + p2.getPoints() + "," + p2.getHealth());

                // Save whose turn it is
                pw.println("NextTurn:" + currentTurn);

                // Save Grid 1
                pw.println("Grid1:");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        pw.print(grid1.getBoard()[i][j]);
                    }
                    pw.println();
                }

                // Save Grid 2
                pw.println("Grid2:");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        pw.print(grid2.getBoard()[i][j]);
                    }
                    pw.println();
                }

                pw.close();
                fos.close();
                System.out.println("Game saved successfully to " + filename + ".txt");
            } catch (Exception e) {
                System.out.println("Error saving game: " + e.getMessage());
            }
        }

        /**
         * Plays a single turn for the given player on their grid.
         * Handles hints, saving, quitting, and making guesses.
         * @param player the current player
         * @param grid the grid belonging to the current player
         */
        private void playTurn(Player player, Grid grid) {
            grid.displayBoard("Player " + currentTurn + ": " + player.getName());
            player.showStatus();

            System.out.print("\nEnter latitude (y-axis) or type 'hint','save','quit': ");
            String input = s.next();

            if (input.equalsIgnoreCase("hint")) {
                grid.giveHint(player);
                player.reducePoints(2);
                System.out.print("Latitude: ");
                latitude = s.nextInt();
            } else if (input.equalsIgnoreCase("save")) {
                saveGame(); // fixed: no parameter
                return;
            } else if (input.equalsIgnoreCase("quit")) {
                return;
            } else {
                latitude = Integer.parseInt(input);
            }

            System.out.print("Longitude: ");
            longitude = s.nextInt();

            grid.makeGuess(player, latitude, longitude);
            player.reduceHealth(1);
        }
    }