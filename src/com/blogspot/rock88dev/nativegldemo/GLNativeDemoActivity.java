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

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class GLNativeDemoActivity extends Activity {

	private GLNativeSurface n_surface;
	private GLJavaSurface j_surface;
	
	private final int GLNative = 1;
	private final int GLJava = 2;
	private int GLRender = GLNative;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTitle("GLNativeDemo: Render - GLNative");
        n_surface = new GLNativeSurface(this);
        setContentView(n_surface);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_MENU){
    		if(GLRender==GLNative) GLRender=GLJava;
    			else GLRender=GLNative;
    		
    		switch(GLRender){
    			case GLNative:
    				setTitle("GLNativeDemo: Render - GLNative");
    		        n_surface = new GLNativeSurface(this);
    		        setContentView(n_surface);
    				break;
    			case GLJava:
    				setTitle("GLNativeDemo: Render - GLJava");
    				n_surface.onClose();
    		        j_surface = new GLJavaSurface(this);
    		        setContentView(j_surface);
    				break;
    		}
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    @Override
    protected void onDestroy() {
		switch(GLRender){
			case GLNative:
				n_surface.onClose();
				break;
			case GLJava:
				j_surface.onClose();
				break;
		}
    	
    	super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
		switch(GLRender){
			case GLNative:
				n_surface.onClose();
				break;
			case GLJava:
				j_surface.onClose();
				break;
		}
    	System.exit(-1);
    }

}