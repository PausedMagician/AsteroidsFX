package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    
    private Polygon polygon;
    private double x;
    private double y;
    private double rotation;
    private float radius;
            

    public String getID() {
        return ID.toString();
    }


    public void setPolygonCoordinates(double... coordinates ) {
        this.polygon = new Polygon(coordinates);
    }

    public Polygon getPolygon() {
        return polygon;
    }
       

    public void setX(double x) {
        this.x =x;
    }

    public double getX() {
        return x;
    }

    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
        
    public float getRadius() {
        return this.radius;
    }

    public void setColor(String string) {
        this.polygon.setFill(Paint.valueOf(string));
    }
}
