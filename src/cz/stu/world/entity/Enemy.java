package cz.stu.world.entity;

import cz.stu.core.Game;
import cz.stu.gfx.Sprite;
import cz.stu.gfx.SpriteManager;
import cz.stu.world.Direction;
import cz.stu.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Tank {
    private static final BufferedImage SPRITE_UP = SpriteManager.getInstance().getSprite(Sprite.ENEMY_UP);
    private static final BufferedImage SPRITE_DOWN = SpriteManager.getInstance().getSprite(Sprite.ENEMY_DOWN);
    private static final BufferedImage SPRITE_LEFT = SpriteManager.getInstance().getSprite(Sprite.ENEMY_LEFT);
    private static final BufferedImage SPRITE_RIGHT = SpriteManager.getInstance().getSprite(Sprite.ENEMY_RIGHT);

    private static final int MAX_WAIT_BEFORE_SHOOTING = Game.UPDATES_PER_SECOND * 2;

    private boolean destroyed = false;
    private int interval_before_shooting = -1;

    public Enemy(int x, int y, World world) {
        super(x, y, world);

        moveRandomDirection();
        setNextShootingInterval();
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        super.render(g, interpolation);

        switch(getDirection()) {
            case UP:
                g.drawImage(SPRITE_UP, (int)getX(), (int)getY(), SIZE, SIZE, null);
                break;
            case DOWN:
                g.drawImage(SPRITE_DOWN, (int)getX(), (int)getY(), SIZE, SIZE, null);
                break;
            case LEFT:
                g.drawImage(SPRITE_LEFT, (int)getX(), (int)getY(), SIZE, SIZE, null);
                break;
            case RIGHT:
                g.drawImage(SPRITE_RIGHT, (int)getX(), (int)getY(), SIZE, SIZE, null);
                break;
        }
    }

    @Override
    public void update() {
        super.update();

        if(bullet == null) {
            interval_before_shooting--;

            if(interval_before_shooting == 0) {
                fire();
                setNextShootingInterval();
            }
        }
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    protected boolean handleCollisions() {
        boolean collided = super.handleCollisions();
        if(collided) {
            moveRandomDirection();
        }

        return collided;
    }

    private void moveRandomDirection() {
        Random rand = new Random();
        int directionValue;
        do {
            directionValue = rand.nextInt(Direction.values().length - 1);
        } while(directionValue == getDirection().ordinal());

        move(Direction.values()[directionValue]);
    }

    private void setNextShootingInterval() {
        Random rand = new Random();
        interval_before_shooting = rand.nextInt(MAX_WAIT_BEFORE_SHOOTING);
    }
}
