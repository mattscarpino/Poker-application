package com.techelevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    final private String name;
    private int balance;
    private Card[] hand;
    private Map<Enum, Integer> ranks = new HashMap<>();
    private Map<Enum, Integer> suits = new HashMap<>();
    private List<Card> usableCards = new ArrayList<>();
    private double bestHandValue;

    public Player(String name, int balance){
        this.name = name;
        this.balance = balance;
        bestHandValue = 0;
    }

    public void setHand(Card card1, Card card2){
        hand = new Card[]{card1, card2};
    }

    public Card[] getHand(){
        return hand;
    }

    public String getName(){
        return name;
    }

    public int getBalance(){
        return balance;
    }

    public void lostMoney(int pot){
        balance -= pot;
    }

    public void wonMoney(int pot){
        balance += pot;
    }

    public void setUsableRanks(){

        for(Card c : usableCards){

            if(ranks.containsKey(c.getRank())){

                ranks.put(c.getRank(), ranks.get(c.getRank()) + 1);

            } else{
                ranks.put(c.getRank(),1);
            }
        }
    }

    public Map<Enum, Integer> getRanks(){
        return ranks;
    }

    public void setUsableSuits(){

        for(Card c : usableCards){

            if(suits.containsKey(c.getSuit())){

                suits.put(c.getSuit(), suits.get(c.getSuit()) + 1);

            } else{
                suits.put(c.getSuit(),1);
            }
        }
    }

    public Map<Enum, Integer> getSuits(){
        return suits;
    }

    public void setUsableCards(Card[] flop, Card turn, Card river){

        for(int i = 0; i < 3; i++){
            usableCards.add(flop[i]);
        }
        usableCards.add(turn);
        usableCards.add(river);

        for(int i = 0; i < 2; i++){
            usableCards.add(hand[i]);
        }
    }

    public List<Card> getUsableCards(){
        return usableCards;
    }

    public void setBestHandValue(double d){
        this.bestHandValue = d;
    }

    public double getBestHandValue(){
        return bestHandValue;
    }

    @Override
    public String toString(){
        return "name: " + name + "\nbalance: " + balance;
    }
}
