package cz.godless.service;

import cz.godless.ability.Ability;
import cz.godless.ability.HeroAbilityManager;
import cz.godless.domain.Hero;
import cz.godless.utility.InputUtils;
import cz.godless.utility.PrintUtils;

import java.util.Map;

public class GameManager {
    private final Hero hero;
    private final HeroAbilityManager heroAbilityManager;

    public GameManager() {
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
    }

    public void startGame(){
        System.out.println("Welcome to the Gladiator Adventure");
        System.out.println("Enter your name (this will be used to track your progress):");
        final String name = InputUtils.readString();

        final Hero hero = new Hero(name);
        System.out.println("Hello " + hero.getName() + ".");
        System.out.println("Let's begin!");
        System.out.println("Your abilities:");
        PrintUtils.printAbilities(hero);
        this.heroAbilityManager.spendHeroAvailablePoints();
    }
}
