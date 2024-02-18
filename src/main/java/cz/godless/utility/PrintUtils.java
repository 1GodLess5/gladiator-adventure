package cz.godless.utility;

import cz.godless.ability.Ability;
import cz.godless.domain.GameCharacter;

import java.util.Map;

public class PrintUtils {
    public static void printAbilities(GameCharacter character){
        int counter = 0;
        for (Map.Entry<Ability, Integer> entry : character.getAbilities().entrySet()){
            if (counter != 5){
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            } else {
                System.out.print(entry.getKey() + ": " + entry.getValue());
            }
            counter++;
        }
        System.out.println();
    }

    public static void printDivider(){
        System.out.println("-----------------------------------");
    }
}
