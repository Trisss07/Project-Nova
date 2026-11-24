package project;
import java.util.Scanner;

public class Player {
    Scanner s = new Scanner(System.in);
    private String name;
    private int points;
    private int health;

    public Player() {
        this.name = "";
        this.points = 0;
        this.health = 100;
    }

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.health = 100;
    }

    /**
     * Gets the player's name.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player's current points.
     * @return the points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the player's points.
     * @param points the new points value
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Gets the player's current health.
     * @return the health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the player's health.
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Adds points to the player.
     * Ensures points do not go below zero.
     * @param p the number of points to add
     */
    public void addPoints(int p) {
        points += p;
        if (points < 0) {
            points = 0;
        }
    }

    /**
     * Reduces the player's points by a given amount.
     * Ensures points do not go below zero.
     * @param amount the number of points to subtract
     */
    public void reducePoints(int amount) {
        points -= amount;
        if (points < 0) points = 0;
    }

    /**
     * Reduces the player's health by a given amount.
     * Ensures health does not go below zero.
     * @param amount the amount of health to subtract
     */
    public void reduceHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Applies a booby trap penalty to the player's health.
     * Ensures health does not go below zero.
     * @param amount the amount of health to subtract due to trap
     */
    public void boobyTrap(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Increases the player's health by a given amount.
     * Ensures health does not exceed 100.
     * @param amount the amount of health to add
     */
    public void increaseHealth(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    /**
     * Displays the player's current status (name, points, health).
     */
    public void showStatus() {
        System.out.println(name + "- Points: " + points + ", Health: " + health + "%");
    }
}