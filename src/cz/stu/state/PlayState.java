package cz.stu.state;

import cz.stu.world.GameProgress;
import cz.stu.world.World;

import java.awt.*;

public class PlayState implements GameState {

    private World world;

    public PlayState() {
        world = new World();
        try {
            world.loadMap("maps/level1.map");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        world.render(g, interpolation);
    }

    @Override
    public void update() {
        world.update();

        GameProgress progress = world.getGameProgress();

        switch (progress) {
            case RUNNING:
                break;
            case LOST:
                System.out.println("YAY, prohral jsi");
                try {
                    world.loadMap("maps/level1.map");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case WON:
                System.out.println("YAY, vyhral jsi");
                break;
        }
    }

    @Override
    public void reset() {

    }
}
