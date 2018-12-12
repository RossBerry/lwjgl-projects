# LWJGL-Examples
Example projects using the Light Weight Java Gaming Library (LWJGL) and Java.


## Requirements:
All projects require [Java](https://www.java.com/en/download/) and [LWJGL](https://www.lwjgl.org/download)


## Installation / Usage Instructions:
- Download LWJGL 3.2 zip file containing the GLFW and OpenGL libraries
- Unzip LWJGL jar files into a folder named LWJGL
- Place LWJGL folder in LWJGL-Examples folder alongside project folders
- Run j.bat with project's main java source file and input file as arguments
  To run Hover-Taxi project for example, open console in project folder and enter the following:
  
      ./j HoverTaxi world
      
- If you want to change the textures used in a project, the new textures must be converted from a jpg before use.  To use an image named new_image.jpg for example, put the image in the project's Picture folder and enter the following in the console:

      ./j Convert new_image


## Projects:
- **Hover-Taxi:**   A simple scene with several spinning objects and several automated "hover taxi" buses that follow preset routes.  All objects and routes in the scene are loaded from the input **world** file.  The display is split in two with the right side showing a top-down view of the scene, and the left side showing the viewpoint of a hover taxi controlled by the user.
      
      Controls:  Rotate Left      --  L or left arrow
                 Rotate right     --  R or right arrow
                 Tilt camera up   --  U or up arrow
                 Tilt camera down --  D or down arrow
                 Drive forward    --  G
                 Stop driving     --  S
