package banking;

import java.util.*;

public class Bank {
    static ConnectionSQL connection;
    Scanner sc = new Scanner(System.in);



    Bank(String fileName) {
        connection = new ConnectionSQL(fileName);
        connection.createTable();
    }

    public void start() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        chooseAction(sc.next());
    }
    public void chooseAction(String input) {

        switch (input){
            case "1": createAccount(); break;
            case "2": loginAccount(); break;
            case "0": exit();
        }
    }

    private void createAccount() {
        Card card = new Card();
        card.generateNewCard();
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getAccountNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPinCode() + "\n");
        connection.insertData(card);
        start();
    }

    private void loginAccount() {
        Card card = new Card();
        System.out.println("\nEnter your card number:");
        String cardNum  = sc.next();
        System.out.println("Enter your PIN:");
        String pinCode  = sc.next();
        card.setAccountNumber(cardNum);
        card.setPinCode(pinCode);
        authentication(card);
    }

    private void authentication(Card checkCard) {
        Card card = connection.selectData(checkCard);
        if(card.getBalance() == -1) {
            System.out.println("\nWrong card number or PIN!");
            start();
        } else {
            System.out.println("\nYou have successfully logged in!");
            selectCardActions(card);
        }
    }



    public void selectCardActions (Card card) {
        System.out.println("\n1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        Card updatedCard = connection.selectData(card);
        switch (sc.next()) {
            case "1": System.out.println("\nBalance: " + updatedCard.getBalance()); selectCardActions(updatedCard); break;
            case "2": addIncome(updatedCard);selectCardActions(updatedCard); break;
            case "3": doTransfer(updatedCard);selectCardActions(updatedCard); break;
            case "4": closeAccount(updatedCard); start(); break;
            case "5": System.out.println("\nYou have successfully logged out!\n"); start(); break;
            case "0": exit();
        }
    }

    private void addIncome(Card card) {
        System.out.println("\nEnter income:");
        int income = sc.nextInt();
        connection.updateData(card, income);
        System.out.println("Income was added!");
    }
    private void doTransfer(Card card) {
        Card transferCard = new Card();
        System.out.println("Transfer");
        System.out.println("Enter card number: ");
        String cardTransferNum = sc.next();


        transferCard.setAccountNumber(cardTransferNum);

        if(!transferCard.isValidCard(cardTransferNum)){
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        } else if(connection.selectTransferData(transferCard).getBalance() == -1){
            System.out.println("Such a card does not exist");
            return;
        }


        System.out.println("Enter how much money you want to transfer: ");
        int transferAmount = sc.nextInt();

        if(transferAmount > card.getBalance())
            System.out.println("Not enough money!");
        else if(card.getAccountNumber().equals(cardTransferNum))
            System.out.println("You can't transfer money to the same account!");
        else {
            transferCard =connection.selectTransferData(transferCard);
            connection.updateData(card, -transferAmount);
            connection.updateData(transferCard, transferAmount);
            System.out.println("Success!");
        }

    }
    private void closeAccount(Card card) {
        connection.deleteData(card);
        System.out.println("\nThe account has been closed!\n");
    }

    private void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }


}
