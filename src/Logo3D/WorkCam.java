/*
 * The MIT License
 *
 * Copyright 2017 eberh_000.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Logo3D;

import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author Eberhard Schneider
 */
public class WorkCam extends PerspectiveCamera {

    // angle from x-Axis to camera axis in xz plane
    double alpha;
    // angle from xz plane to camera axis
    double beta;
    // distance from origin to camera
    double distance;

    Rotate rotX;
    Rotate rotY;
    Rotate rotZ;

    Translate translate;

    // for the new scene mouse dragged listener
    double mousePosX;
    double mousePosY;
    double oldMousePosX;
    double oldMousePosY;
    double deltaX;
    double deltaY;

    public void setAlpha(double alpha) {
        this.alpha = alpha;
        setCameraPositionFromAngles();
    }

    public void setBeta(double beta) {
        this.beta = beta;
        setCameraPositionFromAngles();
    }

    public WorkCam(Node graphics) {
        super(true);
        rotX = new Rotate();
        rotX.setAxis(new Point3D(1, 0, 0));

        rotY = new Rotate();
        rotY.setAxis(new Point3D(0, 1, 0));

        translate = new Translate();

        this.getTransforms().addAll(translate);

        alpha = 0;
        beta = -1.2;
        distance = 200;

        addListeners(graphics);
        //setCameraPositionFromAngles(alpha, beta, distance);

    }

    public void setAngleX(double angle) {
        rotX.setAngle(angle);
    }

    public void setAngleY(double angle) {
        rotY.setAngle(angle);
    }

    private void addListeners(Node n) {

        System.out.println(n.toString());

        n.setOnMousePressed((MouseEvent event) -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            oldMousePosX = mousePosX;
            oldMousePosY = mousePosY;

            System.out.println("Mouse pressed");
        });

        n.setOnMouseDragged((MouseEvent event) -> {
            oldMousePosX = mousePosX;
            oldMousePosY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            deltaX = mousePosX - oldMousePosX;
            deltaY = mousePosY - oldMousePosY;

            double modifier = 1.0;
            double modifierFactor = 0.1;

            if (event.isControlDown()) {
                modifier = 0.1;
            }

            if (event.isShiftDown()) {
                modifier = 10.0;
            }

            if (event.isPrimaryButtonDown()) {
                alpha += deltaX * modifier * modifierFactor;
                beta += deltaY * modifier * modifierFactor;
                setCameraPositionFromAngles();
            }
        });
    }

    private void setCameraPositionFromAngles() {

        // setAngleY(-Math.toDegrees(alpha));
        //setAngleY(Math.toDegrees(-alpha));
        double yCoord = -distance * Math.sin(beta);
        double radiusXZ = distance * Math.cos(beta);

        double zCoord = Math.sin(alpha) * radiusXZ;
        double xCoord = Math.cos(alpha) * radiusXZ;

        //rotX.setAxis(new Point3D(-zCoord, 0, xCoord));
//        if (zCoord < 0) {
//            rotX.setAngle(Math.toDegrees(-beta));
//        } else {
//            rotX.setAngle(Math.toDegrees(beta));
//        }
        translate.setX(xCoord);
        translate.setY(yCoord);
        translate.setZ(zCoord);

        System.out.println("X: " + translate.getX() + ", " + "Y: " + translate.getY() + ", Z: " + translate.getZ() + " alpha: " + alpha + "  beta: " + beta);

    }

}
