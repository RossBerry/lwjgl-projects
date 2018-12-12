/**  
 * HoverTaxi
 *
 * @author Kenneth Berry
*/

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
//import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*; // just for the key constants
//import static org.lwjgl.system.MemoryUtil.*;
import java.util.Scanner;
import java.io.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class HoverTaxi extends Basic 
{

  /**
   * main (overrides Basic.main())
   */
  public static void main(String[] args)
  {
    HoverTaxi app = new HoverTaxi("Exercise 9", 1000, 500, 30, args[0]);
    app.start();
  } // main

  /**
    * Instance variables
    */
  private Shader v1, f1;
  private int hp1;  // handle for the GLSL program
  // have two matrices in shader---projection and viewing
  private int projLoc, viewLoc;
  // for first person view, use frustum and lookAt
  private FloatBuffer frustumBuffer, lookAtBuffer;
  // for map view, use ortho and mapView
  private FloatBuffer orthoBuffer, mapBuffer;
  private ArrayList<Thing> things;
  private ArrayList<Route> routes;
  private TriList tris;
  private int texture1Loc;
  private Camera camera;
  private Thing player;
 

  // construct basic application with given title, pixel width and height
  // of drawing area, and frames per second
  public HoverTaxi( String appTitle, int pw, int ph, int fps, String inputFile )
  {
    super( appTitle, pw, ph, (long) ((1.0/fps)*1000000000) );
 
    // load all the textures
    Pic.init();

    // read data to build all the Things
    try 
    {
      Scanner input = new Scanner(new File(inputFile));
      int thingNumber = input.nextInt();  input.nextLine();
      System.out.println("There are " + thingNumber + " things in the scene");
      int routeNumber = input.nextInt();  input.nextLine();
      System.out.println("There are " + routeNumber + " routes");
      things = new ArrayList<Thing>();
      for (int k=0; k<thingNumber; k++) 
      {
        things.add(new Thing(input));           
      }
      for (int k=0; k<things.size(); k++)
      {
        if (things.get(k).getKind().equals("player"))
        {
          player = things.get(k);
        }
      }
      routes = new ArrayList<Route>();
      for (int k=0; k<routeNumber; k++) 
      {
        routes.add(new Route(input));           
      }
      input.close();
    }
    catch(Exception e) 
    {
      e.printStackTrace();
      System.exit(1);
    }
    // place camera somewhere at start
    camera = new Camera(-.1, .1, -.1, .1, 0.5, 300, new Triple(95,5,3), 135, 0);
  }

  /**
   * init (overrides Basic.init())
   */
  protected void init()
  {
    String vertexShaderCode =
      "#version 330 core\n"+
      "layout (location = 0 ) in vec3 vertexPosition;\n"+
      "layout (location = 1 ) in vec2 vertexTexCoord;\n"+
      "out vec2 texCoord;\n"+
      "uniform mat4 projection;\n"+
      "uniform mat4 view;\n"+
      "void main(void)\n"+
      "{\n"+
      "  texCoord = vertexTexCoord;\n"+
      "  gl_Position = projection * view * vec4( vertexPosition, 1.0 );\n"+
      "}\n";
    System.out.println("Vertex shader:\n" + vertexShaderCode + "\n\n" );
    v1 = new Shader( "vertex", vertexShaderCode );
    String fragmentShaderCode =
      "#version 330 core\n"+
      "in vec2 texCoord;\n"+
      "layout (location = 0 ) out vec4 fragColor;\n"+
      "uniform sampler2D texture1;\n"+
      "void main(void)\n"+
      "{\n"+
      "  fragColor = texture( texture1, texCoord );\n"+
      "}\n";
    System.out.println("Fragment shader:\n" + fragmentShaderCode + "\n\n" );
    f1 = new Shader( "fragment", fragmentShaderCode );
    hp1 = GL20.glCreateProgram();
    Util.error("after create program");
    System.out.println("program handle is " + hp1 );
    GL20.glAttachShader( hp1, v1.getHandle() );
    Util.error("after attach vertex shader to program");
    GL20.glAttachShader( hp1, f1.getHandle() );
    Util.error("after attach fragment shader to program");
    GL20.glLinkProgram( hp1 );
    Util.error("after link program" );
    GL20.glUseProgram( hp1 );
    Util.error("after use program");
    // get location of uniforms 
    projLoc = GL20.glGetUniformLocation( hp1, "projection" );
    viewLoc = GL20.glGetUniformLocation( hp1, "view" );
    texture1Loc = GL20.glGetUniformLocation( hp1, "texture1" );
    // once and for all (it's never changing), get
    // buffer for frustum
    frustumBuffer = camera.getFrustumBuffer();
    // and create the fixed ortho and identity matrix buffers
    orthoBuffer = Util.createFloatBuffer( 16 );
    Mat4 ortho = Mat4.ortho( -50, 50, -50, 50, 0, 101 );
    System.out.println("ortho: \n" + ortho );
    ortho.sendData( orthoBuffer );
    mapBuffer = Util.createFloatBuffer( 16 );
    Mat4 mapView = Mat4.lookAt( new Triple(50,50,100),
                                new Triple(50,50,0),
                                new Triple(0,1,0)
                               );
    System.out.println("mapView:\n " + mapView );
    mapView.sendData( mapBuffer );
    // set background color to white
    GL11.glClearColor( 1.0f, 1.0f, 1.0f, 0.0f );
    // turn on depth testing
    GL11.glEnable( GL11.GL_DEPTH_TEST );
    // clearing the depth buffer means setting all spots to this value (1)
    GL11.glClearDepth( 2.0f );
    // an incoming fragment overwrites the existing fragment if its depth
    // is less
    GL11.glDepthFunc( GL11.GL_LESS );
   }
 
  /**
   * processInputs (overrides Basic.processInputs())
   */
  protected void processInputs()
  {
    // process all waiting input events
    while( InputInfo.size() > 0 ) 
    {
      InputInfo info = InputInfo.get();
      if( info.kind == 'k' && (info.action == GLFW_PRESS 
        || info.action == GLFW_REPEAT) )
      {
        // store info values in more convenient variables
        int code = info.code;
        int mods = info.mods;
        final double amount = 1;  // amount to move
        final double angAmount = 5;
        // move the eye point of the camera
        if ( code == GLFW_KEY_G && mods == 0 )
        { // x:  move left
          player.setSpeed(0.2);
        }
        else if ( code == GLFW_KEY_S && mods == 0 )
        { // x:  move left
          player.setSpeed(0);
        }
        //else if ( code == GLFW_KEY_X && mods == 0 )
        //{ // x:  move left
          //camera.shift( -amount, 0, 0 );
        //}
        //else if ( code == GLFW_KEY_X && mods == 1 )
        //{ // X:  move right
          //camera.shift( amount, 0, 0 );
        //}
        //else if ( code == GLFW_KEY_Y && mods == 0 )
        //{ // y:  move smaller in y direction
          //camera.shift( 0, -amount, 0 );
        //}
        //else if ( code == GLFW_KEY_Y && mods == 1 )
        //{ // Y:  move bigger in y direction
          //camera.shift( 0, amount, 0 );
        //}
        //else if ( code == GLFW_KEY_Z && mods == 0 ) 
        //{ // z:  move smaller in z direction
          //camera.shift( 0, 0, -amount );
        //}
        //else if ( code == GLFW_KEY_Z && mods == 1 ) 
        //{ // Z:  move bigger in Z direction
          //camera.shift( 0, 0, amount );
        //}
        // change angles //
        else if (code == GLFW_KEY_R && mods == 0 ||
                 code == GLFW_KEY_RIGHT && mods == 0) 
        { // r:  rotate clockwise
          camera.rotate( -angAmount );
        }
        else if (code == GLFW_KEY_L && mods == 0 ||
                 code == GLFW_KEY_LEFT && mods == 0) 
        { // R: rotate counterclockwise
          camera.rotate( angAmount );
        }
        else if (code == GLFW_KEY_D && mods == 0 ||
                 code == GLFW_KEY_DOWN && mods == 0) 
        { // t:  tilt downward
          camera.tilt( -angAmount );
        }
        else if (code == GLFW_KEY_U && mods == 0 ||
                 code == GLFW_KEY_UP && mods == 0) 
        { // T: tilt upward
          camera.tilt(angAmount);
        }
      } // input event is a key
      else if ( info.kind == 'm' ) 
      { // mouse moved
        //  System.out.println( info );
      }
      else if( info.kind == 'b' )
      { // button action
        //  System.out.println( info );
      }
    } // loop to process all input events
  }
 
  /**
   * update (overrides Basic.update())
   */
  protected void update()
  {
    player.setAngle(camera.getAngle());
    // give each thing a chance to update itself
    for (int k=0; k<things.size(); k++)
    {
      int routeId = things.get(k).getRouteId();
      System.out.println("update things # " + k + " with id " + things.get(k).getId());
      if (routeId > 0)
      {
        Thing routedThing = things.get(k);
        int i = routeId - 1;
        System.out.println("----------------> thing is on route " + routeId);
        System.out.println("----------------> next waypoint in thing's route is "
                           + routedThing.getNextWaypoint());
        double wX = routes.get(i).getWaypoints()[routedThing.getNextWaypoint()][0];
        double wY = routes.get(i).getWaypoints()[routedThing.getNextWaypoint()][1];
        double wZ = routes.get(i).getWaypoints()[routedThing.getNextWaypoint()][2];
        Triple waypoint = new Triple(wX, wY, wZ);

        if (things.get(k).moveToWaypoint(
              waypoint, 
              routes.get(i).getWaypoints()[routedThing.getNextWaypoint()][3]))
        {
          routedThing.setNextWaypoint(routedThing.getNextWaypoint() + 1);
          if (routedThing.getNextWaypoint() > routes.get(i).getWaypointCount() - 1)
          {
            routedThing.setNextWaypoint(0);
          }
        }
      }
      else
      {
        System.out.println("----------------> thing has no route");
      }
      things.get(k).update();
    }
    Triple offset = new Triple(0,0,2.5);
    camera.setPosition(player.getPosition().add(offset));
  }
 
  protected void display()
  {
    // System.out.println( "Step: " + getStepNumber() + " camera: " + camera );
    // clear the color and depth buffers
    GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
    // create tris from the things:
    tris = new TriList();
    for (int k=0; k<things.size(); k++)
    {
      things.get(k).sendTriangles( tris );
    }
    // ---------------------------------------------------
    // for each texture, draw all triangles that use it
    for (int k=0; k<Pic.size(); k++ )
    {
      // send texture sampler as a uniform
      GL20.glUniform1i( texture1Loc, 0 ); 
      Util.error("after set value of texture1");
      // set up the texture in GPU
      Pic.get(k).activate();
      // send triangle data to GPU
      // and note how many there were
      int count = tris.sendData( k );
      // activate vao
      GL30.glBindVertexArray( tris.getVAO() );
      Util.error("after bind vao");
      //--------------------------------------
      // draw view of world in left sub-window
      // using lookAt and frustum
      //--------------------------------------
      GL11.glViewport( 0, 0, 
                       Util.retinaDisplay*getPixelWidth()/2, 
                       Util.retinaDisplay*getPixelHeight() );
      GL20.glUniformMatrix4fv( projLoc, true, frustumBuffer ); 
      lookAtBuffer = camera.getLookAtBuffer();
      GL20.glUniformMatrix4fv( viewLoc, true, lookAtBuffer ); 
      // draw the buffers
      GL11.glDrawArrays( GL11.GL_TRIANGLES, 0, count*3 );
      Util.error("after draw arrays");
      //--------------------------------------
      // draw view of world in right sub-window
      // using ortho and identity
      //--------------------------------------
      GL11.glViewport( Util.retinaDisplay*getPixelWidth()/2, 0,
                       Util.retinaDisplay*getPixelWidth()/2,
                       Util.retinaDisplay*getPixelHeight() );
      GL20.glUniformMatrix4fv( projLoc, true, orthoBuffer );
      GL20.glUniformMatrix4fv( viewLoc, true, mapBuffer );
      // draw the buffers
      GL11.glDrawArrays( GL11.GL_TRIANGLES, 0, count*3 );
      Util.error("after draw arrays");
    } // draw triangles for pic k
  } // display
} // HoverTaxi
