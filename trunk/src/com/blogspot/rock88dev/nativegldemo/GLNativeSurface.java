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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLNativeSurface extends GLSurfaceView implements android.opengl.GLSurfaceView.Renderer {
	
	public GLNativeSurface(Context context) {
		super(context);
		setRenderer(this);
		requestFocus();
		setFocusableInTouchMode(true);
	}

	@Override
	public void onDrawFrame(GL10 gl10) {
		GLNativeProxy.GL_Native_Draw();
	}

	@Override
	public void onSurfaceChanged(GL10 gl10, int width, int height) {
		GLNativeProxy.GL_Native_SurfaceChanged(width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
		GLNativeProxy.GL_Native_Init();
	}
	
	public void onClose(){
		GLNativeProxy.GL_Native_Deinit();
	}

}
