package cz.stu.world;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;
import cz.stu.world.entity.EntityManager;
import cz.stu.world.entity.Player;

import java.awt.*;

public class World implements Renderable, Updatable {
    private EntityManager em;

    public World() {
        em = new EntityManager();
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        em.render(g, interpolation);
    }

    @Override
    public void update() {
        em.update();
    }

    public void spawnPlayer(int x, int y) {
        em.setPlayer(new Player(x, y));
    }
}
