package cz.stu.world;

import cz.stu.core.Game;
import cz.stu.core.Renderable;
import cz.stu.core.Updatable;
import cz.stu.world.entity.Eagle;
import cz.stu.world.entity.Enemy;
import cz.stu.world.entity.Player;
import cz.stu.world.entity.Tank;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.Random;


public class EntityManager implements Renderable, Updatable {
    private static final int ENEMY_SPAWN_INTERVAL = Game.UPDATES_PER_SECOND * 3;

    private World world;
    private Player player;
    private Eagle eagle;
    private List<Enemy> enemies = new ArrayList<>();
    private int enemiesToSpawn = 0;
    private int timerToSpawnEnemy = ENEMY_SPAWN_INTERVAL;

    private List<Point> enemySpawnPoints = new ArrayList<>();

    public EntityManager(World world) {
        this.world = world;
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        for(Enemy e : enemies) {
            e.render(g, interpolation);
        }

        if(eagle != null) {
            eagle.render(g, interpolation);
        }
        if(player != null) {
            player.render(g, interpolation);
        }
    }

    @Override
    public void update() {
        timerToSpawnEnemy--;

        if(timerToSpawnEnemy == 0) {
            spawnEnemy();
            timerToSpawnEnemy = ENEMY_SPAWN_INTERVAL;
        }

        enemies.forEach(Tank::update);

        if(eagle != null) {
            eagle.update();
        }
        if(player != null) {
            player.update();
        }

        enemies.removeIf(Enemy::isDestroyed);

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEagle(Eagle eagle) {
        this.eagle = eagle;
    }

    public Eagle getEagle() {
        return eagle;
    }

    public void addEnemySpawnPoint(Point point) {
        enemySpawnPoints.add(point);
    }

    public List<Point> getEnemySpawnPoints() {
        return enemySpawnPoints;
    }

    public void spawnEnemy() {
        if(enemiesToSpawn > 0) {
            Point spawnPoint = getRandomEnemySpawnPoint();
            enemies.add(new Enemy((int)spawnPoint.getX(), (int)spawnPoint.getY(), world));
            enemiesToSpawn--;
            System.out.println("Enemy spawned at " + spawnPoint);
        }
    }

    public Point getRandomEnemySpawnPoint() {
        Random rand = new Random();
        return enemySpawnPoints.get(rand.nextInt(enemySpawnPoints.size()));
    }

    public void setEnemiesToSpawn(int enemiesToSpawn) {
        this.enemiesToSpawn = enemiesToSpawn;
    }

    public int getEnemiesToSpawn() {
        return enemiesToSpawn;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
