package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    final Scanner userInput = new Scanner(System.in);

    public void menu(){
        boolean willPlayAgain = true;
        List<Player> players = getPlayers();

        while (willPlayAgain) {

            System.out.println();
            System.out.println("***** Let's play some poker!! ******");
            System.out.println();


            Deck d = new Deck();
            List<Card> deck = d.getDeck();

            Dealer dealer = new Dealer(deck,players);
            dealer.dealCards();

            displayPlayers(players);

            Card[] flop = dealer.getFlop();
            displayFlop(flop);

            Card turn = dealer.getTurnOrRiver();
            displayTurnOrRiver(turn,0);

            Card river = dealer.getTurnOrRiver();
            displayTurnOrRiver(river,1);


            for(Player p : players){
                p.setUsableCards(flop,turn,river);
                p.setUsableSuits();
                p.setUsableRanks();
            }

            HandValue hv = new HandValue(players);
            Player p = hv.findBestHand();
            double bestHandValue = hv.getBestHand();

            displayWinner(p.getName(), bestHandValue);

            willPlayAgain = playAgain();

        }
    }

    public void displayWinner(String name, double value){
        String message = "";

        if(value > 900000000){
            message = "four of a kind";
        } else if(value > 800000000){
            message = "full house";
        } else if(value > 700000000){
            message = "flush";
        } else if(value > 600000000){
            message = "straight";
        } else if(value > 500000000){
            message = "three of a kind";
        } else if(value > 400000000){
            message = "two pair";
        } else if(value > 300000000){
            message = "pair";
        } else{
            message = "high card";
        }
        System.out.println(name + " wins with a " + message);
    }

    public boolean playAgain(){
        boolean playAgain;

        while(true) {
            System.out.println("Would you like to play again? (Y/N)");
            String input = userInput.nextLine();

            if(input.equalsIgnoreCase("Y")){
                playAgain = true;
                break;
            } else if(input.equalsIgnoreCase("N")){
                playAgain = false;
                break;
            } else{
                System.out.println("Please enter a Y or N");
            }
        }
        return playAgain;
    }

    public void displayTurnOrRiver(Card turnOrRiver, int i){

        if(i == 0){
            System.out.println("** the turn! **");
            System.out.println(turnOrRiver);
            System.out.println();
        } else{
            System.out.println("** the river! **");
            System.out.println(turnOrRiver);
            System.out.println();
        }

    }

    public void displayFlop(Card[] flop){

        System.out.println("** the flop! **");
        for(Card c : flop){
            System.out.println(c);
        }
        System.out.println();
    }

    public void displayPlayers(List<Player> players){

        for(Player p : players){

            System.out.print(p.getName() + ": ");
            Card[] hand = p.getHand();
            int counter = 0;

            for(Card c : hand){

                if(counter == 0){
                    System.out.print(c + " * ");
                } else{
                    System.out.println(c);
                }
                counter++;
            }
        }
        System.out.println();
    }

    public List<Player> getPlayers(){

        List<Player> players = new ArrayList<>();
        String filename = "C:\\Users\\Student\\workspace\\Side Projects\\Poker2\\players.txt";
        File f = new File(filename);

        while(true){
            try(Scanner fileReader = new Scanner(f)){

                while(fileReader.hasNextLine()){

                    String lineOfText = fileReader.nextLine();
                    String[] text = lineOfText.split("\\|");
                    String name = text[0];

                    int balance = Integer.parseInt(text[1]);

                    Player p = new Player(name, balance);
                    players.add(p);
                }
                break;

            } catch (FileNotFoundException e) {
                System.out.println("file cannot be found");
            }
        }
        return players;
    }

}
