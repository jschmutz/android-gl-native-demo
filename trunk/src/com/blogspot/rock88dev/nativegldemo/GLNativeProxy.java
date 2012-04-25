/** GLNativeDemo
 * 
 * Copyright (C) 2012
 * @author: rock88
 * 
 * e-mail: rock88a@gmail.com
 * 
 * http://rock88dev.blogspot.com
 * 
 */

package com.blogspot.rock88dev.nativegldemo;

public class GLNativeProxy {

    static {
    	System.loadLibrary("glnativedemo");
    }
    
    public native static void GL_Native_Init();
    public native static void GL_Native_Deinit();
    
    public native static void GL_Native_SurfaceChanged(int width, int height);
    public native static void GL_Native_Draw();
    
    public native static int GL_Native_GetFPS();
}
