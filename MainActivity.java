package com.example.arizz.simpleblackjack;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is the Controller.
 * If lowercase "player" is used, it is referring to the instance of player;
 * otherwise, uppercase "Player" is referring to the class Player
 * @author Arizza Santos
 * @version 1.0 10/4/2018
 */

public class MainActivity extends AppCompatActivity{

    /** Tag for Log; used for testing in LogCat. */
    public final static String MA = "MainActivity";

    /** Instance of a game of Simple Blackjack. */
    private SimpleBlackjack game;

    /** ImageViews for the cards on screen. */
    private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5;
    private ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5;

    /** TextViews for displaying text. */
    private TextView playerSumText, dealerSumText, resultText;

    /** player buttons*/
    private Button hit, stand;

    /** A game Deck. */
    private Deck deck;

    /** The initial drawn cards. */
    private Card c1, c2, c3, c4;

    /** Players in the game. */
    private Player player, dealer;

    /** Number of times the Hit button was clicked. */
    private int hitCount;

    /** Number of times dealer hit. */
    private int dealerHitCount;

    /** Maximum number of Hits. */
    private static final int MAX_HITS = 3;

    /** Called when the activity is created
     * @param savedInstanceState the activity's previously frozen state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // using two decks
        game = new SimpleBlackjack("Dealer", "Player", 2);
        Configuration config = getResources().getConfiguration();
        modifyLayout(config);

        gameSetUp();
    }

    /**
     * Checks the orientation when the user rotates the device
     * @param newConfig Configuration object that specifies the new
     *                  device configuration
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(MA, "Height: " + newConfig.screenHeightDp);
        Log.w(MA, "Width: " + newConfig.screenWidthDp);

        /** Checks the device orientation using Logcat */
        Log.w(MA, "Orientation: " + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.w(MA, "Horizontal position.");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.w(MA, "Portrait position.");
        } else {
            Log.w(MA, "Undetermined position.");
        }

        modifyLayout(newConfig);
    }

    /**
     * Tests what the orientation of the device is, and sets the
     * layout for it, inflating the approximate XML file.
     * @param newConfig Configuration object that specifies the new
     *                  device configuration
     */
    public void modifyLayout(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }
    }

    /**
     * Setup for the beginning of the Game.
     */
    public void gameSetUp() {
        Log.w(MA, "In gameSetUp()");

        /** Set up hit button */
        stand = (Button) findViewById(R.id.stand_button);
        hit = (Button) findViewById(R.id.hit_button);
        ButtonHandler bh_hit = new ButtonHandler();
        hit.setOnClickListener(bh_hit);
        hitCount = 0;

        /** Player's Cards setup */
        playerCard1 = (ImageView) findViewById(R.id.player_first_card);
        playerCard2 = (ImageView) findViewById(R.id.player_second_card);
        playerCard3 = (ImageView) findViewById(R.id.player_third_card);
        playerCard4 = (ImageView) findViewById(R.id.player_fourth_card);
        playerCard5 = (ImageView) findViewById(R.id.player_fifth_card);

        playerSumText = (TextView) findViewById(R.id.player_hand_sum);

        /** Dealer's Cards setup */
        dealerCard1 = (ImageView) findViewById(R.id.dealer_first_card);
        dealerCard2 = (ImageView) findViewById(R.id.dealer_second_card);
        dealerCard3 = (ImageView) findViewById(R.id.dealer_third_card);
        dealerCard4 = (ImageView) findViewById(R.id.dealer_fourth_card);
        dealerCard5 = (ImageView) findViewById(R.id.dealer_fifth_card);

        dealerSumText = (TextView) findViewById(R.id.dealer_hand_sum);

        /** Set up result text */
        resultText = (TextView) findViewById(R.id.result);

        /** Create the Deck */
        deck = game.getDeck();
        player = game.getPlayer2();
        dealer = game.getPlayer1();

        /** The first four cards dealt from the Deck */
        c1 = deck.dealCardFromDeck();
        c2 = deck.dealCardFromDeck();
        c3 = deck.dealCardFromDeck();
        c4 = deck.dealCardFromDeck();

        player.getHand().addToHand(c1);
        dealer.getHand().addToHand(c2);
        player.getHand().addToHand(c3);
        dealer.getHand().addToHand(c4);

        setCardImage(c1.getImageID(), playerCard1);
        setCardImage(c2.getImageID(), dealerCard1);
        setCardImage(c3.getImageID(), playerCard2);

        updatePlayerSum(player, playerSumText);
        if (game.checkIfBlackjack(player.getHand())) {
            setCardImage(c4.getImageID(), dealerCard2);
            updatePlayerSum(dealer, dealerSumText);
            hit.setEnabled(false);
            stand.setEnabled(false);
            resultText.setText(R.string.blackjack_winner);
            determineWinner();
        }
    }

    /**
     * Sums a player's hand and updates the View accordingly
     */
    public void updatePlayerSum(Player p, TextView tv) {
        int sum = game.sumPlayerHand(p);
        Resources res = getResources();
        String sumString = res.getString(R.string.current_sum, sum);
        tv.setText(sumString);
    }

    /**
     * Sets the Card's image to the chosen Image View ID
     * @param card string that represents the card
     * @param imageView the ImageView to show the Card
     */
    public void setCardImage(String card, ImageView imageView) {
        int imageID = getResources().getIdentifier(card, "drawable", getPackageName());
        imageView.setImageResource(imageID);
        imageView.setAlpha(255);
    }

    /**
     * Determines which player card to update based on numHit
     * @param numHits the number times the player clicks on "HIT";
     *                the max number of hits is 3
     * @return the player card to be changed
     */
    public ImageView whichPlayerCard(int numHits) {
        if (numHits == 1) {
            return playerCard3;
        } else if (numHits == 2) {
            return playerCard4;
        } else {
            return playerCard5;
        }
    }

    /**
     * Button Handler used by the Hit Button
     */
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            if (hitCount > MAX_HITS) {
                hit.setEnabled(false); // disable HIT button
                stand.setEnabled(false); // disable STAND button
                Card c;
                setCardImage(c4.getImageID(), dealerCard2);
                updatePlayerSum(dealer, dealerSumText);

                while (dealer.getHand().sumHand() < 17 && !game.isBust(dealer) ) {
                    if (dealerHitCount == 1) {
                        c = deck.dealCardFromDeck();
                        dealer.getHand().addToHand(c);
                        setCardImage(c.getImageID(), dealerCard3);
                        updatePlayerSum(dealer, dealerSumText);
                    } else if (dealerHitCount == 2) {
                        c = deck.dealCardFromDeck();
                        dealer.getHand().addToHand(c);
                        setCardImage(c.getImageID(), dealerCard4);
                        updatePlayerSum(dealer, dealerSumText);
                    } else if (dealerHitCount == 3) {
                        c = deck.dealCardFromDeck();
                        dealer.getHand().addToHand(c);
                        setCardImage(c.getImageID(), dealerCard5);
                        updatePlayerSum(dealer, dealerSumText);
                    }

                    dealerHitCount++;
                }

                determineWinner();

            } else if (game.isBust(dealer) || hitCount <= MAX_HITS) {
                hitCount++;
                String imageID = player.hit(deck);
                setCardImage(imageID, whichPlayerCard(hitCount));
                updatePlayerSum(player, playerSumText);
                if (game.isBust(player)) {
                    setCardImage(c4.getImageID(), dealerCard2);
                    updatePlayerSum(dealer, dealerSumText);
                    resultText.setText(R.string.bust_text);
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                }
            }
        }
    }

    /**
     * player's turn is over and dealer takes its turn.
     * Used when player clicks the STAND button.
     */
    public void stand(View view) {
        Card c;

        hit.setEnabled(false);
        stand.setEnabled(false);
        setCardImage(c4.getImageID(), dealerCard2);
        updatePlayerSum(dealer, dealerSumText);

        while (dealer.getHand().sumHand() < 17 && !game.isBust(dealer) ) {
            if (dealerHitCount == 1) {
                c = deck.dealCardFromDeck();
                dealer.getHand().addToHand(c);
                setCardImage(c.getImageID(), dealerCard3);
                updatePlayerSum(dealer, dealerSumText);
            } else if (dealerHitCount == 2) {
                c = deck.dealCardFromDeck();
                dealer.getHand().addToHand(c);
                setCardImage(c.getImageID(), dealerCard4);
                updatePlayerSum(dealer, dealerSumText);
            } else if (dealerHitCount == 3) {
                c = deck.dealCardFromDeck();
                dealer.getHand().addToHand(c);
                setCardImage(c.getImageID(), dealerCard5);
                updatePlayerSum(dealer, dealerSumText);
            }

            dealerHitCount++;
        }

        determineWinner();
    }

    /**
     * Determine the winner.
     */
    public void determineWinner() {
        if (!game.isBust(player) && player.getHand().sumHand() > dealer.getHand().sumHand()
                || game.isBust(dealer)) {
            resultText.setText(R.string.player_wins);
        } else if (player.getHand().sumHand() == dealer.getHand().sumHand()) {
            resultText.setText(R.string.tie);
        } else {
            resultText.setText(R.string.dealer_wins);
        }
    }

    /**
     * Resets the game.
     */
    public void newGame(View view) {
        /** Creating new game and resetting text */
        // using two decks
        game = new SimpleBlackjack("Dealer", "Player", 2);
        deck = game.getDeck();
        player = game.getPlayer2();
        dealer = game.getPlayer1();
        hit.setEnabled(true);
        stand.setEnabled(true);
        hitCount = 0;
        dealerHitCount = 0;
        playerSumText.setText(R.string.sum_text_start);
        dealerSumText.setText(R.string.sum_text_start);
        resultText.setText("");

        /** ensuring Hand is empty */
        player.getHand().resetHand();
        dealer.getHand().resetHand();

        /** Player's Cards setup */
        playerCard1.setImageResource(R.drawable.back);
        playerCard2.setImageResource(R.drawable.back);
        playerCard3.setAlpha(0);
        playerCard4.setAlpha(0);
        playerCard5.setAlpha(0);

        /** Dealer's Cards setup */
        dealerCard1.setImageResource(R.drawable.back);
        dealerCard2.setImageResource(R.drawable.back);
        dealerCard3.setAlpha(0);
        dealerCard4.setAlpha(0);
        dealerCard5.setAlpha(0);

        /** The first four cards dealt from the Deck */
        c1 = deck.dealCardFromDeck();
        c2 = deck.dealCardFromDeck();
        c3 = deck.dealCardFromDeck();
        c4 = deck.dealCardFromDeck();

        player.getHand().addToHand(c1);
        dealer.getHand().addToHand(c2);
        player.getHand().addToHand(c3);
        dealer.getHand().addToHand(c4);

        setCardImage(c1.getImageID(), playerCard1);
        setCardImage(c2.getImageID(), dealerCard1);
        setCardImage(c3.getImageID(), playerCard2);

        updatePlayerSum(player, playerSumText);
        if (game.checkIfBlackjack(player.getHand())) {
            setCardImage(c4.getImageID(), dealerCard2);
            updatePlayerSum(dealer, dealerSumText);
            hit.setEnabled(false);
            stand.setEnabled(false);
            resultText.setText(R.string.blackjack_winner);
            determineWinner();
        }
    }
}
