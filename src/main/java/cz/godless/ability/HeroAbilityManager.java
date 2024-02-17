package cz.godless.ability;

import cz.godless.domain.Hero;
import cz.godless.utility.InputUtils;
import cz.godless.utility.PrintUtils;

import java.sql.SQLOutput;

public class HeroAbilityManager {
    private final Hero hero;

    public HeroAbilityManager(Hero hero){
        this.hero = hero;
    }

    public void spendHeroAvailablePoints(){
        int availablePoints = hero.getAvailablePoints();

        if (availablePoints == 0){
            System.out.println("You have no points to spend");
            return;
        }
        while (availablePoints > 0){
            System.out.println("You have " + availablePoints + " points to spend. Choose wisely.");
            System.out.println("0 - Explain abilities");
            System.out.println("1 - Attack");
            System.out.println("2 - Defence");
            System.out.println("3 - Dexterity");
            System.out.println("4 - Skill");
            System.out.println("5 - Luck");
            System.out.println("6 - Health");

            final int choice = InputUtils.readInt();
            Ability ability;
            switch (choice) {
                case 0 -> {
                    for (Ability a: Ability.values()){
                        System.out.println(a + ": " + a.getDescription());
                    }
                    System.out.println();
                    continue;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.DEFENCE;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid input.");
                    continue;
                }
            }
            this.hero.updateAbility(ability, 1);
            System.out.println("You have upgraded " + ability + ".");

            this.hero.updateAvailablePoints(-1);
            if (availablePoints > 1){
                PrintUtils.printAbilities(this.hero);
            }
            PrintUtils.printDivider();
            availablePoints--;
        }
        System.out.println("You have spent all your available points. Your abilities are:");
        PrintUtils.printAbilities(this.hero);
        PrintUtils.printDivider();
    }

    public void reuseHeroPoints(){
        while (true) {
            System.out.println("Which ability do you want to remove?");
            System.out.println("0 - I am done");
            System.out.println("1 - Attack");
            System.out.println("2 - Defence");
            System.out.println("3 - Dexterity");
            System.out.println("4 - Skill");
            System.out.println("5 - Luck");
            System.out.println("6 - Health");

            final int choice = InputUtils.readInt();
            Ability ability;
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.DEFENCE;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid input.");
                    continue;
                }
            }
            if (this.hero.getAbilities().get(ability) == 1){
                System.out.println("You cannot remove points from this ability!");
            } else {
                this.hero.updateAbility(ability, -1);
                this.hero.updateAvailablePoints(1);
                System.out.println("You have removed 1 point from " + ability);
                PrintUtils.printAbilities(this.hero);
                PrintUtils.printDivider();
            }
        }
    }
}
