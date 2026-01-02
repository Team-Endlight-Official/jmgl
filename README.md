# jModernGL (jmgl)
Modernisation of LWJGL3 by making it feel & look like LWJGL2 but with extra optional control and modern performance.

# 📰 Description
jModernGL or jmgl for short is a thin wrapper for LWJGL3 by making it feel and look like LWJGL2 closely as possible with the possibility of more control. The sleek design principle of LWJGL2 is being held closely to this library so that there is absolute readability for beginners, control and performance over the rendering loop and whatsoever. The API is designed to be intuitive, beginner friendly, programmable and very readable.

# ✨ Features
- Raw access to LWJGL3 OpenGL.
- Moderately abstracted Windowing & Input classes for faster and cleaner prototyping - allows for more control if needed!
- No performance hiccups and legacy brakes - everything is made by leveraging LWJGL3.
- Support for immediate OpenGL and modern OpenGL - allowing to follow LWJGL2 tutorials and principles with a slight difference.

# Roadmap

# Setup (Eclipse)

# Setup (IntelliJ IDEA)

# 📌 Getting started
Here is a sample code that draws a quad with a projection matrix using immediate GL.
```java
package com.endlight.samples;

import org.lwjgl.opengl.GL11;

import com.jmgl.windowing.Display;

public class Sample0
{
	public static void main(String[] args)
	{
		Display.setTitle("Best Title Ever!");
		Display.setSize(800, 600);
		Display.setGLProfile(3, 3, Display.PROFILE_COMPAT);
		Display.setForwardCompat(false);
		Display.setResizable(true);
		Display.enableVSync(true);
		Display.setFullscreen(false);
		
		Display.setResizeListener((width, height) -> 
		{			
			GL11.glViewport(0, 0, width, height);
		});
		
		Display.create(false);
		
		// Some Display methods have to be called after create because on some methods the window must exist for them to take effect.
		Display.setMinimumSize(640, 480);
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glClearColor(0.3f, 0.6f, 0.8f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		while (!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			// Perspective
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			setPerspective(45.0f, Display.getAspectRatio(), 0.1f, 100f);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0, 0, -2);
			
			// MESH
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(1.0f, 0.0f, 0.0f);
			GL11.glVertex2f(-0.5f, -0.5f);
			
			GL11.glColor3f(0.0f, 1.0f, 0.0f);
			GL11.glVertex2f(-0.5f, 0.5f);
			
			GL11.glColor3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex2f(0.5f, 0.5f);
			
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glVertex2f(0.5f, -0.5f);
			
			GL11.glEnd();
			
			Display.update();
		}
		
		Display.destroy();
	}
	
	public static void setPerspective(float fov, float aspect, float near, float far)
	{
		float fH = (float)Math.tan(Math.toRadians(fov / 2)) * near;
		float fW = fH * aspect;
		GL11.glFrustum(-fW, fW, -fH, fH, near, far);
	}
}

```

# ❓ FAQ
