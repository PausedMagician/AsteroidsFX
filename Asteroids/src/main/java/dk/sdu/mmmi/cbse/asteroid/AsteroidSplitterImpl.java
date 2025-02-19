package dk.sdu.mmmi.cbse.asteroid;

import java.util.Random;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Random rnd = new Random();
        int numSplitAsteroids = rnd.nextInt(3) + 1;
        for (int i = 0; i < numSplitAsteroids; i++) {
            Entity splitAsteroid = new Asteroid();
            splitAsteroid.setRadius(e.getRadius() / 2);
            splitAsteroid.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
            splitAsteroid.setX(e.getX());
            splitAsteroid.setY(e.getY());
            splitAsteroid.setRotation(e.getRotation() + (rnd.nextInt(3) - 1) * 45);
            world.addEntity(splitAsteroid);
        }
        world.removeEntity(e);
    }

}
