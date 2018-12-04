import java.util.Scanner;
import java.nio.FloatBuffer;

public class Vertex {

  private Triple position;
  private Pair texture;

  public Vertex( Scanner input ) {
     position = new Triple( input );
     texture = new Pair( input );
  }

  public Vertex( Triple p, Pair t ) {
     texture = t;
     position = p;
  }

  public Vertex( double x, double y, double z, double s, double t ) {
     position = new Triple(x,y,z);
     texture = new Pair(s,t);
  }

  // create a new vertex with same texture coords
  // with position multiplied by t
  public Vertex transform( Mat4 t ) {
     return new Vertex( t.multAndPerspDiv( position ), texture );
  }

  public void sendData( FloatBuffer posBuffer, FloatBuffer texBuffer ) {
     position.sendData( posBuffer );     
     texture.sendData( texBuffer );     
  }

}
