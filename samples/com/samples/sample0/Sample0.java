package com.samples.sample0;

import org.lwjgl.opengl.GL11;

import com.jmgl.windowing.Display;

public final class Sample0 {

	public static void main(String[] args)
	{
		Display.setResizable(false);
		Display.setTitle("Sample 0");
		Display.setGLProfile(3, 3, Display.PROFILE_CORE);
		Display.setSize(800, 600);
		Display.enableVSync(true);
		Display.setFullscreen(false);
		
		Display.create(false);
		
		GL11.glClearColor(0.234f, 0.234f, 0.234f, 1.0f);
		GL11.glViewport(0, 0, 800, 600);
		
		while (!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Display.update();
		}
		
		Display.destroy();
	}
}
