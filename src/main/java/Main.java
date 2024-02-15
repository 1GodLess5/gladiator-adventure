import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Gladiator Adventure");
        System.out.println("Enter your name (this will be used to track your progress):");
        final String name = scanner.nextLine();

        final Hero hero = new Hero(name);
        System.out.println("Hello " + hero.getName() + ".");
        System.out.println("Let's begin!");
    }
}
