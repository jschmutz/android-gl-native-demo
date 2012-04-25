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

#include "com_blogspot_rock88dev_nativegldemo_GLNativeProxy.h"
#include "gles_render.h"
#include "importgl.h"
#include <sys/time.h>
#include <unistd.h>
#include <android/log.h>

typedef uint32_t	Uint32;

int numFaces = 6;
int angle = 0;
static running = 0;
static struct timeval start;
static int tick = 0;
int fps = 0;

float colors[6][4] = {  // Colors of the 6 faces
		{1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
		{1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
		{0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
		{0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
		{1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
		{1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
	};
  
float vertices[] = {  // Vertices of the 6 faces
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

int a_printf(const char *fmt, ...)
{
    va_list ap;
    va_start(ap,fmt);
    return __android_log_vprint(ANDROID_LOG_WARN, "GL_NATIVE_DEMO", fmt, ap);
}

void StartTicks(void)
{
	gettimeofday(&start, NULL);
}

Uint32 GetTicks()
{
	struct timeval now;
	Uint32 ticks;

	gettimeofday(&now, NULL);
	ticks=(now.tv_sec-start.tv_sec)*1000+(now.tv_usec-start.tv_usec)/1000;
	return(ticks);
}

JNIEXPORT void JNICALL Java_com_blogspot_rock88dev_nativegldemo_GLNativeProxy_GL_1Native_1Init(JNIEnv *env, jclass c)
{
	if (running!=0) return;
	running = 1;
	
	a_printf("GL_Native_Init...");
	StartTicks();
	tick = GetTicks();
	importGLInit();
}

JNIEXPORT void JNICALL Java_com_blogspot_rock88dev_nativegldemo_GLNativeProxy_GL_1Native_1Deinit(JNIEnv *env, jclass c)
{
	if (running!=1) return;
	running = 0;
	
	a_printf("GL_Native_Deinit...");
	importGLDeinit();
}

JNIEXPORT void JNICALL Java_com_blogspot_rock88dev_nativegldemo_GLNativeProxy_GL_1Native_1SurfaceChanged(JNIEnv *env, jclass c, jint width, jint height)
{
	if (running!=1) return;
	
	a_printf("GL_Native_SurfaceChanged: %dx%d",width,height);
	glViewport(0, 0, width, height);
	
	float ratio = (float)width / height;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glFrustumf(-ratio, ratio, -1, 1, 2, 14);
	
	glClearColor(0.6f, 0.8f, 1.0f, 1.0f);
}

JNIEXPORT void JNICALL Java_com_blogspot_rock88dev_nativegldemo_GLNativeProxy_GL_1Native_1Draw(JNIEnv *env, jclass c)
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	glFrontFace(GL_CCW);
	glEnable(GL_CULL_FACE);
	glCullFace(GL_BACK);
	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	
	glPushMatrix();
	
	glTranslatef( 0, 0, -4.0f);
	glScalef( 0.5f, 0.5f, 0.5f); 
	glRotatef(angle, 0.1f, 1.0f, -0.1f);
	
	glEnableClientState(GL_VERTEX_ARRAY);
	glVertexPointer(3, GL_FLOAT, 0, vertices);
	
	int face;
	for (face = 0; face < numFaces; face++)
	{
		glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
		glDrawArrays(GL_TRIANGLE_STRIP, face*4, 4);
	}
	glDisableClientState(GL_VERTEX_ARRAY);
	glDisable(GL_CULL_FACE);
	
	glPopMatrix();
	
	angle%=360;
	angle+=2;
	
	fps++;
	
	if ((GetTicks() - tick) > 1000)
	{
		tick = GetTicks();
		a_printf("FPS = %d",fps);
		fps=0;
	}
}
