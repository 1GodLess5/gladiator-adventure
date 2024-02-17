package cz.godless.service;

import cz.godless.ability.Ability;
import cz.godless.domain.Hero;
import cz.godless.utility.InputUtils;
import cz.godless.utility.PrintUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileService {
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
}
