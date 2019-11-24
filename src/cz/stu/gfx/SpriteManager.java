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
        createSpriteAnimations();
    }

    public void loadSprites() {
        sprites.put(Sprite.BRICK, ResourceReader.loadImage("sprites/brick.png"));
        sprites.put(Sprite.STEEL, ResourceReader.loadImage("sprites/steel.png"));

        sprites.put(Sprite.UNDAMAGED_EAGLE, ResourceReader.loadImage("sprites/eagle_undamaged.png"));
        sprites.put(Sprite.DESTOYED_EAGLE, ResourceReader.loadImage("sprites/eagle_destroyed.png"));

        sprites.put(Sprite.EXPLOSION_1, ResourceReader.loadImage("sprites/bullet_explosion1.png"));
        sprites.put(Sprite.EXPLOSION_2, ResourceReader.loadImage("sprites/bullet_explosion2.png"));
        sprites.put(Sprite.EXPLOSION_3, ResourceReader.loadImage("sprites/bullet_explosion3.png"));

        sprites.put(Sprite.PLAYER_UP, ResourceReader.loadImage("sprites/player_up.png"));
        sprites.put(Sprite.PLAYER_DOWN, ResourceReader.loadImage("sprites/player_down.png"));
        sprites.put(Sprite.PLAYER_LEFT, ResourceReader.loadImage("sprites/player_left.png"));
        sprites.put(Sprite.PLAYER_RIGHT, ResourceReader.loadImage("sprites/player_right.png"));

        sprites.put(Sprite.ENEMY_UP, ResourceReader.loadImage("sprites/enemy_up.png"));
        sprites.put(Sprite.ENEMY_DOWN, ResourceReader.loadImage("sprites/enemy_down.png"));
        sprites.put(Sprite.ENEMY_LEFT, ResourceReader.loadImage("sprites/enemy_left.png"));
        sprites.put(Sprite.ENEMY_RIGHT, ResourceReader.loadImage("sprites/enemy_right.png"));
    }

    public void createSpriteAnimations() {
        spriteAnimations.put(SpriteAnimation.EXPLOSION, new BufferedImage[] {
                getSprite(Sprite.EXPLOSION_1),
                getSprite(Sprite.EXPLOSION_2),
                getSprite(Sprite.EXPLOSION_3)

        });
    }

    public BufferedImage getSprite(Sprite sprite) {
        return sprites.get(sprite);
    }

    public Animation getSpriteAnimations(SpriteAnimation sprite) {
        return new Animation(spriteAnimations.get(sprite));
    }
}
