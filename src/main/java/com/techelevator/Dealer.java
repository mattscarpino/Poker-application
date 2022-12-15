package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class Dealer {

    private List<Card> deck;
    private List<Player> players;

    public Dealer(List<Card> deck, List<Player> players){
        this.deck = deck;
        this.players = players;
    }

    public void dealCards(){

        List<Card> cardsDealt = new ArrayList<>();

        for(Player p : players){

            Card c = deck.remove(0);
            cardsDealt.add(c);
            Card d = deck.remove(0);
            cardsDealt.add(d);
        }

        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            p.setHand(cardsDealt.get(i), cardsDealt.get(i + players.size()));
        }
    }

    public Card[] getFlop(){
        Card[] flop = new Card[3];

        for(int i = 0; i < 4; i++){

            if(i == 0){
                deck.remove(0);

            } else{
                flop[i-1] = deck.remove(0);
            }
        }
        return flop;
    }

    public Card getTurnOrRiver(){

        deck.remove(0);
        return deck.remove(0);
    }

}
