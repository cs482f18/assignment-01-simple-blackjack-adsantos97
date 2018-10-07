package com.example.arizz.simpleblackjack;

import java.util.ArrayList;

/**
 * A class that represents a player's Hand of cards.
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */
public class Hand {

    /** Stores Cards to form a Hand */
    private ArrayList<Card> hand;
    /** The max number of card in a Hand */
    private int handMaxSize;

    /**
     * Hand parameterized constructor.
     * @param handMaxSize maximum size of Hand
     */
    public Hand(int handMaxSize) {
        this.hand = new ArrayList<Card>();
        this.handMaxSize = handMaxSize;
    }

    /**
     * Adds a card to the Hand if Hand isn't full.
     * @param dealtCard the card dealt from the deck
     */
    public void addToHand(Card dealtCard) {
        if (!isHandFull()) {
            hand.add(dealtCard);
        }
    }

    /**
     * Checks if the Hand is full.
     * @return true if the number of cards in the Hand
     *         is equal to the maximum size of Hand
     */
    public boolean isHandFull () {
        return hand.size() == handMaxSize;
    }

    /**
     * Determines sum of values of the cards in the Hand.
     * @return sum/total of the Hand
     */
    public int sumHand() {
        int total = 0;
        int numAces = 0;

        for (Card c: this.hand) {

            if (c.getRank() == Rank.ACE) {
                numAces++;
                total += 11;
            } else if (c.getValue() == 10) {
                total += 10;
            } else {
                total += c.getValue();
            }
        }

        while (total > 21 && numAces > 0) {
            total -= 10;
            numAces--;
        }

        return total;
    }

    /**
     * Checks if the Hand contains a natural Blackjack
     * @return true if the first two cards make a Blackjack
     */
    public boolean hasNaturalBlackjack() {
        return (hand.get(0).getValue() + hand.get(1).getValue()) == 21;
    }

    /**
     * Clears/resets the Hand
     */
    public void resetHand() {
        this.hand.clear();
    }

    /**
     * Outputs the cards in the Hand.
     * It also outputs the number of cards in the Hand.
     * Used for testing purposes.
     */
    public void dumpHand() {
        int i = 0;
        for (Card c : hand) {
            i++;
            System.out.println(c + " Value: " + c.getValue());
        }

        System.out.println("Number of Cards in Hand: " + i);
    }
}
