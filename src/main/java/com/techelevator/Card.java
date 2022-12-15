package com.techelevator;

public class Card {

    private Enum rank;
    private Enum suit;

    public Card(Enum rank, Enum suit){
        this.rank = rank;
        this.suit = suit;
    }

    public Enum getRank(){
        return this.rank;
    }

    public Enum getSuit(){
        return this.suit;
    }

    @Override
    public String toString(){
        return rank.toString() + " of " + suit.toString();
    }
}


