package cz.stu.state;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameStateManager implements Renderable, Updatable {
    private static GameStateManager INSTANCE = new GameStateManager();
    private GameState currentState = null;
    private Map<State, GameState> states = new HashMap<>();

    private GameStateManager() {
    }

    public static GameStateManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GameStateManager();
        }
        return INSTANCE;
    }

    public GameState changeState(State state) {
        return currentState = states.computeIfAbsent(state, (value)-> {
            switch(state) {
                case PLAY:
                    return new PlayState();
                default:
                    throw new RuntimeException("State not supported!");
            }
        });
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        currentState.render(g, interpolation);
    }

    @Override
    public void update() {
        currentState.update();
    }

    public void resetCurrentState() {
        currentState.reset();
    }
}
