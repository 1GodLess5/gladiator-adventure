package cz.godless.service;

import cz.godless.domain.Hero;
import cz.godless.utility.InputUtils;

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
                System.out.println("Game with this name is already saved.");
                continue;
            }

            try {
                String heroDataToString = "TODO";
                Files.writeString(Path.of(path), heroDataToString);

            } catch (IOException e){
                System.out.println("Oops, we encountered an error saving this game.");
                System.out.println("Please try again.");
                continue;
            } catch (InvalidPathException e){
                System.out.println("Invalid characters in file name.");
                System.out.println("Please try again.");
                continue;
            }
            break;
        }
    }
}
