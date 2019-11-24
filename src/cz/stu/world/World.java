package cz.stu.world;

import cz.stu.core.Renderable;
import cz.stu.core.Updatable;
import cz.stu.gfx.Sprite;
import cz.stu.util.ResourceReader;
import cz.stu.world.entity.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements Renderable, Updatable {
    private EntityManager em;
    private TileManager tm;
    private Dimension mapSize = new Dimension();

    public World() {
        em = new EntityManager(this);
        tm = new TileManager();
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

    private void spawnPlayer(int x, int y) {
        em.setPlayer(new Player(x, y, this));
    }

    private void spawnEagle(int x, int y) {
        em.setEagle(new Eagle(x, y));
    }

    // pěkně hnusná dlouhá metoda, ale jsem línej ji rozdělovat na menší kusy :(
    public void loadMap(String filename) throws InvalidMapException {
        List<String> lines = ResourceReader.parseLines(filename);
        int lineCount = 0;
        int rows = 0;
        int columns = 0;

        int row = 0;
        int column = 0;

        int playerTokens = 0;
        Point playerPosition = new Point(-1,-1);

        int eagleTokens = 0;
        Point eaglePosition = new Point(-1,-1);

        Map<Point, Integer> enemySpawnPositions = new HashMap<>();
        em = new EntityManager(this);

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
            } else if(lineCount == 3) {
                em.setEnemiesToSpawn(Integer.parseInt(line));
                continue;
            }
            for(String token : line.split(" ")) {
                String id = token;
                switch(id) {
                    case "0":
                        break;
                    case "1":
                        tm.setTileAt(row, column, Sprite.BRICK, true, false, false);
                        break;
                    case "2":
                        tm.setTileAt(row, column, Sprite.STEEL, false, false, false);
                        break;
                    case "P":
                        playerTokens++;
                        if(playerTokens <= 4) {
                            switch (playerTokens) {
                                case 1:
                                    playerPosition = new Point(column, row);
                                    break;
                                case 2:
                                    if(playerPosition.x + 1 != column || playerPosition.y != row) {
                                        throw new InvalidMapException();
                                    }
                                    break;
                                case 3:
                                    if(playerPosition.x != column || playerPosition.y + 1 != row) {
                                        throw new InvalidMapException();
                                    }
                                    break;
                                case 4:
                                    if(playerPosition.x + 1 != column || playerPosition.y + 1 != row) {
                                        throw new InvalidMapException();
                                    } else {
                                        spawnPlayer((column - 1) * Tile.SIZE, (row - 1) * Tile.SIZE);
                                    }
                                    break;
                            }
                        } else {
                            throw new InvalidMapException();
                        }
                        break;
                    case "E":
                        eagleTokens++;
                        if(eagleTokens <= 4) {
                            switch (eagleTokens) {
                                case 1:
                                    eaglePosition = new Point(column, row);
                                    break;
                                case 2:
                                    if(eaglePosition.x + 1 != column || eaglePosition.y != row) {
                                        throw new InvalidMapException();
                                    }
                                    break;
                                case 3:
                                    if(eaglePosition.x != column || eaglePosition.y + 1 != row) {
                                        throw new InvalidMapException();
                                    }
                                    break;
                                case 4:
                                    if(eaglePosition.x + 1 != column || eaglePosition.y + 1 != row) {
                                        throw new InvalidMapException();
                                    } else {
                                        spawnEagle((column - 1) * Tile.SIZE, (row - 1) * Tile.SIZE);
                                    }
                                    break;
                            }
                        } else {
                            throw new InvalidMapException();
                        }
                        break;
                    case "S":
                        Point spawnPoint = new Point(column, row);
                        if(enemySpawnPositions.containsKey(new Point(spawnPoint.x - 1, spawnPoint.y))) {
                            spawnPoint.translate( - 1, 0);
                            enemySpawnPositions.compute(spawnPoint, (key, value) -> ++value);
                        } else if(enemySpawnPositions.containsKey(new Point(spawnPoint.x, spawnPoint.y - 1))) {
                            spawnPoint.translate( 0, -1);
                            enemySpawnPositions.compute(spawnPoint, (key, value) -> ++value);
                        } else if(enemySpawnPositions.containsKey(new Point(spawnPoint.x - 1, spawnPoint.y - 1))) {
                            spawnPoint.translate( - 1, -1);
                            enemySpawnPositions.compute(spawnPoint, (key, value) -> ++value);
                        } else {
                            if(enemySpawnPositions.get(spawnPoint) == null) {
                                enemySpawnPositions.put(spawnPoint, 1);
                            } else {
                                throw new InvalidMapException();
                            }
                        }
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
        if(playerTokens < 4 && playerTokens != 0) {
            throw new InvalidMapException();
        }

        if(eagleTokens < 4 && eagleTokens != 0) {
            throw new InvalidMapException();
        }

        for(Map.Entry<Point, Integer> e : enemySpawnPositions.entrySet()) {
            if(e.getValue() == 4) {
                Point p = e.getKey();
                p.move((int)p.getX() * Tile.SIZE, (int)p.getY() * Tile.SIZE);
                em.addEnemySpawnPoint(p);
            } else {
                throw new InvalidMapException();
            }
        }

        // TODO : invalid map jestli na mapě není žádný spawn pro nepřátelské tanky
    }

    public Dimension getMapSize() {
        return mapSize;
    }

    public List<Tile> getCollidingTiles(Entity target) {
        List<Tile> result = new ArrayList<>();

        // TODO: Neiterovat zbytečně všechny bloky, stačí zkontrolovat jen ty co jsou blízko
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

    public List<Entity> getCollidingEntities(Entity target) {
        List<Entity> result = new ArrayList<>();

        if(em.getEagle().intersects(target)) {
            result.add(em.getEagle());
        }

        for(Enemy e : em.getEnemies()) {
            if(e.intersects(target)) {
                result.add(e);
            }
        }

        return result;
    }

    public void destroyTile(Tile tile) {
        tm.removeTile((int)tile.getY() / Tile.SIZE, (int)tile.getX() / Tile.SIZE);
    }

    public GameProgress getGameProgress() {
        if(em.getEagle().isDestroyed()) {
            return GameProgress.LOST;
        } else if(em.getEnemies().size() == 0 && em.getEnemiesToSpawn() == 0) {
            return GameProgress.WON;
        } else {
            return GameProgress.RUNNING;
        }
    }
}
