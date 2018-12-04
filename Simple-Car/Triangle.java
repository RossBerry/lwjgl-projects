import java.util.Scanner;
import java.nio.FloatBuffer;

public class Triangle {

   private int texture;  // index into list of textures in Pic
   private Vertex a, b, c;

   public Triangle( Scanner input ) {
      texture = input.nextInt();  input.nextLine();
      a = new Vertex( input );
      b = new Vertex( input );
      c = new Vertex( input );
   }

   public Triangle( int tex, Vertex aIn, Vertex bIn, Vertex cIn ) {
      texture = tex;
      a = aIn;
      b = bIn;
      c = cIn;
   }

   public void sendData( FloatBuffer posBuffer, FloatBuffer texBuffer ) {
      // render three Vertex instances
      a.sendData( posBuffer, texBuffer );
      b.sendData( posBuffer, texBuffer );
      c.sendData( posBuffer, texBuffer );
   }

   public int getPicIndex() {
      return texture;
   }

   // create a new triangle by multiplying all
   // vertices of this triangle by given t
   public Triangle transform( Mat4 t ) {
      return new Triangle( texture, 
                           a.transform(t), b.transform(t), c.transform(t) );
   }

}
