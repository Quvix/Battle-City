package cz.stu.world.entity;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;

import java.awt.*;

public abstract class Entity implements Renderable, Updatable {
    private float x,y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract Rectangle getBounds();

    public boolean intersects(Entity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }
}
