package banking;

import java.util.Random;

public class Card {

    private String accountNumber;
    private String pinCode;
    int balance = 0;


    private static long defaultAccountNumber = 4000_0000_0000_0000L;
    private static long lower = 100_000_000L;
    private static long upper = 999_999_999L;


    private String generatePinCode() {
        String pinCode = new String();
        Random random = new Random();
        for(int i = 0 ; i < 4 ; i++) {
            int pinCodeNumber = random.nextInt(10);
            pinCode += String.valueOf(pinCodeNumber);
        }
        return String.valueOf(pinCode);
    }


    private String generateCardNumber() {
        long sum = 0;
        Random random = new Random();
        long intervalLength = upper - lower + 1;
        long creditCardNumber = defaultAccountNumber + (random.nextLong(intervalLength) + lower) * 10;

        for(long i = 1_000_000_000_000_000L; i >= 10; i = i / 10) {
            long digit = ((creditCardNumber / i) % 10) * 2 ;
            if(digit > 9)
                digit -= 9;
            sum += digit;
            i = i / 10;
            sum += (creditCardNumber / i) % 10;
        }
        sum = (10 - sum % 10) % 10;
        return String.valueOf(creditCardNumber + sum);
    }

    public boolean isValidCard(String cardNum) {
        long sum = 0;
        for(long i = 1_000_000_000_000_000L; i >= 10; i = i / 10) {
            long digit = ((Long.parseLong(cardNum) / i) % 10) * 2 ;
            if(digit > 9)
                digit -= 9;
            sum += digit;
            i = i / 10;
            sum += (Long.parseLong(cardNum) / i) % 10;
        }
        return sum % 10 == 0;
    }
    Card(){};
    Card(String accountNumber, String pinCode, int balance) {
        this.accountNumber = accountNumber;
        this.pinCode = pinCode;
        this.balance = balance;
    }
    public void generateNewCard() {
        this.accountNumber = generateCardNumber();
        this.pinCode = generatePinCode();
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
