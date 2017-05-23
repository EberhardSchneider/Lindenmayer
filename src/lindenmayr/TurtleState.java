/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lindenmayr;

/**
 *
 * @author eberh_000
 */
public class TurtleState {
    
    private double x;
    private double y;
    private double angle;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public void setLocation( double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public TurtleState( double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public void turnRight( double deltaAngle ) {
        angle = (angle - deltaAngle) % 360;
    }
    
    public void turnLeft( double deltaAngle ) {
        angle = (angle + deltaAngle) % 360;
    }
    
    public void move( double deltaX, double deltaY ) {
        x += deltaX;
        y += deltaY;
    }
    
    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " angle: " + angle;
    }
    
}
