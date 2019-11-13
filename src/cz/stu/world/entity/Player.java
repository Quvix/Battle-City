package cz.stu.world.entity;

import cz.stu.input.KeyInput;
import cz.stu.input.KeyPressedListener;
import cz.stu.world.World;

import java.awt.event.KeyEvent;

public class Player extends Tank implements KeyPressedListener {
    public Player(int x, int y, World world) {
        super(x, y, world);
        KeyInput.addKeyPressedListener(this);
    }

    @Override
    public void handle(KeyEvent e) {

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
