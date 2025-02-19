package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author corfixen
 */
public interface AsteroidSPI {
    Entity createAsteroid(GameData gameData);
    void splitAsteroid(Entity asteroid, World world);
}
