package banking;
public class Main {



    public static void main(String[] args) {

        Bank bank = new Bank(args[1]);
        bank.start();

    }
}
