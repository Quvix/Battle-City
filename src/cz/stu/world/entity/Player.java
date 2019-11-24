package cz.stu.world.entity;

import cz.stu.gfx.Sprite;
import cz.stu.gfx.SpriteManager;
import cz.stu.input.KeyInput;
import cz.stu.input.KeyPressedListener;
import cz.stu.world.Direction;
import cz.stu.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Tank implements KeyPressedListener {
    private static final BufferedImage SPRITE_UP = SpriteManager.getInstance().getSprite(Sprite.PLAYER_UP);
    private static final BufferedImage SPRITE_DOWN = SpriteManager.getInstance().getSprite(Sprite.PLAYER_DOWN);
    private static final BufferedImage SPRITE_LEFT = SpriteManager.getInstance().getSprite(Sprite.PLAYER_LEFT);
    private static final BufferedImage SPRITE_RIGHT = SpriteManager.getInstance().getSprite(Sprite.PLAYER_RIGHT);

    public Player(int x, int y, World world) {
        super(x, y, world);
        KeyInput.addKeyPressedListener(this);
    }

    @Override
    public void handle(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_C) {
            fire();
        }
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
            if(KeyInput.PRESSED.contains(KeyEvent.VK_UP)) {
                if(!isMoving() || !isDirectionMatchingKey()) {
                    move(Direction.UP);
                }
            } else if(KeyInput.PRESSED.contains(KeyEvent.VK_DOWN)) {
                if(!isMoving() || !isDirectionMatchingKey()) {
                    move(Direction.DOWN);
                }
            } else if(KeyInput.PRESSED.contains(KeyEvent.VK_LEFT)) {
                if(!isMoving() || !isDirectionMatchingKey()) {
                    move(Direction.LEFT);
                }
            } else if(KeyInput.PRESSED.contains(KeyEvent.VK_RIGHT)) {
                if(!isMoving() || !isDirectionMatchingKey()) {
                    move(Direction.RIGHT);
                }
            } else {
                move(Direction.NONE);
            }

        super.update();
    }

    private boolean isDirectionMatchingKey() {
        switch (getDirection()) {
            case UP:
                return KeyInput.PRESSED.contains(KeyEvent.VK_UP);
            case DOWN:
                return KeyInput.PRESSED.contains(KeyEvent.VK_DOWN);
            case LEFT:
                return KeyInput.PRESSED.contains(KeyEvent.VK_LEFT);
            case RIGHT:
                return KeyInput.PRESSED.contains(KeyEvent.VK_RIGHT);
        }
        return false;
    }
}
