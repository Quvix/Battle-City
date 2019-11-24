package cz.stu.world.entity;

import cz.stu.gfx.Sprite;
import cz.stu.gfx.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Eagle extends Entity {
    public static final int SIZE = Tile.SIZE * 2;
    private static final BufferedImage UNDAMAGED_SPRITE = SpriteManager.getInstance().getSprite(Sprite.UNDAMAGED_EAGLE);
    private static final BufferedImage DESTROYED_SPRITE = SpriteManager.getInstance().getSprite(Sprite.DESTOYED_EAGLE);
    private BufferedImage img;
    private boolean destroyed = false;

    public Eagle(int x, int y) {
        super(x, y);
        img = UNDAMAGED_SPRITE;
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)getX(), (int)getY(), SIZE, SIZE);
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        g.drawImage(img, (int)getX(), (int)getY(), SIZE, SIZE, null);
    }

    @Override
    public void update() {

    }

    public void destroy() {
        img = DESTROYED_SPRITE;
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
