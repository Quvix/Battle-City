package cz.stu.states;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;

public interface GameState extends Updatable, Renderable {
    void reset();
}
