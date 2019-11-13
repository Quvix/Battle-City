package cz.stu.world.entity;

import cz.stu.world.World;

import java.awt.*;
import java.util.List;


public abstract class Tank extends DynamicEntity {
    protected static final int SIZE = 32;
    protected static final float SPEED = 2;
    private Direction direction = Direction.UP;
    private boolean isMoving = false;
    private World world;

    public Tank(int x, int y, World world) {
        super(x, y);
        this.world = world;
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        g.setColor(Color.GREEN);
        g.fillRect((int)getX(), (int)getY(), SIZE, SIZE);
        g.setColor(Color.WHITE);
        switch(direction) {
            case UP:
                g.fillRect((int)(getX() + SIZE / 2 - SIZE / 8), (int)(getY()), SIZE / 4, SIZE / 2);
                break;
            case DOWN:
                g.fillRect((int)(getX() + SIZE / 2 - SIZE / 8), (int)(getY() + SIZE / 2), SIZE / 4, SIZE / 2);
                break;
            case LEFT:
                g.fillRect((int)(getX()), (int)(getY() + SIZE / 2 - SIZE / 8), SIZE / 2, SIZE / 4);
                break;
            case RIGHT:
                g.fillRect((int)(getX() + SIZE / 2), (int)(getY() + SIZE / 2 - SIZE / 8), SIZE / 2, SIZE / 4);
                break;
        }
    }

    @Override
    public void update() {
        super.update();

        handleCollisions();

        // clamp
        setX(Math.max(0, Math.min(world.getMapSize().width - Tank.SIZE, getX())));
        setY(Math.max(0, Math.min(world.getMapSize().height - Tank.SIZE, getY())));
    }

    protected void move(Direction direction) {
        switch(direction) {
            case UP:
            case DOWN:
                if(this.direction == Direction.LEFT || this.direction == Direction.RIGHT) {
                    if((getX() % Tile.SIZE) > (Tile.SIZE / 2)) {
                        setX(((int)(getX() / Tile.SIZE + 1)) * Tile.SIZE);
                    } else {
                        setX(((int)(getX() / Tile.SIZE)) * Tile.SIZE);
                    }
                }
                break;
            case LEFT:
            case RIGHT:
                if(this.direction == Direction.UP || this.direction == Direction.DOWN) {
                    if((getY() % Tile.SIZE) > (Tile.SIZE / 2)) {
                        setY(((int)(getY() / Tile.SIZE + 1)) * Tile.SIZE);
                    } else {
                        setY(((int)(getY() / Tile.SIZE)) * Tile.SIZE);
                    }
                }
                break;
        }

        switch(direction) {
            case UP:
                move(0, -SPEED);
                break;
            case DOWN:
                move(0, SPEED);
                break;
            case LEFT:
                move(-SPEED, 0);
                break;
            case RIGHT:
                move(SPEED, 0);
                break;
            case NONE:
                move(0, 0);
                break;
        }
        if(direction != Direction.NONE) {
            this.direction = direction;
        }
    }

    private void move(float velX, float velY) {
        setVelX(velX);
        setVelY(velY);
        isMoving = velX != 0 || velY != 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    @Override
    public Rectangle getBounds() {
        // TODO: Nevytářet furt nový Rectangle, ale recyklovat
        return new Rectangle((int)getX(), (int)getY(), SIZE, SIZE);
    }

    private boolean handleCollisions() {
        List<Entity> collidingEntities = world.getCollidingEntities(this);
        //System.out.println(collidingEntities.size());

        if(collidingEntities.size() > 0) {
            switch(direction) {
                case UP:
                    int maxY = 0;
                    for(Entity e : collidingEntities) {
                        if(e.getBounds().getMaxY() > maxY) {
                            maxY = (int)e.getBounds().getMaxY();
                        }
                    }
                    setY(maxY);
                    break;
                case DOWN:
                    int minY = world.getMapSize().height;
                    for(Entity e : collidingEntities) {
                        if(e.getBounds().getMinY() < minY) {
                            minY = (int)e.getBounds().getMinY();
                        }
                    }
                    setY(minY - SIZE);
                    break;
                case LEFT:
                    int maxX = 0;
                    for(Entity e : collidingEntities) {
                        if(e.getBounds().getMaxX() > maxX) {
                            maxX = (int)e.getBounds().getMaxX();
                        }
                    }
                    setX(maxX);
                    break;
                case RIGHT:
                    int minX = world.getMapSize().width;
                    for(Entity e : collidingEntities) {
                        if(e.getBounds().getMinX() < minX) {
                            minX = (int)e.getBounds().getMinX();
                        }
                    }
                    setX(minX - SIZE);
                    break;
                case NONE:
                    throw new RuntimeException("Yo, wat the fck?");
            }
            return true;
        }
        return false;
    }
}
