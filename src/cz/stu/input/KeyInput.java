package cz.stu.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyInput extends KeyAdapter {
    public static final Set<Integer> PRESSED = new HashSet<>();
    private static List<KeyPressedListener> keyPressedListeners = new ArrayList<>();
    private static List<KeyReleasedListener> keyReleasedListeners = new ArrayList<>();

    @Override
    public void keyPressed (KeyEvent e) {
        PRESSED.add(e.getKeyCode());
        fireKeyPressedEvent(e);
    }

    @Override
    public void keyReleased (KeyEvent e){
        PRESSED.remove(e.getKeyCode());
        fireKeyReleasedEvent(e);
    }

    public static void addKeyPressedListener(KeyPressedListener l) {
        keyPressedListeners.add(l);
    }

    public static void removeKeyPressedListener(KeyPressedListener l) {
        keyPressedListeners.remove(l);
    }

    private void fireKeyPressedEvent(KeyEvent e) {
        for(KeyPressedListener l : keyPressedListeners) {
            l.handle(e);
        }
    }

    public static void addKeyReleaseListener(KeyReleasedListener l) {
        keyReleasedListeners.add(l);
    }

    public static void removeKeyReleasedListener(KeyReleasedListener l) {
        keyReleasedListeners.add(l);
    }

    public void fireKeyReleasedEvent(KeyEvent e) {
        for(KeyReleasedListener l : keyReleasedListeners) {
            l.handle(e);
        }
    }
}
