/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logo3D;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Eberhard Schneider
 */
public class TurtleState3D {

    Point3D location;

    Point3D heading;
    Point3D left;
    Point3D up;

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public void setHeading(Point3D heading) {
        this.heading = heading;
    }

    public void setLeft(Point3D left) {
        this.left = left;
    }

    public void setUp(Point3D up) {
        this.up = up;
    }

    public TurtleState3D copy() {

        TurtleState3D cloned = new TurtleState3D();
        cloned.setLocation(location);
        cloned.setHeading(heading);
        cloned.setLeft(left);
        cloned.setUp(up);

        return cloned;
    }

    public TurtleState3D() {
        location = new Point3D(0, 0, 0);
        heading = new Point3D(1, 0, 0);
        left = new Point3D(0, 1, 0);
        up = new Point3D(0, 0, 1);
    }

    public void roll(double angle) {
        // rotate around vector heading
        left = rotateVectorAroundAxis(left, heading, angle);
        up = rotateVectorAroundAxis(up, heading, angle);

    }

    public void pitch(double angle) {
        // rotate around vector left
        heading = rotateVectorAroundAxis(heading, left, angle);
        up = rotateVectorAroundAxis(up, left, angle);

    }

    public void yaw(double angle) {
        // rotate around vector up
        heading = rotateVectorAroundAxis(heading, up, angle);
        left = rotateVectorAroundAxis(left, up, angle);
    }

    public void move(double length) {
        location.add(heading.multiply(length));
    }

    // see http://inside.mines.edu/fs_home/gmurray/ArbitraryAxisRotation/
    private Point3D rotateVectorAroundAxis(Point3D vector, Point3D axis, double theta) {
        if (Math.abs(axis.magnitude()) - 1 > 0.001) {
            System.err.println("TurtleState.rotatePointAroundAxis: axis has to have magnitude of 1");
            System.err.println("Magnitude = " + axis.magnitude());
            return null;
        }

        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);

        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        double u = axis.getX();
        double v = axis.getY();
        double w = axis.getZ();

        double xRot = u * (u * x + v * y + w * z) * (1 - cosTheta) + x * cosTheta + (-w * y + v * z) * sinTheta;
        double yRot = v * (u * x + v * y + w * z) * (1 - cosTheta) + y * cosTheta + (w * x - u * z) * sinTheta;
        double zRot = w * (u * x + v * y + w * z) * (1 - cosTheta) + z * cosTheta + (-v * x + u * y) * sinTheta;

        Point3D result = new Point3D(xRot, yRot, zRot).normalize();
        System.out.println("Rotation result: " + result.toString());
        return result;
    }

}
