package cz.godless.service;

import cz.godless.ability.Ability;
import cz.godless.ability.HeroAbilityManager;
import cz.godless.constant.Constant;
import cz.godless.domain.Enemy;
import cz.godless.domain.Hero;
import cz.godless.domain.LoadedGame;
import cz.godless.utility.EnemyGenerator;
import cz.godless.utility.InputUtils;
import cz.godless.utility.PrintUtils;

import java.util.Map;

public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;
    private final FileService fileService;
    private final BattleService battleService;
    private final Map<Integer, Enemy> enemiesByLevel;

    public GameManager() {
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
        this.battleService = new BattleService();
        this.enemiesByLevel = EnemyGenerator.createEnemies();
    }

    public void startGame() {
        this.initGame();

        while (this.currentLevel <= this.enemiesByLevel.size()){
            final Enemy enemy = this.enemiesByLevel.get(this.currentLevel);
            System.out.println("0 - Fight " + enemy.getName() + " (Level " + this.currentLevel + ")");
            System.out.println("1 - Upgrade abilities (" + this.hero.getAvailablePoints() + " points to spend)");
            System.out.println("2 - Save Game");
            System.out.println("3 - Exit Game");

            final int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> {
                    if (this.battleService.isHeroReadyToBattle(this.hero, enemy)){
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.HEALTH);
                        final boolean hasHeroWon = this.battleService.battle(this.hero, enemy);

                        if (hasHeroWon) {
                            PrintUtils.printDivider();
                            System.out.println("You have won this battle! You have gained " + this.currentLevel + " ability points.");
                            this.hero.updateAvailablePoints(this.currentLevel);
                            this.currentLevel++;
                        } else {
                            System.out.println("You have lost.");
                        }
                        this.hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                        System.out.println("You have full health now.");
                        PrintUtils.printDivider();
                    }
                }
                case 1 -> {
                    this.upgradeAbilitiesCycle();
                }
                case 2 -> {
                    this.fileService.saveGame(this.hero, this.currentLevel);
                }
                case 3 -> {
                    System.out.println("Are you sure?");
                    System.out.println("0 - No");
                    System.out.println("1 - Yes");
                    final int exitChoice = InputUtils.readInt();
                    if (exitChoice == 1){
                        System.out.println("Thanks for playing with us!");
                        return;
                    }
                    System.out.println("Continuing...");
                    PrintUtils.printDivider();
                }
                default -> System.out.println("Invalid input.");
            }
        }

        System.out.println("You have went through all of the levels. Congratulations!");
    }

    private void initGame() {
        System.out.println("Welcome to the Gladiator Adventure");
        System.out.println("0 - Start new game");
        System.out.println("1 - Load game");

        while (true){
            final int choice = InputUtils.readInt();
            switch (choice){
                case 0 -> PrintUtils.printDivider();
                case 1 -> {
                    final LoadedGame loadGame = fileService.loadGame();
                    if (loadGame != null){
                        this.hero = loadGame.getHero();
                        this.currentLevel = loadGame.getLevel();
                        return;
                    }
                }
                default -> {
                    System.out.println("Invalid input.");
                    continue;
                }
            }
            break;
        }

        System.out.println("Enter your Hero name:");
        final String name = InputUtils.readString();

        this.hero.setName(name);
        System.out.println("Hello " + hero.getName() + ".");
        System.out.println("Let's begin!");
        PrintUtils.printDivider();
        System.out.println("Your abilities:");
        PrintUtils.printAbilities(hero);
        PrintUtils.printDivider();
        this.heroAbilityManager.spendHeroAvailablePoints();
    }

    private void upgradeAbilitiesCycle() {
        System.out.println("Your abilities are: ");
        PrintUtils.printAbilities(this.hero);

        System.out.println("0 - Go back");
        System.out.println("1 - Spend points (" + this.hero.getAvailablePoints() + " points to spend)");
        System.out.println("2 - Remove points");

        final int choice = InputUtils.readInt();
        switch (choice){
            case 0 -> {}
            case 1 -> this.heroAbilityManager.spendHeroAvailablePoints();
            case 2 -> this.heroAbilityManager.reuseHeroPoints();
            case 3 -> System.out.println("Invalid input.");
        }
    }
}
