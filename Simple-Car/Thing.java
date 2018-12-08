/**
 * Thing.java
 *
 * @author Kenneth Berry
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Thing {

   /**
   * Instance variables
   */
   private static int maxId = 0;
   private int id;  // unique id for each Thing for debugging
   private String kind; // the kind of thing
   private ArrayList<Triangle> modelTris; // the model triangles for this thing
   private double angle; // how much this thing is turned
   private Triple position; // where the thing is
   private double speed; // multiple of the speed unit
   private double turnRate; // the rate at which the thing is turning
   private int routeId; // route this thing follows
   private double[] route;
   private int nextWaypoint;

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
      System.out.println("loading thing " + id + " of kind " + kind);
      String texName = input.next(); input.nextLine();
      System.out.println("thing uses " + texName + " texture");
      int texture = Pic.find(texName); // find index in Pic list 
      double s = input.nextDouble(); // s tiling factor
      double t = input.nextDouble(); // t tiling factor
      System.out.println("s tiling = " + s + "\nt tiling = " + t);
      input.nextLine(); 
      double x = input.nextDouble(); // get initial x position
      double y = input.nextDouble(); // get initial y position
      double z = input.nextDouble(); // get initial z position
      input.nextLine();
      position = new Triple(x,y,z);
      angle = input.nextInt(); input.nextLine();
      speed = input.nextDouble(); input.nextLine();
      turnRate = input.nextDouble(); input.nextLine();
      routeId = input.nextInt(); input.nextLine();
      nextWaypoint = 0;
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
                       new Vertex( w, -l, -h, s, 0 ),
                       new Vertex( w, -l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, -h, 0, 0 ),
                       new Vertex( w, -l, h, s, t ),
                       new Vertex( -w, -l, h, 0, t)));
        // right face
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0 ),
                       new Vertex( w, l, -h, s, 0 ),
                       new Vertex( w, l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0 ),
                       new Vertex( w, l, h, s, t ),
                       new Vertex( w, -l, h, 0, t)));
        // back face
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0, 0 ),
                       new Vertex( -w, l, -h, s, 0 ),
                       new Vertex( -w, l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0, 0 ),
                       new Vertex( -w, l, h, s, t ),
                       new Vertex( w, l, h, 0, t)));
        // left face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( -w, -l, -h, s, 0 ),
                       new Vertex( -w, -l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( -w, -l, h, s, t ),
                       new Vertex( -w, l, h, 0, t)));
        // top face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0, 0 ),
                       new Vertex( w, -l, h, s, 0 ),
                       new Vertex( w, l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0, 0 ),
                       new Vertex( w, l, h, s, t ),
                       new Vertex( -w, l, h, 0, t)));;
        // bottom face
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( w, l, -h, s, 0 ),
                       new Vertex( w, -l, -h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0, 0 ),
                       new Vertex( w, -l, -h, s, t ),
                       new Vertex( -w, -l, -h, 0, t)));
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
                       new Vertex(w, 0, 0, s, 0),
                       new Vertex(h/2, l, 0, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(h/2, l, 0, s, 0),
                       new Vertex(-h/2, l, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-h/2, l, 0, s, 0),
                       new Vertex(-w, 0, 0, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-w, 0, 0, s, 0),
                       new Vertex(-h/2, -l, 0, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(-h/2, -l, 0, s, 0),
                       new Vertex(h/2, -l, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0, 0),
                       new Vertex(h/2, -l, 0, s, 0),
                       new Vertex(w, 0, 0, s, t)));
        // bottom hexagonal-pyramid
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(w, 0, 0, s, 0),
                       new Vertex(h/2, l, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(h/2, l, 0, s, 0),
                       new Vertex(-h/2, l, 0, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-h/2, l, 0, s, 0),
                       new Vertex(-w, 0, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-w, 0, 0, s, 0),
                       new Vertex(-h/2, -l, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(-h/2, -l, 0, s, 0),
                       new Vertex(h/2, -l, 0, s, t)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0, 0),
                       new Vertex(h/2, -l, 0, s, 0),
                       new Vertex(w, 0, 0, s, t)));
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
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(w, 0, 0, 0, 0),
                       new Vertex(0.75*w, 0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5, t),
                       new Vertex(0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(0, l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(0, l, 0, 0, 0),
                       new Vertex(-0.75*w, 0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(-0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(-w, 0, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(-w, 0, 0, 0, 0),
                       new Vertex(-0.75*w, -0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(-0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(0, -l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(0, -l, 0, 0, 0),
                       new Vertex(0.75*w, -0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, h, 0.5*s, t),
                       new Vertex(0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(w, 0, 0, s, 0)));
        // bottom octagonal-pyramid
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(w, 0, 0, 0, 0),
                       new Vertex(0.75*w, 0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(0, l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(0, l, 0, 0, 0),
                       new Vertex(-0.75*w, 0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(-0.75*w, 0.75*l, 0, 0, 0),
                       new Vertex(-w, 0, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(-w, 0, 0, 0, 0),
                       new Vertex(-0.75*w, -0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(-0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(0, -l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(0, -l, 0, 0, 0),
                       new Vertex(0.75*w, -0.75*l, 0, s, 0)));
        modelTris.add( new Triangle(texture, 
                       new Vertex(0, 0, -h, 0.5*s, t),
                       new Vertex(0.75*w, -0.75*l, 0, 0, 0),
                       new Vertex(w, 0, 0, s, 0)));
      } 
      else if(kind.equals("car"))
      {
        // get dimensions
        double w = input.nextDouble() /2;  // along x
        double l = input.nextDouble() /2;  // along y
        double h = input.nextDouble() /2;  // along z
        input.nextLine();
        modelTris = new ArrayList<Triangle>();
        // front face (right side of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, -h, 0.2*s, 0 ),
                       new Vertex( w, -l, -h, 0.6*s, 0 ),
                       new Vertex( w, -l, h, 0.6*s, 0.5*t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, -h, 0.2*s, 0 ),
                       new Vertex( w, -l, h, 0.6*s, 0.5*t ),
                       new Vertex( -w, -l, h, 0.2*s, 0.5*t)));
        // right face (front of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0.5*t ),
                       new Vertex( w, l, -h, 0.2*s, 0.5*t ),
                       new Vertex( w, l, h, 0.2*s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, -l, -h, 0, 0.5*t ),
                       new Vertex( w, l, h, 0.2*s, t ),
                       new Vertex( w, -l, h, 0, t)));
        // back face (left side of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0.2*s, 0.5*t ),
                       new Vertex( -w, l, -h, 0.6*s, 0.5*t ),
                       new Vertex( -w, l, h, 0.6*s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( w, l, -h, 0.2*s, 0.5*t ),
                       new Vertex( -w, l, h, 0.6*s, t ),
                       new Vertex( w, l, h, 0.2*s, t)));
        // left face (back of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0.2*s, 0 ),
                       new Vertex( -w, -l, -h, 0, 0 ),
                       new Vertex( -w, -l, h, 0, 0.5*t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0.2*s, 0 ),
                       new Vertex( -w, -l, h, 0, 0.5*t ),
                       new Vertex( -w, l, h, 0.2*s, 0.5*t)));
        // top face (top of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0.6*s, 0.5*t ),
                       new Vertex( w, -l, h, s, 0.5*t ),
                       new Vertex( w, l, h, s, t)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, -l, h, 0.6*s, 0.5*t ),
                       new Vertex( w, l, h, s, t ),
                       new Vertex( -w, l, h, 0.6*s, t)));;
        // bottom face (bottom of car)
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0.6*s, 0.5*t ),
                       new Vertex( w, l, -h, s, 0.5*t ),
                       new Vertex( w, -l, -h, s, 0)));
        modelTris.add( new Triangle( texture, 
                       new Vertex( -w, l, -h, 0.6*s, 0.5*t ),
                       new Vertex( w, -l, -h, s, 0 ),
                       new Vertex( -w, -l, -h, 0.6*s, 0)));
      }
  } // Thing constructor

  /**
   * getId
   * @return the id of this Thing
   */
  public int getId()
  {
     return id;
  }

  /**
   * getRouteId
   * @return the route id of this Thing
   */
  public int getRouteId()
  {
     return routeId;
  }

  /**
   * getKind
   * @return the kind of this Thing
   */
  public String getKind()
  {
     return kind;
  }

  /**
   * getNextWaypoint
   * @return the next waypoint index of this Thing's route
   */
  public int getNextWaypoint()
  {
    return nextWaypoint;
  }

  /**
   * setNextWaypoint
   * @param waypoint the new next waypoint for this Thing
   */
  public void setNextWaypoint(int waypoint)
  {
    nextWaypoint = waypoint;
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
   * moveToWaypoint - move the thing to the next waypoint
   */
  public boolean moveToWaypoint(Triple waypoint, double waypointAngle)
  {
    Triple d = waypoint.minus(position);
    double dist = Math.sqrt(Math.pow(d.x, 2) + Math.pow(d.y, 2) + Math.pow(d.z, 2));
    if (dist < speed)
    {
      if (Math.abs(waypointAngle - angle) > turnRate ||
          Math.abs(angle - waypointAngle) > turnRate)
      {
        int turnDirection = (int)((waypointAngle - angle) / (waypointAngle - angle));
        angle += Math.abs(turnRate) * turnDirection;
        if (angle < 0)
        {
           angle += 360;
        }
        if (angle >= 360)
        {
           angle -= 360;
        }
        return false;
      }
      else
      {
        System.out.println("Waypoint reached!");
        angle = waypointAngle;
        return true;
      }
    }
    else
    {
      // update the position
      double ang = Math.toRadians(angle);
      Triple v = new Triple( Math.cos(ang), Math.sin(ang), 0).mult(speed);
      position = position.add(v);
      return false;
    }
  }
  /**
   * update - update physical state of this thing
   *          using its speed and turnRate
   */
  public void update()
  {
    System.out.println("----------------> angle is " + angle);
    if (!(routeId > 0))
    {
      // update angle and adjust to stay in [0,360)
      angle += turnRate;
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
}
