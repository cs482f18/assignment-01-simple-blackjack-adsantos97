package com.example.arizz.simpleblackjack;

/**
 * A class that represents a simple Blackjack game.
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */
public class SimpleBlackjack {

    private Deck deck;
    private Player player1;
    private Player player2;

    /**
     * Initialize SimpleBlackjack.
     * @param player1Name the player 1's name -- for our game, dealer is first
     * @param player2Name the player 2's name -- for our game, player is second
     * @param numOfDecks number of decks used
     */
    public SimpleBlackjack (String player1Name, String player2Name, int numOfDecks) {
        deck = new Deck(numOfDecks);
        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
    }

    /**
     * Getter for the Deck
     */
    public Deck getDeck() { return this.deck; }

    /**
     * Getter for player 1
     */
    public Player getPlayer1() { return this.player1; }

    /**
     * Getter for player 2
     */
    public Player getPlayer2() { return this.player2; }

    /**
     * Sums a Player's hand
     * @param player the Player
     */
    public int sumPlayerHand(Player player) {
        return player.getHand().sumHand();
    }

    /**
     * Checks if a Player's hand goes "bust" -- over 21
     */
    public boolean isBust(Player player) {
        return sumPlayerHand(player) > 21;
    }

    /**
     * Checks if natural Blackjack on initial cards.
     * @param playerHand Player's Hand
     */
    public boolean checkIfBlackjack(Hand playerHand) {
        return playerHand.hasNaturalBlackjack();
    }
}
