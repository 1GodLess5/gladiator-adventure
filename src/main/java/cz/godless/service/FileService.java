package cz.godless.service;

import cz.godless.ability.Ability;
import cz.godless.domain.Hero;
import cz.godless.domain.LoadedGame;
import cz.godless.utility.InputUtils;
import cz.godless.utility.PrintUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileService {
    public LoadedGame loadGame(){
        while (true) {
            final File[] savedFiles = new File("saved-games").listFiles();
            if (savedFiles == null || savedFiles.length == 0){
                System.out.println("No saved games found.");
                return null;
            }
            System.out.println("With which of the saved files would you like to continue with?");
            for (int i = 0; i < savedFiles.length; i++){
                System.out.println(i + ". " + savedFiles[i].getName().replace(".txt", ""));
            }

            final int choice = InputUtils.readInt();
            if (choice < 0 || choice >= savedFiles.length){
                System.out.println("Invalid input.");
                continue;
            }

            final String loadGameFile = savedFiles[choice].getName();
            final String filePath = "saved-games/" + loadGameFile;

            try {
                final String heroData = Files.readString(Path.of(filePath));
                System.out.println("Game loaded.");
                return this.stringToHeroData(heroData);

            } catch (IOException e){
            PrintUtils.printDivider();
            System.out.println("Oops, we encountered an error while trying to load this game.");
            System.out.println("Please try again.");
            PrintUtils.printDivider();
            } catch (InvalidPathException e){
                PrintUtils.printDivider();
                System.out.println("Invalid characters in file name.");
                System.out.println("Please try again.");
                PrintUtils.printDivider();
            }
        }
    }

    public void saveGame(Hero hero, int currentLevel) {
        while (true) {
            System.out.println("How do you want to name your save?");
            System.out.println("Be cautious when choosing the name.");
            System.out.println("If you forget the name you may not be able to resume to your progress!");
            final String name = InputUtils.readString();

            final String path = "saved-games/" + name + ".txt";
            if (new File(path).exists()){
                PrintUtils.printDivider();
                System.out.println("Save with this name already exists.");
                PrintUtils.printDivider();
                continue;
            }

            try {
                Files.writeString(Path.of(path), this.heroDataToString(hero, currentLevel));
                System.out.println("Your progress was successfully saved!");
                PrintUtils.printDivider();

            } catch (IOException e){
                PrintUtils.printDivider();
                System.out.println("Oops, we encountered an error while saving this game.");
                System.out.println("Please try again.");
                PrintUtils.printDivider();
                continue;
            } catch (InvalidPathException e){
                PrintUtils.printDivider();
                System.out.println("Invalid characters in file name.");
                System.out.println("Please try again.");
                PrintUtils.printDivider();
                continue;
            }
            break;
        }
    }

    private String heroDataToString(Hero hero, int currentLevel){
        final StringBuilder sb = new StringBuilder();
        sb.append(currentLevel).append("\n");
        sb.append(hero.getName()).append("\n");
        sb.append(hero.getAvailablePoints()).append("\n");
        for (Ability ability: Ability.values()){
            sb.append(ability).append(":").append(hero.getAbilities().get(ability)).append("\n");
        }
        return sb.toString();
    }

    private LoadedGame stringToHeroData(String heroData){
        final String[] lines = heroData.split("\n");
        final int currentLevel = Integer.parseInt(lines[0]);
        final String heroName = lines[1];
        final int heroAvailablePoints = Integer.parseInt(lines[2]);

        final Map<Ability, Integer> abilities = new HashMap<>();
        for (int i = 3; i < 3 + Ability.values().length; i++){
            final String[] abilityData = lines[i].split(":");
            final Ability ability = Ability.valueOf(abilityData[0]);
            final int value = Integer.parseInt(abilityData[1]);
            abilities.put(ability, value);
        }
        return new LoadedGame(
                new Hero(heroName, abilities, heroAvailablePoints),
                currentLevel
        );
    }
}
