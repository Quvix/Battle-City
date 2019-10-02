package cz.stu.world.entity;

public abstract class DynamicEntity extends Entity {
    private float velX = 0, velY = 0;

    public DynamicEntity(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
}
