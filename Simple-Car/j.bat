del *.class
echo "------------------------------------- Syntax errors (if any);"
javac -cp .;..\LWJGL\lwjgl.jar;..\LWJGL\lwjgl-opengl.jar;..\LWJGL\lwjgl-glfw.jar %1.java 
echo "------------------------------------- Runtime errors (if any);"
java -cp .;..\LWJGL\lwjgl.jar;..\LWJGL\lwjgl-opengl.jar;..\LWJGL\lwjgl-glfw.jar;..\LWJGL\lwjgl-glfw-natives-windows.jar;..\LWJGL\lwjgl-natives-windows.jar;..\LWJGL\lwjgl-opengl-natives-windows.jar -Djava.library.path=..\LWJGL %1 %2 %3 %4 %5
