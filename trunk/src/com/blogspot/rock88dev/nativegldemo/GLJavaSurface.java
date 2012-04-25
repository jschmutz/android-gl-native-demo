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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class GLJavaSurface extends GLSurfaceView implements android.opengl.GLSurfaceView.Renderer  {
	private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	private int numFaces = 6;
	private int angle = 0;
	private long fps=0, tick=0;
	
	private float[][] colors = {  // Colors of the 6 faces
		{1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
		{1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
		{0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
		{0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
		{1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
		{1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
	};
	
	private float[] vertices = {  // Vertices of the 6 faces
		// FRONT
		-1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
		1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
		-1.0f,  1.0f,  1.0f,  // 2. left-top-front
		1.0f,  1.0f,  1.0f,  // 3. right-top-front
		// BACK
		1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
		-1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
		1.0f,  1.0f, -1.0f,  // 7. right-top-back
		-1.0f,  1.0f, -1.0f,  // 5. left-top-back
		// LEFT
		-1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
		-1.0f, -1.0f,  1.0f,  // 0. left-bottom-front 
		-1.0f,  1.0f, -1.0f,  // 5. left-top-back
		-1.0f,  1.0f,  1.0f,  // 2. left-top-front
		// RIGHT
		1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
		1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
		1.0f,  1.0f,  1.0f,  // 3. right-top-front
		1.0f,  1.0f, -1.0f,  // 7. right-top-back
		// TOP
		-1.0f,  1.0f,  1.0f,  // 2. left-top-front
		1.0f,  1.0f,  1.0f,  // 3. right-top-front
		-1.0f,  1.0f, -1.0f,  // 5. left-top-back
		1.0f,  1.0f, -1.0f,  // 7. right-top-back
		// BOTTOM
		-1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
		1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
		-1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
		1.0f, -1.0f,  1.0f   // 1. right-bottom-front
		};
	
	public GLJavaSurface(Context context) {
		super(context);
		setRenderer(this);
		requestFocus();
		setFocusableInTouchMode(true);
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glPushMatrix();
		
		gl.glTranslatef( 0, 0, -4.0f);
		gl.glScalef( 0.5f, 0.5f, 0.5f); 
		gl.glRotatef(angle, 0.1f, 1.0f, -0.1f);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		int face;
		for (face = 0; face < numFaces; face++)
		{
			gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
		
		gl.glPopMatrix();
		
		angle%=360;
		angle+=2;
		
	    fps++;
	    if ((System.currentTimeMillis() - tick) > 1000){
	    	Log.d("GL_NATIVE_DEMO","FPS = "+fps);
	    	fps=0;
	    	tick = System.currentTimeMillis();
	    }
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		float ratio = (float)width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 2, 14);
		
		gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
	}
	
	public void onClose(){
		
	}

}
