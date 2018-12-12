/**
 * Route.java
 *
 * @author Kenneth Berry
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Route 
{

   /**
   * Instance variables
   */
   private static int maxId = 0;
   private int id; // unique id for each Route
   private int waypointCount; // the number of waypoints in the Route
   private double[][] waypoints; // a 2d array of waypoints in the Route
   
   /**
    * Thing constructor - takes input from a file and constructs thing
    *      input format: 
    *            waypointCount
    *            first waypoint coordinates (x, y, z)
    *            ...
    *            last waypoint coordinates (x, y, z)
    *            turnAngle
    * @param input  input from file
    */
   public Route(Scanner input)
   {
   		maxId++;
      	id = maxId;
      	System.out.println("loading route: " + id);
      	waypointCount = input.nextInt(); input.nextLine();
      	System.out.println("route " + id + " has " + waypointCount + " waypoints");
      	waypoints = new double[waypointCount][4];
      	for (int w=0; w < waypointCount; w++)
      	{
      		double x = input.nextDouble();  // x position of waypoint
        	double y = input.nextDouble();  // y position of waypoint
        	double z = input.nextDouble();  // z position of waypoint
        	double r = input.nextDouble();  // rotation angle at waypoint
        	input.nextLine();
      		waypoints[w][0] = x;
      		waypoints[w][1] = y;
      		waypoints[w][2] = z;
      		waypoints[w][3] = r;
      		System.out.println("loaded waypoint " + (w+1) + " for route " + id + " "
      			+ waypoints[w][0] + " "
      			+ waypoints[w][1] + " "
      			+ waypoints[w][2] + " "
      			+ waypoints[w][3]);
      	}
      	System.out.println("loaded route: " + id);
    }

   /**
   * getId
   * @return the id of this Route
   */
  public int getId()
  {
     return id;
  }

  /**
   * getWaypointCount
   * @return the waypointCount of this Route
   */
  public int getWaypointCount()
  {
     return waypointCount;
  }

  /**
   * getWaypoints
   * @return the waypoints of this Route
   */
  public double[][] getWaypoints()
  {
     return waypoints;
  }
}






