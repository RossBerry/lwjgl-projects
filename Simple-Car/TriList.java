/* 
  hold the data for a
  bunch of triangles
*/

import java.util.Scanner;
import java.util.ArrayList;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.system.MemoryUtil;

import org.lwjgl.opengl.*;

public class TriList {

   private ArrayList<Triangle> list;

   // remember all OpenGL-related quantities
   // from sendData so can delete
   // ------------------------------------------------
   private boolean firstTime;  // monitor whether getting resources the first time
   private int positionHandle, textureHandle;
   private FloatBuffer positionBuffer, textureBuffer;
   private int vao;
   // ------------------------------------------------

   public TriList( Scanner input ) {

      list = new ArrayList<Triangle>();

      int numTris = input.nextInt();
      
      for (int k=0; k<numTris; k++) {
         list.add( new Triangle( input ) );
      }

      firstTime = true;
   }

   public TriList() {
      list = new ArrayList<Triangle>();
      firstTime = true;
   }


   
   public int size() {
      return list.size();
   }

   public void add( Triangle t ) {
      list.add( t );
   }

   public int getVAO() {
      return vao;
   }

   // take list of triangles and filter out the ones
   // that don't match the specified picIndex,
   //  and set up the GPU
   // to have all their data (position and texture)
   // (and return number of triangles that match)
   public int sendData( int picIndex ) {

      // scan all triangles and count matching ones
      // (need count to make correct-sized buffers)
      int count = 0;
      for (int k=0; k<list.size(); k++) {
         if ( list.get(k).getPicIndex() == picIndex ) {
            count++;
         }
      }

      // create vertex buffer objects and their handles one at a time

      if ( firstTime ) {// allocate GPU buffers just once
         positionHandle = GL15.glGenBuffers();
         textureHandle = GL15.glGenBuffers();
//       System.out.println("have position handle " + positionHandle +
//                          " and texture handle " + textureHandle );
      }

      // delete the current buffers, if any, and create the new buffers

      if ( !firstTime ) {// delete current buffers
         MemoryUtil.memFree( positionBuffer );
         MemoryUtil.memFree( textureBuffer );
      }
    
      // ask for x,y,z for 3 vertices for count triangles
      positionBuffer = makeBuffer( 3 * 3 * count );
      // ask for s,t for 3 vertices for count triangles
      textureBuffer = makeBuffer( 2 * 3 * count );

      // connect triangle data to the VBO's
          // first turn the arrays into buffers

      for (int k=0; k<list.size(); k++) {
         // process triangle k
         Triangle tri = list.get(k);
         if ( tri.getPicIndex() == picIndex ) {
            tri.sendData( positionBuffer, textureBuffer );
         }
      }
      
      positionBuffer.rewind();
      textureBuffer.rewind();

      // Util.showBuffer("position:", positionBuffer);
      // Util.showBuffer("texture:", textureBuffer);

       // now connect the buffers
       GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, positionHandle );
             Util.error("after bind positionHandle");
       GL15.glBufferData( GL15.GL_ARRAY_BUFFER, 
                                     positionBuffer, GL15.GL_STATIC_DRAW );
             Util.error("after set position data");
       GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, textureHandle );
             Util.error("after bind textureHandle");
       GL15.glBufferData( GL15.GL_ARRAY_BUFFER, 
                                     textureBuffer, GL15.GL_STATIC_DRAW );
             Util.error("after set texture data");

    // set up vertex array object

      // using convenience form that produces one vertex array handle
      if ( firstTime ) {// allocate GPU buffer once, and remember it
         vao = GL30.glGenVertexArrays();
           Util.error("after generate single vertex array");
      }

      GL30.glBindVertexArray( vao );
           Util.error("after bind the vao");
//      System.out.println("vao is " + vao );

      // enable the vertex array attributes
      GL20.glEnableVertexAttribArray(0);  // position
             Util.error("after enable attrib 0");
      GL20.glEnableVertexAttribArray(1);  // texture
             Util.error("after enable attrib 1");
  
      // map index 0 to the position buffer, index 1 to the texture buffer
      GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, positionHandle );
             Util.error("after bind position buffer");
      GL20.glVertexAttribPointer( 0, 3, GL11.GL_FLOAT, false, 0, 0 );
             Util.error("after do position vertex attrib pointer");

      // map index 1 to the texture buffer
      GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, textureHandle );
             Util.error("after bind texture buffer");
      GL20.glVertexAttribPointer( 1, 2, GL11.GL_FLOAT, false, 0, 0 );
             Util.error("after do texture vertex attrib pointer");

      if ( firstTime ) {
         firstTime = false;
      }

      return count;
   }

   // nicely create a FloatBuffer with count floats
   private FloatBuffer makeBuffer( int count ) {
      return MemoryUtil.memAllocFloat( count );
   }

}
