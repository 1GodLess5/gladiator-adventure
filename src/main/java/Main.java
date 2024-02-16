import java.util.Map;
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
        System.out.println("Your abilities:");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()){
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        spendHeroAvailablePoints(hero);
    }

    public static void spendHeroAvailablePoints(Hero hero){
        int availablePoints = hero.getAvailablePoints();

        if (availablePoints == 0){
            System.out.println("You have no points to spend");
            return;
        }
        while (availablePoints > 0){
            System.out.println("You have " + availablePoints + " to spend. Choose wisely.");
            System.out.println("0 - Explain abilities");
            System.out.println("1 - Attack");
            System.out.println("2 - Defence");
            System.out.println("3 - Dexterity");
            System.out.println("4 - Skill");
            System.out.println("5 - Luck");
            System.out.println("6 - Health");

            availablePoints--;
        }
    }
}
