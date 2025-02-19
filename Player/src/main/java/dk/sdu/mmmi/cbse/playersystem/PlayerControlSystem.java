package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
            
        for (Entity ent : world.getEntities(Player.class)) {
            Player player = (Player) ent;
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);                
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);                
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if(gameData.getKeys().isDown(GameKeys.SPACE)) {     
                long now = System.currentTimeMillis();
                long timeSinceLastFire = now - player.getLastShot();
                if (timeSinceLastFire < 200) {
                    return;
                }
                player.setLastShot(now);
                System.out.println("Time since last fire: " + timeSinceLastFire);
                getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> {world.addEntity(spi.createBullet(player, gameData));}
                );
            }
            if (player.getX() < 1) {
                player.setX(gameData.getDisplayWidth());
            }
            else if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(1);
            }
            if (player.getY() < 1) {
                player.setY(gameData.getDisplayHeight());
            }
            else if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(1);
            }

        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
