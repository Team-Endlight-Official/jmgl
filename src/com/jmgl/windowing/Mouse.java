package com.jmgl.windowing;

import org.lwjgl.glfw.GLFW;

public final class Mouse
{
	public static final int BUTTONS_ALL = GLFW.GLFW_MOUSE_BUTTON_LAST + 1;
	public static final int BUTTONS_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	public static final int BUTTONS_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
	public static final int BUTTONS_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	
	// Mouse status
	private static boolean exists = false;
	
	private static double x, y;
	private static boolean[] pressed = new boolean[BUTTONS_ALL];
	
	private static void init()
	{
		if (exists) 
		{
			System.err.println("Mouse has already been initialized!");
			return;
		}
		
		Display.setMousePosListener((xPos, yPos) -> 
		{
			x = xPos;
			y = yPos;
		});
		
		Display.setMouseButtonListener((button, action, mods) -> 
		{
			if (action == GLFW.GLFW_PRESS)
			{
				pressed[button] = true;
			}
			else if (action == GLFW.GLFW_RELEASE)
			{
				pressed[button] = false;
			}
		});
		
		exists = true;
	}
	
	private static void checkExists()
	{
		if (!exists) init();
	}
	
	public static void grabCursor(boolean grab)
	{
		checkExists();
		
		GLFW.glfwSetInputMode(Display.ptrWindow(), GLFW.GLFW_CURSOR, grab ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
		if (grab) GLFW.glfwSetCursorPos(Display.ptrWindow(), Display.getWidth() / 2.0f, Display.getHeight() / 2.0f);
	}
	
	public static boolean isButtonDown(int button)
	{
		checkExists();
		return pressed[button];
	}
	
	public static int getX()
	{
		checkExists();
		return (int)x;
	}
	
	public static int getY()
	{
		checkExists();
		return (int)y;
	}
	
	public static double getXRaw()
	{
		checkExists();
		return x;
	}
	
	public static double getYRaw()
	{
		checkExists();
		return y;
	}
}
