package io.github.some_example_name.Controllers;

import io.github.some_example_name.Models.Player;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        player.update(com.badlogic.gdx.Gdx.graphics.getDeltaTime());
    }

    public Player getPlayer() {
        return player;
    }

    public void setMoveUp(boolean value) {
        player.setMoveUp(value);
    }

    public void setMoveDown(boolean value) {
        player.setMoveDown(value);
    }

    public void setMoveLeft(boolean value) {
        player.setMoveLeft(value);
    }

    public void setMoveRight(boolean value) {
        player.setMoveRight(value);
    }
}
