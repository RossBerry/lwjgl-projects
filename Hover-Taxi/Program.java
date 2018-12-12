/*  
 * Program.java
 *
 * An instance of this class provides interface to
 * a GLSL program
*/

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Program
{
  private int handle; // GLSL program handle

  /**
   * Program constructor - constructs and compiles a
   *    Program instance from the given shaders.
   *   !(can only be called when OpenGL context is active)!
   * @param vertexShader
   * @param fragmentShader
   */
  public Program( Shader vertexShader, Shader fragmentShader )
  {
    handle = glCreateProgram();
    Util.error("after create program");
    System.out.println("program handle is " + handle );
    glAttachShader( handle, vertexShader.getHandle() );
    Util.error("after attach vertex shader to program");
    glAttachShader( handle, fragmentShader.getHandle() );
    Util.error("after attach fragment shader to program");
    glLinkProgram( handle );
    Util.error("after link program" );
  }

  /**
   * getHandle - accessor method for GLSL program handle
   * @return handle
   */
  public int getHandle()
  {
    return handle;
  }

  /**
   * use - use GLSL program handle
   */
  public void use()
  {
    GL20.glUseProgram( handle );
    Util.error("after use program");
  }

  /**
   * delete - delete GLSL program handle
   */
  public void delete()
  {
    glDeleteProgram( handle );
  }
}
