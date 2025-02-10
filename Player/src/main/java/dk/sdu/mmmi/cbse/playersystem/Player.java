package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private long lastShot = 0;

    public void setLastShot(long lastShot) {
        this.lastShot = lastShot;
    }

    public long getLastShot() {
        return lastShot;
    }
}
