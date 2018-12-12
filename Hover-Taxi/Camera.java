/*
   holds the viewing data and
   generates frustum and lookAt
   matrices
*/

import java.util.Scanner;
import java.nio.FloatBuffer;

public class Camera {

  // human modeling of viewing setup
  private Triple e; 
  private double azi, alt;

  // frustum data
  private double l, r, b, t, n, f;
  private Mat4 frustum, lookAt;

  private FloatBuffer fBuff, lBuff;

  public Camera( double left, double right, double bottom, double top,
                 double near, double far,
                 Triple eye, double azimuth, double altitude ) {
     l = left;  r = right;
     b = bottom; t = top;
     n = near;  f = far;

     e = eye;
     azi = azimuth;
     alt = altitude;
 
     fBuff = Util.createFloatBuffer( 16 );
     lBuff = Util.createFloatBuffer( 16 );

     update();
  }

  // from core data update frustum and lookAt
  public void update() {

     System.out.println("e: " + e + " azimuth: " + azi + " altitude: " + alt );

     // compute c:
     double alpha = Math.toRadians( azi );  // azi, alt are in degrees
     double beta = Math.toRadians( alt );
     Triple c = new Triple( e.x + Math.cos(beta)*n*Math.cos(alpha),
                     e.y + Math.cos(beta)*n*Math.sin(alpha),
                     e.z + n*Math.sin(beta) 
                   );

     lookAt = Mat4.lookAt( e, c, Triple.zAxis );
    
     frustum = Mat4.frustum( l, r, b, t, n, f );

     // fill buffers
     frustum.sendData( fBuff );
     lookAt.sendData( lBuff );
        
   }

   public FloatBuffer getFrustumBuffer() {
      return fBuff;
   }

   public FloatBuffer getLookAtBuffer() {
      return lBuff;
   }

   public Triple getPosition()
   {
     return e;
   }

   public double getAngle()
   {
     return azi;
   }

   public void setPosition(Triple newPosition)
   {
     e = newPosition;
     update();
   }

   public void shift( double dx, double dy, double dz ) {
       e = e.add( new Triple(dx,dy,dz) );
       update();
    }

    public void rotate( double amount ) {
       azi += amount;

       // adjust to stay in [0,360) range
       if ( azi < 0 ) {
          azi += 360;
       }
       if ( azi >= 360 ) {
          azi -= 360;
       }

       update();
    }

  public void tilt( double amount ) {
     alt += amount;

     update();
  }

}
