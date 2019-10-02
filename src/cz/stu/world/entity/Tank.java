package cz.stu.world.entity;

import java.awt.*;

public abstract class Tank extends DynamicEntity {
    protected static final int SIZE = 32;
    protected static final float SPEED = 2;
    private Direction direction = Direction.UP;
    private boolean isMoving = false;


    public Tank(int x, int y) {
        super(x, y);
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
    }

    @Override
    public boolean intersects(Entity entity) {
        return false;
    }

    protected void move(Direction direction) {
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
}
