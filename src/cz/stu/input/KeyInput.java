package cz.stu.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyInput extends KeyAdapter {

    public static final Set<Integer> PRESSED = new HashSet<>();

    @Override
    public void keyPressed (KeyEvent e){
        PRESSED.add(e.getKeyCode());
    }

    @Override
    public void keyReleased (KeyEvent e){
        PRESSED.remove(e.getKeyCode());
    }
}
