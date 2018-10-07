package com.example.arizz.simpleblackjack;

/**
 * A class that represents a Player.
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */
public class Player {

    private Hand hand;
    private String name;

    /**
     * Player parameterized constructor.
     * @param name name of player
     */
    public Player(String name) {
        this.hand = new Hand(5);
        this.name = name;
    }

    /** Getter for the Hand */
    public Hand getHand() {
        return this.hand;
    }

    /** Getter for the Hand */
    public String getName() {
        return this.name;
    }

    /**
     * "Hit" method (for player) to ask for another card in an
     * attempt to get closer to a count of or exactly 21.
     * @param d the Deck to hit from
     * @return Image ID used for the Card images
     */
    public String hit(Deck d) {
        Card c = d.dealCardFromDeck();
        this.hand.addToHand(c);
        return c.getImageID();
    }
}
