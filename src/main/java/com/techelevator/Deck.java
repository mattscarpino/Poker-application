package com.techelevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> deck = new ArrayList<>();
    private Ranks[] ranks = Ranks.values();
    private Suits[] suits = Suits.values();

    public Deck(){

        for(int i = 0; i < suits.length; i++){
            for(int x = 0; x < ranks.length; x++){

                Card c = new Card(ranks[x], suits[i]);
                deck.add(c);
            }
        }
        Collections.shuffle(deck);
    }

    public List<Card> getDeck(){
        return deck;
    }

}
