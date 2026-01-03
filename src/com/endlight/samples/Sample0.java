package com.endlight.samples;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.jmgl.windowing.Display;
import com.jmgl.windowing.Keyboard;
import com.jmgl.windowing.Mouse;

public class Sample0
{	
	static float moveX = 0f;
	static float moveY = 0f;
	
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
		Mouse.grabCursor(true);
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glClearColor(0.3f, 0.6f, 0.8f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		while (!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Display.setTitle("Best Title Ever! X: " + Mouse.getX() + "; Y: " + Mouse.getY());
			
			if (Mouse.isButtonDown(Mouse.BUTTONS_LEFT))
			{
				System.out.println("Click!");
			}
			
			moveX += 0.05f * Keyboard.getKeyAxis(GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D);
			moveY += 0.05f * Keyboard.getKeyAxis(GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W);
			
			// Perspective
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			setPerspective(45.0f, Display.getAspectRatio(), 0.1f, 100f);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(moveX, moveY, -6);
			
			// MESH
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(1.0f, 0.5f, 0.5f);
			GL11.glVertex2f(-0.5f, -0.5f);
			
			GL11.glColor3f(0.5f, 1.0f, 0.5f);
			GL11.glVertex2f(-0.5f, 0.5f);
			
			GL11.glColor3f(0.5f, 0.5f, 1.0f);
			GL11.glVertex2f(0.5f, 0.5f);
			
			GL11.glColor3f(0.5f, 0.5f, 1.0f);
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
