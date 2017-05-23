/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logo3D;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;


/**
 *
 * @author Eberhard Schneider
 */
public class TurtleState {
    
    
    Point3D location;
    Point3D heading;
    double roll;
//    Point3D pitch;
//    Point3D yaw;
//    Point3D roll;

    /**
     *
     */
    public TurtleState() {
       location = new Point3D( 0, 0, 0);
       
       heading = new Point3D( 1, 1, 1).normalize();
       
       double roll;
       
//       pitch = new Point3D( 0, 0, 0);
//       
//       yaw = new Point3D( 0, 0, 0);
//       
//       roll = new Point3D( 0, 0, 0);
    }
   
    public void setLocation( double x, double y, double z) {
       location = new Point3D( x, y, z).normalize();
    }
   
   
    public void setHeading( double x, double y, double z) {
       heading = new Point3D( x, y, z).normalize();
    }
   
//    public void setPitch( double x, double y, double z) {
//       pitch = new Point3D( x, y, z).normalize();
//    }
//   
//    public void setYaw( double x, double y, double z) {
//       yaw = new Point3D( x, y, z).normalize();
//    }
//   
//    public void setRoll( double x, double y, double z) {
//       roll = new Point3D( x, y, z).normalize();
//    }
    
    public void roll( double angle ) {
        this.roll += angle;
        this.roll = this.roll % 360;
    }
    
    public void yaw( double angle ) {
        location.
    }
    
}
