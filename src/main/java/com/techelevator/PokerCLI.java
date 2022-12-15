package com.techelevator;

public class PokerCLI {

    public void run(){

        UserInterface ui = new UserInterface();
        ui.menu();


    }

    public static void main(String[] args) {

        PokerCLI cli = new PokerCLI();
        cli.run();
    }

}
