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
package matrix;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Eberhard Schneider
 */
public class Matrix {

    static class Column {

        public ArrayList<Double> entries = new ArrayList<>();
    }

    ArrayList<Column> columns = new ArrayList<>();

    public Matrix(Double[][] entries) {
        for (Double[] c : entries) {
            Column currentColumn = new Column();
            currentColumn.entries.addAll(Arrays.asList(c));
            columns.add(currentColumn);
        }
    }

    @Override
    public String toString() {
        Double[][] matrix = toArray();

        String s = "";

        for (Double[] matrix1 : matrix) {
            for (Double matrix11 : matrix1) {
                s += "  " + matrix11 + "  ";
            }
            s += "\n";
        }

        return s;

    }

    public Double[][] toArray() {

        int n = columns.size();
        int m = columns.get(0).entries.size();

        Double[][] matrix = new Double[m][n];

        for (int c = 0; c < n; c++) {
            Column currentColumn = columns.get(c);
            for (int r = 0; r < m; r++) {
                matrix[r][c] = currentColumn.entries.get(r);
            }
        }

        return matrix;

    }
}
