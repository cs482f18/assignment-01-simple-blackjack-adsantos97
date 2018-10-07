package com.example.arizz.simpleblackjack;

/**
 * A class that represents a Card.
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */
public class Card {

    /** Rank of this Card. */
    private Rank rank;
    /** Suit of this Card. */
    private Suit suit;
    /** Value of this Card. */
    private int value;
    /** Image ID used for the Card images in Drawable. */
    private String imageID;

    /**
     * Card parameterized constructor.
     * @param rank the rank of the new card
     * @param suit the suit of the new card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = determineValue();
        this.imageID = this.toStringID();
    }

    /**
     * Getter for the Card's rank.
     */
    public Rank getRank() { return this.rank; }

    /**
     * Getter for the Card's value.
     */
    public int getValue() { return this.value; }

    /**
     * Getter for the Card's image ID.
     */
    public String getImageID() { return this.imageID; }

    /**
     * Determines the value of card based on Rank.
     * @return value based on Rank of the card
     */
    public int determineValue() {
        int value = 0;

        switch (rank) {
            case ACE:
                value = 11;
                break;
            case TWO:
                value = 2;
                break;
            case THREE:
                value = 3;
                break;
            case FOUR:
                value = 4;
                break;
            case FIVE:
                value = 5;
                break;
            case SIX:
                value = 6;
                break;
            case SEVEN:
                value = 7;
                break;
            case EIGHT:
                value = 8;
                break;
            case NINE:
                value = 9;
                break;
            case TEN:
                value = 10;
                break;
            case JACK:
                value = 10;
                break;
            case QUEEN:
                value = 10;
                break;
            case KING:
                value = 10;
                break;
        }

        return value;
    }

    /**
     * Creates the image ID for the Card.
     * @return string ID of Card
     */
    public String toStringID() {
        return this.toString().toLowerCase();
    }

    /**
     * Override toString() method.
     * @return string representation of Card
     */
    @Override
    public String toString() {
        return this.rank + "_of_" + this.suit;
    }
}
