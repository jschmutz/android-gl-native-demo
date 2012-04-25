LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_OPTIM := release

LOCAL_CFLAGS :=  -O3 -ffast-math -fomit-frame-pointer -DANDROID_NDK -DDISABLE_IMPORTGL
LOCAL_CPPFLAGS := -O3 -frtti -ffast-math -fomit-frame-pointer

LOCAL_ARM_MODE := thumb


LOCAL_MODULE    := glnativedemo
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := 	src/gles_render.c \
					src/importgl.c

LOCAL_LDLIBS    += -lGLESv1_CM -ldl -llog

include $(BUILD_SHARED_LIBRARY)
