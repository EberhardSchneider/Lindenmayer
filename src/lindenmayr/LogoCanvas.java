/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lindenmayr;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author eberh_000
 */
public class LogoCanvas extends Canvas {
    
    final int CANVAS_WIDTH = 1000;
    final int CANVAS_HEIGHT = 800;
    
    final double twopi = 6.2831853071795864769252867;
    final double twopiover360 = twopi / 360.0f;
    
    TurtleState state;
    
    double deltaAngle;
    double deltaLength;
    double rotation = 0;
    
    public void setAngle( double a ) {
        this.deltaAngle = a;
    }
    
    public void setLength( double l ) {
        this.deltaLength = l;
    }
    
    public void setRotation( double r ) {
        this.rotation = r;
    }
    

    
    public LogoCanvas( double deltaAngle, double deltaLength ) {
        super( 1000, 800);
        this.deltaAngle = deltaAngle;
        this.deltaLength = deltaLength;  
        state = new TurtleState( 0.0, 0.0, 90.0 );
    }
    
    public void drawString( String s ) {
        
        ArrayList<Line> lines = new ArrayList<>();
        Stack<TurtleState> stack = new Stack<>();
        
        state.setLocation( 0, 0);
        state.setAngle( rotation );
        
        Point2D.Double oldCoord = new Point2D.Double( state.getX(), state.getY() );
        
        
        GraphicsContext gc = this.getGraphicsContext2D();
        
        
        
        gc.setFill( Color.BLACK );
        gc.fillRect( 0, 0, this.getWidth(), this.getHeight() );
        gc.setStroke( Color.WHITE );
        gc.setLineWidth( 2 );
        
        double maxX = -100;
        double maxY = -100;
        double minX = 100;
        double minY = 100;
        
        for ( int i = 0; i < s.length(); i++ ) {
            Character c = s.charAt( i );
           
            switch (c) {
                case 'F':
                case 'G':
                    double deltaX =  Math.cos( state.getAngle() * twopiover360 ) * deltaLength;
                    double deltaY = Math.sin( state.getAngle() * twopiover360 ) * deltaLength;
                    
                    state.move( deltaX, deltaY );
                    
                    double x = state.getX();
                    double y = state.getY();
                    if ( x > maxX ) maxX = x;
                    if ( x < minX ) minX = x;
                    if ( y > maxY ) maxY = y;
                    if ( y < minY ) minY = y;
                    
                    lines.add( new Line( oldCoord.getX() ,oldCoord.getY() , state.getX() , state.getY() ) );
                    break;
                case '+':
                    state.turnLeft( deltaAngle );
                    break;
                case '-':
                    state.turnRight( deltaAngle );
                    break;
                case '[':
                    stack.push( new TurtleState( state.getX(), state.getY(), state.getAngle()  ) );
                    break;
                case ']':
                    state = stack.pop();
                    break;
                case 'f':
                    deltaX =  Math.cos( state.getAngle() * twopiover360 ) * deltaLength;
                    deltaY = Math.sin( state.getAngle() * twopiover360 ) * deltaLength;
                    
                    state.move( deltaX, deltaY );
                    break;
            }
            oldCoord = new Point2D.Double( state.getX(), state.getY() );
        }
        
        // normalize coordinates
        
        double scaleX = this.getWidth() / (maxX - minX);
        double scaleY = this.getHeight() / ( maxY - minY);
        
        double scale = scaleX < scaleY ? scaleX * .9 : scaleY * .9;
        
        
        for (Line l : lines) {
            double newX1 = ( l.getStartX() - minX) *  scale;
            double newX2 = ( l.getEndX() - minX) *  scale;
            double newY1 = (l.getStartY() - minY) *  scale;
            double newY2 = (l.getEndY() - minY)  * scale;
            l.setStartX( newX1 );
            l.setStartY( newY1 );
            l.setEndX( newX2 );
            l.setEndY( newY2 );
            
            gc.strokeLine( newX1 + 30, newY1 + 30, newX2 +30 , newY2+30);
        }
        
        
        
    }
    
}
