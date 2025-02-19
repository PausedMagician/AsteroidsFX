package dk.sdu.mmmi.cbse.asteroid;

import java.util.List;
import java.util.Random;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService, AsteroidSPI {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();
    private Long lastSpawn;

    @Override
    public void process(GameData gameData, World world) {

        List<Entity> asteroids = world.getEntities(Asteroid.class);

        if (asteroids.size() < 5 && (lastSpawn == null || System.currentTimeMillis() - lastSpawn > 5000)) {
            lastSpawn = System.currentTimeMillis();
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        } else if (asteroids.isEmpty()) {
            return;
        }

        for (Entity asteroid : asteroids) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            int neg = (int) (0 - asteroid.getRadius() * 1.5);
            int width = (int) (gameData.getDisplayWidth() + asteroid.getRadius() * 1.5);
            int height = (int) (gameData.getDisplayHeight() + asteroid.getRadius() * 1.5);

            if (asteroid.getX() < neg) {
                asteroid.setX(width);
            } else if (asteroid.getX() > width) {
                asteroid.setX(neg);
            }
            if (asteroid.getY() < neg) {
                asteroid.setY(height);
            } else if (asteroid.getY() > height) {
                asteroid.setY(neg);
            }
        }

    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }

    @Override
    public Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();

        Random rnd = new Random();

        int size = rnd.nextInt(10) + 10;
        asteroid.setRadius(size);
        double radians = Math.toRadians(rnd.nextInt(360));
        // Create a polygon with 3-6 points
        int points = rnd.nextInt(4) + 3;
        double[] poly = new double[points * 2];
        for (int i = 0; i < points; i++) {
            double x = Math.cos(radians + Math.toRadians(360 / points * i)) * size;
            double y = Math.sin(radians + Math.toRadians(360 / points * i)) * size;
            poly[i * 2] = x;
            poly[i * 2 + 1] = y;
        }
        asteroid.setPolygonCoordinates(poly);


        if (rnd.nextBoolean()) {
            // Spawn on left or right side
            // Ouside the screen with 1.5 * radius
            if (rnd.nextBoolean()) {
                asteroid.setX(rnd.nextBoolean() ? 0 - size * 1.5 : gameData.getDisplayWidth() + size * 1.5);
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
            } else {
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(rnd.nextBoolean() ? 0 - size * 1.5 : gameData.getDisplayHeight() + size * 1.5);
            }
        } else {
            // Spawn on top or bottom side
            // Ouside the screen with 1.5 * radius
            if (rnd.nextBoolean()) {
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(rnd.nextBoolean() ? 0 - size * 1.5 : gameData.getDisplayHeight() + size * 1.5);
            } else {
                asteroid.setX(rnd.nextBoolean() ? 0 - size * 1.5 : gameData.getDisplayWidth() + size * 1.5);
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
            }
        }

        asteroid.setRotation((float) Math.random() * 360);

        return asteroid;
    }

    @Override
    public void splitAsteroid(Entity asteroid, World world) {
        asteroidSplitter.createSplitAsteroid(asteroid, world);
    }


}
