package cz.stu.world;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;
import cz.stu.gfx.Sprite;
import cz.stu.util.ResourceReader;
import cz.stu.world.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements Renderable, Updatable {
    private EntityManager em;
    private TileManager tm;
    private Dimension mapSize = new Dimension();

    public World() {
        em = new EntityManager();
        tm = new TileManager();
        try {
            loadMap("maps/test.map");
        } catch (InvalidMapException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        tm.render(g, interpolation);
        em.render(g, interpolation);
    }

    @Override
    public void update() {
        tm.update();
        em.update();
    }

    public void spawnPlayer(int x, int y) {
        em.setPlayer(new Player(x, y, this));
    }

    public void loadMap(String filename) throws InvalidMapException {
        List<String> lines = ResourceReader.parseLines(filename);
        int lineCount = 0;
        int rows = 0;
        int columns = 0;

        int row = 0;
        int column = 0;

        for(String line : lines) {
            lineCount++;
            if(lineCount == 1) {
                rows = Integer.parseInt(line);
                continue;
            } else if(lineCount == 2) {
                columns = Integer.parseInt(line);
                tm.init(new Dimension(columns, rows));
                mapSize.height = rows * Tile.SIZE;
                mapSize.width = columns * Tile.SIZE;
                continue;
            }
            for(String token : line.split(" ")) {
                String id = token;
                switch(id) {
                    case "0":
                        break;
                    case "1":
                        tm.setTileAt(row, column, Sprite.BRICK);
                        break;
                    case "P":
                        // spawn player or should I?
                        break;
                    default:
                        // "Unexpected entity ID"
                        throw new InvalidMapException();
                }
                column++;
            }
            row++;
            if(column != columns) {
                throw new InvalidMapException();
            }
            column = 0;
        }

        if(row != rows) {
            throw new InvalidMapException();
        }
    }

    public Dimension getMapSize() {
        return mapSize;
    }

    public List<Entity> getCollidingEntities(Entity target) {
        List<Entity> result = new ArrayList<>();

        for(int row = 0; row < tm.getMapSize().height; row++) {
            for(int column = 0; column < tm.getMapSize().width; column++) {
                if(tm.getTileAt(row, column) != null) {
                    if(target.intersects(tm.getTileAt(row, column))) {
                        result.add(tm.getTileAt(row, column));
                    }
                }
            }
        }
        return result;
    }
}
