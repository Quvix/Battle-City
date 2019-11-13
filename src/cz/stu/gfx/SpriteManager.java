package cz.stu.gfx;

import cz.stu.util.ResourceReader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    private static SpriteManager INSTANCE = new SpriteManager();
    private Map<Sprite, BufferedImage> sprites;
    private Map<SpriteAnimation, BufferedImage[]> spriteAnimations;

    public static SpriteManager getInstance() {
        return INSTANCE;
    }

    private SpriteManager() {
        sprites = new HashMap<>();
        spriteAnimations = new HashMap<>();
        loadSprites();
    }

    public void loadSprites() {
        sprites.put(Sprite.BRICK, ResourceReader.loadImage("sprites/brick.png"));
    }

    public BufferedImage getSprite(Sprite sprite) {
        return sprites.get(sprite);
    }

    public Animation getSpriteAnimations(SpriteAnimation sprite) {
        return new Animation(spriteAnimations.get(sprite));
    }
}
