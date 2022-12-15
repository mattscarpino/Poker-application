package com.techelevator;

import java.util.*;

public class HandValue {

    private List<Player> players;
    private int fourOfAKind = 900000000;
    private int fullHouse = 800000000;
    private int flush = 700000000;
    private int straight = 600000000;
    private int threeOfAKind = 500000000;
    private int twoPair = 400000000;
    private int pair = 300000000;
    private double bestHand;

    public HandValue(List<Player> players){

        this.players = players;
        bestHand = 0;
    }

    public Player findBestHand(){


        for(Player p : players){

            boolean isFourOfAKind = false;
            boolean isFullHouse = false;
            boolean isFlush = false;
            boolean isThreeOfAKind = false;
            boolean isTwoPair = false;
            boolean isPair = false;

            int firstThreeOfAKind = 0;
            int secondThreeOfAKind = 0;
            int firstPair = 0;
            int secondPair = 0;

            Map<Enum, Integer> ranks = p.getRanks();
            Map<Enum, Integer> suits = p.getSuits();
            List<Card> allCards = p.getUsableCards();
            List<Integer> inBetween = new ArrayList<>();
            List<Double> highCards = new ArrayList<>();

            double addHighCards = 0;
            double result = 0;
            int firstCard = 0, secondCard = 0,thirdCard = 0,fourthCard = 0,fifthCard = 0, sixthCard = 0, seventhCard;

            // find out if four of a kind/ full house/ three of a kind/ two pair/ pair/ and high card values
            for(Enum key : ranks.keySet()){

                if(ranks.get(key) == 4){

                    result = findRankValue(key);
                    isFourOfAKind = true;

                } else if(ranks.get(key) == 3){

                    if(firstThreeOfAKind == 0){

                        firstThreeOfAKind = findRankValue(key);

                    } else {

                        secondThreeOfAKind = findRankValue(key);
                        isPair = true;
                    }
                    isThreeOfAKind = true;

                } else if(ranks.get(key) == 2){

                    isPair = true;
                    if(firstPair == 0){

                        firstPair = findRankValue(key);

                    } else if(secondPair == 0){
                        secondPair = findRankValue(key);
                        isTwoPair = true;

                    } else if (findRankValue(key) > firstPair){
                        firstPair = findRankValue(key);

                    } else if(findRankValue(key) > secondPair){
                        secondPair = findRankValue(key);
                    }
                } else if(ranks.get(key) == 1){

                    // still need to figure out how to add unused pair values into high cards
                    if(firstCard == 0){
                        firstCard = findRankValue(key);
                        inBetween.add(firstCard);

                    } else if(secondCard == 0){
                        secondCard = findRankValue(key);
                        inBetween.add(secondCard);

                    } else if(thirdCard == 0){
                        thirdCard = findRankValue(key);
                        inBetween.add(thirdCard);

                    } else if(fourthCard == 0){
                        fourthCard = findRankValue(key);
                        inBetween.add(fourthCard);

                    } else if(fifthCard == 0){
                        fifthCard = findRankValue(key);
                        inBetween.add(fifthCard);

                    } else if(sixthCard == 0){
                        sixthCard = findRankValue(key);
                        inBetween.add(sixthCard);

                    } else {
                        seventhCard = findRankValue(key);
                        inBetween.add(seventhCard);
                    }
                }
            }

            Collections.sort(inBetween);
            Collections.reverse(inBetween);

            int number = 1;
            for(int i : inBetween){
                highCards.add(findHighCardValue(i, number));
                number *= .01;
            }

            if(firstThreeOfAKind != 0 && secondThreeOfAKind != 0){

                if(firstThreeOfAKind > secondThreeOfAKind){
                    result = firstThreeOfAKind + secondFullHouse(secondThreeOfAKind);

                } else{
                    result = secondThreeOfAKind + secondFullHouse(firstThreeOfAKind);
                }
            }

            if(isThreeOfAKind && isPair){

                if(firstPair > secondPair){
                    result = firstThreeOfAKind + secondFullHouse(firstPair);
                } else{
                    result = firstThreeOfAKind + secondFullHouse(secondPair);
                }

                isFullHouse = true;
            }

            // find if flush
            for(Enum e : suits.keySet()){

                if(suits.get(e) >= 5){

                    addHighCards = 0;
                    isFlush = true;
                    double num = 1;

                    // might use more than 5 cards and won't only use highest 5
                    for(Card c : allCards){

                        if(c.getSuit().equals(e)){

                            int br = findRankValue(c.getRank());
                            addHighCards += findHighCardValue(br, num);
                            num *= .01;
                        }
                    }
                }
            }

            int straightValue = findIfStraight(ranks);

            if(isFourOfAKind){

                if(highCards.size() >= 1){
                    addHighCards = highCards.get(0);
                }

                p.setBestHandValue(fourOfAKind + result + addHighCards);

            } else if(isFullHouse){
                p.setBestHandValue(fullHouse + result);

            } else if(isFlush){

                p.setBestHandValue(flush + addHighCards);

            } else if(straightValue != 0){
                p.setBestHandValue(straightValue);

            } else if(isThreeOfAKind){

                    addHighCards = highCards.get(0) + highCards.get(1);

                p.setBestHandValue(threeOfAKind + firstThreeOfAKind + addHighCards);

            } else if(isTwoPair){

                if(inBetween.size() >= 1){
                    addHighCards = highCards.get(0);
                }

                p.setBestHandValue(twoPair + firstPair + secondPair + addHighCards);

            } else if(isPair){

                addHighCards = highCards.get(0) + highCards.get(1) + highCards.get(2);

                p.setBestHandValue(pair + firstPair + addHighCards);

            } else{

                addHighCards = highCards.get(0) + highCards.get(1) + highCards.get(2) + highCards.get(3) + highCards.get(4);
                p.setBestHandValue(addHighCards);

            }
            if(p.getBestHandValue() > bestHand){
                bestHand = p.getBestHandValue();
            }
        }
        Player play = null;

        for(Player p : players){
            if(p.getBestHandValue() == bestHand){
                play = p;
            }
        }

        return play;
    }

    public double getBestHand(){
        return bestHand;
    }

    public int findIfStraight(Map<Enum, Integer> findCardType){

        if(findCardType.containsKey(Ranks.TEN) && findCardType.containsKey(Ranks.JACK) && findCardType.containsKey(Ranks.QUEEN) && findCardType.containsKey(Ranks.KING) && findCardType.containsKey(Ranks.ACE)){

            return straight + 13;

        } else if (findCardType.containsKey(Ranks.NINE) && findCardType.containsKey(Ranks.TEN) && findCardType.containsKey(Ranks.JACK) && findCardType.containsKey(Ranks.QUEEN) && findCardType.containsKey(Ranks.KING)){
            return straight + 12;

        } else if(findCardType.containsKey(Ranks.EIGHT) && findCardType.containsKey(Ranks.NINE) && findCardType.containsKey(Ranks.TEN) && findCardType.containsKey(Ranks.JACK) && findCardType.containsKey(Ranks.QUEEN)){
            return straight + 11;

        } else if (findCardType.containsKey(Ranks.SEVEN) && findCardType.containsKey(Ranks.EIGHT) && findCardType.containsKey(Ranks.NINE) && findCardType.containsKey(Ranks.TEN) && findCardType.containsKey(Ranks.JACK)){
            return straight + 10;

        } else if (findCardType.containsKey(Ranks.SIX) && findCardType.containsKey(Ranks.SEVEN) && findCardType.containsKey(Ranks.EIGHT) && findCardType.containsKey(Ranks.NINE) && findCardType.containsKey(Ranks.TEN)){
            return straight + 9;

        } else if(findCardType.containsKey(Ranks.FIVE) && findCardType.containsKey(Ranks.SIX) && findCardType.containsKey(Ranks.SEVEN) && findCardType.containsKey(Ranks.EIGHT) && findCardType.containsKey(Ranks.NINE)){
            return straight + 8;

        } else if(findCardType.containsKey(Ranks.FOUR) && findCardType.containsKey(Ranks.FIVE) && findCardType.containsKey(Ranks.SIX) && findCardType.containsKey(Ranks.SEVEN) && findCardType.containsKey(Ranks.EIGHT)){
            return straight + 7;

        } else if(findCardType.containsKey(Ranks.THREE) && findCardType.containsKey(Ranks.FOUR) && findCardType.containsKey(Ranks.FIVE) && findCardType.containsKey(Ranks.SIX) && findCardType.containsKey(Ranks.SEVEN)){
            return straight + 8;

        } else if(findCardType.containsKey(Ranks.TWO) && findCardType.containsKey(Ranks.THREE) && findCardType.containsKey(Ranks.FOUR) && findCardType.containsKey(Ranks.FIVE) && findCardType.containsKey(Ranks.SIX)){
            return straight + 7;

        } else if(findCardType.containsKey(Ranks.ACE) && findCardType.containsKey(Ranks.TWO) && findCardType.containsKey(Ranks.THREE) && findCardType.containsKey(Ranks.FOUR) && findCardType.containsKey(Ranks.FIVE)){
            return straight + 6;
        }
        return 0;
    }

    public double findHighCardValue(int cardValue, double num){
        double value = 0;

        if(cardValue == 9000000){
            value = .9 * num;
        } else if(cardValue == 5000000){
            value = .8 * num;
        } else if(cardValue == 3000000){
            value = .7 * num;
        } else if(cardValue == 1000000){
            value = .6 * num;
        } else if(cardValue == 600000){
            value = .5 * num;
        } else if(cardValue == 300000){
            value = .4 * num;
        } else if(cardValue == 100000){
            value = .3 * num;
        } else if(cardValue == 60000){
            value = .2 * num;
        } else if(cardValue == 30000){
            value = .15 * num;
        } else if (cardValue == 10000){
            value = .14 * num;
        } else if(cardValue == 6000){
            value = .13 * num;
        } else if(cardValue == 3000){
            value = .12 * num;
        } else if(cardValue == 1000){
            value = .11 * num;
        }
        return value;
    }

    public int findRankValue(Enum rank){

        if(rank.equals(Ranks.ACE)){
            return 9000000;
        } else if(rank.equals(Ranks.KING)){
            return 5000000;
        }else if(rank.equals(Ranks.QUEEN)){
            return 3000000;
        } else if(rank.equals(Ranks.JACK)){
            return 1000000;
        } else if(rank.equals(Ranks.TEN)){
            return 600000;
        }else if(rank.equals(Ranks.NINE)){
            return 300000;
        } else if(rank.equals(Ranks.EIGHT)){
            return 100000;
        } else if(rank.equals(Ranks.SEVEN)){
            return 60000;
        } else if(rank.equals(Ranks.SIX)){
            return 30000;
        } else if(rank.equals(Ranks.FIVE)){
            return 10000;
        } else if(rank.equals(Ranks.FOUR)){
            return 6000;
        } else if(rank.equals(Ranks.THREE)){
            return 3000;
        } else {
            return 1000;
        }
    }

    public int secondFullHouse(int num){
        if(num == 9000000){
            return 13;
        } else if(num == 5000000){
            return 12;
        } else if(num == 3000000){
            return 11;
        } else if(num == 1000000){
            return 10;
        } else if(num == 600000){
            return 9;
        } else if(num == 300000){
            return 8;
        } else if(num == 100000){
            return 7;
        } else if(num == 60000){
            return 6;
        } else if(num == 30000){
            return 5;
        } else if(num == 10000){
            return 4;
        } else if(num == 6000){
            return 3;
        } else if(num == 3000){
            return 2;
        } else {
            return 1;
        }
    }

}
