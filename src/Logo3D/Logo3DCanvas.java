/*
 * Copyright (C) 2017 Eberhard Schneider
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Logo3D;

import java.util.ArrayList;
import java.util.Stack;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author eberh_000
 */
public class Logo3DCanvas extends Canvas {

    static class Line3D {

        private double xStart;
        private double yStart;
        private double zStart;

        private double xEnd;
        private double yEnd;
        private double zEnd;

        public double getxStart() {
            return xStart;
        }

        public void setxStart(double xStart) {
            this.xStart = xStart;
        }

        public double getyStart() {
            return yStart;
        }

        public void setyStart(double yStart) {
            this.yStart = yStart;
        }

        public double getzStart() {
            return zStart;
        }

        public void setzStart(double zStart) {
            this.zStart = zStart;
        }

        public double getxEnd() {
            return xEnd;
        }

        public void setxEnd(double xEnd) {
            this.xEnd = xEnd;
        }

        public double getyEnd() {
            return yEnd;
        }

        public void setyEnd(double yEnd) {
            this.yEnd = yEnd;
        }

        public double getzEnd() {
            return zEnd;
        }

        public void setzEnd(double zEnd) {
            this.zEnd = zEnd;
        }

        public Point3D getStartPoint() {
            return new Point3D(xStart, yStart, zStart);
        }

        public Point3D getEndPoint() {
            return new Point3D(xEnd, yEnd, zEnd);
        }

        public Line3D(double xStart, double yStart, double zStart,
                double xEnd, double yEnd, double zEnd) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.zStart = zStart;

            this.xEnd = xEnd;
            this.yEnd = yEnd;
            this.zEnd = zEnd;
        }

        // see http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
        public static Cylinder drawLine(Point3D origin, Point3D target) {
            Point3D yAxis = new Point3D(0, 1, 0);
            Point3D diff = target.subtract(origin);
            double height = diff.magnitude();

            Point3D mid = target.midpoint(origin);
            Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

            Point3D axisOfRotation = diff.crossProduct(yAxis);
            double angle = Math.acos(diff.normalize().dotProduct(yAxis));
            Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

            Cylinder line = new Cylinder(1, height);

            line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

            return line;
        }
    }

    final int CANVAS_WIDTH = 1000;
    final int CANVAS_HEIGHT = 800;

    final double twopi = 6.2831853071795864769252867;
    final double twopiover360 = twopi / 360.0f;

    TurtleState3D state;

    double deltaAngle;
    double deltaLength;
    double rotation = 0;

    public void setAngle(double a) {
        this.deltaAngle = a;
    }

    public void setLength(double l) {
        this.deltaLength = l;
    }

    public void setRotation(double r) {
        this.rotation = r;
    }

    public Logo3DCanvas(double deltaAngle, double deltaLength) {
        super(1000, 800);
        this.deltaAngle = deltaAngle;
        this.deltaLength = deltaLength;
        state = new TurtleState3D();
    }

    public Group drawString(String s) {
        Group tree = new Group();

        ArrayList<Line3D> lines = new ArrayList<>();
        Stack<TurtleState3D> stack = new Stack<>();

        Point3D location = state.getLocation();
        Point3D oldLocation = new Point3D(location.getX(), location.getY(), location.getZ());

        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);

        double maxX = -100;
        double maxY = -100;
        double minX = 100;
        double minY = 100;

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            switch (c) {
                case 'F':
                case 'G':

                    state.move(deltaLength);

                    double x = state.getLocation().getX();
                    double y = state.getLocation().getY();
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (x < minX) {
                        minX = x;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                    if (y < minY) {
                        minY = y;
                    }

                    lines.add(new Line3D(oldLocation.getX(), oldLocation.getY(), oldLocation.getZ(),
                            state.getLocation().getX(), state.getLocation().getY(), state.getLocation().getZ()));
                    break;
                case '+':
                    state.yaw(deltaAngle);
                    break;
                case '-':
                    state.yaw(-deltaAngle);
                    break;
                case '\\':
                    state.roll(deltaAngle);
                    break;
                case '/':
                    state.roll(-deltaAngle);
                    break;
                case '&':
                    state.pitch(deltaAngle);
                    break;
                case '^':
                    state.pitch(-deltaAngle);
                    break;
                case '[':
                    stack.push(state.copy());
                    break;
                case ']':
                    state = stack.pop();
                    break;
                case 'f':
                    state.move(deltaLength);
                    break;
            }
            oldLocation = state.getLocation();
        }

//        // normalize coordinates
//
//        double scaleX = this.getWidth() / (maxX - minX);
//        double scaleY = this.getHeight() / ( maxY - minY);
//
//        double scale = scaleX < scaleY ? scaleX * .9 : scaleY * .9;
//
//
//        for (Line3 l : lines) {
//            double newX1 = ( l.getStartX() - minX) *  scale;
//            double newX2 = ( l.getEndX() - minX) *  scale;
//            double newY1 = (l.getStartY() - minY) *  scale;
//            double newY2 = (l.getEndY() - minY)  * scale;
//            l.setStartX( newX1 );
//            l.setStartY( newY1 );
//            l.setEndX( newX2 );
//            l.setEndY( newY2 );
//
//            gc.strokeLine( newX1 + 30, newY1 + 30, newX2 +30 , newY2+30);
//
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        for (Line3D line : lines) {
            Cylinder c = Line3D.drawLine(line.getStartPoint(), line.getEndPoint());
            c.setMaterial(blueMaterial);
            tree.getChildren().add(c);
        }

        return tree;

    }

}
