package cz.stu.world.entity;

import cz.stu.gfx.Sprite;
import cz.stu.gfx.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Entity {
    public static final int SIZE = 16;
    private BufferedImage img;

    public Tile(int x, int y, Sprite sprite) {
        super(x, y);
        img = SpriteManager.getInstance().getSprite(sprite);
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
}
