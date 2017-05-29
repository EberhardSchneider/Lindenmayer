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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;

public class Cube extends Group {

    final Rotate rx = new Rotate(0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

    public Cube(double size, Color back, Color bottom, Color right, Color left, Color top, Color front, double shade) {
        getTransforms().addAll(rz, ry, rx);
        getChildren().addAll(
                RectangleBuilder.create() // back face
                .width(size).height(size)
                .fill(back.deriveColor(0.0, 1.0, (1 - 0.5 * shade), 1.0))
                .translateX(-0.5 * size)
                .translateY(-0.5 * size)
                .translateZ(0.5 * size)
                .smooth(true)
                .stroke(Color.BLACK)
                .build(),
                RectangleBuilder.create() // bottom face
                .width(size).height(size)
                .fill(bottom.deriveColor(0.0, 1.0, (1 - 0.4 * shade), 1.0))
                .translateX(-0.5 * size)
                .translateY(0)
                .rotationAxis(Rotate.X_AXIS)
                .rotate(90)
                .smooth(true)
                .stroke(Color.BLACK)
                .build(),
                RectangleBuilder.create() // right face
                .width(size).height(size)
                .fill(right.deriveColor(0.0, 1.0, (1 - 0.3 * shade), 1.0))
                .translateX(-1 * size)
                .translateY(-0.5 * size)
                .rotationAxis(Rotate.Y_AXIS)
                .rotate(90)
                .smooth(true)
                .stroke(Color.BLACK)
                .build(),
                RectangleBuilder.create() // left face
                .width(size).height(size)
                .fill(left.deriveColor(0.0, 1.0, (1 - 0.2 * shade), 1.0))
                .translateX(0)
                .translateY(-0.5 * size)
                .rotationAxis(Rotate.Y_AXIS)
                .rotate(90)
                .smooth(true)
                .stroke(Color.BLACK)
                .build(),
                RectangleBuilder.create() // top face
                .width(size).height(size)
                .fill(top.deriveColor(0.0, 1.0, (1 - 0.1 * shade), 1.0))
                .translateX(-0.5 * size)
                .translateY(-1 * size)
                .rotationAxis(Rotate.X_AXIS)
                .rotate(90)
                .smooth(true)
                .stroke(Color.BLACK)
                .build(),
                RectangleBuilder.create() // front face
                .width(size).height(size)
                .fill(front)
                .translateX(-0.5 * size)
                .translateY(-0.5 * size)
                .translateZ(-0.5 * size)
                .smooth(true)
                .stroke(Color.BLACK)
                .build()
        );
    }
}
