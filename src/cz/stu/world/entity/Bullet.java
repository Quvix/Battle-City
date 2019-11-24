package cz.stu.world.entity;

import cz.stu.gfx.Animation;
import cz.stu.gfx.SpriteAnimation;
import cz.stu.gfx.SpriteManager;
import cz.stu.world.Direction;
import cz.stu.world.World;

import java.awt.*;
import java.util.List;

public class Bullet extends DynamicEntity {
    private static final float SPEED = 5;
    public static final int SIZE = 4;
    private static final int EXPLOSION_SIZE = 32;
    private Direction direction;
    private boolean exploded = false;
    private boolean destroyed = false;
    private World world;
    private Animation explosion;

    public Bullet(int x, int y, Direction direction, World world) {
        super(x, y);
        this.direction = direction;
        this.world = world;
        explosion = SpriteManager.getInstance().getSpriteAnimations(SpriteAnimation.EXPLOSION);
        explosion.setLooped(false);
        explosion.setTicksPerImage(3);

        switch(direction) {
            case UP:
                setVelY(-SPEED);
                break;
            case DOWN:
                setVelY(SPEED);
                break;
            case LEFT:
                setVelX(-SPEED);
                break;
            case RIGHT:
                setVelX(SPEED);
                break;
            case NONE:
                throw new RuntimeException("Yo, what the fuck? that ain't matrix");
        }
    }

    @Override
    public Rectangle getBounds() {
        // TODO: Nevytářet furt nový Rectangle, ale recyklovat
        return new Rectangle((int)getX(), (int)getY(), SIZE, SIZE);
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        if(!exploded) {
            g.setColor(Color.WHITE);
            // jediný příklad užití interpolace v celém projektu... SOOOOOO SMOOTH <3
            g.fillRect((int)(getX() + getVelX() * interpolation), (int)(getY() + getVelY() * interpolation), SIZE, SIZE);
        } else {
            g.drawImage(explosion.getCurrentFrame(), (int)getX() - EXPLOSION_SIZE / 2, (int)getY() - EXPLOSION_SIZE / 2, EXPLOSION_SIZE, EXPLOSION_SIZE, null);
        }
    }

    @Override
    public void update() {
        super.update();

        if(exploded) {
            if(explosion.isFinished()) {
                destroyed = true;
            } else {
                explosion.update();
            }
        } else {
            handleCollisions();
        }

        if(getX() < 0 || getX() + SIZE > world.getMapSize().width) {
            explode();
        } else if(getY() < 0 || getY() + SIZE > world.getMapSize().height) {
            explode();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private void handleCollisions() {
        List<Tile> collidingTiles = world.getCollidingTiles(this);
        collidingTiles.removeIf(Tile::isPenetrable);

        if(collidingTiles.size() > 0) {
            explode();

            for(Tile tile : collidingTiles) {
                if(tile.isDestroyable()) {
                    world.destroyTile(tile);
                }
            }
        }

        List<Entity> collidingEntities = world.getCollidingEntities(this);
        for(Entity e : collidingEntities) {
            if(e instanceof Eagle) {
                ((Eagle) e).destroy();
                explode();
            }

            if(e instanceof Enemy) {
                ((Enemy) e).destroy();
                explode();
            }
        }
    }

    private void explode() {
        exploded = true;
        setVelY(0);
        setVelX(0);
    }
}
