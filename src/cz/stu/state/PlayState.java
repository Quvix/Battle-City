package cz.stu.state;

import cz.stu.world.World;

import java.awt.*;

public class PlayState implements GameState {

    private World world;

    public PlayState() {
        world = new World();
        world.spawnPlayer(100, 100);
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        world.render(g, interpolation);
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void reset() {

    }
}
