package cz.stu.world;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;
import cz.stu.gfx.Sprite;
import cz.stu.world.entity.Tile;

import java.awt.*;

public class TileManager implements Updatable, Renderable {
    private Tile[][] tiles;
    private Dimension mapSize;

    public TileManager() {
        init(new Dimension(0, 0));
    }

    public void init(Dimension mapSize) {
        tiles = new Tile[mapSize.height][mapSize.width];
        this.mapSize = mapSize;
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        for(Tile[] line : tiles) {
            for(Tile tile : line) {
                if(tile != null) {
                    tile.render(g, interpolation);
                }
            }
        }
    }

    @Override
    public void update() {
        for(Tile[] line : tiles) {
            for(Tile tile : line) {
                if(tile != null) {
                    tile.update();
                }
            }
        }
    }

    public Tile getTileAt(int row, int column) {
        if(column < 0 || column >= mapSize.width) {
            throw new ArrayIndexOutOfBoundsException(column);
        }
        if(row < 0 || row >= mapSize.height) {
            throw new ArrayIndexOutOfBoundsException(row);
        }
        return tiles[row][column];
    }

    public void setTileAt(int row, int column, Sprite sprite, boolean destroyable, boolean penetrable, boolean passable) {
        if(column < 0 || column >= mapSize.width) {
            throw new ArrayIndexOutOfBoundsException(column);
        }
        if(row < 0 || row >= mapSize.height) {
            throw new ArrayIndexOutOfBoundsException(row);
        }
        tiles[row][column] = new Tile(column * Tile.SIZE, row * Tile.SIZE, sprite, destroyable, penetrable, passable);
    }

    public void removeTile(int row, int column) {
        //System.out.println(row + " " + column);
        tiles[row][column] = null;
    }

    public Dimension getMapSize() {
        return mapSize;
    }
}
