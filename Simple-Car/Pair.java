/*
  this class is immutable
  (as long as you don't directly change
   s, t)
*/

import java.util.Scanner;
import java.nio.FloatBuffer;

public class Pair {

   // public, but absolutely do not change them directly!
   public double s, t;

   public Pair( double sIn, double tIn ) {
      s = sIn;
      t = tIn;
   }

   public Pair( Scanner input ) {
      s = input.nextDouble();
      t = input.nextDouble();
      input.nextLine();
   }

   // send this Pair's data to a buffer
   public void sendData( FloatBuffer buffer ) {
      buffer.put( (float) s );
      buffer.put( (float) t );
   }

   public String toString() {
      return "[" + s + " " + t + "]";
   }

}
