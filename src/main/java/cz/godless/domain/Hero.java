package cz.godless.domain;

import cz.godless.ability.Ability;
import cz.godless.constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {
    private int availablePoints;

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.availablePoints = Constant.INITIAL_ABILITY_POINTS;
    }

    public Hero(String name, Map<Ability, Integer> abilities, int availablePoints){
        super(name, abilities);
        this.availablePoints = availablePoints;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void updateAvailablePoints(int delta) {
        this.availablePoints += delta;
    }

    public void updateAbility(Ability ability, int delta){
        if (ability.equals(Ability.HEALTH)) {
            this.abilities.put(ability, this.abilities.get(ability) + delta * Constant.HEALTH_OF_ONE_POINT);
        } else {
            this.abilities.put(ability, this.abilities.get(ability) + delta);
        }

    }

    private Map<Ability, Integer> getInitialAbilities() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.DEFENCE, 1,
                Ability.DEXTERITY, 1,
                Ability.SKILL, 1,
                Ability.LUCK, 1,
                Ability.HEALTH, 50
        ));
    }
}
