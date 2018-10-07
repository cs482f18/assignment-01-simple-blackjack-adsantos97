package com.example.arizz.simpleblackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class that represents a Deck.
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */
public class Deck {

    /** Stores Cards to form a Deck. */
    private ArrayList<Card> deck;

    /**
     * Initializes 1 Deck of 52 Cards and shuffles it.
     */
    public Deck() {
        this.deck = new ArrayList<Card>();
        createDeck(1);
        shuffleDeck();
    }

    /**
     * Initializes deck with number of decks used and shuffles it.
     * Can be used for 1 deck of 52 Cards as well.
     * @param numOfDecks number of decks being created/used together
     */
    public Deck(int numOfDecks) {
        this.deck = new ArrayList<Card>();
        createDeck(numOfDecks);
        shuffleDeck();
    }

    /**
     * Creates a Deck based on the number of decks being used.
     * @param numOfDecks number of decks being used
     */
    public void createDeck(int numOfDecks) {
        for (int i = 0; i < numOfDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    this.deck.add(new Card(rank, suit));
                }
            }
        }
    }

    /**
     * Shuffles the Deck utilizing a pre-made shuffle() in java.
     */
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    /**
     * Deals a card from the Deck.
     * @return top Card from the Deck
     */
    public Card dealCardFromDeck() {
        return this.deck.remove(0);
    }

    /**
     * Clears the Deck if needed.
     */
    public void clearDeck() { deck.clear(); }

    /**
     * Outputs image ID's for the card in the deck.
     * It also outputs the number of cards in the deck.
     * Used for testing purposes.
     */
    public void dumpDeckIDs() {
        int i = 0;
        for (Card c : deck) {
            i++;
            System.out.println(c.getImageID());
        }

        System.out.println("Number of Cards in Deck: " + i);
    }
}
