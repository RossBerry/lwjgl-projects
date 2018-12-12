/**  
 * Vertex
 *
 * A vertext that contains a Triple of position coordinates and
 * a Pair of texture coordinates.
*/

import java.util.Scanner;
import java.nio.FloatBuffer;

public class Vertex 
{

  /**
    * Instance variables
    */
  private Triple position;
  private Pair texture;

  /**
    * Vertex constructor
    * @param input Scanner input
    */
  public Vertex(Scanner input)
  {
     position = new Triple( input );
     texture = new Pair(input);
  }

  /**
    * Vertex constructor
    * @param p Triple (x,y,z) position coordinates
    * @param t Par (s,t) texture coordinates
    */
  public Vertex(Triple p, Pair t)
  {
     texture = t;
     position = p;
  }

  /**
    * Vertex constructor
    * @param x double x position coordinate
    * @param y double y position coordinate
    * @param z double z position coordinate
    * @param s double s texture coordinate
    * @param s double t texture coordinate
    */
  public Vertex(double x, double y, double z, double s, double t)
  {
     position = new Triple(x,y,z);
     texture = new Pair(s,t);
  }

  /**
    * transform - create a new vertex with same texture coords but with
    *             position multiplied by transform matrix t.
    * @param t Mat4 transform matrix
    * @return Vertex transformed by transform matrix
    */
  public Vertex transform(Mat4 t)
  {
     return new Vertex(t.multAndPerspDiv(position), texture);
  }

  /**
    * sendData - return position and texture coordinates as FloatBuffers
    * @param posBuffer FloatBuffer to send position coordinates to
    * @param texBuffer FloatBuffer to send texture coordinates to
    */
  public void sendData(FloatBuffer posBuffer, FloatBuffer texBuffer)
  {
     position.sendData(posBuffer);     
     texture.sendData(texBuffer);     
  }
}
