package cz.stu.world.entity;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;

import java.awt.*;

public class EntityManager implements Renderable, Updatable {
    private Player player;

    @Override
    public void render(Graphics2D g, float interpolation) {
        if(player != null) {
            player.render(g, interpolation);
        }
    }

    @Override
    public void update() {
        if(player != null) {
            player.update();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
