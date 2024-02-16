package cz.godless;

import cz.godless.service.GameManager;

public class Main {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        gameManager.startGame();
    }
}
