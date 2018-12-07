/**
 * Thing.java
 *
 * @author Kenneth Berry
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Thing {

   /**
   * Instance variables
   */
   private static int maxId = 0;
   private int id;  // unique id for each Thing for debugging
   private String kind; // the kind of thing
   private ArrayList<Triangle> modelTris; // the model triangles for this thing
   private int angle; // how much this thing is turned
   private Triple position; // where the thing is
   private int speed; // multiple of the speed unit
   private int turnRate; // the rate at which the thing is turning

   /**
    * Thing constructor - takes input from a file and constructs thing
    *      input format: 
    *            kind
    *            texture name
    *            initial position x y z
    *            initial angle
    *            initial speed
    *            initial turn rate
    *            width length height
    * @param input  input from file
    */
   public Thing(Scanner input)
   {
      maxId++;
      id = maxId;
      kind = input.next(); input.nextLine();
      String texName = input.next(); input.nextLine();
      int texture = Pic.find(texName); // find index in Pic list:
      double x = input.nextDouble(); // get initial x position
      double y = input.nextDouble(); // get initial y position
      double z = input.nextDouble(); // get initial z position
      input.nextLine();
      position = new Triple(x,y,z);
      angle = input.nextInt(); input.nextLine();
      speed = input.nextInt(); input.nextLine();
      turnRate = input.nextInt(); input.nextLine();
      // build tris from kind and other info
      if (kind.equals("box"))
      {
        // get dimensions
        double w = input.nextDouble() /2;  // along x
        double l = input.nextDouble() /2;  // along y
        double h = input.nextDouble() /2;  // along z
        input.nextLine();
        modelTris = new ArrayList<Triangle>();
        // front face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, -h, 0, 0 ),
                       new Vertex( w, -l, -h, 1, 0 ),
                       new Vertex( w, -l, h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, -h, 0, 0 ),
                       new Vertex( w, -l, h, 1, 1 ),
                       new Vertex( -w, -l, h, 0, 1)));
        // right face
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0 ),
                       new Vertex( w, l, -h, 1, 0 ),
                       new Vertex( w, l, h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0 ),
                       new Vertex( w, l, h, 1, 1 ),
                       new Vertex( w, -l, h, 0, 1)));
        // back face
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0, 0 ),
                       new Vertex( -w, l, -h, 1, 0 ),
                       new Vertex( -w, l, h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0, 0 ),
                       new Vertex( -w, l, h, 1, 1 ),
                       new Vertex( w, l, h, 0, 1)));
        // left face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( -w, -l, -h, 1, 0 ),
                       new Vertex( -w, -l, h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( -w, -l, h, 1, 1 ),
                       new Vertex( -w, l, h, 0, 1)));
        // top face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0, 0 ),
                       new Vertex( w, -l, h, 1, 0 ),
                       new Vertex( w, l, h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0, 0 ),
                       new Vertex( w, l, h, 1, 1 ),
                       new Vertex( -w, l, h, 0, 1)));;
        // bottom face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( w, l, -h, 1, 0 ),
                       new Vertex( w, -l, -h, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( w, -l, -h, 1, 1 ),
                       new Vertex( -w, -l, -h, 0, 1)));
      }
      else if(kind.equals("hexagonal-bipyramid"))
      {
        // get dimensions
        double w = input.nextDouble() /2;  // along x
        double l = input.nextDouble() /2;  // along y
        double h = input.nextDouble() /2;  // along z
        input.nextLine();
        modelTris = new ArrayList<Triangle>();
        // top hexagonal-pyramid
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(w, 0, 0, 1, 0),
                       new Vertex(h/2, l, 0, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(h/2, l, 0, 1, 0),
                       new Vertex(-h/2, l, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-h/2, l, 0, 1,0),
                       new Vertex(-w, 0, 0, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-w, 0, 0, 1, 0),
                       new Vertex(-h/2, -l, 0, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-h/2, -l, 0, 1, 0),
                       new Vertex(h/2, -l, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(h/2, -l, 0, 1, 0),
                       new Vertex(w, 0, 0, 1, 1)));
        // bottom hexagonal-pyramid
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(w, 0, 0, 1, 0),
                       new Vertex(h/2, l, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(h/2, l, 0, 1, 0),
                       new Vertex(-h/2, l, 0, 1, 1)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-h/2, l, 0, 1, 0),
                       new Vertex(-w, 0, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-w, 0, 0, 1, 0),
                       new Vertex(-h/2, -l, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-h/2, -l, 0, 1, 0),
                       new Vertex(h/2, -l, 0, 1, 1)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(h/2, -l, 0, 1, 0),
                       new Vertex(w, 0, 0, 1, 1)));
      }
      else if(kind.equals("octagonal-bipyramid"))
      {
        // get dimensions
        double w = input.nextDouble() /2;  // along x
        double l = input.nextDouble() /2;  // along y
        double h = input.nextDouble() /2;  // along z
        input.nextLine();
        modelTris = new ArrayList<Triangle>();
        // top octagonal-pyramid
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(w, 0, 0, 0, 0),
                       new Vertex(0.75*w, 0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(0, l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(0, l, 0, 0, 0),
                       new Vertex(-0.75*w, 0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(-0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(-w, 0, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(-w, 0, 0, 0, 0),
                       new Vertex(-0.75*w, -0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(-0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(0, -l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(0, -l, 0, 0, 0),
                       new Vertex(0.75*w, -0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, 1),
                       new Vertex(0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(w, 0, 0, 1, 0)));
        // bottom octagonal-pyramid
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(w, 0, 0, 0, 0),
                       new Vertex(0.75*w, 0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(0, l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(0, l, 0, 0, 0),
                       new Vertex(-0.75*w, 0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(-0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(-w, 0, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(-w, 0, 0, 0, 0),
                       new Vertex(-0.75*w, -0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(-0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(0, -l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(0, -l, 0, 0, 0),
                       new Vertex(0.75*w, -0.75*l, 0, 1, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5, 1),
                       new Vertex(0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(w, 0, 0, 1, 0)));
      } 
      else if(kind.equals("car"))
      {}
      
      
  } // Thing constructor

  /**
   * getId
   */
  public int getId()
  {
     return id;
  }

  /**
   * sendTriangles
   * @param tris list of triangles that make up this thing
   */
  public void sendTriangles(TriList tris)
  {

    // create rotation and translation matrices
    Mat4 rotate = Mat4.rotate(0,0,1, angle); // about z axis
    /*  // check correctness
    Mat4 rotT = rotate.transpose();
    Mat4 prod = rotate.mult(rotT);
    System.out.println("this should be I:\n" + prod );
    */
    Mat4 translate = Mat4.translate(position);
    // combine for efficiency and convenience
    Mat4 transform = translate.mult(rotate);
    for (int k=0; k<modelTris.size(); k++)
    {
      // add transformed triangle to list
      tris.add(modelTris.get(k).transform(transform));
    }   
  }

  /**
   * update - update physical state of this thing
   *          using its speed and turnRate
   */
  public void update()
  {
    // update angle and adjust to stay in [0,360)
    angle += turnRate;
    System.out.println("----------------> angle is " + angle);
    if (angle < 0)
    {
       angle += 360;
    }
    if (angle >= 360)
    {
      angle -= 360;
    }
    // update the position
    double ang = Math.toRadians(angle);
    Triple v = new Triple( Math.cos(ang), Math.sin(ang), 0).mult(speed);
    position = position.add(v);
  }
}
