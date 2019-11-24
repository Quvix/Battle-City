package cz.stu.world.entity;

import cz.stu.gfx.Sprite;
import cz.stu.gfx.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Entity {
    public static final int SIZE = 16;
    private BufferedImage img;
    private boolean destroyable;
    private boolean penetrable;
    private boolean passable;

    public Tile(int x, int y, Sprite sprite, boolean destroyable, boolean penetrable, boolean passable) {
        super(x, y);

        img = SpriteManager.getInstance().getSprite(sprite);
        this.destroyable = destroyable;
        this.penetrable = penetrable;
        this.passable = passable;
    }

    @Override
    public Rectangle getBounds() {
        // TODO: Nevytářet furt nový Rectangle, ale recyklovat
        return new Rectangle((int)getX(), (int)getY(), SIZE, SIZE);
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        /*g.setColor(Color.PINK);
        g.fillRect((int)getX(), (int)getY(), SIZE, SIZE);*/
        g.drawImage(img, (int)getX(), (int)getY(), SIZE, SIZE, null);
    }

    @Override
    public void update() {

    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public boolean isPenetrable() {
        return penetrable;
    }

    public boolean isPassable() {
        return passable;
    }
}
